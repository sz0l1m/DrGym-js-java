'use client';

import { useState, useEffect } from 'react';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import ToggleButton from '@mui/material/ToggleButton';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import SportsGymnasticsIcon from '@mui/icons-material/SportsGymnastics';
import Autocomplete from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import axiosInstance from '@/utils/axiosInstance';

export default function Ranking({ username, showAppMessage }) {
  const [loading, setLoading] = useState(true);
  const [loadingRanking, setLoadingRanking] = useState(true);
  const [error, setError] = useState(null);
  const [exerciseType, setExerciseType] = useState('strength');
  const [exercise, setExercise] = useState('');
  const [exercisesNames, setExercisesNames] = useState({});
  const [ranking, setRanking] = useState(null);

  useEffect(() => {
    const fetchExercises = async () => {
      try {
        setLoading(true);
        const response = await axiosInstance.get(`/api/exercises/with-ranking`);
        console.log('exercises', response.data);
        setExercisesNames(response.data);
      } catch (err) {
        setError('Failed to fetch exercises');
        showAppMessage({
          status: true,
          text: 'Something went wrong',
          type: 'error',
        });
      } finally {
        setLoading(false);
      }
    };
    fetchExercises();
  }, [showAppMessage]);

  useEffect(() => {
    const fetchRanking = async (exercise) => {
      try {
        setLoadingRanking(true);
        const response = await axiosInstance.get(
          `/api/users/${username}/ranking?exerciseId=${exercise.id}`
        );
        console.log('exerciseType', exerciseType);
        console.log('ranking', response.data);
        setRanking(response.data);
      } catch (err) {
        setError('Failed to fetch ranking');
        showAppMessage({
          status: true,
          text: 'Something went wrong',
          type: 'error',
        });
      } finally {
        setLoadingRanking(false);
      }
    };
    if (exercise) {
      fetchRanking(exercise);
    } else if (exercisesNames[exerciseType]?.length > 0)
      fetchRanking(exercisesNames[exerciseType][0]);
  }, [exercise, exerciseType, exercisesNames, username, showAppMessage]);

  if (loading) {
    return <Typography textAlign="center">Loading rankings...</Typography>;
  }
  if (error) {
    return (
      <Typography textAlign="center" color="error">
        {error}
      </Typography>
    );
  }
  return (
    <>
      <ToggleButtonGroup
        color="info"
        value={exerciseType}
        exclusive
        onChange={(event, newType) => setExerciseType(newType)}
        aria-label="Exercise Type Selector"
        sx={{ mb: 2 }}
      >
        <ToggleButton value="strength">
          <FitnessCenterIcon sx={{ mr: 1 }} />
          Strength
        </ToggleButton>
        <ToggleButton value="crossfit">
          <SportsGymnasticsIcon sx={{ mr: 1 }} />
          CrossFit
        </ToggleButton>
      </ToggleButtonGroup>
      {exercisesNames[exerciseType].length ? (
        <Autocomplete
          options={exercisesNames[exerciseType]}
          getOptionLabel={(option) => option.name || ''}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          value={exercise || exercisesNames[exerciseType][0]}
          onChange={(event, newValue) => {
            setExercise(newValue);
          }}
          renderInput={(params) => (
            <TextField {...params} label={'Exercise'} name="exercise" />
          )}
        />
      ) : (
        <Typography color="textSecondary">
          There are no rankings yet for this type of exercise
        </Typography>
      )}
      {!loadingRanking ? (
        <Typography textAlign="center">Loaded...</Typography>
      ) : (
        <Typography textAlign="center">Loading ranking...</Typography>
      )}
    </>
  );
}
