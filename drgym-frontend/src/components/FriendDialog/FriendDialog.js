import { useState } from 'react';
import { Formik, Form } from 'formik';
import {
  CircularProgress,
  InputAdornment,
  Button,
  Dialog,
  DialogContent,
  useMediaQuery,
  IconButton,
} from '@mui/material';
import Grid from '@mui/material/Grid2';
import CloseIcon from '@mui/icons-material/Close';
import { useTheme } from '@mui/material/styles';
import axiosInstance from '@/utils/axiosInstance';
import FriendDialogTitle from './FriendDialogTitle';
import CustomInput from '@/components/CustomInput';
import {
  UsernameSchema,
  UsernameDefaultValues,
} from '@/utils/schemas/UsernameSchema';

export default function FriendDialog({
  popupStatus,
  togglePopup,
  showAppMessage,
}) {
  const [loading, setLoading] = useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

  const handleAddFriend = async (formData, form) => {
    try {
      setLoading(true);
      const response = await axiosInstance.post(`/api/friends/sendRequest`, {
        username: formData.username,
      });
      showAppMessage({
        status: true,
        text: `Friend request to ${formData.username} has been sent.`,
        type: 'success',
      });
      handleClose();
    } catch (error) {
      if (error.response?.data === 'User does not exist') {
        form.setFieldError('username', 'no account found');
        showAppMessage({
          status: true,
          text: 'There is no account associated with this username.',
          type: 'error',
        });
      } else {
        showAppMessage({
          status: true,
          text: 'Something went wrong.',
          type: 'error',
        });
      }
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    togglePopup(false);
  };

  return (
    <Dialog
      fullScreen={fullScreen}
      maxWidth="md"
      fullWidth
      open={popupStatus}
      aria-labelledby="new-friend-dialog"
    >
      <FriendDialogTitle id="new-friend-dialog" onClose={handleClose}>
        Add friend
      </FriendDialogTitle>
      <Formik
        initialValues={UsernameDefaultValues()}
        validationSchema={UsernameSchema()}
        onSubmit={handleAddFriend}
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
              <DialogContent sx={{ p: 3, pt: 1 }}>
                <Grid container direction="column" gap={2}>
                  <Grid xs={12}>
                    <CustomInput
                      label="Username"
                      name="username"
                      type="username"
                      value={values.username}
                      error={errors.username}
                      touched={touched.username}
                      onBlur={handleBlur}
                      onChange={handleChange}
                      tabIndex={0}
                      endAdornment={
                        values.username && (
                          <InputAdornment position="end">
                            <IconButton
                              onClick={() => {
                                setFieldValue('username', '', false);
                                setFieldError('username', null);
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
                      Send friend request
                    </Button>
                  </Grid>
                </Grid>
              </DialogContent>
            </Form>
          );
        }}
      </Formik>
    </Dialog>
  );
}
