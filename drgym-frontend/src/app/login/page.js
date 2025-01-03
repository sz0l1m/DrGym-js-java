'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import { styled } from '@mui/material/styles';
import {
  Button,
  Divider,
  IconButton,
  InputAdornment,
  CircularProgress,
} from '@mui/material';
import Grid from '@mui/material/Grid2';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { Formik, Form } from 'formik';
import { LoginSchema, LoginDefaultValues } from '@/utils/schemas/LoginSchema';
import Link from 'next/link';
import { withSnackbar } from '@/utils/snackbarProvider';
import CustomInput from '@/components/CustomInput';
import { signIn } from 'next-auth/react';

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
    try {
      setLoading(true);
      const res = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/api/auth/login`,
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            identifier: formData.email,
            password: formData.password,
          }),
          credentials: 'include',
        }
      );
      const user = await res.json();
      await signIn('credentials', {
        username: user.username,
        avatar: user.avatar || null,
        callbackUrl: '/user/workouts',
        redirect: false,
      });
    } catch (err) {
      console.error('Error in login page: ', err);
      showAppMessage({
        status: true,
        text: `Error: ${err.message}`,
        type: 'error',
      });
    } finally {
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
        <Formik
          initialValues={LoginDefaultValues()}
          validationSchema={LoginSchema()}
          onSubmit={handleLogin}
        >
          {({ values, touched, errors, handleBlur, handleChange }) => {
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
                    />
                  </Grid>
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
                      tabIndex={2}
                      endAdornment={
                        <InputAdornment position="end">
                          <IconButton
                            aria-label="toggle password visibility"
                            edge="end"
                            color="primary"
                            onClick={handleTogglePassword}
                            onMouseDown={handleMouseDownPassword}
                          >
                            {showPassword ? <VisibilityOff /> : <Visibility />}
                          </IconButton>
                        </InputAdornment>
                      }
                    />
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
                    <Link href="/auth/forgot-password">
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
