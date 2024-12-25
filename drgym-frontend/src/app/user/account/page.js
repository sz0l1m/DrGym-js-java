'use client';

import { useState, useEffect } from 'react';
import Typography from '@mui/material/Typography';
import { withSnackbar } from '@/utils/snackbarProvider';

const Account = ({ showAppMessage }) => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  if (error) return <Typography>Error: {error}</Typography>;
  return (
    <Typography variant="h5" gutterBottom>
      Account
    </Typography>
  );
};

export default withSnackbar(Account);
