import { useState, useEffect } from 'react';
import { Box, Typography } from '@mui/material';
import Grid from '@mui/material/Grid2';
import Post from '@/components/Post';
import SkeletonCard from '@/components/SkeletonCard';
import axiosInstance from '@/utils/axiosInstance';

const PostList = ({ username, onlyThisUser, showAppMessage }) => {
  const [workoutsData, setWorkoutsData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // FIXME
  useEffect(() => {
    const fetchWorkouts = async () => {
      try {
        setLoading(true);
        if (onlyThisUser) {
          const response = await axiosInstance.get(
            `/api/users/${username}/workouts`
          );
          setWorkoutsData(response.data);
        } else {
          const response = await axiosInstance.get(
            `/api/users/${username}/workouts`
          );
          setWorkoutsData(response.data);
        }
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

    if (username) {
      fetchWorkouts();
    }
  }, [username, onlyThisUser, showAppMessage]);

  if (loading)
    return (
      <Grid container direction="column" alignItems="center">
        {Array.from({ length: 3 }).map((_, index) => (
          <SkeletonCard key={index} type="post" />
        ))}
      </Grid>
    );
  if (error) return <Typography textAlign="center">{error}</Typography>;
  return (
    <Grid container direction="column" alignItems="center">
      {workoutsData.length === 0 ? (
        <Typography variant="h6">There are no posts here yet.</Typography>
      ) : (
        workoutsData.map((workout) => (
          <Post key={workout.workoutId} workout={workout} />
        ))
      )}
    </Grid>
  );
};

export default PostList;
