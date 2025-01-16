import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Box,
  CircularProgress,
  Typography,
  FormControl,
  FormControlLabel,
  Checkbox,
} from '@mui/material';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import axiosInstance from '@/utils/axiosInstance';
import WorkoutCard from '@/components/WorkoutCard';
import { getUsername } from '@/utils/localStorage';

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
      fetchWorkouts();
    }
  }, [open, username, showAppMessage, onClose]);

  const validationSchema = Yup.object().shape({
    title: Yup.string().required('Title is required'),
    description: Yup.string().required('Description is required'),
  });

  const handleWorkoutSelection = (workout) => {
    setSelectedWorkout((prev) => (prev?.id === workout.id ? null : workout));
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
      <DialogTitle>Add New Post</DialogTitle>
      <Formik
        initialValues={{
          title: '',
          description: '',
        }}
        validationSchema={validationSchema}
        onSubmit={(values, { setSubmitting }) => {
          onSubmit({ ...values, workout: selectedWorkout });
          setSubmitting(false);
          onClose();
        }}
      >
        {({ values, errors, handleChange, handleBlur, isSubmitting }) => (
          <Form>
            <DialogContent dividers>
              <TextField
                fullWidth
                label="Title"
                name="title"
                value={values.title}
                onChange={handleChange}
                onBlur={handleBlur}
                error={!!errors.title}
                helperText={errors.title}
                margin="normal"
              />
              <TextField
                fullWidth
                label="Description"
                name="description"
                value={values.description}
                onChange={handleChange}
                onBlur={handleBlur}
                error={!!errors.description}
                helperText={errors.description}
                margin="normal"
                multiline
                rows={4}
              />
              <Box sx={{ mt: 2 }}>
                <Typography variant="h6" gutterBottom>
                  Select a Workout
                </Typography>
                {loading ? (
                  <CircularProgress />
                ) : (
                  <Box>
                    {workouts.map((workout) => (
                      <Box key={workout.id} sx={{ mb: 2 }}>
                        <WorkoutCard
                          workout={workout}
                          onDelete={() => {}}
                          onEditWorkout={() => {}}
                          showAppMessage={() => {}}
                        />
                        <FormControlLabel
                          control={
                            <Checkbox
                              checked={selectedWorkout?.id === workout.id}
                              onChange={() => handleWorkoutSelection(workout)}
                            />
                          }
                          label="Select this workout"
                        />
                      </Box>
                    ))}
                  </Box>
                )}
              </Box>
            </DialogContent>
            <DialogActions>
              <Button onClick={onClose} color="error">
                Cancel
              </Button>
              <Button
                type="submit"
                variant="contained"
                disabled={isSubmitting || !selectedWorkout}
              >
                {isSubmitting ? <CircularProgress size={24} /> : 'Add Post'}
              </Button>
            </DialogActions>
          </Form>
        )}
      </Formik>
    </Dialog>
  );
}
