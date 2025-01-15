import { useState } from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import WorkoutInfo from '@/components/WorkoutInfo';
import { formatRelativeTime } from '@/utils/dateUtils';
import UserHeader from '@/components/UserHeader';

export default function Post({ post }) {
  const workout = post.training;
  const realitveStartDate = formatRelativeTime(workout.startDate);

  return (
    <>
      <Box sx={{ width: '100%', maxWidth: '1000px', margin: '0 auto', py: 2 }}>
        <Card sx={{ maxWidth: '100%' }}>
          <UserHeader
            username={workout.username}
            subheader={
              realitveStartDate.charAt(0).toUpperCase() +
              realitveStartDate.slice(1)
            }
          />
          <WorkoutInfo workout={workout} isPost />
        </Card>
      </Box>
    </>
  );
}
