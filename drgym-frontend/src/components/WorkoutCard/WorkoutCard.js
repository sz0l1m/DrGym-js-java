import { useState } from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import IconButton from '@mui/material/IconButton';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import EditIcon from '@mui/icons-material/Edit';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import WorkoutForm from '@/components/WorkoutForm';
import WorkoutInfo from '@/components/WorkoutInfo';
import axiosInstance from '@/utils/axiosInstance';
import DeleteConfirmation from '@/components/DeleteConfirmation';
import { formatDate, formatRelativeTime } from '@/utils/dateUtils';

export default function WorkoutCard({
  workout,
  onDelete,
  onEditWorkout,
  showAppMessage,
}) {
  const [anchorEl, setAnchorEl] = useState(null);
  const [openDeleteConfirmation, setOpenDeleteConfirmation] = useState(false);
  const [openDialog, setOpenDialog] = useState(false);
  const [loading, setLoading] = useState(false);

  const menuOpen = Boolean(anchorEl);
  const realitveStartDate = formatRelativeTime(workout.startDate);

  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const deleteWorkout = async () => {
    try {
      setLoading(true);
      await axiosInstance.delete(`/api/workouts/${workout.workoutId}`);
      showAppMessage({
        status: true,
        text: 'Workout deleted successfully',
        type: 'success',
      });
      onDelete(workout.workoutId);
    } catch (error) {
      console.error('Error deleting workout:', error);
      showAppMessage({
        status: true,
        text: 'Error deleting workout',
        type: 'error',
      });
    } finally {
      setLoading(false);
      setOpenDeleteConfirmation(false);
    }
  };

  return (
    <>
      <Box sx={{ width: '100%', maxWidth: '1000px', margin: '0 auto', py: 2 }}>
        <Card sx={{ maxWidth: '100%' }}>
          <CardHeader
            action={
              <IconButton aria-label="settings" onClick={handleMenuClick}>
                <MoreVertIcon />
              </IconButton>
            }
            title={formatDate(workout.startDate, 'd MMMM yyyy')}
            subheader={
              realitveStartDate.charAt(0).toUpperCase() +
              realitveStartDate.slice(1)
            }
          />
          <Menu
            anchorEl={anchorEl}
            open={menuOpen}
            onClose={handleMenuClose}
            anchorOrigin={{
              vertical: 'bottom',
              horizontal: 'center',
            }}
            transformOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
          >
            <MenuItem onClick={() => setOpenDialog(true)}>
              <EditIcon sx={{ mr: 1 }} />
              Edit
            </MenuItem>
            <MenuItem onClick={() => setOpenDeleteConfirmation(true)}>
              <DeleteForeverIcon sx={{ mr: 1 }} />
              Delete
            </MenuItem>
          </Menu>
          <WorkoutInfo workout={workout} />
        </Card>
        <WorkoutForm
          dialogTitle="Edit workout"
          popupType="edit"
          popupStatus={openDialog}
          togglePopup={setOpenDialog}
          workout={workout}
          onEditWorkout={onEditWorkout}
          showAppMessage={showAppMessage}
        />
        <DeleteConfirmation
          message="Are you sure you want to delete this workout?"
          open={openDeleteConfirmation}
          loading={loading}
          onConfirm={deleteWorkout}
          onClose={() => setOpenDeleteConfirmation(false)}
        />
      </Box>
    </>
  );
}
