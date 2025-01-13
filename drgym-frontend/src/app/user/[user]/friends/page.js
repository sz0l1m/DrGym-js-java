'use client';

import { useState, useEffect } from 'react';
import Typography from '@mui/material/Typography';
import Card from '@mui/material/Card';
import UserHeader from '@/components/UserHeader';
import axiosInstance from '@/utils/axiosInstance';
import { friends as mockFriends } from '@/utils/mockData';
import Box from '@mui/material/Box';
import Skeleton from '@mui/material/Skeleton';
import CardHeader from '@mui/material/CardHeader';
import { withSnackbar } from '@/utils/snackbarProvider';
import FriendDialog from '@/components/FriendDialog';
import Grid from '@mui/material/Grid2';
import Button from '@mui/material/Button';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import { Divider } from '@mui/material';
import { useSession } from 'next-auth/react';

const Friends = ({ showAppMessage }) => {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [friends, setFriends] = useState([]);
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { data: session } = useSession();

  useEffect(() => {
    const fetchFriends = async () => {
      try {
        setLoading(true);
        const response = await axiosInstance.get(
          `/api/friends/${session?.user?.username}`
        );
        setFriends(response.data.friends);
        setRequests(response.data.invitations);
      } catch (err) {
        showAppMessage({
          status: true,
          text: 'Something went wrong',
          type: 'error',
        });
        setError('Failed to fetch friends. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    fetchFriends();
  }, [session?.user?.username, showAppMessage]);

  // FIXME (axiosInstance)
  const handleAcceptRequest = async (username, avatar) => {
    try {
      await new Promise((resolve) => setTimeout(resolve, 2000));
      // await axios.delete(
      //   `${process.env.NEXT_PUBLIC_API_URL}/api/friends/accept/${username}`
      // );
      showAppMessage({
        status: true,
        text: `Accepted friend request from ${username}`,
        type: 'success',
      });
      setRequests((prevRequests) =>
        prevRequests.filter((request) => request.sender !== username)
      );
      setFriends((prevFriends) => [...prevFriends, { username, avatar }]);
    } catch (err) {
      showAppMessage({
        status: true,
        text: 'Something went wrong',
        type: 'error',
      });
    }
  };

  const handleDeclineRequest = async (username) => {
    try {
      await new Promise((resolve) => setTimeout(resolve, 2000));
      // await axios.delete(
      //   `${process.env.NEXT_PUBLIC_API_URL}/api/friends/decline/${username}`
      // );
      showAppMessage({
        status: true,
        text: `Declined friend request from ${username}`,
        type: 'success',
      });
      setRequests((prevRequests) =>
        prevRequests.filter((request) => request.sender !== username)
      );
    } catch (err) {
      showAppMessage({
        status: true,
        text: 'Something went wrong',
        type: 'error',
      });
    }
  };

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
        text: `Removed ${username} from friends`,
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
    <>
      <Box
        sx={{
          width: '100%',
          maxWidth: '1000px',
          margin: '0 auto',
          py: 2,
        }}
      >
        {requests.length > 0 && !loading && (
          <>
            <Typography variant="h5" gutterBottom>
              Friend Requests
            </Typography>
            {requests.map((friend) => (
              <Card key={friend.sender} sx={{ maxWidth: '100%', my: 1 }}>
                <UserHeader
                  username={friend.sender}
                  actions="request"
                  onAccept={handleAcceptRequest}
                  onDecline={handleDeclineRequest}
                />
              </Card>
            ))}
            <Divider sx={{ my: 4 }} />
          </>
        )}
        <Grid container justifyContent="space-between" sx={{ mb: 4 }}>
          <Typography variant="h5" gutterBottom>
            {!loading && !friends.length
              ? 'You have not added any friends yet'
              : 'Your Friends'}
          </Typography>
          <Button
            variant="contained"
            startIcon={<PersonAddIcon />}
            onClick={() => setDialogOpen(true)}
          >
            Add friend
          </Button>
        </Grid>
        {!loading
          ? friends.map((friend) => (
              <Card key={friend.username} sx={{ maxWidth: '100%', my: 1 }}>
                <UserHeader
                  username={friend.username}
                  actions="friend"
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
      <FriendDialog
        popupStatus={dialogOpen}
        togglePopup={setDialogOpen}
        showAppMessage={showAppMessage}
      />
    </>
  );
};

export default withSnackbar(Friends);
