import Box from '@mui/material/Box';
import Tooltip from '@mui/material/Tooltip';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import BalanceIcon from '@mui/icons-material/Balance';
import LoopIcon from '@mui/icons-material/Loop';
import MonitorHeartOutlinedIcon from '@mui/icons-material/MonitorHeartOutlined';
import TimerOutlinedIcon from '@mui/icons-material/TimerOutlined';
import style from './ActivityInfo.module.css';
import { formatDate } from '@/utils/dateUtils';

export default function ActivityInfo({ activity }) {
  const duration = formatDate(activity.duration, 'HH:mm:ss');

  return (
    <Box className={style.activity}>
      {duration !== '00:00:00' ? (
        <>
          <Box sx={{ width: '200px' }} className={style.activityElement}>
            <Tooltip title="Cardio exercise">
              <MonitorHeartOutlinedIcon />
            </Tooltip>
            {activity.exerciseName}
          </Box>

          <Box sx={{ width: '100px' }} className={style.activityElement}>
            <Tooltip title="Duration">
              <TimerOutlinedIcon />
            </Tooltip>
            {duration}
          </Box>
          <Box sx={{ width: '100px' }} className={style.activityElement}></Box>
        </>
      ) : (
        <>
          <Box sx={{ width: '200px' }} className={style.activityElement}>
            <Tooltip title="Strength exercise">
              <FitnessCenterIcon />
            </Tooltip>
            {activity.exerciseName}
          </Box>

          <Box sx={{ width: '100px' }} className={style.activityElement}>
            <Tooltip title="Number of reps">
              <LoopIcon />
            </Tooltip>
            {activity.reps}
          </Box>

          <Box sx={{ width: '100px' }} className={style.activityElement}>
            <Tooltip title="Weight [kg]">
              <BalanceIcon sx={{ ml: 4 }} />
            </Tooltip>
            {activity.weight}
          </Box>
        </>
      )}
    </Box>
  );
}
