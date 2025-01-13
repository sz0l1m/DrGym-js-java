const getUsername = () => {
  const username =
    typeof window !== 'undefined' ? localStorage.getItem('username') : null;

  return username;
};

const getAvatar = () => {
  const avatar =
    typeof window !== 'undefined' ? localStorage.getItem('avatar') : null;

  return avatar;
};

export { getUsername, getAvatar };
