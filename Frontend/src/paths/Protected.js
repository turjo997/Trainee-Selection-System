import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ isLoggedIn, requiredRole, children }) => {
  if (!isLoggedIn) {
    return <Navigate to="/login" replace />;
  }
  const userRole = localStorage.getItem('userRole');
  if (userRole !== requiredRole) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
};

export default ProtectedRoute;