import Box from '@mui/material/Box';
import Tooltip from '@mui/material/Tooltip';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import BalanceIcon from '@mui/icons-material/Balance';
import LoopIcon from '@mui/icons-material/Loop';
import MonitorHeartOutlinedIcon from '@mui/icons-material/MonitorHeartOutlined';
import TimerOutlinedIcon from '@mui/icons-material/TimerOutlined';
import style from './ExerciseInfo.module.css';

export default function ExerciseInfo({ activity }) {
  return (
    <Box className={style.exercise}>
      {activity.duration !== undefined ? (
        <>
          <Box sx={{ width: '200px' }} className={style.exerciseElement}>
            <Tooltip title="Cardio exercise">
              <MonitorHeartOutlinedIcon />
            </Tooltip>
            {activity.activity_name}
          </Box>

          <Box sx={{ width: '100px' }} className={style.exerciseElement}>
            <Tooltip title="Duration">
              <TimerOutlinedIcon />
            </Tooltip>
            {activity.duration}
          </Box>
          <Box sx={{ width: '100px' }} className={style.exerciseElement}></Box>
        </>
      ) : (
        <>
          <Box sx={{ width: '200px' }} className={style.exerciseElement}>
            <Tooltip title="Strength exercise">
              <FitnessCenterIcon />
            </Tooltip>
            {activity.activity_name}
          </Box>

          <Box sx={{ width: '100px' }} className={style.exerciseElement}>
            <Tooltip title="Number of sets">
              <LoopIcon />
            </Tooltip>
            {activity.sets}
          </Box>

          <Box sx={{ width: '100px' }} className={style.exerciseElement}>
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
