'use client';

import { useState, useEffect, Suspense } from 'react';
import { useRouter, usePathname, useSearchParams } from 'next/navigation';
import Typography from '@mui/material/Typography';
import { withSnackbar } from '@/utils/snackbarProvider';
import PostList from '@/components/PostList';
import { getUsername } from '@/utils/localStorage';

const PostsContent = ({ showAppMessage }) => {
  const username = getUsername();
  const router = useRouter();
  const searchParams = useSearchParams();
  const pathname = usePathname();

  useEffect(() => {
    const message = searchParams.get('message');
    const type = searchParams.get('type');
    if (message) {
      router.replace(`${pathname}`, undefined, { shallow: true });
      showAppMessage({
        status: true,
        text: message,
        type: type || 'info',
      });
    }
  }, [router, pathname, searchParams, showAppMessage]);

  return <PostList username={username} showAppMessage={showAppMessage} />;
};

const Posts = ({ showAppMessage }) => {
  return (
    <Suspense fallback={<Typography>Loading posts...</Typography>}>
      <PostsContent showAppMessage={showAppMessage} />
    </Suspense>
  );
};

export default withSnackbar(Posts);
