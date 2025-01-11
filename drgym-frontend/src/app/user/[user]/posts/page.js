'use client';

import { useState, useEffect, Suspense } from 'react';
import { useRouter, usePathname, useSearchParams } from 'next/navigation';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid2';
import { withSnackbar } from '@/utils/snackbarProvider';
import Post from '@/components/Post';
import axios from 'axios';
import SkeletonCard from '@/components/SkeletonCard';
import { useSession } from 'next-auth/react';

const PostsContent = ({ showAppMessage }) => {
  const { data: session, status } = useSession();
  const username = session?.user?.username;
  const router = useRouter();
  const searchParams = useSearchParams();
  const pathname = usePathname();
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
      router.replace(`${pathname}`, undefined, { shallow: true });
      showAppMessage({
        status: true,
        text: message,
        type: type || 'info',
      });
    }
  }, [router, pathname, searchParams, showAppMessage]);

  if (error) return <Typography>Error: {error}</Typography>;
  return (
    <Grid container direction="column" alignItems="center">
      {loading
        ? Array.from({ length: 3 }).map((_, index) => (
            <SkeletonCard key={index} type="post" />
          ))
        : workoutsData.map((workout) => (
            <Post key={workout.workoutId} workout={workout} />
          ))}
    </Grid>
  );
};

const Posts = ({ showAppMessage }) => {
  return (
    <Suspense fallback={<Typography>Loading posts...</Typography>}>
      <PostsContent showAppMessage={showAppMessage} />
    </Suspense>
  );
};

export default withSnackbar(Posts);
