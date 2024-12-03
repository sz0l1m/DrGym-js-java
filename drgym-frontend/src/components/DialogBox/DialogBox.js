import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import CommentIcon from '@mui/icons-material/Comment';
import KeyIcon from '@mui/icons-material/Key';
import LanguageIcon from '@mui/icons-material/Language';
import PersonIcon from '@mui/icons-material/Person';
import HttpIcon from '@mui/icons-material/Http';
import { CircularProgress, Typography } from '@mui/material';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogBoxTitle from './DialogBoxTitle';
import useMediaQuery from '@mui/material/useMediaQuery';
import TextField from '@mui/material/TextField';
import InputAdornment from '@mui/material/InputAdornment';
import Button from '@mui/material/Button';
import { useTheme } from '@mui/material/styles';
import style from './DialogBox.module.css';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import * as yup from 'yup';

let schema = yup.object().shape({
  name: yup
    .string()
    .max(30, 'is too long (max 30 chars)')
    .min(2, 'is too short (min 2 chars)')
    .required('required field'),
  url: yup
    .string()
    .url()
    .max(250, 'is too long (max 250 chars)')
    .required('required field'),
  username: yup
    .string()
    .max(30, 'is too long (max 30 chars)')
    .min(2, 'is too short (min 2 chars)')
    .required('required field'),
  passwordHint: yup
    .string()
    .max(250, 'is too long (max 250 chars)')
    .required('required field'),
  notes: yup.string().max(250, 'is too long (max 250 chars)').nullable(),
});

export default function DialogBox({ dialogTitle, popupStatus, togglePopup }) {
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

  const handleClose = () => {
    togglePopup(false);
  };

  return (
    <>
      <Dialog
        fullScreen={fullScreen}
        maxWidth="md"
        fullWidth
        open={popupStatus}
        aria-labelledby="new-workout-dialog"
      >
        <DialogBoxTitle id="new-workout-dialog" onClose={handleClose}>
          {dialogTitle}
        </DialogBoxTitle>
        <Typography>In DialogBox</Typography>
      </Dialog>
    </>
  );
}
