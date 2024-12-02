import { format, parseISO, formatDistanceToNow } from 'date-fns';

export const formatDate = (date, pattern = 'MMMM d, yyyy h:mm a') =>
  format(parseISO(date), pattern);

export const formatRelativeTime = (date) =>
  formatDistanceToNow(parseISO(date), { addSuffix: true });
