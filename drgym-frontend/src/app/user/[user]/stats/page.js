'use client';

import React from 'react';
import Grid from '@mui/material/Grid2';
import Typography from '@mui/material/Typography';
import Calendar from '@/components/Calendar';
import { withSnackbar } from '@/utils/snackbarProvider';
import BodyHighlighter from '@/components/BodyHighlighter';
import Ranking from '@/components/Ranking';

const Stats = ({ params, showAppMessage }) => {
  const { user } = React.use(params);

  return (
    <Grid
      container
      justifyContent="center"
      direction="column"
      sx={{
        width: '100%',
        maxWidth: '1000px',
        margin: '0 auto',
        py: 2,
      }}
    >
      <Typography sx={{ mb: 3 }} variant="h6" gutterBottom>
        Your workout calendar
      </Typography>
      <Calendar username={user} />
      <Grid sx={{ mt: 6 }}>
        <BodyHighlighter username={user} />
      </Grid>
      <Grid sx={{ mt: 6 }}>
        <Ranking showAppMessage={showAppMessage} />
      </Grid>
    </Grid>
  );
};

export default withSnackbar(Stats);
