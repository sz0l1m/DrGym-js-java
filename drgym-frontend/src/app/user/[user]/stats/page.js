'use client';

import React from 'react';
import Grid from '@mui/material/Grid2';
import Typography from '@mui/material/Typography';
import Calendar from '@/components/Calendar';

const Stats = ({ params }) => {
  const { username } = React.use(params);

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
        <Typography sx={{ mb: 3 }} variant="h6" gutterBottom>
          Your workout calendar
        </Typography>
        <Calendar username={username} />
      </Grid>
    </Grid>
  );
};

export default Stats;
