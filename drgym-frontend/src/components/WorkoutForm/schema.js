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
  sets: yup
    .number()
    .nullable()
    .typeError('Sets must be a number')
    .required('Sets are required'),
  weight: yup
    .number()
    .nullable()
    .typeError('Weight must be a number')
    .required('Weight is required'),
  duration: yup.mixed().nullable(),
});

const cardioExerciseSchema = yup.object().shape({
  exerciseType: yup.string().required('Exercise Type is required'),
  exercise: yup.string().required('Exercise is required'),
  sets: yup.number().nullable().typeError('Sets must be a number'),
  weight: yup.number().nullable().typeError('Weight must be a number'),
  duration: yup.mixed().nullable().required('Duration is required'),
});

export { schema, strengthExerciseSchema, cardioExerciseSchema };
