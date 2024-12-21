import React, { useState } from 'react';
import { Formik, Form } from 'formik';
import {
  Box,
  CircularProgress,
  FormControl,
  FormLabel,
  FormControlLabel,
  Select,
  Radio,
  RadioGroup,
  MenuItem,
  TextField,
  Button,
  Dialog,
  Divider,
  DialogActions,
  DialogContent,
  useMediaQuery,
  InputLabel,
  List,
  ListItem,
  ListItemText,
  IconButton,
  Typography,
  Tooltip,
} from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { DateTimePicker, TimePicker } from '@mui/x-date-pickers';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { renderTimeViewClock } from '@mui/x-date-pickers/timeViewRenderers';

import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFnsV3';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import WorkoutFormTitle from './WorkoutFormTitle';
import ExerciseInfo from '@/components/WorkoutCard/ExerciseInfo';
import { schema, strengthExerciseSchema, cardioExerciseSchema } from './schema';

const cardioExercises = ['Running', 'Cycling', 'Swimming'];
const strengthExercises = ['Weightlifting', 'Push-ups', 'Squats'];

export default function WorkoutForm({
  dialogTitle,
  popupType,
  popupStatus,
  togglePopup,
  workout = {},
}) {
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));
  const [exerciseList, setExerciseList] = useState([]);

  const handleAddWorkout = (values, actions) => {
    actions.setSubmitting(true);
    setTimeout(() => {
      alert(JSON.stringify({ ...values, exercises: exerciseList }, null, 2));
      setExerciseList([]);
      actions.setSubmitting(false);
      handleClose();
    }, 1000);
  };

  const handleClose = () => {
    togglePopup(false);
  };

  return (
    <Dialog
      fullScreen={fullScreen}
      maxWidth="md"
      fullWidth
      open={popupStatus}
      aria-labelledby="new-workout-dialog"
    >
      <WorkoutFormTitle id="new-workout-dialog" onClose={handleClose}>
        {dialogTitle}
      </WorkoutFormTitle>
      <Formik
        initialValues={
          popupType === 'new'
            ? {
                startDate: null,
                endDate: null,
                description: '',
                exerciseType: '',
                exercise: '',
                sets: '',
                weight: '',
                duration: null,
              }
            : {
                startDate: new Date(workout.startDate),
                endDate: new Date(workout.endDate),
                description: workout.description,
                exerciseType: workout.exerciseType || '',
                exercise: workout.exercise || '',
                sets: '',
                weight: '',
                duration: null,
              }
        }
        onSubmit={(values, actions) => handleAddWorkout(values, actions)}
        validationSchema={schema}
      >
        {({
          values,
          errors,
          touched,
          handleBlur,
          handleChange,
          setFieldValue,
          isSubmitting,
          setErrors,
        }) => (
          <Form>
            <DialogContent sx={{ p: 3 }}>
              <Box sx={{ display: 'flex', gap: 2 }}>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                  <DateTimePicker
                    viewRenderers={{
                      hours: renderTimeViewClock,
                      minutes: renderTimeViewClock,
                      seconds: renderTimeViewClock,
                    }}
                    name="startDate"
                    value={values.startDate}
                    maxDate={values.endDate || undefined}
                    onChange={(newValue) => {
                      setFieldValue('startDate', newValue);
                    }}
                    onBlur={handleBlur}
                    label={
                      !!errors.startDate ? `${errors.startDate}` : 'Start Date'
                    }
                    slotProps={{
                      textField: {
                        error: !!errors.startDate,
                      },
                    }}
                  />
                  <DateTimePicker
                    viewRenderers={{
                      hours: renderTimeViewClock,
                      minutes: renderTimeViewClock,
                      seconds: renderTimeViewClock,
                    }}
                    name="endDate"
                    value={values.endDate}
                    minDate={values.startDate || undefined}
                    onChange={(newValue) => {
                      setFieldValue('endDate', newValue);
                    }}
                    onBlur={handleBlur}
                    label={!!errors.endDate ? `${errors.endDate}` : 'End Date'}
                    slotProps={{
                      textField: {
                        error: !!errors.endDate,
                      },
                    }}
                  />
                </LocalizationProvider>
              </Box>
              <TextField
                id="description"
                label={
                  !!errors.description ? `${errors.description}` : 'Description'
                }
                name="description"
                error={!!errors.description}
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.description}
                fullWidth
                multiline
                maxRows={4}
                sx={{ mt: 2 }}
              />

              <FormControl sx={{ mt: 4 }} fullWidth>
                <FormLabel error={!!errors.exerciseType}>
                  {errors.exerciseType || 'Exercise Type'}
                </FormLabel>
                <RadioGroup
                  row
                  name="exerciseType"
                  value={values.exerciseType}
                  onChange={(e) => {
                    setFieldValue('exerciseType', e.target.value);
                    setFieldValue('exercise', '');
                    setFieldValue('sets', '');
                    setFieldValue('weight', '');
                    setFieldValue('duration', null);
                  }}
                >
                  <FormControlLabel
                    value="strength"
                    control={<Radio />}
                    label="Strength"
                  />
                  <FormControlLabel
                    value="cardio"
                    control={<Radio />}
                    label="Cardio"
                  />
                </RadioGroup>
              </FormControl>

              <FormControl fullWidth sx={{ mt: 2 }} error={!!errors.exercise}>
                <InputLabel id="exerciseSelect">
                  {errors.exercise || 'Exercise'}
                </InputLabel>
                <Select
                  labelId="exerciseSelect"
                  name="exercise"
                  label={errors.exercise || 'Exercise'}
                  value={values.exercise}
                  onChange={handleChange}
                  disabled={!values.exerciseType}
                  onBlur={handleBlur}
                >
                  {(values.exerciseType === 'cardio'
                    ? cardioExercises
                    : strengthExercises
                  ).map((exercise) => (
                    <MenuItem key={exercise} value={exercise}>
                      {exercise}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>

              {values.exerciseType === 'strength' && (
                <Box sx={{ display: 'flex', gap: 2, mt: 2 }}>
                  <TextField
                    label={errors.sets || 'Sets'}
                    name="sets"
                    type="number"
                    value={values.sets}
                    onBlur={handleBlur}
                    error={!!errors.sets}
                    onChange={(e) => {
                      const value = e.target.value;
                      if (!value || parseInt(value, 10) >= 0) {
                        handleChange(e);
                      }
                    }}
                    slotProps={{
                      input: {
                        min: 0,
                        onKeyDown: (e) => {
                          if (e.key === '-' || e.key === 'e') {
                            e.preventDefault();
                          }
                        },
                      },
                    }}
                  />
                  <TextField
                    label={errors.weight || 'Weight (kg)'}
                    name="weight"
                    type="number"
                    value={values.weight}
                    onBlur={handleBlur}
                    error={!!errors.weight}
                    onChange={(e) => {
                      const value = e.target.value;
                      if (!value || parseInt(value, 10) >= 0) {
                        handleChange(e);
                      }
                    }}
                    slotProps={{
                      input: {
                        min: 0,
                        onKeyDown: (e) => {
                          if (e.key === '-' || e.key === 'e') {
                            e.preventDefault();
                          }
                        },
                      },
                    }}
                  />
                </Box>
              )}

              {values.exerciseType === 'cardio' && (
                <Box sx={{ mt: 2 }}>
                  <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <TimePicker
                      label="Duration"
                      value={values.duration}
                      onChange={(newValue) =>
                        setFieldValue('duration', newValue)
                      }
                      renderInput={(params) => (
                        <TextField
                          {...params}
                          error={!!errors.duration && touched.duration}
                          helperText={touched.duration && errors.duration}
                        />
                      )}
                    />
                  </LocalizationProvider>
                </Box>
              )}

              <Button
                sx={{ mt: 2 }}
                variant="contained"
                color="secondary"
                onClick={() => {
                  const newExercise = {
                    exerciseType: values.exerciseType,
                    exercise: values.exercise,
                    sets: String(values.sets) || null,
                    weight: String(values.weight) || null,
                    duration: values.duration,
                  };

                  const exerciseSchema =
                    values.exerciseType === 'strength'
                      ? strengthExerciseSchema
                      : cardioExerciseSchema;
                  exerciseSchema
                    .validate(newExercise, { abortEarly: false })
                    .then(() => {
                      setExerciseList((prev) => [...prev, newExercise]);
                      setFieldValue('exerciseType', '');
                      setFieldValue('exercise', '');
                      setFieldValue('sets', '');
                      setFieldValue('weight', '');
                      setFieldValue('duration', null);
                      setErrors({});
                    })
                    .catch((validationErrors) => {
                      const errors = validationErrors.inner.reduce(
                        (acc, err) => ({
                          ...acc,
                          [err.path]: err.message,
                        }),
                        {}
                      );
                      setErrors(errors);
                    });
                }}
              >
                Add Exercise
              </Button>

              {exerciseList.length > 0 && (
                <Box>
                  <Divider sx={{ mt: 2, mb: 2 }} />
                  {exerciseList.map((exercise, index) => (
                    <Box
                      key={index}
                      sx={{
                        display: 'flex',
                        alignItems: 'baseline',
                        justifyContent: 'space-between',
                        mb: 2,
                      }}
                    >
                      <Box sx={{ flexGrow: 1 }}>
                        <ExerciseInfo
                          activity={{
                            exerciseName: exercise.exercise,
                            duration:
                              exercise.duration?.toISOString() || '00:00:00',
                            reps: exercise.sets || 0,
                            weight: exercise.weight || 0,
                          }}
                        />
                      </Box>
                      <Tooltip title="Delete exercise">
                        <IconButton
                          edge="end"
                          color="error"
                          sx={{ mr: 1 }}
                          onClick={() =>
                            setExerciseList((prev) =>
                              prev.filter((_, i) => i !== index)
                            )
                          }
                        >
                          <DeleteIcon />
                        </IconButton>
                      </Tooltip>
                    </Box>
                  ))}
                </Box>
              )}
            </DialogContent>
            <DialogActions sx={{ p: 2 }}>
              <Button onClick={handleClose} color="error">
                Cancel
              </Button>
              <Button
                type="submit"
                variant="contained"
                disabled={isSubmitting}
                startIcon={popupType === 'new' ? <AddIcon /> : <EditIcon />}
                endIcon={
                  isSubmitting && (
                    <CircularProgress color="secondary" size={18} />
                  )
                }
              >
                {popupType === 'new' ? 'Add Workout' : 'Edit Workout'}
              </Button>
            </DialogActions>
          </Form>
        )}
      </Formik>
    </Dialog>
  );
}
