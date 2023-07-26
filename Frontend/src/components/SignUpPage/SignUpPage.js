import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './SignUpPage.css'; // Add custom CSS file for styling
import { useNavigate } from 'react-router-dom';

const SignUpPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [gender, setGender] = useState('male'); // Default gender is 'male'
  const [dob, setDob] = useState('');
  const [contact, setContact] = useState('');
  const [degreeName, setDegreeName] = useState('');
  const [institute, setInstitute] = useState('');
  const [cgpa, setCgpa] = useState('');
  const [passingYear, setPassingYear] = useState('');
  const [address, setAddress] = useState('');
  const [errMsg, setErrMsg] = useState('');
  const [success, setSuccess] = useState(false);


  const API_BASE_URL = 'http://localhost:8082/applicant/register';

  const validateForm = () => {
    // Perform validation for each form field
    if (email.trim() === '') {
      // Email is required
      return false;
    }

    // Validate Email format using regular expression
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      // Invalid Email format
      return false;
    }

    if (password.trim() === '') {
      // Password is required
      return false;
    }

    if (firstName.trim() === '') {
      // First Name is required
      return false;
    }

    if (lastName.trim() === '') {
      // Last Name is required
      return false;
    }

    if (gender.trim() === '') {
      // Gender is required
      return false;
    }

    if (dob.trim() === '') {
      // Date of Birth is required
      return false;
    }

    if (contact.trim() === '') {
      // Contact is required
      return false;
    }

    //Validate Contact format using regular expression
    const contactRegex = /^\d+$/;
    if (!contactRegex.test(contact)) {
      // Invalid Contact format
      return false;
    }

    if (degreeName.trim() === '') {
      // Degree Name is required
      return false;
    }

    if (institute.trim() === '') {
      // Institute is required
      return false;
    }

    if (cgpa.trim() === '') {
      // CGPA is required
      return false;
    }

    // Validate CGPA format using regular expression
    const cgpaRegex = /^\d+(\.\d{1,3})?$/;
    if (!cgpaRegex.test(cgpa)) {
      // Invalid CGPA format
      return false;
    }

    if (passingYear.trim() === '') {
      // Passing Year is required
      return false;
    }

    // Validate Passing Year format using regular expression
    const yearRegex = /^\d{4}$/;
    if (!yearRegex.test(passingYear)) {
      // Invalid Passing Year format
      return false;
    }

    if (address.trim() === '') {
      // Address is required
      return false;
    }

    // Form is valid
    return true;
  };

  const navigate = useNavigate();

  useEffect(() => {
    if (success) {
      // Redirect to the login page upon successful registration
      setTimeout(() => {
        navigate('/login');
      }, 1000); // Redirect after 2 seconds (adjust the delay as per your requirement)
    }
  }, [success, navigate]);

  const handleSignUp = async (e) => {
    e.preventDefault();

    if (!validateForm()) {
      setErrMsg("Invalid Entry");
      return;
    }

    // Add your sign-up logic here
    try {
      const data = {
        email,
        password,
        firstName,
        lastName,
        gender,
        dob,
        contact,
        degreeName,
        institute,
        cgpa,
        passingYear,
        address
      };

      console.log(data);
      // Send the POST request using axios
      const response = await axios.post(API_BASE_URL, data, {
        headers: { 'Content-Type': 'application/json' }
      });

      // Registration successful, store the JWT token in local storage
      localStorage.setItem('token', response.data.token);
      setSuccess(true);
      // Clear form fields upon successful registration
      setEmail('');
      setPassword('');
      setFirstName('');
      setLastName('');
      setGender('');
      //setDob('');
      setContact('');
      setDegreeName('');
      setInstitute('');
      setCgpa('');
      setPassingYear('');
      setAddress('');
      // ... (clear other fields)
    } catch (err) {
      console.log('Error response:', err.response);

      if (err?.response?.status === 400) {
        setErrMsg('Applicant with the same email already exists');
      } else if (err?.response?.status === 400) {
        setErrMsg('Invalid Entry Error');
      } else if (!err?.response) {
        setErrMsg('No Server Response');
      } else {
        setErrMsg('Registration Failed');
      }
    }
  };

  return (
    <div className="sign-up-page">
      <div className="container">
        {
          success ? (
            <section>
              <h1>Success!</h1>
              <p>
                You have successfully registered. You will be redirected to the login page shortly.
              </p>
            </section>
          ) : (
            <div className="row justify-content-center">
              <div className="col-md-8">
                <div className="sign-up-form">
                  <p className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>

                  <h2 className="text-center mb-4">Sign Up</h2>
                  <form onSubmit={handleSignUp}>
                    <div className="row">
                      <div className="col-md-6">
                        {/* Email */}
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
                        {/* Password */}
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
                        {/* First Name */}
                        <div className="mb-3">
                          <label htmlFor="firstName" className="form-label">
                            First Name
                          </label>
                          <input
                            type="text"
                            className="form-control"
                            id="firstName"
                            placeholder="Enter first name"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            required
                          />
                        </div>
                        {/* Last Name */}
                        <div className="mb-3">
                          <label htmlFor="lastName" className="form-label">
                            Last Name
                          </label>
                          <input
                            type="text"
                            className="form-control"
                            id="lastName"
                            placeholder="Enter last name"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            required
                          />
                        </div>
                      </div>
                      <div className="col-md-6">
                        {/* Gender */}
                        <div className="mb-3">
                          <label className="form-label">Gender</label>
                          <select
                            className="form-select"
                            value={gender}
                            onChange={(e) => setGender(e.target.value)}
                            required
                          >
                            <option value="">Select gender</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                          </select>
                        </div>
                        {/* Date of Birth */}
                        <div className="mb-3">
                          <label htmlFor="dob" className="form-label">
                            Date of Birth
                          </label>
                          <input
                            type="date"
                            className="form-control"
                            id="dob"
                            value={dob}
                            onChange={(e) => setDob(e.target.value)}
                            required
                          />
                        </div>
                        {/* Contact Number */}
                        <div className="mb-3">
                          <label htmlFor="contact" className="form-label">
                            Contact Number
                          </label>
                          <input
                            type="tel"
                            className="form-control"
                            id="contact"
                            placeholder="Enter contact number"
                            value={contact}
                            onChange={(e) => setContact(e.target.value)}
                            required
                          />
                        </div>
                        {/* Degree Name */}
                        <div className="mb-3">
                          <label htmlFor="degreeName" className="form-label">
                            Degree Name
                          </label>
                          <input
                            type="text"
                            className="form-control"
                            id="degreeName"
                            placeholder="Enter degree name"
                            value={degreeName}
                            onChange={(e) => setDegreeName(e.target.value)}
                            required
                          />
                        </div>
                      </div>
                    </div>
                    {/* Institute */}
                    <div className="mb-3">
                      <label htmlFor="institute" className="form-label">
                        Institute
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        id="institute"
                        placeholder="Enter institute"
                        value={institute}
                        onChange={(e) => setInstitute(e.target.value)}
                        required
                      />
                    </div>
                    {/* CGPA */}
                    <div className="mb-3">
                      <label htmlFor="cgpa" className="form-label">
                        CGPA
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        id="cgpa"
                        placeholder="Enter CGPA"
                        value={cgpa}
                        onChange={(e) => setCgpa(e.target.value)}
                        required
                      />
                    </div>
                    {/* Passing Year */}
                    <div className="mb-3">
                      <label htmlFor="passingYear" className="form-label">
                        Passing Year
                      </label>
                      <input
                        type="number"
                        className="form-control"
                        id="passingYear"
                        placeholder="Enter passing year"
                        value={passingYear}
                        onChange={(e) => setPassingYear(e.target.value)}
                        required
                      />
                    </div>
                    {/* Address */}
                    <div className="mb-3">
                      <label htmlFor="address" className="form-label">
                        Address
                      </label>
                      <textarea
                        className="form-control"
                        id="address"
                        rows="3"
                        placeholder="Enter address"
                        value={address}
                        onChange={(e) => setAddress(e.target.value)}
                        required
                      ></textarea>
                    </div>
                    <div className="text-center mt-4">
                      <button type="submit" className="btn btn-primary">
                        Sign Up
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          )
        }


      </div>
    </div>
  );
};

export default SignUpPage;

