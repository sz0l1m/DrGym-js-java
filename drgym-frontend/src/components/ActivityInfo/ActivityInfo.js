import Box from '@mui/material/Box';
import Tooltip from '@mui/material/Tooltip';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import BalanceIcon from '@mui/icons-material/Balance';
import LoopIcon from '@mui/icons-material/Loop';
import MonitorHeartOutlinedIcon from '@mui/icons-material/MonitorHeartOutlined';
import TimerOutlinedIcon from '@mui/icons-material/TimerOutlined';
import SportsGymnasticsIcon from '@mui/icons-material/SportsGymnastics';
import Grid from '@mui/material/Grid2';
import style from './ActivityInfo.module.css';

export default function ActivityInfo({ activity }) {
  const type = activity.exerciseType;

  return (
    <Grid
      container
      flexWrap="wrap"
      sx={{
        justifyContent: { xs: 'flex-start', sm: 'space-between' },
        maxWidth: '600px',
        mb: 3,
        mr: 3,
      }}
    >
      {type === 'C' ? (
        <>
          <Box
            sx={{ width: { xs: '100%', sm: '200px' } }}
            className={style.activityElement}
          >
            <Tooltip title="Cardio exercise">
              <MonitorHeartOutlinedIcon />
            </Tooltip>
            {activity.exerciseName}
          </Box>

          <Box
            sx={{ width: '100px', pt: { xs: 2, sm: 0 } }}
            className={style.activityElement}
          >
            <Tooltip title="Duration">
              <TimerOutlinedIcon />
            </Tooltip>
            {activity.duration}
          </Box>
          <Box sx={{ width: '100px' }} className={style.activityElement}></Box>
        </>
      ) : type === 'S' ? (
        <>
          <Box
            sx={{ width: { xs: '100%', sm: '200px' } }}
            className={style.activityElement}
          >
            <Tooltip title="Strength exercise">
              <FitnessCenterIcon />
            </Tooltip>
            {activity.exerciseName}
          </Box>

          <Box
            sx={{ width: '100px', pt: { xs: 2, sm: 0 } }}
            className={style.activityElement}
          >
            <Tooltip title="Number of reps">
              <LoopIcon />
            </Tooltip>
            {activity.reps}
          </Box>

          <Box
            sx={{ width: '100px', pt: { xs: 2, sm: 0 } }}
            className={style.activityElement}
          >
            <Tooltip title="Weight [kg]">
              <BalanceIcon sx={{ ml: 4 }} />
            </Tooltip>
            {activity.weight}
          </Box>
        </>
      ) : (
        <>
          <Box
            sx={{ width: { xs: '100%', sm: '200px' } }}
            className={style.activityElement}
          >
            <Tooltip title="Crossfit exercise">
              <SportsGymnasticsIcon />
            </Tooltip>
            {activity.exerciseName}
          </Box>

          <Box
            sx={{ width: '100px', pt: { xs: 2, sm: 0 } }}
            className={style.activityElement}
          >
            <Tooltip title="Duration">
              <TimerOutlinedIcon />
            </Tooltip>
            {activity.duration}
          </Box>

          <Box
            sx={{ width: '100px', pt: { xs: 2, sm: 0 } }}
            className={style.activityElement}
          >
            <Tooltip title="Weight [kg]">
              <BalanceIcon sx={{ ml: 4 }} />
            </Tooltip>
            {activity.weight}
          </Box>
        </>
      )}
    </Grid>
  );
}
