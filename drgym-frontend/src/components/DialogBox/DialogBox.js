import React from 'react';
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
  DialogActions,
  DialogContent,
  useMediaQuery,
} from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { renderTimeViewClock } from '@mui/x-date-pickers/timeViewRenderers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFnsV3';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DialogBoxTitle from './DialogBoxTitle';
import * as yup from 'yup';

const cardioExercises = ['Running', 'Cycling', 'Swimming'];
const strengthExercises = ['Weightlifting', 'Push-ups', 'Squats'];

const schema = yup.object().shape({
  startDate: yup
    .date()
    .required('Start date is required')
    .typeError('Invalid date'),
  endDate: yup
    .date()
    .required('End date is required')
    .typeError('Invalid date'),
  description: yup.string().max(50, 'Description is too long (max 50 chars)'),
  exerciseType: yup.string().required('Exercise type is required'),
  exercise: yup.string().required('Exercise is required'),
});

export default function DialogBox({
  dialogTitle,
  popupType,
  popupStatus,
  togglePopup,
  workout = {},
}) {
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

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
      <DialogBoxTitle id="new-workout-dialog" onClose={handleClose}>
        {dialogTitle}
      </DialogBoxTitle>
      <Formik
        initialValues={
          popupType === 'new'
            ? {
                startDate: null,
                endDate: null,
                description: '',
                exerciseType: '',
                exercise: '',
              }
            : {
                startDate: new Date(workout.startDate),
                endDate: new Date(workout.endDate),
                description: workout.description,
                exerciseType: workout.exerciseType || '',
                exercise: workout.exercise || '',
              }
        }
        onSubmit={(values, actions) => {
          actions.setSubmitting(true);
          setTimeout(() => {
            alert(JSON.stringify(values, null, 2));
            actions.setSubmitting(false);
            handleClose();
          }, 1000);
        }}
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
        }) => (
          <Form>
            <DialogContent sx={{ p: 2 }}>
              <Box sx={{ display: 'flex', gap: 2 }}>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                  <DateTimePicker
                    viewRenderers={{
                      hours: renderTimeViewClock,
                      minutes: renderTimeViewClock,
                      seconds: renderTimeViewClock,
                    }}
                    label="Start Date"
                    value={values.startDate}
                    onChange={(newValue) => {
                      setFieldValue('startDate', newValue);
                    }}
                    onBlur={handleBlur}
                  />
                  <DateTimePicker
                    viewRenderers={{
                      hours: renderTimeViewClock,
                      minutes: renderTimeViewClock,
                      seconds: renderTimeViewClock,
                    }}
                    label="End Date"
                    value={values.endDate}
                    onChange={(newValue) => {
                      setFieldValue('endDate', newValue);
                    }}
                    onBlur={handleBlur}
                  />
                </LocalizationProvider>
              </Box>
              <TextField
                id="description"
                label={
                  !!errors.description
                    ? `Description ${errors.description}`
                    : 'Description'
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

              <FormControl sx={{ mt: 2 }} fullWidth>
                <FormLabel>Exercise Type</FormLabel>
                <RadioGroup
                  row
                  name="exerciseType"
                  value={values.exerciseType}
                  onChange={(e) => {
                    setFieldValue('exerciseType', e.target.value);
                    setFieldValue('exercise', ''); // Reset exercise when type changes
                  }}
                >
                  <FormControlLabel
                    value="cardio"
                    control={<Radio />}
                    label="Cardio"
                  />
                  <FormControlLabel
                    value="strength"
                    control={<Radio />}
                    label="Strength"
                  />
                </RadioGroup>
                {errors.exerciseType && touched.exerciseType && (
                  <Box color="error.main">{errors.exerciseType}</Box>
                )}
              </FormControl>
              <FormControl
                sx={{ mt: 2 }}
                fullWidth
                disabled={!values.exerciseType}
              >
                <FormLabel>Exercise</FormLabel>
                <Select
                  name="exercise"
                  value={values.exercise}
                  onChange={(e) => setFieldValue('exercise', e.target.value)}
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
                {errors.exercise && touched.exercise && (
                  <Box color="error.main">{errors.exercise}</Box>
                )}
              </FormControl>
            </DialogContent>
            <DialogActions sx={{ p: 2 }}>
              <Button onClick={handleClose} color="error">
                Cancel
              </Button>
              <Button
                type="submit"
                variant="contained"
                color="secondary"
                disabled={isSubmitting}
                startIcon={popupType === 'new' ? <AddIcon /> : <EditIcon />}
                endIcon={
                  isSubmitting && (
                    <CircularProgress color="secondary" size={18} />
                  )
                }
              >
                {popupType == 'new' ? 'Add workout' : 'Edit workout'}
              </Button>
            </DialogActions>
          </Form>
        )}
      </Formik>
    </Dialog>
  );
}
