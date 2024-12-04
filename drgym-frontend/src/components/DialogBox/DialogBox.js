import React from 'react';
import { Formik, Form } from 'formik';
import {
  Box,
  CircularProgress,
  Typography,
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
});

export default function DialogBox({
  dialogTitle,
  popupType,
  popupStatus,
  togglePopup,
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
        initialValues={{
          startDate: null,
          endDate: null,
          description: '',
        }}
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

// import { Formik, Form } from 'formik';
// import { CircularProgress, Typography } from '@mui/material';
// import Dialog from '@mui/material/Dialog';
// import Grid from '@mui/material/Grid2';
// import DialogActions from '@mui/material/DialogActions';
// import DialogContent from '@mui/material/DialogContent';
// import DialogBoxTitle from './DialogBoxTitle';
// import useMediaQuery from '@mui/material/useMediaQuery';
// import TextField from '@mui/material/TextField';
// import InputAdornment from '@mui/material/InputAdornment';
// import Button from '@mui/material/Button';
// import AddIcon from '@mui/icons-material/Add';
// import EditIcon from '@mui/icons-material/Edit';
// import CommentIcon from '@mui/icons-material/Comment';
// import KeyIcon from '@mui/icons-material/Key';
// import LanguageIcon from '@mui/icons-material/Language';
// import PersonIcon from '@mui/icons-material/Person';
// import HttpIcon from '@mui/icons-material/Http';
// import { useTheme } from '@mui/material/styles';
// import style from './DialogBox.module.css';
// import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
// import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
// import { renderTimeViewClock } from '@mui/x-date-pickers/timeViewRenderers';
// import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
// import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFnsV3';
// import {
//   Divider,
//   FormControl,
//   IconButton,
//   InputLabel,
//   OutlinedInput,
// } from '@mui/material';
// import { Visibility, VisibilityOff } from '@mui/icons-material';
// import * as yup from 'yup';

// let schema = yup.object().shape({
//   description: yup.string().max(50, 'is too long (max 50 chars)'),
//   name: yup
//     .string()
//     .max(30, 'is too long (max 30 chars)')
//     .min(2, 'is too short (min 2 chars)')
//     .required('required field'),
//   url: yup
//     .string()
//     .url()
//     .max(250, 'is too long (max 250 chars)')
//     .required('required field'),
//   username: yup
//     .string()
//     .max(30, 'is too long (max 30 chars)')
//     .min(2, 'is too short (min 2 chars)')
//     .required('required field'),
//   passwordHint: yup
//     .string()
//     .max(250, 'is too long (max 250 chars)')
//     .required('required field'),
//   notes: yup.string().max(250, 'is too long (max 250 chars)').nullable(),
// });

// export default function DialogBox({
//   dialogTitle,
//   popupType,
//   popupStatus,
//   togglePopup,
//   loading,
// }) {
//   const theme = useTheme();
//   const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

//   const handleClose = () => {
//     togglePopup(false);
//   };

//   return (
//     <>
//       <Dialog
//         fullScreen={fullScreen}
//         maxWidth="md"
//         fullWidth
//         open={popupStatus}
//         aria-labelledby="new-workout-dialog"
//       >
//         <DialogBoxTitle id="new-workout-dialog" onClose={handleClose}>
//           {dialogTitle}
//         </DialogBoxTitle>
//         <Formik
//           initialValues={{ name: 'jared' }}
//           onSubmit={(values, actions) => {
//             setTimeout(() => {
//               alert(JSON.stringify(values, null, 2));
//               actions.setSubmitting(false);
//             }, 1000);
//           }}
//           validationSchema={schema}
//         >
//           {({ values, errors, handleBlur, handleChange, isValid, touched }) => (
//             <Form>
//               {console.log('VALUES___', values)}
//               <DialogContent sx={{ p: 2 }}>
//                 <LocalizationProvider dateAdapter={AdapterDateFns}>
//                   <DateTimePicker
//                     id="startTime"
//                     label="Start time"
//                     name="startDate"
//                     onBlur={handleBlur}
//                     onAccept={handleChange}
//                     value={values['startDate']}
//                     viewRenderers={{
//                       hours: renderTimeViewClock,
//                       minutes: renderTimeViewClock,
//                       seconds: renderTimeViewClock,
//                     }}
//                   />
//                 </LocalizationProvider>
//                 <TextField
//                   id="description"
//                   label={
//                     !!errors['description']
//                       ? `Description ${errors['description']}`
//                       : 'Description'
//                   }
//                   name="description"
//                   error={!!errors['description']}
//                   onBlur={handleBlur}
//                   onChange={handleChange}
//                   type="email"
//                   value={values['description']}
//                   fullWidth
//                   multiline
//                   maxRows={4}
//                 />
//               </DialogContent>
//               <DialogActions sx={{ p: 2 }}>
//                 <Grid container className={style.actionGrid}>
//                   <Grid item xs={6} className={style.actionRightColumn}>
//                     <Button onClick={handleClose} color="error">
//                       Cancel
//                     </Button>
//                     <Button
//                       type="submit"
//                       variant="contained"
//                       color="secondary"
//                       disabled={loading}
//                       startIcon={
//                         popupType === 'new' ? <AddIcon /> : <EditIcon />
//                       }
//                       endIcon={
//                         loading && (
//                           <CircularProgress color="secondary" size={18} />
//                         )
//                       }
//                     >
//                       {popupType == 'new' ? 'Add workout' : 'Edit workout'}
//                     </Button>
//                   </Grid>
//                 </Grid>
//               </DialogActions>
//             </Form>
//           )}
//         </Formik>
//       </Dialog>
//     </>
//   );
// }
// import { Formik, Form } from 'formik';
// import { CircularProgress, Typography } from '@mui/material';
// import Dialog from '@mui/material/Dialog';
// import Grid from '@mui/material/Grid2';
// import DialogActions from '@mui/material/DialogActions';
// import DialogContent from '@mui/material/DialogContent';
// import DialogBoxTitle from './DialogBoxTitle';
// import useMediaQuery from '@mui/material/useMediaQuery';
// import TextField from '@mui/material/TextField';
// import InputAdornment from '@mui/material/InputAdornment';
// import Button from '@mui/material/Button';
// import AddIcon from '@mui/icons-material/Add';
// import EditIcon from '@mui/icons-material/Edit';
// import CommentIcon from '@mui/icons-material/Comment';
// import KeyIcon from '@mui/icons-material/Key';
// import LanguageIcon from '@mui/icons-material/Language';
// import PersonIcon from '@mui/icons-material/Person';
// import HttpIcon from '@mui/icons-material/Http';
// import { useTheme } from '@mui/material/styles';
// import style from './DialogBox.module.css';
// import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
// import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
// import { renderTimeViewClock } from '@mui/x-date-pickers/timeViewRenderers';
// import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
// import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFnsV3';
// import * as yup from 'yup';

// let schema = yup.object().shape({
//   name: yup
//     .string()
//     .max(30, 'is too long (max 30 chars)')
//     .min(2, 'is too short (min 2 chars)')
//     .required('required field'),
//   url: yup
//     .string()
//     .url()
//     .max(250, 'is too long (max 250 chars)')
//     .required('required field'),
//   username: yup
//     .string()
//     .max(30, 'is too long (max 30 chars)')
//     .min(2, 'is too short (min 2 chars)')
//     .required('required field'),
//   passwordHint: yup
//     .string()
//     .max(250, 'is too long (max 250 chars)')
//     .required('required field'),
//   notes: yup.string().max(250, 'is too long (max 250 chars)').nullable(),
// });

// export default function DialogBox({ dialogTitle, popupStatus, togglePopup }) {
//   const theme = useTheme();
//   const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

//   const handleClose = () => {
//     togglePopup(false);
//   };

//   return (
//     <>
//       <Dialog
//         fullScreen={fullScreen}
//         maxWidth="md"
//         fullWidth
//         open={popupStatus}
//         aria-labelledby="new-workout-dialog"
//       >
//         <DialogBoxTitle id="new-workout-dialog" onClose={handleClose}>
//           {dialogTitle}
//         </DialogBoxTitle>
//         <Formik
//           initialValues={{ name: 'jared' }}
//           onSubmit={(values, actions) => {
//             setTimeout(() => {
//               alert(JSON.stringify(values, null, 2));
//               actions.setSubmitting(false);
//             }, 1000);
//           }}
//         >
//           {({ values, errors, handleBlur, handleChange, isValid, touched }) => (
//             <Form>
//               <DialogContent sx={{ p: 2 }}>
//                 <TextField
//                   fullWidth
//                   error={errors.name.status}
//                   label={
//                     errors.name.status
//                       ? `Website name - ${errors.name.message}`
//                       : 'Website name'
//                   }
//                   name="name"
//                   size="small"
//                   sx={{ mb: 2 }}
//                   value={fields.name}
//                   onChange={handleChange}
//                   slotProps={{
//                     startAdornment: (
//                       <InputAdornment position="start">
//                         <LanguageIcon color="accent" />
//                       </InputAdornment>
//                     ),
//                   }}
//                 />
//                 <TextField
//                   fullWidth
//                   error={errors.url.status}
//                   label={
//                     errors.url.status
//                       ? `Website URL - ${errors.url.message}`
//                       : 'Website URL'
//                   }
//                   helperText="Please type or paste valid website URL"
//                   name="url"
//                   size="small"
//                   sx={{ my: 2 }}
//                   value={fields.url}
//                   onChange={handleChange}
//                   slotProps={{
//                     startAdornment: (
//                       <InputAdornment position="start">
//                         <HttpIcon color="accent" />
//                       </InputAdornment>
//                     ),
//                   }}
//                 />
//                 <TextField
//                   fullWidth
//                   error={errors.username.status}
//                   label={
//                     errors.username.status
//                       ? `Username - ${errors.username.message}`
//                       : 'Username'
//                   }
//                   name="username"
//                   size="small"
//                   sx={{ my: 2 }}
//                   value={fields.username}
//                   onChange={handleChange}
//                   slotProps={{
//                     startAdornment: (
//                       <InputAdornment position="start">
//                         <PersonIcon color="accent" />
//                       </InputAdornment>
//                     ),
//                   }}
//                 />
//                 <TextField
//                   fullWidth
//                   error={errors.passwordHint.status}
//                   label={
//                     errors.passwordHint.status
//                       ? `Password hint - ${errors.passwordHint.message}`
//                       : 'Password hint'
//                   }
//                   helperText="Please, provide password hint - not your password"
//                   name="passwordHint"
//                   size="small"
//                   sx={{ my: 2 }}
//                   value={fields.passwordHint}
//                   onChange={handleChange}
//                   slotProps={{
//                     startAdornment: (
//                       <InputAdornment position="start">
//                         <KeyIcon color="accent" />
//                       </InputAdornment>
//                     ),
//                   }}
//                 />
//                 <TextField
//                   fullWidth
//                   multiline
//                   minRows={3}
//                   error={errors.notes.status}
//                   label={
//                     errors.notes.status
//                       ? `Notes - ${errors.notes.message}`
//                       : 'Notes'
//                   }
//                   name="notes"
//                   size="small"
//                   sx={{ mt: 2 }}
//                   value={fields.notes}
//                   onChange={handleChange}
//                   slotProps={{
//                     startAdornment: (
//                       <InputAdornment position="start">
//                         <CommentIcon color="accent" />
//                       </InputAdornment>
//                     ),
//                   }}
//                 />
//               </DialogContent>
//               <DialogActions sx={{ p: 2 }}>
//                 <Grid container className={style.actionGrid}>
//                   <Grid item xs={6} className={style.actionLeftColumn}>
//                     {clearForm && (
//                       <Button
//                         classes={{ startIcon: style.clearButton }}
//                         onClick={clearForms}
//                         color="error"
//                         startIcon={<DeleteForeverIcon sx={{ mt: '-2px' }} />}
//                       >
//                         Clear
//                       </Button>
//                     )}
//                   </Grid>
//                   <Grid item xs={6} className={style.actionRightColumn}>
//                     <Button onClick={handleClose} color="error">
//                       Cancel
//                     </Button>
//                     <Button
//                       type="submit"
//                       variant="contained"
//                       color="secondary"
//                       disabled={loading}
//                       startIcon={
//                         popupType === 'new' ? <AddIcon /> : <EditIcon />
//                       }
//                       endIcon={
//                         loading && (
//                           <CircularProgress color="secondary" size={18} />
//                         )
//                       }
//                     >
//                       {buttonName}
//                     </Button>
//                   </Grid>
//                 </Grid>
//               </DialogActions>
//             </Form>
//           )}
//         </Formik>
//         <LocalizationProvider dateAdapter={AdapterDateFns}>
//           <DateTimePicker
//             label="With Time Clock"
//             viewRenderers={{
//               hours: renderTimeViewClock,
//               minutes: renderTimeViewClock,
//               seconds: renderTimeViewClock,
//             }}
//           />
//         </LocalizationProvider>
//       </Dialog>
//     </>
//   );
// }
