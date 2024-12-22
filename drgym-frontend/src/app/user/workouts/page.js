'use client';

import { Box, Button, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import axios from 'axios';
import WorkoutCard from '@/components/WorkoutCard';
import AddIcon from '@mui/icons-material/Add';
import WorkoutForm from '@/components/WorkoutForm';
import { withSnackbar } from '@/utils/snackbarProvider';
import style from './workouts.module.css';

const Workouts = ({ showAppMessage }) => {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [workoutsData, setWorkoutsData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchWorkouts = async () => {
      try {
        setLoading(true);
        const response = await axios.get(
          'http://localhost:8080/api/users/skuter/workouts'
        );
        setWorkoutsData(response.data);
      } catch (err) {
        setError(err.message);
        showAppMessage({
          status: true,
          text: 'Error fetching workouts',
          type: 'error',
        });
      } finally {
        setLoading(false);
      }
    };

    fetchWorkouts();
  }, [showAppMessage]);

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
        {workoutsData.map((workout) => (
          <WorkoutCard
            key={workout.workoutId}
            workout={workout}
            onDelete={handleDeleteWorkout}
            showAppMessage={showAppMessage}
          />
        ))}
      </Box>
      <WorkoutForm
        dialogTitle="Add new workout"
        popupType="new"
        popupStatus={dialogOpen}
        togglePopup={setDialogOpen}
        showAppMessage={showAppMessage}
      />
    </>
  );
};

export default withSnackbar(Workouts);
