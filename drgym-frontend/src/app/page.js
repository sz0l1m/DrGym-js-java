import AppBar from '@mui/material/AppBar';
import Container from '@mui/material/Container';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

export default function HomePage() {
  return (
    <>
      <Typography variant="h4" gutterBottom>
        Test MaterialUI
      </Typography>
      <Button variant="contained" color="secondary">
        Click Me
      </Button>
    </>
  );
}

// 'use client';

// import * as React from 'react';
// import Box from '@mui/material/Box';
// import Drawer from '@mui/material/Drawer';
// import Button from '@mui/material/Button';
// import List from '@mui/material/List';
// import Divider from '@mui/material/Divider';
// import ListItem from '@mui/material/ListItem';
// import ListItemButton from '@mui/material/ListItemButton';
// import ListItemIcon from '@mui/material/ListItemIcon';
// import ListItemText from '@mui/material/ListItemText';
// import InboxIcon from '@mui/icons-material/MoveToInbox';
// import MailIcon from '@mui/icons-material/Mail';

// export default function AnchorTemporaryDrawer() {
//   const [state, setState] = React.useState({
//     top: false,
//     left: false,
//     bottom: false,
//     right: false,
//   });

//   const toggleDrawer = (anchor, open) => (event) => {
//     if (
//       event.type === 'keydown' &&
//       (event.key === 'Tab' || event.key === 'Shift')
//     ) {
//       return;
//     }

//     setState({ ...state, [anchor]: open });
//   };

//   return (
//     <div>
//       {['left', 'right', 'top', 'bottom'].map((anchor) => (
//         <React.Fragment key={anchor}>
//           <Button onClick={toggleDrawer(anchor, true)}>{anchor}</Button>
//           <Drawer
//             anchor={anchor}
//             open={state[anchor]}
//             onClose={toggleDrawer(anchor, false)}
//           >
//             <Box
//               sx={{ width: 250 }}
//               role="presentation"
//               onClick={toggleDrawer(anchor, false)}
//               onKeyDown={toggleDrawer(anchor, false)}
//             >
//               <List>
//                 {['Inbox', 'Starred', 'Send email', 'Drafts'].map(
//                   (text, index) => (
//                     <ListItem key={text} disablePadding>
//                       <ListItemButton>
//                         <ListItemIcon>
//                           <InboxIcon />
//                         </ListItemIcon>
//                         <ListItemText primary={text} />
//                       </ListItemButton>
//                     </ListItem>
//                   )
//                 )}
//               </List>
//               <Divider />
//               <List>
//                 {['All mail', 'Trash', 'Spam'].map((text, index) => (
//                   <ListItem key={text} disablePadding>
//                     <ListItemButton>
//                       <ListItemIcon>
//                         <InboxIcon />
//                       </ListItemIcon>
//                       <ListItemText primary={text} />
//                     </ListItemButton>
//                   </ListItem>
//                 ))}
//               </List>
//             </Box>
//           </Drawer>
//         </React.Fragment>
//       ))}
//     </div>
//   );
// }
