'use client';

import React, { useState } from 'react';
import { Formik, Form } from 'formik';
import { Box, Button, Avatar, Typography, TextField } from '@mui/material';
import { styled } from '@mui/system';
import { useDropzone } from 'react-dropzone';
import Grid from '@mui/material/Grid2';
import { withSnackbar } from '@/utils/snackbarProvider';
import CustomInput from '@/components/CustomInput';
import {
  AccountSchema,
  AccountDefaultValues,
} from '@/utils/schemas/AccountSchema';
import { set } from 'date-fns';

const DropzoneContainer = styled(Box)(({ theme }) => ({
  border: '2px dashed #ccc',
  borderRadius: '8px',
  padding: theme.spacing(3),
  textAlign: 'center',
  cursor: 'pointer',
  '&:hover': {
    borderColor: theme.palette.primary.main,
  },
}));

const userData = {
  username: 'john_doe',
  firstName: 'John',
  surname: 'Doe',
  weight: 80,
  height: 180,
  avatar: null,
};

const AccountPage = ({ showAppMessage }) => {
  const [avatar, setAvatar] = useState(userData.avatar || null);
  const [hasChanges, setHasChanges] = useState(false);

  const handleAvatarChange = (acceptedFiles) => {
    if (acceptedFiles.length > 0) {
      const file = acceptedFiles[0];
      const preview = URL.createObjectURL(file);
      setAvatar(preview);
      setHasChanges(true);
    }
  };

  const handleResetFields = (resetForm) => {
    resetForm();
    setAvatar(userData.avatar || null);
    setHasChanges(false);
  };

  const handleDeleteAvatar = () => {
    setAvatar(null);
    setHasChanges(true);
  };

  const handleFormSubmit = (values, { resetForm }) => {
    console.log('Form submitted:', { ...values, avatar });
    showAppMessage({
      status: true,
      text: 'Profile updated successfully!',
      type: 'success',
    });
    setHasChanges(false);
    resetForm();
  };

  const { getRootProps, getInputProps } = useDropzone({
    accept: 'image/*',
    maxFiles: 1,
    onDrop: handleAvatarChange,
  });

  return (
    <Grid container direction="column">
      <Typography variant="h5" sx={{ my: 2 }}>
        Account Settings
      </Typography>
      <Formik
        validationSchema={AccountSchema}
        initialValues={AccountDefaultValues(userData)}
        onSubmit={handleFormSubmit}
      >
        {({ values, errors, touched, handleChange, handleBlur, resetForm }) => (
          <Form>
            <Grid
              container
              direction="row"
              justifyContent="center"
              gap={6}
              sx={{ mt: 2 }}
            >
              <Grid
                sx={{
                  width: {
                    xs: '100%',
                    sm: '80%',
                    md: '50%',
                  },
                }}
                textAlign="center"
              >
                <Avatar
                  src={avatar || undefined}
                  alt={values.username.charAt(0).toUpperCase()}
                  sx={{
                    width: 100,
                    height: 100,
                    fontSize: 40,
                    backgroundColor: 'primary.main',
                    margin: '0 auto',
                  }}
                >
                  {!avatar && values.username.charAt(0).toUpperCase()}
                </Avatar>
                <DropzoneContainer {...getRootProps()} sx={{ mt: 2 }}>
                  <input {...getInputProps()} />
                  <Typography variant="body1">
                    Drag & drop your avatar here, or click to select
                  </Typography>
                  <Typography variant="caption">
                    (Only one image file is allowed)
                  </Typography>
                </DropzoneContainer>
                <Button
                  variant="outlined"
                  color="secondary"
                  onClick={handleDeleteAvatar}
                  disabled={!avatar}
                  sx={{ mt: 2 }}
                >
                  Delete Avatar
                </Button>
              </Grid>
              <Box
                sx={{
                  flexGrow: 1,
                  display: 'flex',
                  flexDirection: 'column',
                  gap: 2,
                  mt: 2,
                  maxWidth: {
                    xs: '100%',
                    sm: '80%',
                    md: '50%',
                  },
                }}
              >
                <CustomInput
                  label="Username"
                  name="username"
                  value={values.username}
                  error={errors.username}
                  touched={touched.username}
                  onBlur={handleBlur}
                  onChange={(e) => {
                    handleChange(e);
                    setHasChanges(true);
                  }}
                  tabIndex={2}
                />
                <CustomInput
                  label="First Name"
                  name="firstName"
                  value={values.firstName}
                  error={errors.firstName}
                  touched={touched.firstName}
                  onBlur={handleBlur}
                  onChange={(e) => {
                    handleChange(e);
                    setHasChanges(true);
                  }}
                  tabIndex={3}
                />
                <CustomInput
                  label="Surname"
                  name="surname"
                  value={values.surname}
                  error={errors.surname}
                  touched={touched.surname}
                  onBlur={handleBlur}
                  onChange={(e) => {
                    handleChange(e);
                    setHasChanges(true);
                  }}
                  tabIndex={4}
                />
                <TextField
                  label={errors.weight ? 'Weight - ' + errors.weight : 'Weight'}
                  name="weight"
                  type="number"
                  value={values.weight}
                  onBlur={handleBlur}
                  error={!!errors.weight}
                  onChange={(e) => {
                    setHasChanges(true);
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
                  label={errors.height ? 'Height - ' + errors.height : 'Height'}
                  name="height"
                  type="number"
                  value={values.height}
                  onBlur={handleBlur}
                  error={!!errors.height}
                  onChange={(e) => {
                    setHasChanges(true);
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
                <Box
                  sx={{
                    display: 'flex',
                    flexDirection: 'row',
                    gap: 2,
                    justifyContent: 'center',
                  }}
                >
                  <Button
                    variant="outlined"
                    color="primary"
                    onClick={() => handleResetFields(resetForm)}
                    disabled={!hasChanges}
                  >
                    Reset Changes
                  </Button>
                  <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    disabled={!hasChanges}
                  >
                    Save Changes
                  </Button>
                </Box>
              </Box>
            </Grid>
          </Form>
        )}
      </Formik>
    </Grid>
  );
};

export default withSnackbar(AccountPage);
