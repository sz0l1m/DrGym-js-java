'use client';

import React, { useState } from 'react';
import YouTube from 'react-youtube';
import Skeleton from '@mui/material/Skeleton';
import Box from '@mui/material/Box';

const YouTubePlayer = ({ videoId }) => {
  const [loading, setLoading] = useState(true);

  const opts = {
    playerVars: {
      autoplay: 0,
    },
  };

  const onReady = (event) => {
    setLoading(false);
  };

  return (
    <Box
      sx={{
        position: 'relative',
        width: '100%',
        maxWidth: '640px',
        aspectRatio: '16 / 9',
        margin: '0 auto',
        overflow: 'hidden',
      }}
    >
      {loading && (
        <Skeleton
          variant="rectangular"
          sx={{
            width: '100%',
            height: '100%',
            position: 'absolute',
            top: 0,
            left: 0,
          }}
          animation="wave"
        />
      )}

      <Box
        sx={{
          display: loading ? 'none' : 'block',
          position: 'absolute',
          top: 0,
          left: 0,
          width: '100%',
          height: '100%',
        }}
      >
        <YouTube videoId={videoId} opts={opts} onReady={onReady} />
      </Box>
    </Box>
  );
};

export default YouTubePlayer;
