import { useState } from 'react';
import { alpha, styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import Container from '@mui/material/Container';
import Divider from '@mui/material/Divider';
import MenuItem from '@mui/material/MenuItem';
import Drawer from '@mui/material/Drawer';
import MenuIcon from '@mui/icons-material/Menu';
import CloseRoundedIcon from '@mui/icons-material/CloseRounded';
import Typography from '@mui/material/Typography';
import ListItemText from '@mui/material/ListItemText';
import ListItemIcon from '@mui/material/ListItemIcon';
import Link from 'next/link';
import style from './CustomAppBar.module.css';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import BarChartIcon from '@mui/icons-material/BarChart';
import GroupIcon from '@mui/icons-material/Group';
import EmojiPeopleIcon from '@mui/icons-material/EmojiPeople';

const StyledToolbar = styled(Toolbar)(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-between',
  flexShrink: 0,
  borderRadius: `calc(${theme.shape.borderRadius}px + 8px)`,
  backdropFilter: 'blur(24px)',
  border: '1px solid',
  borderColor: (theme.vars || theme).palette.divider,
  backgroundColor: theme.vars
    ? `rgba(${theme.vars.palette.background.defaultChannel} / 0.4)`
    : alpha(theme.palette.background.default, 0.4),
  boxShadow: (theme.vars || theme).shadows[1],
  padding: '8px 12px',
}));

export default function CustomAppBar() {
  const [open, setOpen] = useState(false);

  const toggleDrawer = (newOpen) => () => {
    setOpen(newOpen);
  };

  return (
    <AppBar
      position="fixed"
      enableColorOnDark
      sx={{
        boxShadow: 0,
        bgcolor: 'transparent',
        backgroundImage: 'none',
        mt: 'calc(var(--template-frame-height, 0px) + 28px)',
      }}
    >
      <Container maxWidth="lg">
        <StyledToolbar variant="dense" disableGutters>
          <Box
            sx={{ flexGrow: 1, display: 'flex', alignItems: 'center', px: 0 }}
          >
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
            <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
              <Divider orientation="vertical" flexItem sx={{ mx: 1 }} />
              <Button
                variant="text"
                color="secondary"
                size="small"
                startIcon={<EmojiPeopleIcon />}
              >
                Posts
              </Button>
              <Divider orientation="vertical" flexItem sx={{ mx: 1 }} />
              <Button
                variant="text"
                color="secondary"
                size="small"
                startIcon={<FitnessCenterIcon />}
              >
                Your Workouts
              </Button>
              <Divider orientation="vertical" flexItem sx={{ mx: 1 }} />
              <Button
                variant="text"
                color="secondary"
                size="small"
                startIcon={<BarChartIcon />}
              >
                Statistics
              </Button>
              <Divider orientation="vertical" flexItem sx={{ mx: 1 }} />
              <Button
                variant="text"
                color="secondary"
                size="small"
                startIcon={<GroupIcon />}
              >
                Friends
              </Button>
            </Box>
          </Box>
          <Box
            sx={{
              display: { xs: 'none', md: 'flex' },
              gap: 1,
              alignItems: 'center',
            }}
          >
            <Button color="primary" variant="text" size="small">
              Sign in
            </Button>
            <Button color="primary" variant="contained" size="small">
              Sign up
            </Button>
          </Box>
          <Box sx={{ display: { xs: 'flex', md: 'none' }, gap: 1 }}>
            <IconButton aria-label="Menu button" onClick={toggleDrawer(true)}>
              <MenuIcon />
            </IconButton>
            <Drawer
              anchor="top"
              open={open}
              onClose={toggleDrawer(false)}
              PaperProps={{
                sx: {
                  top: 'var(--template-frame-height, 0px)',
                },
              }}
            >
              <Box sx={{ p: 2, backgroundColor: 'background.default' }}>
                <Box
                  sx={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'baseline',
                  }}
                >
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
                  <IconButton onClick={toggleDrawer(false)}>
                    <CloseRoundedIcon />
                  </IconButton>
                </Box>
                <Divider sx={{ my: 2 }} />
                <MenuItem>
                  <ListItemIcon>
                    <EmojiPeopleIcon fontSize="small" />
                  </ListItemIcon>
                  <ListItemText>Posts</ListItemText>
                </MenuItem>
                <MenuItem>
                  <ListItemIcon>
                    <FitnessCenterIcon fontSize="small" />
                  </ListItemIcon>
                  <ListItemText>My Workouts</ListItemText>
                </MenuItem>
                <MenuItem>
                  <ListItemIcon>
                    <BarChartIcon fontSize="small" />
                  </ListItemIcon>
                  <ListItemText>Statistics</ListItemText>
                </MenuItem>
                <MenuItem sx={{ mb: 2 }}>
                  <ListItemIcon>
                    <GroupIcon fontSize="small" />
                  </ListItemIcon>
                  <ListItemText>Friends</ListItemText>
                </MenuItem>
                <Divider />
                <MenuItem sx={{ mt: 2 }}>
                  <Button color="primary" variant="contained" fullWidth>
                    Sign up
                  </Button>
                </MenuItem>
                <MenuItem>
                  <Button color="primary" variant="outlined" fullWidth>
                    Sign in
                  </Button>
                </MenuItem>
              </Box>
            </Drawer>
          </Box>
        </StyledToolbar>
      </Container>
    </AppBar>
  );
}
