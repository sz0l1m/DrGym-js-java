import { forwardRef } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';
import style from './DeleteConfirmation.module.css';

const Transition = forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

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
        TransitionComponent={Transition}
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

// export default function AlertDialogSlide() {
//   const [open, setOpen] = React.useState(false);

//   const handleClickOpen = () => {
//     setOpen(true);
//   };

//   const handleClose = () => {
//     setOpen(false);
//   };

//   return (
//     <React.Fragment>
//       <Button variant="outlined" onClick={handleClickOpen}>
//         Slide in alert dialog
//       </Button>
//       <Dialog
//         open={open}
//         TransitionComponent={Transition}
//         keepMounted
//         onClose={handleClose}
//         aria-describedby="alert-dialog-slide-description"
//       >
//         <DialogTitle>{"Use Google's location service?"}</DialogTitle>
//         <DialogContent>
//           <DialogContentText id="alert-dialog-slide-description">
//             Let Google help apps determine location. This means sending anonymous
//             location data to Google, even when no apps are running.
//           </DialogContentText>
//         </DialogContent>
//         <DialogActions>
//           <Button onClick={handleClose}>Disagree</Button>
//           <Button onClick={handleClose}>Agree</Button>
//         </DialogActions>
//       </Dialog>
//     </React.Fragment>
//   );
// }
