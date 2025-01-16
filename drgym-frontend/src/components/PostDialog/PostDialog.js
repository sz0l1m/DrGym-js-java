import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  Button,
  Box,
  CircularProgress,
  Typography,
  Divider,
  Checkbox,
  IconButton,
} from '@mui/material';
import Grid from '@mui/material/Grid2';
import CloseIcon from '@mui/icons-material/Close';
import { Formik, Form } from 'formik';
import axiosInstance from '@/utils/axiosInstance';
import WorkoutCard from '@/components/WorkoutCard';
import { getUsername } from '@/utils/localStorage';
import CustomInput from '@/components/CustomInput';
import { PostSchema, PostDefaultValues } from '@/utils/schemas/PostSchema';

export default function PostDialog({
  open,
  onClose,
  onSubmit,
  showAppMessage,
}) {
  const [workouts, setWorkouts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedWorkout, setSelectedWorkout] = useState(null);

  const username = getUsername();

  useEffect(() => {
    const fetchWorkouts = async () => {
      try {
        setLoading(true);
        const response = await axiosInstance.get(
          `/api/users/${username}/workouts`
        );
        setWorkouts(response.data);
      } catch (error) {
        console.error('Error fetching workouts:', error);
        showAppMessage({
          status: true,
          text: 'Error fetching workouts',
          type: 'error',
        });
        onClose();
      } finally {
        setLoading(false);
      }
    };
    if (open) {
      setSelectedWorkout(null);
      fetchWorkouts();
    }
  }, [open, username, showAppMessage, onClose]);

  const handleWorkoutSelection = (workout) => {
    if (selectedWorkout) {
      setWorkouts((prev) => [selectedWorkout, ...prev]);
    }
    if (selectedWorkout?.id === workout.id) {
      setSelectedWorkout(null);
    } else {
      setSelectedWorkout(workout);
      setWorkouts((prev) => prev.filter((w) => w.id !== workout.id));
    }
  };

  const getWorkoutMessage = () => {
    if (loading) return 'Loading workouts...';
    if (!selectedWorkout && workouts.length === 0)
      return 'You don’t have any workouts. Please create one to add.';
    if (selectedWorkout && workouts.length === 0)
      return 'There are no other workouts available.';
    if (selectedWorkout)
      return 'Change your workout by selecting a different one from the list below.';
    return 'Select a workout to include in your post.';
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
      <Formik
        validationSchema={PostSchema}
        initialValues={PostDefaultValues}
        onSubmit={(values, { setSubmitting }) => {
          onSubmit({ ...values, workout: selectedWorkout });
          setSubmitting(false);
          onClose();
        }}
      >
        {({
          values,
          errors,
          touched,
          handleChange,
          handleBlur,
          isSubmitting,
        }) => (
          <Form>
            <DialogTitle>
              <Box
                sx={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  alignItems: 'center',
                }}
              >
                <Typography variant="h6">Add New Post</Typography>
                <IconButton onClick={onClose}>
                  <CloseIcon />
                </IconButton>
              </Box>
            </DialogTitle>
            <DialogContent dividers>
              <Grid container direction="column" spacing={2}>
                <Box sx={{ display: 'flex', justifyContent: 'flex-start' }}>
                  <Button
                    type="submit"
                    variant="contained"
                    disabled={isSubmitting || !selectedWorkout}
                  >
                    {isSubmitting ? <CircularProgress size={24} /> : 'Add Post'}
                  </Button>
                </Box>
                <CustomInput
                  label="Title"
                  name="title"
                  value={values.title}
                  error={errors.title}
                  touched={touched.title}
                  onBlur={handleBlur}
                  onChange={handleChange}
                />
                <CustomInput
                  label="Description"
                  name="description"
                  value={values.description}
                  error={errors.description}
                  touched={touched.description}
                  onBlur={handleBlur}
                  onChange={handleChange}
                  multiline
                  rows={4}
                />
              </Grid>
              {selectedWorkout && (
                <Box sx={{ mt: 2 }}>
                  <Typography variant="h6" gutterBottom sx={{ mb: 0 }}>
                    Selected Workout
                  </Typography>
                  <Checkbox
                    checked
                    onChange={() => handleWorkoutSelection(selectedWorkout)}
                    sx={{ pb: 0 }}
                  />
                  <WorkoutCard workout={selectedWorkout} disableActions />
                </Box>
              )}
              <Divider sx={{ mt: 2 }} />
              <Box sx={{ mt: 2 }}>
                <Typography variant="body1" gutterBottom>
                  {getWorkoutMessage()}
                </Typography>
                {!loading &&
                  workouts.map((workout) => (
                    <Box key={workout.id} sx={{ mb: 2 }}>
                      <Checkbox
                        checked={selectedWorkout?.id === workout.id}
                        onChange={() => handleWorkoutSelection(workout)}
                        sx={{ pb: 0 }}
                      />
                      <WorkoutCard workout={workout} disableActions />
                      <Divider sx={{ mt: 2 }} />
                    </Box>
                  ))}
              </Box>
            </DialogContent>
          </Form>
        )}
      </Formik>
    </Dialog>
  );
}
