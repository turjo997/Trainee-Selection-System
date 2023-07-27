import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import jwtDecode from 'jwt-decode';
import './SignInPage.css';

const SignInPage = ({ setIsLoggedIn }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errMsg, setErrMsg] = useState('');
  const navigate = useNavigate();

  const handleSignIn = async (e) => {
    e.preventDefault();
    try {
      const data = {
        email,
        password
      };

      // Send the POST request using axios
      const response = await axios.post('http://localhost:8082/user/login', data);

      const result = response.data;
      const { token } = result;

      console.log('Token:', token);

      // Decode the token to get the user information, including the roles array
      const decodedToken = jwtDecode(token);

      console.log('Decoded Token:', decodedToken);

      // Get the roles array from the decoded token
      const roles = decodedToken.roles;
      const userId = decodedToken.userId;


      console.log('Roles:', roles);

      // Assuming the roles array contains only one role object, extract the role authority
      const role = roles.length > 0 ? roles[0].authority : '';

      console.log('Role:', role);
      console.log('UseID:', userId);

      // Store the token in local storage upon successful login
      localStorage.setItem('token', token);
      localStorage.setItem('userId', userId);

      localStorage.setItem('userRole', role);


      // Set isLoggedIn to true upon successful login
      setIsLoggedIn(true);


      // Redirect to the appropriate dashboard based on the user's role
      if (role === 'ADMIN') {
        navigate('/applicants');
      } else if (role === 'APPLICANT') {
        navigate('/openings');
      } else if (role === 'EVALUATOR') {
        navigate('/evaluator');
      }
    } catch (error) {
      console.error('Error:', error);
      setErrMsg('Invalid credentials. Please try again.');
    }
  };

  return (
    <div className="sign-in-page">
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-6 col-lg-4 sign-in-form">
            <h2 className="text-center mb-4">Sign In</h2>
            <form onSubmit={handleSignIn}>
              <div className="mb-3">
                <label htmlFor="email" className="form-label">
                  Email address
                </label>
                <input
                  type="email"
                  className="form-control"
                  id="email"
                  placeholder="Enter email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="password" className="form-label">
                  Password
                </label>
                <input
                  type="password"
                  className="form-control"
                  id="password"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>

              <div className="text-center">
                <button type="submit" className="btn btn-primary">
                  Sign In
                </button>
              </div>
              {errMsg && <p className="text-center text-danger mt-2">{errMsg}</p>}
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignInPage;
