import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';
import style from './DeleteConfirmation.module.css';

export default function DeleteConfirmation({
  title = 'Warning!',
  message,
  open,
  onConfirm,
  onClose,
}) {
  return (
    <>
      <Dialog
        open={open}
        onClose={onClose}
        aria-labelledby="delete-confirmation"
      >
        <DialogTitle id="delete-confirmation" className={style.dialogTitle}>
          {title}
        </DialogTitle>
        <DialogContent sx={{ p: 2 }}>{message}</DialogContent>
        <DialogActions>
          <Button color="secondary" onClick={onClose}>
            Cancel
          </Button>
          <Button
            variant="contained"
            color="error"
            onClick={onConfirm}
            // disabled={loading}
            // endIcon={
            //   loading && <CircularProgress color="secondary" size={18} />
            // }
          >
            Delete
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
