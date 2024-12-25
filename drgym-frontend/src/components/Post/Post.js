import { useState } from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import Avatar from '@mui/material/Avatar';
import WorkoutInfo from '@/components/WorkoutInfo';
import { red } from '@mui/material/colors';
import { formatRelativeTime } from '@/utils/dateUtils';

export default function WorkoutCard({ workout }) {
  const [loading, setLoading] = useState(false);

  const realitveStartDate = formatRelativeTime(workout.startDate);

  return (
    <>
      <Box sx={{ width: '100%', maxWidth: '1000px', margin: '0 auto', py: 2 }}>
        <Card sx={{ maxWidth: '100%' }}>
          <CardHeader
            avatar={
              <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                {String(workout.username).charAt(0).toUpperCase()}
              </Avatar>
            }
            title={workout.username}
            subheader={
              realitveStartDate.charAt(0).toUpperCase() +
              realitveStartDate.slice(1)
            }
          />
          <WorkoutInfo workout={workout} />
        </Card>
      </Box>
    </>
  );
}
