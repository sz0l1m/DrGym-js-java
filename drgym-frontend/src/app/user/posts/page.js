'use client';

import { useState, useEffect } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import { withSnackbar } from '@/utils/snackbarProvider';
import Post from '@/components/Post';
import { Skeleton } from '@mui/material';
import axios from 'axios';
import style from './posts.module.css';

const Posts = ({ showAppMessage }) => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const [workoutsData, setWorkoutsData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchWorkouts = async () => {
      try {
        setLoading(true);
        const response1 = await axios.get(
          'http://localhost:8080/api/users/skuter/workouts'
        );
        const response2 = await axios.get(
          'http://localhost:8080/api/users/szolim/workouts'
        );
        setWorkoutsData([...response1.data, ...response2.data]);
      } catch (err) {
        setError(err.message);
        showAppMessage({
          status: true,
          text: 'Error fetching workouts',
          type: 'error',
        });
      } finally {
        setLoading(false);
      }
    };

    fetchWorkouts();
  }, [showAppMessage]);

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

  if (loading) return <Typography>Loading workouts...</Typography>;
  if (error) return <Typography>Error: {error}</Typography>;
  return (
    <>
      <Box className={style.postsWrapper}>
        {workoutsData.map((workout) => (
          <Post key={workout.workoutId} workout={workout} />
        ))}
      </Box>
    </>
  );
};

export default withSnackbar(Posts);
