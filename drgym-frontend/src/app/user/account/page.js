'use client';

import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as yup from 'yup';
import { Box, Button, Avatar, Typography } from '@mui/material';
import { styled } from '@mui/system';
import { useDropzone } from 'react-dropzone';
import Grid from '@mui/material/Grid2';
import { withSnackbar } from '@/utils/snackbarProvider';
import CustomInput from '@/components/CustomInput';

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
  const userData = {
    username: 'john_doe',
    name: 'John',
    surname: 'Doe',
    avatar: null,
  };

  const [avatar, setAvatar] = useState(userData.avatar || null);
  const [hasChanges, setHasChanges] = useState(false);

  const validationSchema = yup.object({
    username: yup.string().required('Username is required'),
    name: yup.string().required('Name is required'),
    surname: yup.string().required('Surname is required'),
  });

  const formik = useFormik({
    initialValues: {
      username: userData.username || '',
      name: userData.name || '',
      surname: userData.surname || '',
    },
    validationSchema,
    onSubmit: (values) => {
      console.log('Form submitted:', { ...values, avatar });
      showAppMessage({
        status: true,
        text: 'Profile updated successfully!',
        type: 'success',
      });
      setHasChanges(false);
    },
    onChange: () => setHasChanges(true),
  });

  const onDrop = (acceptedFiles) => {
    if (acceptedFiles.length > 0) {
      const file = acceptedFiles[0];
      const preview = URL.createObjectURL(file);
      setAvatar(preview);
      setHasChanges(true);
    }
  };

  const { getRootProps, getInputProps } = useDropzone({
    accept: 'image/*',
    maxFiles: 1,
    onDrop,
  });

  const resetFields = () => {
    formik.resetForm();
    setAvatar(userData.avatar || null);
    setHasChanges(false);
  };

  const deleteFile = () => {
    setAvatar(null);
    setHasChanges(true);
  };

  const avatarFallback = (
    <Avatar
      sx={{
        width: 100,
        height: 100,
        fontSize: 40,
        backgroundColor: 'primary.main',
      }}
    >
      {userData.username.charAt(0).toUpperCase()}
    </Avatar>
  );

  return (
    <Grid
      container
      spacing={2}
      direction="column"
      alignItems="center"
      sx={{
        width: { xs: '100%', sm: '80%', md: '60%' },
        maxWidth: '500px',
        margin: '0 auto',
      }}
    >
      <Typography variant="h5" sx={{ my: 2 }}>
        Account Settings
      </Typography>
      <Box
        component="form"
        noValidate
        onSubmit={formik.handleSubmit}
        sx={{ width: '100%' }}
      >
        <Grid container spacing={2}>
          <Grid xs={12} textAlign="center">
            {avatar ? (
              <Avatar
                src={avatar}
                alt="Avatar"
                sx={{ width: 100, height: 100, margin: '0 auto' }}
              />
            ) : (
              avatarFallback
            )}
            <DropzoneContainer {...getRootProps()} sx={{ mt: 2 }}>
              <input {...getInputProps()} />
              <Typography variant="body1">
                Drag & drop your avatar here, or click to select
              </Typography>
              <Typography variant="caption">
                (Only one image file is allowed)
              </Typography>
            </DropzoneContainer>
            <Box
              sx={{ display: 'flex', gap: 2, justifyContent: 'center', mt: 2 }}
            >
              <Button
                variant="outlined"
                color="secondary"
                onClick={deleteFile}
                disabled={!avatar}
              >
                Delete Avatar
              </Button>
              <Button
                variant="outlined"
                color="primary"
                onClick={resetFields}
                disabled={!hasChanges}
              >
                Reset Changes
              </Button>
            </Box>
          </Grid>

          <Grid xs={12}>
            <CustomInput
              label="Username"
              name="username"
              value={formik.values.username}
              error={formik.errors.username}
              touched={formik.touched.username}
              onBlur={formik.handleBlur}
              onChange={(e) => {
                formik.handleChange(e);
                setHasChanges(true);
              }}
              tabIndex={1}
            />
          </Grid>
          <Grid xs={12}>
            <CustomInput
              label="Name"
              name="name"
              value={formik.values.name}
              error={formik.errors.name}
              touched={formik.touched.name}
              onBlur={formik.handleBlur}
              onChange={(e) => {
                formik.handleChange(e);
                setHasChanges(true);
              }}
              tabIndex={2}
            />
          </Grid>
          <Grid xs={12}>
            <CustomInput
              label="Surname"
              name="surname"
              value={formik.values.surname}
              error={formik.errors.surname}
              touched={formik.touched.surname}
              onBlur={formik.handleBlur}
              onChange={(e) => {
                formik.handleChange(e);
                setHasChanges(true);
              }}
              tabIndex={3}
            />
          </Grid>

          <Grid xs={12}>
            <Button
              type="submit"
              variant="contained"
              fullWidth
              color="primary"
              sx={{ mt: 2 }}
              disabled={!hasChanges}
            >
              Save Changes
            </Button>
          </Grid>
        </Grid>
      </Box>
    </Grid>
  );
};

export default withSnackbar(AccountPage);
