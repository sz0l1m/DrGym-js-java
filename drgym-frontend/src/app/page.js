'use client';

import Typography from '@mui/material/Typography';
import YouTubePlayer from '@/components/YouTubePlayer';

export default function HomePage() {
  return (
    <>
      <Typography variant="h4" gutterBottom>
        Main Page
      </Typography>
      <YouTubePlayer videoId="dQw4w9WgXcQ" />
      <YouTubePlayer videoId="dQw4w9WgXcQ" />
    </>
  );
}
