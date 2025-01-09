import React from 'react';
import Typography from '@mui/material/Typography';
import { ActivityCalendar } from 'react-activity-calendar';

const activityData = [
  { date: '2024-01-01', count: 2, level: 1 },
  { date: '2024-01-10', count: 6, level: 2 },
  { date: '2024-02-05', count: 4, level: 3 },
  { date: '2024-03-15', count: 10, level: 4 },
];

const Stats = ({ params }) => {
  const { user } = React.use(params);

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

  const processedData = setMinCalendarRange(
    [...activityData],
    '2024-01-01',
    '2024-12-31'
  );

  return (
    <>
      <Typography variant="h4" gutterBottom>
        Stats for {user}
      </Typography>
      <ActivityCalendar
        data={processedData}
        labels={{
          legend: {
            less: 'Less',
            more: 'More',
          },
          tooltip: '<strong>{{count}} activities</strong> on {{date}}',
        }}
      />
    </>
  );
};

export default Stats;
