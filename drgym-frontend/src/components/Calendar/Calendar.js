import React from 'react';
import { ActivityCalendar } from 'react-activity-calendar';
import { Tooltip } from '@mui/material';
import { calendarData } from '@/utils/mockData';

const Calendar = ({ username }) => {
  const calendarTheme = {
    light: ['#ebedf0', '#bbdefb', '#64b5f6', '#1976d2', '#0d47a1'],
    dark: ['#ebedf0', '#bbdefb', '#64b5f6', '#1976d2', '#0d47a1'],
    //   Purple ['#ebedf0', '#d1c4e9', '#9575cd', '#512da8', '#311b92'],
    //   Green ['#ebedf0', '#c6e48b', '#7bc96f', '#239a3b', '#196127'],
  };

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

  return (
    <>
      <ActivityCalendar
        data={processedCalendarData}
        theme={calendarTheme}
        maxLevel={4}
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
