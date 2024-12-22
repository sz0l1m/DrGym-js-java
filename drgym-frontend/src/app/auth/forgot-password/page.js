'use client';

import React, { useRef, useState } from 'react';
import Typography from '@mui/material/Typography';
import PersonIcon from '@mui/icons-material/Person';
import { CircularProgress } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { Formik, Form } from 'formik';
import { Button, InputAdornment, IconButton } from '@mui/material';
import Grid from '@mui/material/Grid2';
import {
  ForogtPasswordSchema,
  ForogtPasswordDefaultValues,
} from '@/utils/schemas/ForgotPasswordSchema';
import { withSnackbar } from '@/utils/snackbarProvider';
import CustomInput from '@/components/CustomInput';

const ResetPassword = ({ csrfToken, showAppMessage }) => {
  const [loading, setLoading] = useState(false);
  const [resetMessage, setResetMessage] = useState(null);

  const formikRef = useRef();

  const handleResetPassword = async (formData, form) => {
    try {
      // Reset password endpoint
      if (formData.email === 'drgym@admin') {
        showAppMessage({
          status: true,
          text: 'Request successfully sent.',
          type: 'success',
        });
        setResetMessage(
          'We have sent you a link. Please check your inbox and follow the instructions.'
        );
      } else {
        form.resetForm();
        showAppMessage({
          status: true,
          text: 'User not found (example error).',
          type: 'error',
        });
      }
    } catch (error) {
      console.error('reset password error', error);
    }
  };

  return (
    <Grid
      container
      direction="column"
      sx={{
        width: {
          xs: '100%',
          sm: '80%',
          md: '60%',
        },
        maxWidth: '500px',
        alignItems: 'center',
        margin: '0 auto',
      }}
    >
      <Grid sx={{ width: '100%' }}>
        {resetMessage && (
          <Typography sx={{ mb: 2, textAlign: 'center' }} variant="body1">
            {resetMessage}
          </Typography>
        )}
        {!resetMessage && (
          <>
            <Typography sx={{ mb: 3 }} variant="body1">
              Please enter your e-mail
            </Typography>
            <Formik
              innerRef={formikRef}
              initialValues={ForogtPasswordDefaultValues()}
              validationSchema={ForogtPasswordSchema()}
              onSubmit={handleResetPassword}
            >
              {({
                errors,
                handleBlur,
                handleChange,
                handleSubmit,
                isValid,
                setFieldError,
                setFieldValue,
                touched,
                values,
              }) => {
                return (
                  <Form>
                    <input
                      name="csrfToken"
                      type="hidden"
                      defaultValue={csrfToken}
                    />
                    <Grid container direction="column" gap={2}>
                      <Grid xs={12}>
                        <CustomInput
                          label="E-mail address"
                          name="email"
                          type="email"
                          value={values.email}
                          error={errors.email}
                          touched={touched.email}
                          onBlur={handleBlur}
                          onChange={handleChange}
                          tabIndex={1}
                          endAdornment={
                            values.email && (
                              <InputAdornment position="end">
                                <IconButton
                                  onClick={() => {
                                    setFieldValue('email', '', false);
                                    setFieldError('email', null);
                                  }}
                                >
                                  <CloseIcon color="accent" />
                                </IconButton>
                              </InputAdornment>
                            )
                          }
                        />
                      </Grid>
                      <Grid xs={12}>
                        <Button
                          fullWidth
                          onClick={handleSubmit}
                          variant="contained"
                          color="primary"
                          disabled={loading}
                          endIcon={
                            loading && (
                              <CircularProgress color="primary" size={18} />
                            )
                          }
                        >
                          Reset Password
                        </Button>
                      </Grid>
                    </Grid>
                  </Form>
                );
              }}
            </Formik>
          </>
        )}
      </Grid>
    </Grid>
  );
};

export default withSnackbar(ResetPassword);
