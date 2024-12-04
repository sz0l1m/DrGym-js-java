'use client';

import { Box, Button, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import axios from 'axios';
import WorkoutCard from '@/components/WorkoutCard';
import AddIcon from '@mui/icons-material/Add';
import DialogBox from '@/components/DialogBox';
import style from './workouts.module.css';

// let workoutsData = [
//   {
//     workoutId: 12345,
//     startDate: '2024-11-01T17:41',
//     endDate: '2024-11-02T20:41',
//     description: 'This is a workout description.',
//     activities: [
//       {
//         activityId: 1,
//         activity_name: 'Lat Pulldown',
//         weight: 100,
//         reps: 4,
//       },
//       {
//         activityId: 2,
//         activity_name: 'Treadmill',
//         duration: '1:00:00',
//       },
//     ],
//   },
//   {
//     workoutId: 67890,
//     startDate: '2024-12-02T20:35',
//     endDate: '2024-12-02T20:41',
//     description: 'Leg day workout!',
//     activities: [
//       {
//         activityId: 4,
//         activity_name: 'Lunges',
//         weight: 50,
//         reps: 3,
//       },
//     ],
//   },
//   {
//     workoutId: 67891,
//     startDate: '2024-12-02T17:41',
//     endDate: '2024-12-02T20:41',
//     activities: [
//       {
//         activityId: 3,
//         activity_name: 'Squats',
//         duration: '0:20:15',
//       },
//     ],
//   },
// ];

export default function HomePage() {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [workoutsData, setWorkoutsData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchWorkouts = async () => {
      try {
        setLoading(true);
        const response = await axios.get(
          'http://localhost:8080/api/workouts/3'
        );
        setWorkoutsData(response.data);
      } catch (err) {
        console.error('Error fetching workouts:', err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchWorkouts();
  }, []);

  const handleDeleteWorkout = (workoutId) => {
    setWorkoutsData((prev) =>
      prev.filter((workout) => workout.workoutId !== workoutId)
    );
  };

  if (loading) return <Typography>Loading workouts...</Typography>;
  if (error) return <Typography>Error: {error}</Typography>;
  return (
    <>
      <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant="h5" gutterBottom>
          My Workouts
        </Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => setDialogOpen(true)}
        >
          Add workout
        </Button>
      </Box>
      <Box className={style.workoutsWrapper}>
        {/* {workoutsData.map((workout) => (
          <WorkoutCard
            key={workout.workoutId}
            workout={workout}
            onDelete={handleDeleteWorkout}
          />
        ))} */}
        <WorkoutCard
          key={workoutsData.workoutId}
          workout={workoutsData}
          onDelete={handleDeleteWorkout}
        />
      </Box>
      <DialogBox
        dialogTitle="Add new workout"
        popupType="new"
        popupStatus={dialogOpen}
        togglePopup={setDialogOpen}
      />
    </>
  );
}
