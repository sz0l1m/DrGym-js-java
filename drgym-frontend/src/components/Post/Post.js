import { useState } from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import WorkoutInfo from '@/components/WorkoutInfo';
import { formatRelativeTime } from '@/utils/dateUtils';
import UserHeader from '@/components/UserHeader';
import { Typography } from '@mui/material';
import Grid from '@mui/material/Grid2';
import { IconButton, Tooltip } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import DeleteConfirmation from '@/components/DeleteConfirmation';

export default function Post({ post, actions }) {
  const workout = post.training;
  const realitveStartDate = formatRelativeTime(workout.startDate);

  return (
    <>
      <Box sx={{ width: '100%', maxWidth: '1000px', margin: '0 auto', py: 2 }}>
        <Card sx={{ maxWidth: '100%' }}>
          <Grid container justifyContent="space-between">
            <Typography sx={{ pt: 2, pl: 2, m: 0 }} variant="h5" gutterBottom>
              {post.title}
            </Typography>
            {actions && (
              <Box sx={{ pt: 2, pr: 2 }}>
                <Tooltip title="Edit post">
                  <IconButton
                    aria-label="edit post"
                    // onClick={handleDeleteClick}
                  >
                    <EditIcon color="info" />
                  </IconButton>
                </Tooltip>
                <Tooltip title="Delete post" sx={{ ml: 1 }}>
                  <IconButton
                    aria-label="delete post"
                    // onClick={handleDeleteClick}
                  >
                    <DeleteIcon color="error" />
                  </IconButton>
                </Tooltip>
              </Box>
            )}
          </Grid>
          <UserHeader
            username={workout.username}
            subheader={
              realitveStartDate.charAt(0).toUpperCase() +
              realitveStartDate.slice(1)
            }
          />
          <Typography sx={{ pb: 1, pl: 2, m: 0 }} variant="body2" gutterBottom>
            {post.content}
          </Typography>
          <WorkoutInfo workout={workout} isPost />
        </Card>
      </Box>
    <DeleteConfirmation open={true} />
    </>
  );
}
