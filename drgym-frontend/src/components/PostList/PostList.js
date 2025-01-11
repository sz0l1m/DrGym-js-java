import { useState, useEffect } from 'react';
import { Box, Typography } from '@mui/material';
import Grid from '@mui/material/Grid2';
import Post from '@/components/Post';
import SkeletonCard from '@/components/SkeletonCard';
import axios from 'axios';

const PostList = ({ username, showAppMessage }) => {
  const [workoutsData, setWorkoutsData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchWorkouts = async () => {
      try {
        setLoading(true);
        const response1 = await axios.get(
          `${process.env.NEXT_PUBLIC_API_URL}/api/users/szolim/workouts`
        );
        const response2 = await axios.get(
          `${process.env.NEXT_PUBLIC_API_URL}/api/users/pedziwiatr/workouts`
        );
        setWorkoutsData([...response1.data, ...response2.data]);
      } catch (err) {
        setError('Error fetching posts');
        showAppMessage({
          status: true,
          text: 'Something went wrong',
          type: 'error',
        });
      } finally {
        setLoading(false);
      }
    };

    fetchWorkouts();
  }, [showAppMessage]);

  if (error) return <Typography textAlign="center">{error}</Typography>;
  return (
    <Grid container direction="column" alignItems="center">
      {loading ? (
        Array.from({ length: 3 }).map((_, index) => (
          <SkeletonCard key={index} type="post" />
        ))
      ) : workoutsData.length === 0 ? (
        <Typography variant="body1">There are no posts here yet.</Typography>
      ) : (
        workoutsData.map((workout) => (
          <Post key={workout.workoutId} workout={workout} />
        ))
      )}
    </Grid>
  );
};

export default PostList;
