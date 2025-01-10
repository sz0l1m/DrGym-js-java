'use client';

import { useState, useEffect } from 'react';
import Typography from '@mui/material/Typography';
import Card from '@mui/material/Card';
import UserHeader from '@/components/UserHeader';
import axios from 'axios';
import { friends as mockFriends } from '@/utils/mockData';
import Box from '@mui/material/Box';
import Skeleton from '@mui/material/Skeleton';
import CardHeader from '@mui/material/CardHeader';
import { withSnackbar } from '@/utils/snackbarProvider';

const Friends = ({ showAppMessage }) => {
  const [friends, setFriends] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchFriends = async () => {
      try {
        setLoading(true);
        setTimeout(() => {
          setFriends([]);
          // setFriends(mockFriends);
          setLoading(false);
        }, 2000);
        // const response = await axios.get(
        //   `${process.env.NEXT_PUBLIC_API_URL}/api/friends`
        // );
        // setFriends(response.data);
      } catch (err) {
        showAppMessage({
          status: true,
          text: 'Something went wrong',
          type: 'error',
        });
        setError('Failed to fetch friends. Please try again later.');
      } finally {
        // setLoading(false);
      }
    };

    fetchFriends();
  }, [showAppMessage]);

  const handleDeleteFriend = async (username) => {
    try {
      await new Promise((resolve) => setTimeout(resolve, 2000));
      // await axios.delete(
      //   `${process.env.NEXT_PUBLIC_API_URL}/api/friends/${username}`
      // );
      setFriends((prevFriends) =>
        prevFriends.filter((friend) => friend.username !== username)
      );
      showAppMessage({
        status: true,
        text: 'Friend deleted successfully',
        type: 'success',
      });
    } catch (err) {
      showAppMessage({
        status: true,
        text: 'Failed to delete friend',
        type: 'error',
      });
    }
  };

  if (error) {
    return (
      <Typography textAlign="center" color="error">
        {error}
      </Typography>
    );
  }

  return (
    <Box
      sx={{
        width: '100%',
        maxWidth: '1000px',
        margin: '0 auto',
        py: 2,
      }}
    >
      <Typography variant="h5" gutterBottom>
        {!loading && !friends.length
          ? 'You have not added any friends yet'
          : 'Your Friends'}
      </Typography>
      {!loading
        ? friends.map((friend) => (
            <Card key={friend.username} sx={{ maxWidth: '100%', my: 1 }}>
              <UserHeader
                username={friend.username}
                actions
                onDelete={handleDeleteFriend}
              />
            </Card>
          ))
        : Array.from({ length: 3 }).map((_, index) => (
            <Card key={index} sx={{ maxWidth: '100%', my: 1 }}>
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
          ))}
    </Box>
  );
};

export default withSnackbar(Friends);
