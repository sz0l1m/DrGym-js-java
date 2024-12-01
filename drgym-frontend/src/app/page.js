import { Button, Container, Typography } from '@mui/material';

export default function HomePage() {
  return (
    <div>
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
        <Typography variant="h4" gutterBottom>
          Test MaterialUI
        </Typography>
        <Button variant="contained" color="primary">
          Click Me
        </Button>
      </Container>
    </div>
  );
}
