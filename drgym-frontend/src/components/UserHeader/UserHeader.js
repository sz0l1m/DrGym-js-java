import { useState } from 'react';
import CardHeader from '@mui/material/CardHeader';
import Avatar from '@mui/material/Avatar';
import Link from 'next/link';
import { IconButton, Tooltip, Typography } from '@mui/material';
import Grid from '@mui/material/Grid2';
import { red } from '@mui/material/colors';
import { useRouter } from 'next/navigation';
import PersonRemoveIcon from '@mui/icons-material/PersonRemove';

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
                bgcolor: red[900],
              }}
              aria-label="avatar"
            >
              {String(username).charAt(0).toUpperCase()}
            </Avatar>
          </IconButton>
        }
        title={
          <Grid container justifyContent="space-between" alignItems="center">
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
            {actions && (
              <Tooltip title="Remove friend">
                <IconButton aria-label="settings">
                  <PersonRemoveIcon color="error" />
                </IconButton>
              </Tooltip>
            )}
          </Grid>
        }
        subheader={subheader}
      />
    </>
  );
}
