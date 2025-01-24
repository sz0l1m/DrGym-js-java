'use client';

import React, { useState } from 'react';
import YouTube from 'react-youtube';
import Skeleton from '@mui/material/Skeleton';
import Box from '@mui/material/Box';

const YouTubePlayer = ({ videoId }) => {
  const [loading, setLoading] = useState(true);

  const opts = {
    height: '390px',
    width: '640px',
    playerVars: {
      autoplay: 0,
    },
  };

  const onReady = (event) => {
    setLoading(false);
  };

  return (
    <Box sx={{ position: 'relative', width: opts.width, height: opts.height }}>
      {loading && (
        <Skeleton
          variant="rectangular"
          width={opts.width}
          height={opts.height}
          animation="wave"
        />
      )}

      <Box
        sx={{
          display: loading ? 'none' : 'block',
          position: 'absolute',
          top: 0,
          left: 0,
        }}
      >
        <YouTube videoId={videoId} opts={opts} onReady={onReady} />
      </Box>
    </Box>
  );
};

export default YouTubePlayer;
