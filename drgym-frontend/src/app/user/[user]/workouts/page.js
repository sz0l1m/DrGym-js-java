'use client';

import { Box, Button, Typography } from '@mui/material';
import { useEffect, useState, useCallback } from 'react';
import axios from 'axios';
import WorkoutCard from '@/components/WorkoutCard';
import AddIcon from '@mui/icons-material/Add';
import WorkoutForm from '@/components/WorkoutForm';
import { withSnackbar } from '@/utils/snackbarProvider';
import SkeletonCard from '@/components/SkeletonCard';
import Grid from '@mui/material/Grid2';
import { useSession } from 'next-auth/react';

const Workouts = ({ showAppMessage }) => {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [workoutsData, setWorkoutsData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { data: session, status } = useSession();
  const username = session?.user?.username;

  const fetchWorkouts = useCallback(async () => {
    try {
      setLoading(true);
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_API_URL}/api/users/szolim/workouts`,
        {
          withCredentials: true,
        }
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
  }, [showAppMessage]);

  useEffect(() => {
    fetchWorkouts();
  }, [fetchWorkouts]);

  const handleDeleteWorkout = (workoutId) => {
    setWorkoutsData((prev) =>
      prev.filter((workout) => workout.workoutId !== workoutId)
    );
  };

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
      <Grid container direction="column" alignItems="center">
        {loading ? (
          Array.from({ length: 3 }).map((_, index) => (
            <SkeletonCard key={index} />
          ))
        ) : workoutsData.length === 0 ? (
          <Typography variant="body1">
            You have not added any workouts yet.
          </Typography>
        ) : (
          workoutsData.map((workout) => (
            <WorkoutCard
              key={workout.workoutId}
              workout={workout}
              onDelete={handleDeleteWorkout}
              showAppMessage={showAppMessage}
            />
          ))
        )}
      </Grid>
      <WorkoutForm
        dialogTitle="Add new workout"
        popupType="new"
        popupStatus={dialogOpen}
        togglePopup={setDialogOpen}
        showAppMessage={showAppMessage}
        onAddWorkout={fetchWorkouts}
      />
    </>
  );
};

export default withSnackbar(Workouts);
