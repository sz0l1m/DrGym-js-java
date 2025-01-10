import React from 'react';
import { useState, useEffect } from 'react';
import { ActivityCalendar } from 'react-activity-calendar';
import { Tooltip, Typography } from '@mui/material';
import Grid from '@mui/material/Grid2';
import axios from 'axios';
import Model from 'react-body-highlighter';
import { bodyData as mockData } from '@/utils/mockData';

const BodyHighlighter = ({ username }) => {
  const [bodyData, setBodyData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBodyData = async () => {
      try {
        setLoading(true);
        // const response = await axios.get(`/api/body/${username}`);
        // setBodyData(response.data);
        setTimeout(() => {
          setBodyData(mockData);
          setLoading(false);
        }, 1500);
      } catch (error) {
        setError('Failed to load the data');
      } finally {
        // setLoading(false);
      }
    };

    fetchBodyData();
  }, [username]);

  const handleClick = React.useCallback(({ muscle, data }) => {
    const { exercises, frequency } = data;

    alert(
      `You clicked the ${muscle}! You've worked out this muscle ${frequency} times through the following exercises: ${JSON.stringify(exercises)}`
    );
  }, []);

  return (
    <>
      {error && (
        <Typography sx={{ mb: 2 }} textAlign="center" color="error">
          {error}
        </Typography>
      )}
      <Grid container justifyContent="center">
        <Model
          data={bodyData}
          highlightedColors={['#bbdefb', '#3a88d5', '#0d47a1']}
          style={{ width: '15rem', padding: '1rem' }}
          onClick={handleClick}
        />
        <Model
          type="posterior"
          data={bodyData}
          highlightedColors={['#bbdefb', '#3a88d5', '#0d47a1']}
          style={{ width: '15rem', padding: '1rem' }}
          onClick={handleClick}
        />
      </Grid>
    </>
  );
};

export default BodyHighlighter;
