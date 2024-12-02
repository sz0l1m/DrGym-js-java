'use client';

import { Box, Typography } from '@mui/material';
import WorkoutCard from '@/components/WorkoutCard';
import style from './workouts.module.css';

let workoutsData = [
  {
    workout_id: 12345,
    start_date: '2022-01-01',
    end_date: '2024-12-12',
    description: 'This is a workout description.',
    activities: [
      {
        activity_id: 1,
        activity_name: 'Lat Pulldown',
        weight: 100,
        sets: 4,
      },
      {
        activity_id: 2,
        activity_name: 'Treadmill',
        weight: 30,
        sets: 20,
      },
    ],
  },
  {
    workout_id: 67890,
    start_date: '2023-03-10',
    end_date: '2023-03-10',
    description: 'Leg day workout!',
    activities: [
      {
        activity_id: 3,
        activity_name: 'Squats',
        weight: 100,
        sets: 5,
      },
      {
        activity_id: 4,
        activity_name: 'Lunges',
        weight: 50,
        sets: 3,
      },
    ],
  },
];

export default function HomePage() {
  return (
    <>
      <Typography variant="h5" gutterBottom>
        My Workouts
      </Typography>
      <Box className={style.workoutsWrapper}>
        {workoutsData.map((workout) => (
          <WorkoutCard key={workout.workout_id} workout={workout} />
        ))}
      </Box>
    </>
  );
}
