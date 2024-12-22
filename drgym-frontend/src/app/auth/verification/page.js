'use client';

import { useRouter, useSearchParams } from 'next/navigation';
import React, { useState, useEffect } from 'react';
import { withSnackbar } from '@/utils/snackbarProvider';
import { Typography } from '@mui/material';

const VerificationPage = ({ showAppMessage }) => {
  const searchParams = useSearchParams();
  const router = useRouter();

  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState('');

  useEffect(() => {
    const account = searchParams.get('account');
    const email = searchParams.get('email');
    const verificationCode = searchParams.get('verificationCode');

    if (account === 'welcome') {
      setLoading(false);
      setMessage(
        'Thank you for creating an account. We have sent a verification link to your e-mail. Usually, the email will arrive in your inbox immediately, but sometimes it can take a while. Please be patient and, in the meantime, check your spam folder.'
      );
      showAppMessage({
        status: true,
        text: 'Account has been created.',
        type: 'info',
      });
    } else if (!email || !verificationCode) {
      setLoading(false);
      setMessage(
        'We are sorry but the verification link seems to be broken. Please try again.'
      );
      showAppMessage({
        status: true,
        text: 'Wrong verification URL.',
        type: 'error',
      });
    } else {
      // Verification endpoint
      setLoading(false);
      setMessage(
        'Your account has been verified. Please wait to be redirected to the login page.'
      );
      showAppMessage({
        status: true,
        text: 'Account has been verified.',
        type: 'success',
      });
      setTimeout(() => {
        router.replace('/login');
      }, 5000);
    }
  }, [router, searchParams, showAppMessage]);

  return (
    <>
      {!loading && (
        <Typography style={{ textAlign: 'center' }}>{message}</Typography>
      )}
    </>
  );
};

export default withSnackbar(VerificationPage);
