import { getToken } from 'next-auth/jwt';
import { NextResponse } from 'next/server';

export async function middleware(req) {
  const token = await getToken({ req });
  const { pathname } = req.nextUrl;

  if (
    token &&
    (pathname === '/login' ||
      pathname === '/register' ||
      pathname.startsWith('/auth'))
  ) {
    const username = token?.username;
    return NextResponse.redirect(
      new URL(
        `/user/${username}/posts?message=You are already signed in`,
        req.url
      )
    );
  }

  if (!token && pathname.startsWith('/user')) {
    return NextResponse.redirect(
      new URL('/login?message=You have to sign in first', req.url)
    );
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/login', '/register', '/auth/:path*', '/user/:path*'],
};
