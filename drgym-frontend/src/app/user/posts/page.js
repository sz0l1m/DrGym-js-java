'use client';

import { useEffect } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import Typography from '@mui/material/Typography';
import { withSnackbar } from '@/utils/snackbarProvider';

const Posts = ({ showAppMessage }) => {
  const router = useRouter();
  const searchParams = useSearchParams();

  useEffect(() => {
    const message = searchParams.get('message');
    const type = searchParams.get('type');
    if (message) {
      router.replace('/user/posts', undefined, { shallow: true });
      showAppMessage({
        status: true,
        text: message,
        type: type || 'info',
      });
    }
  }, [router, searchParams, showAppMessage]);

  return (
    <>
      <Typography variant="h4" gutterBottom>
        Posts
      </Typography>
    </>
  );
};

export default withSnackbar(Posts);
