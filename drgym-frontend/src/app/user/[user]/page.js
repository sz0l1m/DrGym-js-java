'use client';

import React from 'react';
import Grid from '@mui/material/Grid2';
import Card from '@mui/material/Card';
import Calendar from '@/components/Calendar';
import BodyHighlighter from '@/components/BodyHighlighter';
import UserHeader from '@/components/UserHeader';
import Skeleton from '@mui/material/Skeleton';
import CardHeader from '@mui/material/CardHeader';
import { useRouter } from 'next/navigation';

const User = ({ params }) => {
  const [loading, setLoading] = React.useState(false);
  const { user } = React.use(params);
  const router = useRouter();

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
      <Grid>
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

        <Calendar username={user} />
      </Grid>
      <Grid sx={{ mt: 6 }}>
        <BodyHighlighter username={user} />
      </Grid>
    </Grid>
  );
};

export default User;
