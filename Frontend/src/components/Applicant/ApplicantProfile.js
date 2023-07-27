import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ApplicantProfile.css';

const ApplicantInfo = () => {
    const [userId, setUserId] = useState('');
    const [applicantInfo, setApplicantInfo] = useState({
        firstName: '',
        lastName: '',
        gender: '',
        dob: '',
        contact: '',
        degreeName: '',
        institute: '',
        cgpa: '',
        passingYear: '',
        address: '',
    });
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        // Get userId from local storage or set a default value
        const storedUserId = localStorage.getItem('userId');
        setUserId(storedUserId || 'YOUR_DEFAULT_USER_ID');
        fetchApplicantInfo(storedUserId || 'YOUR_DEFAULT_USER_ID');
    }, []);

    const fetchApplicantInfo = async (userId) => {
        try {
            const response = await axios.get(`http://localhost:8082/applicant/get/${userId}`);
            setApplicantInfo(response.data.data);
        } catch (error) {
            console.error('Error fetching applicant information:', error);
            setErrorMessage('Error fetching applicant information. Please try again later.');
        }
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setApplicantInfo((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleUpdateApplicant = async () => {
        try {
            const data = {
                userId, // Assuming you have the userId somewhere
                ...applicantInfo,
            };

            const response = await axios.put('http://localhost:8082/applicant/update', data, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            setSuccessMessage('Applicant information updated successfully!');
        } catch (error) {
            console.error('Error updating applicant information:', error);
            setErrorMessage('Error updating applicant information. Please try again later.');
        }
    };

    return (
        <div className="applicant-info-container">
            <h2>Applicant Information</h2>

            {successMessage && <p className="success-message">{successMessage}</p>}
            {errorMessage && <p className="error-message">{errorMessage}</p>}


            <div className="input-field">
                <label>First Name:</label>
                <input
                    type="text"
                    name="firstName"
                    value={applicantInfo.firstName}
                    onChange={handleInputChange}
                />
            </div>
            <div className="input-field">
                <label>Last Name:</label>
                <input
                    type="text"
                    name="lastName"
                    value={applicantInfo.lastName}
                    onChange={handleInputChange}
                />
            </div>
            <div className="input-field">
                <label>Gender:</label>
                <input
                    type="text"
                    name="gender"
                    value={applicantInfo.gender}
                    onChange={handleInputChange}
                />
            </div>
            <div className="input-field">
                <label>Date of Birth:</label>
                <input
                    type="text"
                    name="dob"
                    value={applicantInfo.dob}
                    onChange={handleInputChange}
                />
            </div>
            <div className="input-field">
                <label>Contact:</label>
                <input
                    type="text"
                    name="contact"
                    value={applicantInfo.contact}
                    onChange={handleInputChange}
                />
            </div>
            <div className="input-field">
                <label>Degree Name:</label>
                <input
                    type="text"
                    name="degreeName"
                    value={applicantInfo.degreeName}
                    onChange={handleInputChange}
                />
            </div>
            <div className="input-field">
                <label>Institute:</label>
                <input
                    type="text"
                    name="institute"
                    value={applicantInfo.institute}
                    onChange={handleInputChange}
                />
            </div>
            <div className="input-field">
                <label>CGPA:</label>
                <input
                    type="number"
                    name="cgpa"
                    value={applicantInfo.cgpa}
                    onChange={handleInputChange}
                />
            </div>
            <div className="input-field">
                <label>Passing Year:</label>
                <input
                    type="number"
                    name="passingYear"
                    value={applicantInfo.passingYear}
                    onChange={handleInputChange}
                />
            </div>
            <div className="input-field">
                <label>Address:</label>
                <textarea
                    name="address"
                    value={applicantInfo.address}
                    onChange={handleInputChange}
                />
            </div>
            {/* Add other input fields for the remaining applicant information */}
            <button className="update-button" onClick={handleUpdateApplicant}>
                Update
            </button>

        </div>
    );
};

export default ApplicantInfo;
