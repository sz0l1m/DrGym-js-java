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
import { LoginSchema, LoginDefaultValues } from './schema';
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

const Login = ({ csrfToken = null, showAppMessage }) => {
  const router = useRouter();
  const [showPassword, toggleShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleTogglePassword = () => {
    toggleShowPassword(!showPassword);
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const handleLogin = async (formData, form) => {
    console.log('formData:', formData);
    console.log('form:', form);
    setLoading(true);
    if (formData.password === 'Password1!') {
      setLoading(false);
      router.push('/user/workouts');
      showAppMessage({
        status: true,
        text: 'You have been logged in.',
        type: 'success',
      });
    } else {
      setLoading(false);
      form.resetForm();
      showAppMessage({
        status: true,
        text: 'Provided credentials are invalid.',
        type: 'error',
      });
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
        <Formik
          initialValues={LoginDefaultValues()}
          validationSchema={LoginSchema()}
          onSubmit={handleLogin}
        >
          {({ values, errors, handleBlur, handleChange }) => {
            return (
              <Form>
                <input
                  name="csrfToken"
                  type="hidden"
                  defaultValue={csrfToken}
                />
                <Grid container direction={'column'}>
                  <Grid xs={12} sx={{ width: '100%', mb: 2 }}>
                    <FormControl fullWidth>
                      <InputLabel>
                        {!!errors.username
                          ? `E-mail address - ${errors.username}`
                          : 'E-mail address'}
                      </InputLabel>
                      <OutlinedInput
                        error={!!errors.username}
                        label={
                          !!errors.username
                            ? `E-mail address - ${errors.username}`
                            : 'E-mail address'
                        }
                        name="username"
                        onBlur={(event) => handleBlur(event)}
                        onChange={(event) => handleChange(event)}
                        type="email"
                        value={values.username}
                        inputProps={{ tabIndex: '1' }}
                      />
                    </FormControl>
                  </Grid>
                  <Grid xs={12} sx={{ mb: 2 }}>
                    <FormControl fullWidth>
                      <InputLabel>
                        {!!errors.password
                          ? `Password - ${errors.password}`
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
                        error={!!errors.password}
                        label={
                          !!errors.password
                            ? `Password - ${errors.password}`
                            : 'Password'
                        }
                        name="password"
                        onBlur={(event) => handleBlur(event)}
                        onChange={(event) => handleChange(event)}
                        type={showPassword ? 'text' : 'password'}
                        value={values.password}
                        inputProps={{ tabIndex: '2' }}
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
                      tabIndex={3}
                    >
                      {loading ? 'Please wait...' : 'LOGIN'}
                    </Button>
                    <Link href="/reset-password">
                      <Button
                        fullWidth
                        variant="outlined"
                        sx={{ mb: 4, lineHeight: '40px' }}
                        color="primary"
                        size="large"
                        tabIndex={4}
                      >
                        Forgot password?
                      </Button>
                    </Link>
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
        <Link href="/register">
          <Button
            fullWidth
            variant="contained"
            sx={{ mb: 4, lineHeight: '40px' }}
            color="primary"
            size="large"
            tabIndex={5}
          >
            CREATE ACCOUNT
          </Button>
        </Link>
      </Grid>
    </Grid>
  );
};

export default withSnackbar(Login);
