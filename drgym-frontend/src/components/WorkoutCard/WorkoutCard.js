import { useState, forwardRef } from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Divider from '@mui/material/Divider';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import EastIcon from '@mui/icons-material/East';
import FormatListBulletedIcon from '@mui/icons-material/FormatListBulleted';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import EditIcon from '@mui/icons-material/Edit';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import ExerciseInfo from './ExerciseInfo';
import DeleteConfirmation from '@/components/DeleteConfirmation';
import {
  formatDate,
  formatRelativeTime,
  getDiffInHoursAndMinutes,
} from '@/utils/dateUtils';
import style from './WorkoutCard.module.css';

const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme }) => ({
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
  variants: [
    {
      props: ({ expand }) => !expand,
      style: {
        transform: 'rotate(0deg)',
      },
    },
    {
      props: ({ expand }) => !!expand,
      style: {
        transform: 'rotate(180deg)',
      },
    },
  ],
}));

export default function WorkoutCard({ workout }) {
  const [expanded, setExpanded] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const [openDeleteConfirmation, setOpenDeleteConfirmation] = useState(false);

  const menuOpen = Boolean(anchorEl);
  const realitveStartDate = formatRelativeTime(workout.start_date);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
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
            title={formatDate(workout.start_date, 'd MMMM yyyy')}
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
            <MenuItem onClick={handleMenuClose}>
              <EditIcon sx={{ mr: 1 }} />
              Edit
            </MenuItem>
            <MenuItem onClick={() => setOpenDeleteConfirmation(true)}>
              <DeleteForeverIcon sx={{ mr: 1 }} />
              Delete
            </MenuItem>
          </Menu>
          <CardContent>
            <Box className={style.workoutDateTime} sx={{ marginBottom: 4 }}>
              <CalendarMonthIcon sx={{ pb: '1px' }} />
              {formatDate(workout.start_date, 'd MMM H:mm')}
              <EastIcon />
              {formatDate(workout.end_date, 'd MMM H:mm')}
              <AccessTimeIcon sx={{ ml: 4 }} />
              {getDiffInHoursAndMinutes(workout.start_date, workout.end_date)}
            </Box>
            {workout.description && (
              <Typography sx={{ mt: 3 }}>{workout.description}</Typography>
            )}
          </CardContent>
          <CardActions sx={{ px: 2, pb: 2 }}>
            <FormatListBulletedIcon sx={{ mr: '4px' }} />
            <Typography>Exercises</Typography>
            <ExpandMore
              expand={expanded}
              onClick={handleExpandClick}
              aria-expanded={expanded}
              aria-label="show more"
            >
              <ExpandMoreIcon />
            </ExpandMore>
          </CardActions>
          <Collapse in={expanded} timeout="auto" unmountOnExit>
            <CardContent>
              {workout.activities.map((activity) => (
                <Box key={activity.activity_id}>
                  <Divider sx={{ mb: 3 }} />
                  <ExerciseInfo activity={activity} />
                </Box>
              ))}
            </CardContent>
          </Collapse>
        </Card>
        <DeleteConfirmation
          message="Are you sure you want to delete this workout?"
          open={openDeleteConfirmation}
          onConfirm={() => setOpenDeleteConfirmation(false)}
          onClose={() => setOpenDeleteConfirmation(false)}
        />
      </Box>
    </>
  );
}
