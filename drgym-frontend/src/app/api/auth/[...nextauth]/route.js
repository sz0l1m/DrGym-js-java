import NextAuth from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';

const handler = NextAuth({
  session: { strategy: 'jwt' },
  providers: [
    // CredentialsProvider({
    //   id: 'credentials',
    //   name: 'credentials',
    //   credentials: {
    //     username: { label: 'Username', type: 'text' },
    //     password: { label: 'Password', type: 'text' },
    //   },
    //   async authorize(credentials, req) {
    //     console.log('username__________', credentials.username);
    //     return credentials;
    //   },
    // }),
    CredentialsProvider({
      name: 'credentials',
      credentials: {
        username: { label: 'Username', type: 'text' },
        avatar: { label: 'Avatar', type: 'text' },
      },
      async authorize(credentials) {
        return {
          username: credentials.username,
          avatar: credentials.avatar,
        };
      },
    }),
  ],
  theme: {
    colorScheme: 'light',
  },
  pages: {
    error: '/login',
    signIn: '/login',
  },
  callbacks: {
    // async signIn({ user, account, profile, email, credentials }) {
    //   return true;
    // },
    // async redirect({ url, baseUrl }) {
    //   if (baseUrl.startsWith(process.env.NEXT_PUBLIC_API_URL))
    //     return `${process.env.NEXT_PUBLIC_APP_URL}`;
    //   return baseUrl;
    // },
    async jwt({ token, user }) {
      if (user) {
        token.username = user.username;
        token.avatar = user.avatar || null;
      }
      return token;
    },
    async session({ session, token }) {
      if (token) {
        session.user.username = token.username;
        session.user.avatar = token.avatar || null;
      }
      return session;
    },
  },
});

export { handler as POST, handler as GET };

// import NextAuth from 'next-auth';
// import CredentialsProvider from 'next-auth/providers/credentials';

// const handler = NextAuth({
//   session: { strategy: 'jwt' },
//   providers: [
//     CredentialsProvider({
//       id: 'credentials',
//       name: 'credentials',
//       async authorize(credentials, req) {
//         try {
//           const res = await fetch(
//             `${process.env.NEXT_PUBLIC_API_URL}/api/auth/login`,
//             {
//               method: 'POST',
//               headers: { 'Content-Type': 'application/json' },
//               body: JSON.stringify({
//                 identifier: credentials.identifier,
//                 password: credentials.password,
//               }),
//               credentials: 'include',
//             }
//           );

//           const user = await res.json();
//           console.log('res__________', res);
//           console.log('user__________', user);

//           if (user.error) {
//             console.log('user.error__________', user.error);
//             throw new Error(user.code);
//           } else {
//             return user;
//           }
//         } catch (error) {
//           console.error('general error', error);
//           if (error == 'Error: credentials') {
//             throw new Error('credentials');
//           } else if (error == 'Error: verification') {
//             throw new Error('verification');
//           } else {
//             throw new Error('general');
//           }
//         }

//         //   if (!res.ok) {
//         //     throw new Error("Invalid credentials");
//         //   }

//         //   const data = await res.json();

//         //   if (data && data.username && data.avatar) {
//         //     return {
//         //       username: data.username,
//         //       avatar: data.avatar,
//         //     };
//         //   }

//         //   throw new Error("Login failed");
//         // } catch (error) {
//         //   console.error("Login error:", error);
//         //   return null;
//         // }
//       },
//     }),
//   ],
//   theme: {
//     colorScheme: 'light',
//   },
//   pages: {
//     error: '/login',
//     signIn: '/login',
//   },
//   callbacks: {
//     // async signIn({ user, account, profile, email, credentials }) {
//     //   return true;
//     // },
//     // async redirect({ url, baseUrl }) {
//     //   if (baseUrl.startsWith(process.env.NEXT_PUBLIC_API_URL))
//     //     return `${process.env.NEXT_PUBLIC_APP_URL}`;
//     //   return baseUrl;
//     // },
//     async jwt({ token, user }) {
//       if (user) {
//         token.username = user.username;
//         token.avatar = user.avatar || null;
//       }
//       return token;
//     },
//     async session({ session, token }) {
//       if (token) {
//         session.user.username = token.username;
//         session.user.avatar = token.avatar || null;
//       }
//       return session;
//     },
//   },
// });

// export { handler as POST, handler as GET };
