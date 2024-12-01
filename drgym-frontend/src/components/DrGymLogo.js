import { Typography } from '@mui/material';
import Link from 'next/link';
import style from './DrGymLogo.module.css';

export default function DrGymLogo() {
  return (
    <Link href="/" className={style.drGymLink}>
      <Typography
        color="primary"
        variant="h5"
        component="div"
        sx={{ flexGrow: 1, px: '5px' }}
      >
        DrGym
      </Typography>
    </Link>
  );
}
