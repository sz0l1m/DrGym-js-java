'use client';

import React from 'react';
import Typography from '@mui/material/Typography';
import { ActivityCalendar } from 'react-activity-calendar';
import { Divider, Tooltip } from '@mui/material';
import { calendarTheme } from './themes';
import { calendarData } from '@/utils/mockData';

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

  const processedCalendarData = setMinCalendarRange(
    [...calendarData],
    '2024-01-01',
    '2024-12-31'
  );

  return (
    <>
      <Typography sx={{ mb: 3 }} variant="h6" gutterBottom>
        Your workout calendar
      </Typography>
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

export default Stats;
