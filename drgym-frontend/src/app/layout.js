'use client';

import { ThemeProvider } from '@mui/material/styles';
import { CssBaseline, Container } from '@mui/material';
import CustomAppBar from '@/components/CustomAppBar';
import theme from '@/styles/theme';
import '@/app/globals.css';

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <ThemeProvider theme={theme}>
          <CssBaseline />
          <CustomAppBar />
          <Container
            maxWidth="lg"
            component="main"
            sx={{
              display: 'flex',
              flexDirection: 'column',
              my: 16,
              gap: 4,
            }}
          >
            {children}
          </Container>
        </ThemeProvider>
      </body>
    </html>
  );
}
