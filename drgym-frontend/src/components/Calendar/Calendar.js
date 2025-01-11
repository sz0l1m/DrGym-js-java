import { useState, useEffect } from 'react';
import { ActivityCalendar } from 'react-activity-calendar';
import { Tooltip, Typography } from '@mui/material';
import { calendarData as mockData } from '@/utils/mockData';
import axios from 'axios';

const Calendar = ({ username }) => {
  const [calendarData, setCalendarData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const calendarTheme = {
    light: ['#ebedf0', '#bbdefb', '#64b5f6', '#1976d2', '#0d47a1'],
    dark: ['#ebedf0', '#bbdefb', '#64b5f6', '#1976d2', '#0d47a1'],
    //   Purple ['#ebedf0', '#d1c4e9', '#9575cd', '#512da8', '#311b92'],
    //   Green ['#ebedf0', '#c6e48b', '#7bc96f', '#239a3b', '#196127'],
  };

  useEffect(() => {
    const fetchCalendarData = async () => {
      try {
        setLoading(true);
        const response = await axios.get(
          `${process.env.NEXT_PUBLIC_API_URL}/api/users/mandrysz/daily-exercise-count`,
          {
            withCredentials: true,
          }
        );
        setCalendarData(
          setMinCalendarRange(response.data, '2024-01-01', '2024-12-31')
        );
      } catch (error) {
        setError('Failed to load calendar data');
      } finally {
        setLoading(false);
      }
    };

    fetchCalendarData();
  }, [username]);

  const setMinCalendarRange = (data, startDate, endDate) => {
    const dataMap = data.reduce((acc, item) => {
      acc[item.date] = item;
      return acc;
    }, {});

    if (!dataMap[startDate]) {
      data.push({ date: startDate, count: 0, level: 0 });
    }

    if (!dataMap[endDate]) {
      data.push({ date: endDate, count: 0, level: 0 });
    }

    return data;
  };

  const processedCalendarData = setMinCalendarRange(
    [...calendarData],
    '2024-01-01',
    '2024-12-31'
  );

  if (error) return <Typography>{error}</Typography>;
  return (
    <>
      <ActivityCalendar
        data={processedCalendarData}
        theme={calendarTheme}
        maxLevel={4}
        loading={loading}
        weekStart={1}
        renderBlock={(block, activity) => (
          <Tooltip
            key={activity.date}
            title={`${activity.count} exercises on ${activity.date}`}
          >
            {block}
          </Tooltip>
        )}
        renderColorLegend={(block, level) => (
          <Tooltip key={`legend-${level}`} title={`Level ${level}`}>
            {block}
          </Tooltip>
        )}
      />
    </>
  );
};

export default Calendar;
