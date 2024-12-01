'use client';

import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Container from '@mui/material/Container';
import theme from '@/styles/theme';
import '@/app/globals.css';
import CustomAppBar from '@/components/CustomAppBar';

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <ThemeProvider theme={theme}>
          <CssBaseline />
          <Container
            maxWidth="false"
            component="main"
            sx={{
              display: 'flex',
              flexDirection: 'column',
              my: 4,
              gap: 4,
            }}
          >
            <CustomAppBar />
            {children}
          </Container>
        </ThemeProvider>
      </body>
    </html>
  );
}
