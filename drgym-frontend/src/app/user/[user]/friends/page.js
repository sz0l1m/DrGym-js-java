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

const Friends = () => {
  const [friends, setFriends] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchFriends = async () => {
      try {
        setLoading(true);
        setTimeout(() => {
          setFriends(mockFriends);
          setLoading(false);
        }, 2000);
        // const response = await axios.get(
        //   `${process.env.NEXT_PUBLIC_API_URL}/api/friends`
        // );
        // setFriends(response.data);
      } catch (err) {
        setError('Failed to fetch friends. Please try again later.');
      } finally {
        // setLoading(false);
      }
    };

    fetchFriends();
  }, []);

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
        Your Friends
      </Typography>
      {loading
        ? Array.from({ length: 3 }).map((_, index) => (
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
          ))
        : friends.map((friend) => (
            <Card key={friend.username} sx={{ maxWidth: '100%', my: 1 }}>
              <UserHeader username={friend.username} actions />
            </Card>
          ))}
    </Box>
  );
};

export default Friends;
