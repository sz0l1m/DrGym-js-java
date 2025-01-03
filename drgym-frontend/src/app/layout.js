'use client';

import { ThemeProvider } from '@mui/material/styles';
import { Box, CssBaseline, Container } from '@mui/material';
import CustomAppBar from '@/components/CustomAppBar';
import theme from '@/styles/theme';
import '@/app/globals.css';
import { AppProgressBar as ProgressBar } from 'next-nprogress-bar';
import { SessionProvider } from 'next-auth/react';

export default function RootLayout({ children, session }) {
  return (
    <html lang="en">
      <body>
        <SessionProvider session={session}>
          <ThemeProvider theme={theme}>
            <CssBaseline />
            <ProgressBar
              height="4px"
              color="#457b9d"
              options={{ showSpinner: false }}
              shallowRouting
            />
            <CustomAppBar />
            <Container
              maxWidth="lg"
              component="main"
              sx={{
                display: 'flex',
                flexDirection: 'column',
                my: 14,
                gap: 4,
              }}
            >
              <Box sx={{ p: 2 }}>{children}</Box>
            </Container>
          </ThemeProvider>
        </SessionProvider>
      </body>
    </html>
  );
}
