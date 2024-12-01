'use client';

import { Box, Typography } from '@mui/material';
import WorkoutCard from '@/components/WorkoutCard';
import style from './workouts.module.css';

export default function HomePage() {
  return (
    <>
      <Typography variant="h5" gutterBottom>
        My Workouts
      </Typography>
      <Box className={style.workoutsWrapper}>
        <WorkoutCard />
        <WorkoutCard />
        <WorkoutCard />
        <WorkoutCard />
        <WorkoutCard />
      </Box>
    </>
  );
}
