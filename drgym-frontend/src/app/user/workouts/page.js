'use client';

import { Box, Typography } from '@mui/material';
import WorkoutCard from '@/components/WorkoutCard';
import style from './workouts.module.css';

let workoutsData = [
  {
    workout_id: 12345,
    start_date: '2024-11-01T17:41',
    end_date: '2024-12-02T20:41',
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
        duration: '1:00:00',
      },
    ],
  },
  {
    workout_id: 67890,
    start_date: '2024-12-02T20:35',
    end_date: '2024-12-02T20:41',
    description: 'Leg day workout!',
    activities: [
      {
        activity_id: 4,
        activity_name: 'Lunges',
        weight: 50,
        sets: 3,
      },
    ],
  },
  {
    workout_id: 67891,
    start_date: '2024-12-02T17:41',
    end_date: '2024-12-02T20:41',
    activities: [
      {
        activity_id: 3,
        activity_name: 'Squats',
        duration: '0:20:15',
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
