import { alpha, styled } from '@mui/material/styles';
import { usePathname } from 'next/navigation';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Divider from '@mui/material/Divider';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import BarChartIcon from '@mui/icons-material/BarChart';
import GroupIcon from '@mui/icons-material/Group';
import EmojiPeopleIcon from '@mui/icons-material/EmojiPeople';
import DrGymLogo from '@/components/DrGymLogo';
import CustomDrawer from '@/components/CustomDrawer';
import Link from 'next/link';

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
            <DrGymLogo />
            <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
              <Divider orientation="vertical" flexItem sx={{ mx: 1 }} />
              <Link href="/user/posts">
                <Button
                  variant={usePathname() == '/user/posts' ? 'outlined' : 'text'}
                  color="secondary"
                  size="small"
                  startIcon={<EmojiPeopleIcon />}
                >
                  Posts
                </Button>
              </Link>
              <Divider orientation="vertical" flexItem sx={{ mx: 1 }} />
              <Link href="/user/workouts">
                <Button
                  variant={
                    usePathname() == '/user/workouts' ? 'outlined' : 'text'
                  }
                  color="secondary"
                  size="small"
                  startIcon={<FitnessCenterIcon />}
                >
                  Workouts
                </Button>
              </Link>
              <Divider orientation="vertical" flexItem sx={{ mx: 1 }} />
              <Link href="/user/stats">
                <Button
                  variant={usePathname() == '/user/stats' ? 'outlined' : 'text'}
                  color="secondary"
                  size="small"
                  startIcon={<BarChartIcon />}
                >
                  Statistics
                </Button>
              </Link>
              <Divider orientation="vertical" flexItem sx={{ mx: 1 }} />
              <Link href="/user/friends">
                <Button
                  variant={
                    usePathname() == '/user/friends' ? 'outlined' : 'text'
                  }
                  color="secondary"
                  size="small"
                  startIcon={<GroupIcon />}
                >
                  Friends
                </Button>
              </Link>
            </Box>
          </Box>
          <Box
            sx={{
              display: { xs: 'none', md: 'flex' },
              gap: 1,
              alignItems: 'center',
            }}
          >
            <Link href="/login">
              <Button color="primary" variant="text" size="small">
                Sign in
              </Button>
            </Link>
            <Link href="/register">
              <Button color="primary" variant="contained" size="small">
                Sign up
              </Button>
            </Link>
          </Box>
          <CustomDrawer />
        </StyledToolbar>
      </Container>
    </AppBar>
  );
}
