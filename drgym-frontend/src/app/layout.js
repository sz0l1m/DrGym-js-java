'use client';

import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Container from '@mui/material/Container';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import LoginIcon from '@mui/icons-material/Login';
import PersonAddAlt1Icon from '@mui/icons-material/PersonAddAlt1';
import theme from '@/styles/theme';
import '@/app/globals.css';

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <ThemeProvider theme={theme}>
          <CssBaseline />
          <Container
            maxWidth="lg"
            component="main"
            sx={{
              display: 'flex',
              flexDirection: 'column',
              my: 4,
              gap: 4,
            }}
          >
            <AppBar position="static">
              <Toolbar>
                <IconButton
                  size="large"
                  edge="start"
                  color="inherit"
                  aria-label="menu"
                  sx={{ mr: 2 }}
                >
                  <MenuIcon />
                </IconButton>
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                  DrGym
                </Typography>
                <Button color="inherit" startIcon={<LoginIcon />}>
                  Login
                </Button>
                <Button
                  sx={{ ml: 2 }}
                  color="inherit"
                  startIcon={<PersonAddAlt1Icon />}
                >
                  Register
                </Button>
              </Toolbar>
            </AppBar>
            {children}
          </Container>
        </ThemeProvider>
      </body>
    </html>
  );
}
