import React from 'react';
import Typography from '@mui/material/Typography';

const Stats = ({ params }) => {
  const { user } = React.use(params);
  return (
    <>
      <Typography variant="h4" gutterBottom>
        Stats for {user}
      </Typography>
    </>
  );
};

export default Stats;
