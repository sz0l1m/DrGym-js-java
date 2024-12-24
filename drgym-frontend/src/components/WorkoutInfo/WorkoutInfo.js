import { useState } from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Divider from '@mui/material/Divider';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import EastIcon from '@mui/icons-material/East';
import FormatListBulletedIcon from '@mui/icons-material/FormatListBulleted';
import Tooltip from '@mui/material/Tooltip';
import ActivityInfo from '@/components/ActivityInfo';
import { formatDate, getDiffInHoursAndMinutes } from '@/utils/dateUtils';
import style from './WorkoutInfo.module.css';

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

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  return (
    <>
      <CardContent>
        <Box className={style.workoutDateTime} sx={{ marginBottom: 4 }}>
          <Tooltip title="Start time">
            <CalendarMonthIcon sx={{ pb: '1px' }} />
          </Tooltip>
          {formatDate(workout.startDate, 'd MMM H:mm')}
          <Tooltip title="End time">
            <EastIcon />
          </Tooltip>
          {formatDate(workout.endDate, 'd MMM H:mm')}
          <Tooltip title="Duration">
            <AccessTimeIcon sx={{ ml: 5 }} />
          </Tooltip>
          {getDiffInHoursAndMinutes(workout.startDate, workout.endDate)}
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
            <Box key={activity.activityId}>
              <Divider sx={{ mb: 3 }} />
              <ActivityInfo activity={activity} />
            </Box>
          ))}
        </CardContent>
      </Collapse>
    </>
  );
}
