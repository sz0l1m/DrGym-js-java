import { useState } from 'react';
import CardHeader from '@mui/material/CardHeader';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Link from 'next/link';
import {
  IconButton,
  Tooltip,
  Typography,
  CircularProgress,
} from '@mui/material';
import Grid from '@mui/material/Grid2';
import { red } from '@mui/material/colors';
import { useRouter } from 'next/navigation';
import PersonRemoveIcon from '@mui/icons-material/PersonRemove';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import DeleteConfirmation from '@/components/DeleteConfirmation';

export default function UserHeader({
  username,
  avatar,
  subheader,
  actions,
  onDelete,
  onAccept,
  onDecline,
}) {
  const router = useRouter();
  const [loading, setLoading] = useState(false);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [deleting, setDeleting] = useState(false);

  const handleDeleteClick = () => {
    setDeleteDialogOpen(true);
  };

  const handleConfirmDelete = async () => {
    try {
      setDeleting(true);
      await onDelete(username);
    } finally {
      setDeleting(false);
      setDeleteDialogOpen(false);
    }
  };

  const handleCancelDelete = () => {
    setDeleteDialogOpen(false);
  };

  const handleAcceptRequest = async () => {
    try {
      setLoading(true);
      await onAccept(username, avatar);
    } finally {
      setLoading(false);
    }
  };

  const handleDeclineRequest = async () => {
    try {
      setLoading(true);
      await onDecline(username);
    } finally {
      setLoading(false);
    }
  };

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
            {actions === 'friend' && (
              <Tooltip title="Remove friend">
                <IconButton
                  aria-label="remove friend"
                  onClick={handleDeleteClick}
                >
                  <PersonRemoveIcon color="error" />
                </IconButton>
              </Tooltip>
            )}
            {actions === 'request' && (
              <Box>
                {loading ? (
                  <CircularProgress size={24} sx={{ mr: 4 }} />
                ) : (
                  <>
                    <Tooltip title="Accept request">
                      <IconButton
                        disabled={loading}
                        aria-label="accept request"
                        onClick={handleAcceptRequest}
                      >
                        <CheckIcon color="success" />
                      </IconButton>
                    </Tooltip>
                    <Tooltip title="Decline request">
                      <IconButton
                        disabled={loading}
                        aria-label="decline request"
                        onClick={handleDeclineRequest}
                      >
                        <CloseIcon color="error" />
                      </IconButton>
                    </Tooltip>
                  </>
                )}
              </Box>
            )}
          </Grid>
        }
        subheader={subheader}
      />
      <DeleteConfirmation
        title="Remove Friend"
        message={`Are you sure you want to remove ${username} from your friends?`}
        open={deleteDialogOpen}
        loading={deleting}
        onConfirm={handleConfirmDelete}
        onClose={handleCancelDelete}
      />
    </>
  );
}
