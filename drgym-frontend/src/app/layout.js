'use client';

import { useState } from 'react';
import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Container from '@mui/material/Container';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import LoginIcon from '@mui/icons-material/Login';
import HomeIcon from '@mui/icons-material/Home';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import PersonAddAlt1Icon from '@mui/icons-material/PersonAddAlt1';
import BarChartIcon from '@mui/icons-material/BarChart';
import GroupIcon from '@mui/icons-material/Group';
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

// 'use client';

// import { useState } from 'react';
// import { ThemeProvider } from '@mui/material/styles';
// import CssBaseline from '@mui/material/CssBaseline';
// import AppBar from '@mui/material/AppBar';
// import Box from '@mui/material/Box';
// import Drawer from '@mui/material/Drawer';
// import List from '@mui/material/List';
// import ListItem from '@mui/material/ListItem';
// import ListItemButton from '@mui/material/ListItemButton';
// import ListItemIcon from '@mui/material/ListItemIcon';
// import ListItemText from '@mui/material/ListItemText';
// import Container from '@mui/material/Container';
// import Toolbar from '@mui/material/Toolbar';
// import Typography from '@mui/material/Typography';
// import Button from '@mui/material/Button';
// import IconButton from '@mui/material/IconButton';
// import MenuIcon from '@mui/icons-material/Menu';
// import LoginIcon from '@mui/icons-material/Login';
// import HomeIcon from '@mui/icons-material/Home';
// import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
// import PersonAddAlt1Icon from '@mui/icons-material/PersonAddAlt1';
// import BarChartIcon from '@mui/icons-material/BarChart';
// import GroupIcon from '@mui/icons-material/Group';
// import theme from '@/styles/theme';
// import '@/app/globals.css';

// export default function RootLayout({ children }) {
//   const [openDrawer, setOpenDrawer] = useState(false);

//   const toggleDrawer = (newOpenDrawer) => () => {
//     setOpenDrawer(newOpenDrawer);
//   };

//   return (
//     <html lang="en">
//       <body>
//         <ThemeProvider theme={theme}>
//           <CssBaseline />
//           <Container
//             maxWidth="false"
//             component="main"
//             sx={{
//               display: 'flex',
//               flexDirection: 'column',
//               my: 4,
//               gap: 4,
//             }}
//           >
//             <AppBar position="static">
//               <Toolbar>
//                 <IconButton
//                   size="large"
//                   edge="start"
//                   color="inherit"
//                   aria-label="menu"
//                   sx={{ mr: 2 }}
//                   onClick={toggleDrawer(true)}
//                 >
//                   <MenuIcon />
//                 </IconButton>
//                 <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
//                   DrGym
//                 </Typography>
//                 <Button color="inherit" startIcon={<LoginIcon />}>
//                   Login
//                 </Button>
//                 <Button
//                   sx={{ ml: 2 }}
//                   color="inherit"
//                   startIcon={<PersonAddAlt1Icon />}
//                 >
//                   Register
//                 </Button>
//               </Toolbar>
//             </AppBar>
//             <Drawer open={openDrawer} onClose={toggleDrawer(false)}>
//               <Box
//                 sx={{ width: 250 }}
//                 role="presentation"
//                 onClick={toggleDrawer(false)}
//               >
//                 <List>
//                   <ListItem disablePadding>
//                     <ListItemButton>
//                       <ListItemIcon>
//                         <HomeIcon />
//                       </ListItemIcon>
//                       <ListItemText primary="Home Page" />
//                     </ListItemButton>
//                   </ListItem>
//                   <ListItem disablePadding>
//                     <ListItemButton>
//                       <ListItemIcon>
//                         <FitnessCenterIcon />
//                       </ListItemIcon>
//                       <ListItemText primary="Your Workouts" />
//                     </ListItemButton>
//                   </ListItem>
//                   <ListItem disablePadding>
//                     <ListItemButton>
//                       <ListItemIcon>
//                         <BarChartIcon />
//                       </ListItemIcon>
//                       <ListItemText primary="Statistics" />
//                     </ListItemButton>
//                   </ListItem>
//                   <ListItem disablePadding>
//                     <ListItemButton>
//                       <ListItemIcon>
//                         <GroupIcon />
//                       </ListItemIcon>
//                       <ListItemText primary="Friends" />
//                     </ListItemButton>
//                   </ListItem>
//                 </List>
//               </Box>
//             </Drawer>
//             {children}
//           </Container>
//         </ThemeProvider>
//       </body>
//     </html>
//   );
// }
