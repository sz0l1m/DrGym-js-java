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
import axios from 'axios';
import { useSession } from 'next-auth/react';

const User = ({ params }) => {
  const [loading, setLoading] = React.useState(true);
  const { user } = React.use(params);
  const [avatar, setAvatar] = React.useState(null);
  const router = useRouter();
  const { data: session } = useSession();

  useEffect(() => {
    const checkFriendStatus = async () => {
      try {
        await new Promise((resolve) => setTimeout(resolve, 1000));
        setLoading(false);
        // const response = await axios.get(
        //   `${process.env.NEXT_PUBLIC_API_URL}/api/friends/${user}`
        // );

        // if (response.data.isFriend) {
        //   setAvatar(response.data.avatar);
        //   setLoading(false);
        // } else {
        //   router.replace(
        //     `/user/${session.username}/posts?message=User ${user} is not your friend&type=warning`
        //   );
        // }
      } catch (err) {
        router.replace(
          `/user/${session.username}/posts?message=An error occurred. Redirected.&type=error`
        );
      }
    };

    checkFriendStatus();
  }, [router, user, session]);

  const handleDeleteFriend = async (username) => {
    try {
      await new Promise((resolve) => setTimeout(resolve, 2000));
      // await axios.delete(
      //   `${process.env.NEXT_PUBLIC_API_URL}/api/friends/${username}`
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
      </Box>
      {!loading && (
        <>
          <Calendar username={user} />
          <Grid sx={{ mt: 6 }}>
            <BodyHighlighter username={user} />
          </Grid>
        </>
      )}
    </Grid>
  );
};

export default User;
