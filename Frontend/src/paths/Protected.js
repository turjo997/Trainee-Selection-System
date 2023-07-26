import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ isLoggedIn, requiredRole, children }) => {
  if (!isLoggedIn) {
    return <Navigate to="/login" replace />;
  }

  // Check if the user has the required role
  const userRole = localStorage.getItem('userRole');
  if (userRole !== requiredRole) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
};

export default ProtectedRoute;