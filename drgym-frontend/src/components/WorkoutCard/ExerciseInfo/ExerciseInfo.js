import Box from '@mui/material/Box';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import BalanceIcon from '@mui/icons-material/Balance';
import LoopIcon from '@mui/icons-material/Loop';
import style from './ExerciseInfo.module.css';

export default function ExerciseInfo() {
  return (
    <Box className={style.exercise}>
      <Box className={style.exerciseElement}>
        <FitnessCenterIcon />
        Bench press
      </Box>
      <Box className={style.exerciseElement}>
        <LoopIcon />
        10
      </Box>
      <Box className={style.exerciseElement}>
        <BalanceIcon sx={{ ml: 4 }} />
        100 kg
      </Box>
    </Box>
  );
}
