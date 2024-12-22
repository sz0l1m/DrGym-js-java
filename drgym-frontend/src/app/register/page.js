'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import { styled } from '@mui/material/styles';
import {
  Button,
  Divider,
  FormControl,
  IconButton,
  InputAdornment,
  InputLabel,
  OutlinedInput,
} from '@mui/material';
import Grid from '@mui/material/Grid2';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { Formik, Form } from 'formik';
import {
  RegisterSchema,
  RegisterDefaultValues,
} from '@/utils/schemas/RegisterSchema';
import Link from 'next/link';
import { CircularProgress } from '@mui/material';
import { withSnackbar } from '@/utils/snackbarProvider';

const Root = styled('div')(({ theme }) => ({
  width: '100%',
  ...theme.typography.body2,
  marginBottom: theme.spacing(4),
  '& > :not(style) ~ :not(style)': {
    marginTop: theme.spacing(2),
  },
}));

const Register = ({ csrfToken = null, showAppMessage }) => {
  const router = useRouter();
  const [showPassword, toggleShowPassword] = useState(false);
  const [showConfirmPassword, toggleShowConfirmPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleTogglePassword = () => {
    toggleShowPassword(!showPassword);
  };

  const handleToggleConfirmPassword = () => {
    toggleShowConfirmPassword(!showConfirmPassword);
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const handleRegister = async (formData, form) => {
    console.log('formData', formData);
    setLoading(true);
    if (formData.username === 'drgym@admin') {
      setLoading(false);
      router.push(`/user/posts?message=You have been registered.&type=success`);
    } else {
      setLoading(false);
      form.resetForm();
      showAppMessage({
        status: true,
        text: 'User already exists (example error).',
        type: 'error',
      });
    }
    // try {
    //   setLoading(true);
    //   const registerResponse = await fetch('/api/user/register', {
    //     method: 'POST',
    //     body: JSON.stringify({
    //       firstName: formData.firstName,
    //       lastName: formData.lastName,
    //       email: formData.username,
    //       password: formData.password,
    //     }),
    //     headers: {
    //       'Content-Type': 'application/json',
    //     },
    //   });
    //   const registerData = await registerResponse.json();
    //   if (registerData.error) {
    //     showAppMessage({
    //       status: true,
    //       text: registerData.error,
    //       type: 'error',
    //     });
    //     setLoading(false);
    //   } else {
    //     showAppMessage({
    //       status: true,
    //       text: 'Account has been created. Please verify your e-mail.',
    //       type: 'success',
    //     });
    //     setLoading(false);
    //     setRedirection(true);
    //     setTimeout(() => {
    //       router.push('/verification?account=welcome');
    //     }, 3000);
    //   }
    // } catch (error) {
    //   console.error('handle register error', error);
    // }
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
        <Formik
          initialValues={RegisterDefaultValues()}
          validationSchema={RegisterSchema()}
          onSubmit={handleRegister}
        >
          {({ errors, handleBlur, handleChange, values }) => {
            return (
              <Form>
                <input
                  name="csrfToken"
                  type="hidden"
                  defaultValue={csrfToken}
                />
                <Grid container direction="column" gap={2}>
                  <Grid xs={12}>
                    <FormControl fullWidth>
                      <InputLabel>
                        {!!errors['firstName']
                          ? `First name - ${errors['firstName']}`
                          : 'First name'}
                      </InputLabel>
                      <OutlinedInput
                        error={!!errors['firstName']}
                        label={
                          !!errors['firstName']
                            ? `First name - ${errors['firstName']}`
                            : 'First name'
                        }
                        name="firstName"
                        onBlur={(event) => handleBlur(event)}
                        onChange={(event) => handleChange(event)}
                        type="text"
                        value={values['firstName']}
                        inputProps={{ tabIndex: '1', autoFocus: true }}
                      />
                    </FormControl>
                  </Grid>
                  <Grid xs={12}>
                    <FormControl fullWidth>
                      <InputLabel>
                        {!!errors['lastName']
                          ? `Last name - ${errors['lastName']}`
                          : 'Last name'}
                      </InputLabel>
                      <OutlinedInput
                        error={!!errors['lastName']}
                        label={
                          !!errors['lastName']
                            ? `Last name - ${errors['lastName']}`
                            : 'Last name'
                        }
                        name="lastName"
                        onBlur={(event) => handleBlur(event)}
                        onChange={(event) => handleChange(event)}
                        type="text"
                        value={values['lastName']}
                        inputProps={{ tabIndex: '2' }}
                      />
                    </FormControl>
                  </Grid>
                  <Grid xs={12}>
                    <FormControl fullWidth>
                      <InputLabel>
                        {!!errors['username']
                          ? `E-mail address - ${errors['username']}`
                          : 'E-mail address'}
                      </InputLabel>
                      <OutlinedInput
                        error={!!errors['username']}
                        label={
                          !!errors['username']
                            ? `E-mail address - ${errors['username']}`
                            : 'E-mail address'
                        }
                        name="username"
                        onBlur={(event) => handleBlur(event)}
                        onChange={(event) => handleChange(event)}
                        type="email"
                        value={values['username']}
                        inputProps={{ tabIndex: '3' }}
                      />
                    </FormControl>
                  </Grid>
                  <Grid xs={12}>
                    <FormControl fullWidth>
                      <InputLabel>
                        {!!errors['password']
                          ? `Password - ${errors['password']}`
                          : 'Password'}
                      </InputLabel>
                      <OutlinedInput
                        endAdornment={
                          <InputAdornment position="end">
                            <IconButton
                              aria-label="toggle password visibility"
                              edge="end"
                              color="primary"
                              onClick={handleTogglePassword}
                              onMouseDown={handleMouseDownPassword}
                            >
                              {showPassword ? (
                                <VisibilityOff />
                              ) : (
                                <Visibility />
                              )}
                            </IconButton>
                          </InputAdornment>
                        }
                        error={!!errors['password']}
                        label={
                          !!errors['password']
                            ? `Password - ${errors['password']}`
                            : 'Password'
                        }
                        name="password"
                        onBlur={(event) => handleBlur(event)}
                        onChange={(event) => handleChange(event)}
                        type={showPassword ? 'text' : 'password'}
                        value={values['password']}
                        inputProps={{ tabIndex: '4' }}
                      />
                    </FormControl>
                  </Grid>
                  <Grid xs={12}>
                    <FormControl fullWidth>
                      <InputLabel>
                        {!!errors['confirmPassword']
                          ? `Confirm password - ${errors['confirmPassword']}`
                          : 'Confirm password'}
                      </InputLabel>
                      <OutlinedInput
                        endAdornment={
                          <InputAdornment position="end">
                            <IconButton
                              aria-label="toggle confirm password visibility"
                              edge="end"
                              color="primary"
                              onClick={handleToggleConfirmPassword}
                              onMouseDown={handleMouseDownPassword}
                            >
                              {showConfirmPassword ? (
                                <VisibilityOff />
                              ) : (
                                <Visibility />
                              )}
                            </IconButton>
                          </InputAdornment>
                        }
                        error={!!errors['confirmPassword']}
                        label={
                          !!errors['confirmPassword']
                            ? `Confirm password - ${errors['confirmPassword']}`
                            : 'Confirm password'
                        }
                        name="confirmPassword"
                        onBlur={(event) => handleBlur(event)}
                        onChange={(event) => handleChange(event)}
                        type={showConfirmPassword ? 'text' : 'password'}
                        value={values['confirmPassword']}
                        inputProps={{ tabIndex: '5' }}
                      />
                    </FormControl>
                  </Grid>
                  <Grid xs={12}>
                    <Button
                      fullWidth
                      type="submit"
                      variant="contained"
                      sx={{ mt: 2, mb: 4, lineHeight: '40px' }}
                      color="primary"
                      disabled={loading}
                      endIcon={
                        loading && <CircularProgress color="info" size={18} />
                      }
                      size="large"
                      tabIndex={6}
                    >
                      {loading ? 'Please wait...' : 'CREATE ACCOUNT'}
                    </Button>
                  </Grid>
                </Grid>
              </Form>
            );
          }}
        </Formik>
      </Grid>
      <Grid sx={{ width: '100%' }}>
        <Root>
          <Divider>OR</Divider>
        </Root>
        <Link href="/login">
          <Button
            fullWidth
            variant="contained"
            sx={{ mb: 4, lineHeight: '40px' }}
            color="primary"
            size="large"
            tabIndex={7}
          >
            LOGIN
          </Button>
        </Link>
      </Grid>
    </Grid>
  );
};

export default withSnackbar(Register);
