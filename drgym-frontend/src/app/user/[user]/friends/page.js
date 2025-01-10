'use client';

import Typography from '@mui/material/Typography';
import { friends } from '@/utils/mockData';
import UserHeader from '@/components/UserHeader';
import Card from '@mui/material/Card';

const Friends = () => {
  return (
    <>
      <Typography variant="h4" gutterBottom>
        Friends
      </Typography>
      {friends.map((friend) => (
        <Card key={friend.username} sx={{ maxWidth: '100%', my: 1 }}>
          <UserHeader username={friend.username} actions />
        </Card>
      ))}
    </>
  );
};

export default Friends;
