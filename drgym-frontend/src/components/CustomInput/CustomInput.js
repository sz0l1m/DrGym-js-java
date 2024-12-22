import React from 'react';
import {
  FormControl,
  InputLabel,
  OutlinedInput,
  InputAdornment,
  IconButton,
} from '@mui/material';

const CustomInput = ({
  label,
  name,
  type = 'text',
  value,
  error,
  touched,
  onBlur,
  onChange,
  tabIndex,
  endAdornment,
}) => {
  const showError = !!error && (!!touched || !!value);

  return (
    <FormControl fullWidth>
      <InputLabel>{showError ? `${label} - ${error}` : label}</InputLabel>
      <OutlinedInput
        error={showError}
        label={showError ? `${label} - ${error}` : label}
        name={name}
        type={type}
        value={value}
        onBlur={onBlur}
        onChange={onChange}
        inputProps={{ tabIndex }}
        endAdornment={endAdornment}
      />
    </FormControl>
  );
};

export default CustomInput;
