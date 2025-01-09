'use client';

import React from 'react';
import Typography from '@mui/material/Typography';
import { ActivityCalendar } from 'react-activity-calendar';
import { Tooltip } from '@mui/material';
import { calendarTheme } from './themes';

const activityData = [
  { date: '2024-01-01', count: 2, level: 1 },
  { date: '2024-01-10', count: 6, level: 2 },
  { date: '2024-02-05', count: 4, level: 2 },
  { date: '2024-03-15', count: 10, level: 3 },
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

export default Stats;
