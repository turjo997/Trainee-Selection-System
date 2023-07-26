// import { useState } from 'react';
// import { useNavigate } from 'react-router-dom';

// export const useAuth = () => {
//   const [isLoggedIn, setIsLoggedIn] = useState(false);
//   const [userRole, setUserRole] = useState(''); // You can set the user's role here

//   const login = (role) => {
//     setUserRole(role);
//     setIsLoggedIn(true);
//   };

//   const logout = () => {
//     setUserRole('');
//     setIsLoggedIn(false);
//   };

//   const navigate = useNavigate();

//   const checkAuth = (allowedRoles) => {
//     if (!isLoggedIn) {
//       navigate('/signin'); // Redirect to the signin page if the user is not logged in
//       return false;
//     }

//     if (!allowedRoles.includes(userRole)) {
//       navigate('/unauthorized'); // Redirect to unauthorized page if the user role is not allowed
//       return false;
//     }

//     return true;
//   };

//   return { isLoggedIn, userRole, login, logout, checkAuth };
// };


import { useState } from 'react';

export const useAuth = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState('');

  const login = (role) => {
    setUserRole(role);
    setIsLoggedIn(true);
  };

  const logout = () => {
    setUserRole('');
    setIsLoggedIn(false);
  };

  const checkAuth = (allowedRoles) => {
    if (!isLoggedIn) {
      return false;
    }

    if (!allowedRoles.includes(userRole)) {
      return false;
    }

    return true;
  };

  return { isLoggedIn, userRole, login, logout, checkAuth };
};
