import axios from 'axios';
import React, { useState } from 'react';
import './Evaluator.css';

const CreateEvaluator = () => {
    const [evaluatorName, setEvaluatorName] = useState('');
    const [evaluatorNameError, setEvaluatorNameError] = useState('');
    const [email, setEmail] = useState('');
    const [emailError, setEmailError] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [designation, setDesignation] = useState('');
    const [designationError, setDesignationError] = useState('');
    const [contactNumber, setContactNumber] = useState('');
    const [contactNumberError, setContactNumberError] = useState('');
    const [qualification, setQualification] = useState('');
    const [qualificationError, setQualificationError] = useState('');
    const [specialization, setSpecialization] = useState('');
    const [specializationError, setSpecializationError] = useState('');
    const [confirmPasswordError, setConfirmPasswordError] = useState('');

    const [active, setActive] = useState(false);

    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const API_BASE_URL = "http://localhost:8082/admin";

    const userId = localStorage.getItem('userId');

    console.log(userId);

    const specializationOptions = [
        'JAVA', 'IOS', 'Android', 'SQA', 'Devops' // Add more options as needed
    ];

    const designationOptions = [
        'Associate Software Engineer',
        'Software Engineer',
        'Technical Lead',
        'Project Manager',
        // Add more options as needed
    ];


    const qualificationOptions = [
        'Bachelors Degree',
        'Masters Degree',
        'Diploma Degeree'
        // Add more options as needed
    ];


    const validatePassword = (password) => {
        const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
        return passwordRegex.test(password);
    };

    const handleSubmit = (e) => {
        e.preventDefault();


        // Validate password and confirm password
        if (password !== confirmPassword) {
            setConfirmPasswordError('Passwords do not match');
            return;
        } else {
            setConfirmPasswordError('');
        }

        if (!validateForm()) {
            // Form is not valid, display an error message or take appropriate action
            setErrorMessage('Please fill in all the required fields and make sure the password meets the criteria.');
            setSuccessMessage('');

            return;
        }


        // Create a data object with the form values
        const data = {
            userId: userId,
            evaluatorName,
            email,
            password,
            designation,
            contactNumber,
            qualification,
            specialization,
            active
        };

        // Send the form data to your backend API using Axios
        axios.post(API_BASE_URL + '/create/evaluator', data)
            .then((response) => {
                // Handle the success response
                console.log('Data saved successfully:', response.data);
                // Reset the form fields or show a success message upon successful submission
                setSuccessMessage('Evaluator added successfully!');
                setErrorMessage('');

                // Reset the form fields
                setEvaluatorName('');
                setEmail('');
                setPassword('');
                setDesignation('');
                setContactNumber('');
                setQualification('');
                setSpecialization('');
                setConfirmPassword('');
                setActive(false);

            })
            .catch((error) => {
                // Handle the error response
                console.error('Error saving data:', error);
                // Display an error message or take appropriate action
                setErrorMessage('Error adding evaluator. Please try again later.');
                setSuccessMessage('');

            });
    };

    const validateForm = () => {

        // Perform validation for each form field
        setEvaluatorNameError('');
        setEmailError('');
        setPasswordError('');
        setContactNumberError('');
        setDesignationError('');
        setQualificationError('');
        setSpecializationError('');
        setConfirmPasswordError('');
        let isValid = true;

        if (evaluatorName.trim() === '') {
            setEvaluatorNameError('Evaluator Name is required');
            isValid = false;
        }

        if (email.trim() === '') {
            setEmailError('Evaluator Email is required');
            isValid = false;
        } else {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                setEmailError('Invalid Email format');
                isValid = false;
            }
        }

        if (!validatePassword(password)) {
            setPasswordError('Invalid password. Password must be at least 6 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.');
            isValid = false;
        }

        if (password !== confirmPassword) {
            setConfirmPasswordError('Passwords do not match');
            isValid = false;
        }

        if (designation.trim() === '') {
            setDesignationError('Evaluator Designation is required');
            isValid = false;
        }

        if (contactNumber.trim() === '') {
            setContactNumberError('Evaluator Contact Number is required');
            isValid = false;
        } else {
            const contactRegex = /^\d+$/;
            if (!contactRegex.test(contactNumber)) {
                setContactNumberError('Invalid Contact Number');
                isValid = false;
            }
        }

        if (qualification.trim() === '') {
            setQualificationError('Evaluator Qualification is required');
            isValid = false;
        }

        if (specialization.trim() === '') {
            setSpecializationError('Evaluator Specialization is required');
            isValid = false;
        }

        // Form is valid
        return isValid;
    };

    return (
        <div className="container">

            {successMessage && <p className="success-message">{successMessage}</p>}
            {errorMessage && <p className="error-message">{errorMessage}</p>}


            <div>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="evaluatorName" className="form-label">
                            Evaluator Name
                        </label>
                        <input
                            type="text"
                            className="form-control"
                            id="evaluatorName"
                            value={evaluatorName}
                            onChange={(e) => setEvaluatorName(e.target.value)}
                            placeholder='please enter the name'
                            required
                        />
                        {evaluatorNameError && <p className="error-message">{evaluatorNameError}</p>}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">
                            Evaluator Email
                        </label>
                        <input
                            type="email"
                            className="form-control"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder='please enter the email'
                            required
                        />
                        {emailError && <p className="error-message">{emailError}</p>}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">
                            Password
                        </label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Please enter the password"
                            required
                        />
                        {passwordError && <p className="error-message">{passwordError}</p>}
                    </div>

                    {/* Confirm Password */}
                    <div className="mb-3">
                        <label htmlFor="confirmPassword" className="form-label">
                            Confirm Password
                        </label>
                        <input
                            type="password"
                            className="form-control"
                            id="confirmPassword"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            placeholder="Please confirm the password"
                            required
                        />
                        {confirmPasswordError && <p className="error-message">{confirmPasswordError}</p>}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="designation" className="form-label">
                            Designation
                        </label>
                        <select
                            className="form-control"
                            id="designation"
                            value={designation}
                            onChange={(e) => {
                                setDesignation(e.target.value);
                                setDesignationError(''); // Reset designation error when a selection is made
                            }}


                        >
                            <option value="">Select Designation</option>
                            {designationOptions.map((option) => (
                                <option key={option} value={option}>
                                    {option}
                                </option>
                            ))}
                        </select>
                        {designationError && <p className="error-message">{designationError}</p>}
                    </div>


                    <div className="mb-3">
                        <label htmlFor="contactNumber" className="form-label">
                            Contact Number
                        </label>
                        <input
                            type="tel"
                            className="form-control"
                            id="contactNumber"
                            value={contactNumber}
                            onChange={(e) => setContactNumber(e.target.value)}
                            placeholder='please enter the contact'
                            required
                        />
                        {contactNumberError && <p className="error-message">{contactNumberError}</p>}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="qualification" className="form-label">
                            Qualification
                        </label>
                        <select
                            className="form-control"
                            id="qualification"
                            value={qualification}
                            onChange={(e) => {
                                setQualification(e.target.value);
                                setQualificationError(''); // Reset designation error when a selection is made
                            }}
                        >
                            <option value="">Select Qualification</option>
                            {qualificationOptions.map((option) => (
                                <option key={option} value={option}>
                                    {option}
                                </option>
                            ))}
                        </select>
                        {qualificationError && <p className="error-message">{qualificationError}</p>}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="specialization" className="form-label">
                            Specialization
                        </label>
                        <select
                            className="form-control"
                            id="specialization"
                            value={specialization}
                            onChange={(e) => {
                                setSpecialization(e.target.value);
                                setSpecializationError(''); // Reset designation error when a selection is made
                            }}
                        >
                            <option value="">Select Specialization</option>
                            {specializationOptions.map((option) => (
                                <option key={option} value={option}>
                                    {option}
                                </option>
                            ))}
                        </select>
                        {specializationError && <p className="error-message">{specializationError}</p>}
                    </div>
                    <div className="mb-3 form-check">
                        <input
                            type="checkbox"
                            className="form-check-input"
                            id="active"
                            checked={active}
                            onChange={(e) => setActive(e.target.checked)}
                        />

                        <label htmlFor="active" className="form-check-label">
                            Active
                        </label>

                    </div>
                    <button type="submit" className="btn btn-primary">
                        Submit
                    </button>
                </form>
            </div>
        </div>

    );
};

export default CreateEvaluator;