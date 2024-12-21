import * as yup from 'yup';

const schema = yup.object().shape({
  startDate: yup
    .date()
    .required('Start Date is required')
    .typeError('Invalid date'),
  endDate: yup
    .date()
    .required('End Date is required')
    .typeError('Invalid date'),
  description: yup.string().max(50, 'Description is too long (max 50 chars)'),
});

const strengthExerciseSchema = yup.object().shape({
  exerciseType: yup.string().required('Exercise Type is required'),
  exercise: yup.string().required('Exercise is required'),
  reps: yup
    .number()
    .typeError('Reps must be a number')
    .min(1, 'Reps must be at least 1')
    .required('Reps are required'),
  weight: yup
    .number()
    .typeError('Weight must be a number')
    .min(0, 'Weight cannot be negative')
    .required('Weight is required'),
  duration: yup.mixed().nullable(),
});

const cardioExerciseSchema = yup.object().shape({
  exerciseType: yup.string().required('Exercise Type is required'),
  exercise: yup.string().required('Exercise is required'),
  reps: yup.number().nullable().typeError('Reps must be a number'),
  weight: yup.number().nullable().typeError('Weight must be a number'),
  duration: yup.mixed().nullable().required('Duration is required'),
});

export { schema, strengthExerciseSchema, cardioExerciseSchema };
