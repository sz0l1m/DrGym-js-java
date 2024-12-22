'use client';

import Typography from '@mui/material/Typography';
import { withSnackbar } from '@/utils/snackbarProvider';

const Login = ({ showAppMessage }) => {
  const showError = () => {
    showAppMessage({
      status: true,
      text: 'An error occurred!',
      type: 'error',
    });
  };

  const showSuccess = () => {
    showAppMessage({
      status: true,
      text: 'Operation successful!',
      type: 'success',
    });
  };
  return (
    <>
      <Typography variant="h4" gutterBottom>
        Login
      </Typography>
      <div>
        <button onClick={showSuccess}>Show Success</button>
        <button onClick={showError}>Show Error</button>
      </div>
    </>
  );
};

export default withSnackbar(Login);
