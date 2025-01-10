import { useState } from 'react';
import CardHeader from '@mui/material/CardHeader';
import Avatar from '@mui/material/Avatar';
import Link from 'next/link';
import { IconButton, Typography } from '@mui/material';
import { red } from '@mui/material/colors';
import { useRouter } from 'next/navigation';

export default function UserHeader({ username, avatar, subheader, actions }) {
  const router = useRouter();
  const [loading, setLoading] = useState(false);

  return (
    <>
      <CardHeader
        avatar={
          <IconButton
            onClick={() => router.push(`/user/${username}`)}
            sx={{
              bgcolor: 'transparent',
              padding: '5px',
            }}
          >
            <Avatar
              sx={{
                bgcolor: red[500],
              }}
              aria-label="avatar"
            >
              {String(username).charAt(0).toUpperCase()}
            </Avatar>
          </IconButton>
        }
        title={
          <Link href={`/user/${username}`} passHref>
            <Typography
              sx={{
                color: 'inherit',
                textDecoration: 'none',
                cursor: 'pointer',
                '&:hover': {
                  textDecoration: 'underline',
                },
              }}
            >
              {username}
            </Typography>
          </Link>
        }
        subheader={subheader}
      />
    </>
  );
}
