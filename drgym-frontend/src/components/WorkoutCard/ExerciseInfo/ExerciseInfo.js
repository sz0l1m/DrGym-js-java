import Box from '@mui/material/Box';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import BalanceIcon from '@mui/icons-material/Balance';
import LoopIcon from '@mui/icons-material/Loop';
import TimerOutlinedIcon from '@mui/icons-material/TimerOutlined';
import style from './ExerciseInfo.module.css';

export default function ExerciseInfo({ activity }) {
  return (
    <Box className={style.exercise}>
      <Box sx={{ width: '200px' }} className={style.exerciseElement}>
        <FitnessCenterIcon />
        {activity.activity_name}
      </Box>
      {activity.duration !== undefined ? (
        <>
          <Box sx={{ width: '100px' }} className={style.exerciseElement}>
            <TimerOutlinedIcon />
            {activity.duration}
          </Box>
          <Box sx={{ width: '100px' }} className={style.exerciseElement}></Box>
        </>
      ) : (
        <>
          <Box sx={{ width: '100px' }} className={style.exerciseElement}>
            <LoopIcon />
            {activity.sets}
          </Box>
          <Box sx={{ width: '100px' }} className={style.exerciseElement}>
            <BalanceIcon sx={{ ml: 4 }} />
            {activity.weight}
          </Box>
        </>
      )}
    </Box>
  );
}
