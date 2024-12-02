import Box from '@mui/material/Box';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import BalanceIcon from '@mui/icons-material/Balance';
import LoopIcon from '@mui/icons-material/Loop';
import style from './ExerciseInfo.module.css';

export default function ExerciseInfo({ activity }) {
  return (
    <Box className={style.exercise}>
      <Box className={style.exerciseElement}>
        <FitnessCenterIcon />
        {activity.activity_name}
      </Box>
      <Box className={style.exerciseElement}>
        <LoopIcon />
        {activity.sets}
      </Box>
      <Box className={style.exerciseElement}>
        <BalanceIcon sx={{ ml: 4 }} />
        {activity.weight}
      </Box>
    </Box>
  );
}
