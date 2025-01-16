import Box from '@mui/material/Box';
import Tooltip from '@mui/material/Tooltip';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import BalanceIcon from '@mui/icons-material/Balance';
import LoopIcon from '@mui/icons-material/Loop';
import MonitorHeartOutlinedIcon from '@mui/icons-material/MonitorHeartOutlined';
import TimerOutlinedIcon from '@mui/icons-material/TimerOutlined';
import SportsGymnasticsIcon from '@mui/icons-material/SportsGymnastics';
import style from './ActivityInfo.module.css';

export default function ActivityInfo({ activity }) {
  const type = activity.exerciseType;

  return (
    <Box className={style.activity}>
      {type === 'C' ? (
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
            {activity.duration}
          </Box>
          <Box sx={{ width: '100px' }} className={style.activityElement}></Box>
        </>
      ) : type === 'S' ? (
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
      ) : (
        <>
          <Box sx={{ width: '200px' }} className={style.activityElement}>
            <Tooltip title="Crossfit exercise">
              <SportsGymnasticsIcon />
            </Tooltip>
            {activity.exerciseName}
          </Box>

          <Box sx={{ width: '100px' }} className={style.activityElement}>
            <Tooltip title="Duration">
              <TimerOutlinedIcon />
            </Tooltip>
            {activity.duration}
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
