'use client';

import React, { useState } from 'react';
import { useSearchParams, useRouter } from 'next/navigation';
import { CircularProgress } from '@mui/material';
import { Formik, Form } from 'formik';
import { Button, IconButton, InputAdornment } from '@mui/material';
import Grid from '@mui/material/Grid2';
import Typography from '@mui/material/Typography';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import {
  ResetPasswordSchema,
  ResetPasswordDefaultValues,
} from '@/utils/schemas/ResetPasswordSchema';
import { withSnackbar } from '@/utils/snackbarProvider';
import CustomInput from '@/components/CustomInput';

const ResetPassword = ({ showAppMessage }) => {
  const searchParams = useSearchParams();
  const router = useRouter();
  const [loading, setLoading] = useState(false);
  const [showPassword, toggleShowPassword] = useState(false);
  const [showConfirmPassword, toggleShowConfirmPassword] = useState(false);

  const email = searchParams.get('email');
  const passwordResetToken = searchParams.get('passwordResetToken');

  const handleTogglePassword = () => {
    toggleShowPassword(!showPassword);
  };

  const handleToggleConfirmPassword = () => {
    toggleShowConfirmPassword(!showConfirmPassword);
  };

  const handleResetPassword = async (formData, form) => {
    try {
      setLoading(true);

      if (!email || !passwordResetToken) {
        showAppMessage({
          status: true,
          text: 'Invalid reset password link.',
          type: 'error',
        });
        setLoading(false);
        return;
      }

      // Reset password endpoint
      if (formData.password === 'Password1!') {
        showAppMessage({
          status: true,
          text: 'Your password has been successfully changed. Redirecting...',
          type: 'success',
        });
        setLoading(false);
        setTimeout(() => {
          router.push('/login');
        }, 3000);
      } else {
        form.resetForm();
        showAppMessage({
          status: true,
          text: 'An error occurred. Please try again later.',
          type: 'error',
        });
        setLoading(false);
      }
    } catch (error) {
      form.resetForm();
      console.error('Error during password reset:', error);
      showAppMessage({
        status: true,
        text: 'Something went wrong. Please try again later.',
        type: 'error',
      });
      setLoading(false);
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
        <Typography sx={{ mb: 3 }} variant="body1">
          Please enter your new password
        </Typography>
        <Formik
          initialValues={ResetPasswordDefaultValues()}
          validationSchema={ResetPasswordSchema()}
          onSubmit={handleResetPassword}
        >
          {({
            errors,
            handleBlur,
            handleChange,
            handleSubmit,
            touched,
            values,
            setFieldError,
            setFieldValue,
          }) => {
            return (
              <Form>
                <Grid container direction="column" gap={2}>
                  <Grid xs={12}>
                    <CustomInput
                      label="Password"
                      name="password"
                      type={showPassword ? 'text' : 'password'}
                      value={values.password}
                      error={errors.password}
                      touched={touched.password}
                      onBlur={handleBlur}
                      onChange={handleChange}
                      tabIndex={1}
                      endAdornment={
                        <InputAdornment position="end">
                          <IconButton
                            aria-label="toggle password visibility"
                            edge="end"
                            color="primary"
                            onClick={handleTogglePassword}
                          >
                            {showPassword ? <VisibilityOff /> : <Visibility />}
                          </IconButton>
                        </InputAdornment>
                      }
                    />
                  </Grid>
                  <Grid xs={12}>
                    <CustomInput
                      label="Confirm Password"
                      name="confirmPassword"
                      type={showConfirmPassword ? 'text' : 'password'}
                      value={values.confirmPassword}
                      error={errors.confirmPassword}
                      touched={touched.confirmPassword}
                      onBlur={handleBlur}
                      onChange={handleChange}
                      tabIndex={2}
                      endAdornment={
                        <InputAdornment position="end">
                          <IconButton
                            aria-label="toggle confirm password visibility"
                            edge="end"
                            color="primary"
                            onClick={handleToggleConfirmPassword}
                          >
                            {showConfirmPassword ? (
                              <VisibilityOff />
                            ) : (
                              <Visibility />
                            )}
                          </IconButton>
                        </InputAdornment>
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
                      Change Password
                    </Button>
                  </Grid>
                </Grid>
              </Form>
            );
          }}
        </Formik>
      </Grid>
    </Grid>
  );
};

export default withSnackbar(ResetPassword);
