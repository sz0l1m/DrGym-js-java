'use client';

import React, { useState, useEffect } from 'react';
import { Formik, Form } from 'formik';
import { Box, Button, Avatar, Typography, TextField } from '@mui/material';
import { styled } from '@mui/system';
import { useDropzone } from 'react-dropzone';
import Grid from '@mui/material/Grid2';
import { withSnackbar } from '@/utils/snackbarProvider';
import CustomInput from '@/components/CustomInput';
import DeleteConfirmation from '@/components/DeleteConfirmation';
import {
  AccountSchema,
  AccountDefaultValues,
} from '@/utils/schemas/AccountSchema';
import axiosInstance from '@/utils/axiosInstance';
import { getUsername } from '@/utils/localStorage';
import { signOut } from 'next-auth/react';
import { removeUserData } from '@/utils/localStorage';
import Autocomplete from '@mui/material/Autocomplete';
import FormControl from '@mui/material/FormControl';

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

const AccountPage = ({ showAppMessage }) => {
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [deleting, setDeleting] = useState(false);
  const [error, setError] = useState(null);
  const [exercises, setExercises] = useState([]);
  const [avatar, setAvatar] = useState(userData?.avatar || null);
  const [hasChanges, setHasChanges] = useState(false);
  const username = getUsername();

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        setLoading(true);

        const userResponse = await axiosInstance.get(`/api/users/${username}`);
        const exercisesResponse = await axiosInstance.get(
          '/api/exercises/by-type'
        );
        const exerciseData = [
          ...exercisesResponse.data.strength,
          ...exercisesResponse.data.cardio,
          ...exercisesResponse.data.crossfit,
        ];
        userResponse.data.favoriteExercise = exerciseData.find(
          (exercise) => exercise.id === userResponse.data.favoriteExercise
        );
        setUserData({
          ...userResponse.data,
          firstName: userResponse.data.name,
          avatar: null,
        });
        setExercises(exerciseData);

        setAvatar(userResponse.data.avatar || null);
      } catch (err) {
        console.error('Error fetching user data:', err);
        setError('Error fetching user data');
        showAppMessage({
          status: true,
          text: 'Something went wrong',
          type: 'error',
        });
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, [username, showAppMessage]);

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
    showAppMessage({
      status: true,
      text: 'Profile updated successfully!',
      type: 'success',
    });
    setHasChanges(false);
    resetForm();
  };

  const handleDeleteAccount = async () => {
    try {
      setDeleting(true);
      await axiosInstance.delete(`/api/users/${username}`);
      removeUserData();
      signOut();
    } catch (err) {
      console.error('Error deleting account:', err);
      setDeleting(false);
      showAppMessage({
        status: true,
        text: 'Failed to delete account',
        type: 'error',
      });
    }
  };

  const handleCancelDelete = () => {
    setDeleteDialogOpen(false);
  };

  const { getRootProps, getInputProps } = useDropzone({
    accept: 'image/*',
    maxFiles: 1,
    onDrop: handleAvatarChange,
  });

  if (loading) return <Typography>Loading...</Typography>;
  if (error) return <Typography>Error: {error}</Typography>;
  return (
    <>
      <Grid container direction="column">
        <Typography variant="h5" sx={{ my: 2 }}>
          Account Settings for <strong>{username}</strong>
        </Typography>
        <Formik
          validationSchema={AccountSchema}
          initialValues={AccountDefaultValues(userData)}
          onSubmit={handleFormSubmit}
        >
          {({
            values,
            errors,
            touched,
            handleChange,
            handleBlur,
            resetForm,
          }) => (
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
                    alt={username.charAt(0).toUpperCase()}
                    sx={{
                      width: 100,
                      height: 100,
                      fontSize: 40,
                      backgroundColor: 'primary.main',
                      margin: '0 auto',
                    }}
                  >
                    {!avatar && username.charAt(0).toUpperCase()}
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
                    flexGrow: 2,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
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
                    label={
                      errors.weight ? 'Weight - ' + errors.weight : 'Weight'
                    }
                    name="weight"
                    type="number"
                    value={values.weight}
                    onBlur={handleBlur}
                    error={!!errors.weight}
                    sx={{ width: '100%' }}
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
                    label={
                      errors.height ? 'Height - ' + errors.height : 'Height'
                    }
                    name="height"
                    type="number"
                    value={values.height}
                    onBlur={handleBlur}
                    error={!!errors.height}
                    sx={{ width: '100%' }}
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
                  <FormControl fullWidth>
                    <Autocomplete
                      options={exercises}
                      getOptionLabel={(option) => option.name || ''}
                      isOptionEqualToValue={(option, value) =>
                        option.id === value.id
                      }
                      value={values.exercise || null}
                      onChange={(event, newValue) => {
                        handleChange({
                          target: {
                            name: 'exercise',
                            value: newValue,
                          },
                        });
                      }}
                      onBlur={handleBlur}
                      renderInput={(params) => (
                        <TextField
                          {...params}
                          label={'Favourite Exercise'}
                          name="exercise"
                          onBlur={handleBlur}
                        />
                      )}
                    />
                  </FormControl>
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
                  <Button
                    variant="outlined"
                    color="error"
                    onClick={() => setDeleteDialogOpen(true)}
                    sx={{
                      mt: 2,
                    }}
                  >
                    Delete Account
                  </Button>
                </Box>
              </Grid>
            </Form>
          )}
        </Formik>
      </Grid>
      <DeleteConfirmation
        title="Delete Account"
        message={`Are you sure you want to delete your account? This action cannot be undone.`}
        open={deleteDialogOpen}
        loading={deleting}
        onConfirm={handleDeleteAccount}
        onClose={handleCancelDelete}
      />
    </>
  );
};

export default withSnackbar(AccountPage);
