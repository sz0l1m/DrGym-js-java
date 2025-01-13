'use client';

import React, { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid2';
import Card from '@mui/material/Card';
import Calendar from '@/components/Calendar';
import BodyHighlighter from '@/components/BodyHighlighter';
import UserHeader from '@/components/UserHeader';
import Skeleton from '@mui/material/Skeleton';
import CardHeader from '@mui/material/CardHeader';
import Box from '@mui/material/Box';
import { useRouter } from 'next/navigation';
import axiosInstance from '@/utils/axiosInstance';
import { useSession } from 'next-auth/react';
import PostList from '@/components/PostList';

const User = ({ params }) => {
  const [loading, setLoading] = React.useState(true);
  const { user } = React.use(params);
  const [avatar, setAvatar] = React.useState(null);
  const router = useRouter();
  const { data: session } = useSession();

  useEffect(() => {
    const checkFriendStatus = async () => {
      try {
        setLoading(false);
        if (session?.user?.username === user) {
          router.replace(`/user/${user}/account`);
          return;
        }
        const response = await axiosInstance.get(
          `/api/friends/isFriend/${session?.user?.username}/${user}`
        );
        if (response.data) {
          setAvatar(response.data?.avatar || null);
          setLoading(false);
        } else {
          router.replace(
            `/user/${session?.user?.username}/posts?message=User ${user} is not your friend&type=warning`
          );
        }
      } catch (err) {
        router.replace(
          `/user/${session?.user?.username}/posts?message=An error occurred. Redirected.&type=error`
        );
      }
    };

    checkFriendStatus();
  }, [router, user, session]);

  // FIXME
  const handleDeleteFriend = async (username) => {
    try {
      await new Promise((resolve) => setTimeout(resolve, 2000));
      // const response = await axiosInstance.delete(
      //   `/api/friends/delete}`
      // );
      setFriends((prevFriends) =>
        prevFriends.filter((friend) => friend.username !== username)
      );
      router.replace(
        `/posts?message=Removed ${username} from friends&type=success`
      );
    } catch (err) {
      showAppMessage({
        status: true,
        text: 'Failed to delete friend',
        type: 'error',
      });
    }
  };

  return (
    <Grid
      container
      justifyContent="center"
      sx={{
        width: '100%',
        maxWidth: '1000px',
        margin: '0 auto',
        py: 2,
      }}
    >
      <Box sx={{ width: '100%', maxWidth: '1000px', margin: '0 auto', py: 2 }}>
        {!loading ? (
          <Card sx={{ maxWidth: '100%', mt: 1, mb: 6 }}>
            <UserHeader
              username={user}
              avatar={null}
              actions="friend"
              onDelete={handleDeleteFriend}
            />
          </Card>
        ) : (
          <Card sx={{ maxWidth: '100%', my: 1, mb: 6 }}>
            <CardHeader
              avatar={
                <Skeleton
                  animation="wave"
                  variant="circular"
                  width={40}
                  height={40}
                />
              }
              title={
                <Skeleton
                  animation="wave"
                  height={20}
                  width="20%"
                  style={{ marginBottom: 6 }}
                />
              }
            />
          </Card>
        )}
        {!loading && (
          <Box display="flex" flexDirection="column" alignItems="center">
            <Calendar username={user} />
            <Box sx={{ my: 6 }}>
              <BodyHighlighter username={user} />
            </Box>
            <Box sx={{ width: '100%' }}>
              <PostList username={user} onlyThisUser />
            </Box>
          </Box>
        )}
      </Box>
    </Grid>
  );
};

export default User;
