// // import React, { useEffect, useState } from 'react';
// // import './ApplicantProfile.css'; // Add custom CSS file for styling
// // import axios from 'axios';
// // import { useNavigate } from 'react-router-dom';

// // const ApplicantProfile = () => {
// //     const [applicants, setApplicants] = useState([]);
// //     const [firstName, setFirstName] = useState('');
// //     const [lastName, setLastName] = useState('');
// //     const [gender, setGender] = useState('male');
// //     const [dob, setDob] = useState('');
// //     const [contact, setContact] = useState('');
// //     const [degreeName, setDegreeName] = useState('');
// //     const [institute, setInstitute] = useState('');
// //     const [cgpa, setCgpa] = useState('');
// //     const [passingYear, setPassingYear] = useState('');
// //     const [address, setAddress] = useState('');
// //     const [errMsg, setErrMsg] = useState('');
// //     const [success, setSuccess] = useState(false);

// //     const [editedApplicant, setEditedApplicant] = useState({});

// //     const [isEditMode, setIsEditMode] = useState(false); // New state variable


// //     const userId = localStorage.getItem('userId');

// //     const navigate = useNavigate();

// //     useEffect(() => {
// //         fetchApplicants();
// //     }, []);


// //     const fetchApplicants = () => {
// //         axios
// //             .get(`http://localhost:8082/applicant/get/${userId}`)
// //             .then((response) => {
// //                 const responseData = response.data.data;


// //                 const applicantsArray = Array.isArray(responseData) ? responseData : [responseData];
// //                 setApplicants(applicantsArray);
// //                 setEditedApplicant({});

// //                 console.log(response.data.data)
// //                 // const applicantsArray = Array.isArray(responseData) ? responseData : [responseData];
// //                 // setApplicants(applicantsArray);
// //                 // setEditedApplicant({});

// //             })
// //             .catch((error) => {
// //                 console.error('Error fetching applicants:', error);
// //             });
// //     };


// //     const handleInputChange = (event) => {
// //         const { name, value } = event.target;
// //         setEditedApplicant((prevState) => ({
// //             ...prevState,
// //             [name]: value,
// //         }));
// //     };

// //     const handleUpdateProfile = () => {

// //         axios
// //             .put(`http://localhost:8082/applicant/update`, editedApplicant)
// //             .then((response) => {
// //                 console.log('Profile updated successfully:', response.data);
// //                 fetchApplicants(); 
// //             })
// //             .catch((error) => {
// //                 console.error('Error updating profile:', error);
// //             });
// //     };

// //     return (


// //         <div className="container">
// //             <h1 className="profile-heading">Applicant Profile</h1>
// //             {applicants.map((applicant) => (
// //                 <div key={applicant.applicantId} className="applicant-card">
// //                     <h2 className="applicant-name">{applicant.firstName + ' ' + applicant.lastName}</h2>
// //                     <div className="form-group">
// //                         <label>Email:</label>
// //                         <input
// //                             type="text"
// //                             className="form-control"
// //                             name="email"
// //                             value={editedApplicant.email || applicant.email}
// //                             onChange={handleInputChange}
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>Date of Birth:</label>
// //                         <input
// //                             type="text"
// //                             className="form-control"
// //                             name="dob"
// //                             value={editedApplicant.dob || applicant.dob}
// //                             onChange={handleInputChange}
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>Gender:</label>
// //                         <input
// //                             type="text"
// //                             className="form-control"
// //                             name="gender"
// //                             value={editedApplicant.gender || applicant.gender}
// //                             onChange={handleInputChange}
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>Contact:</label>
// //                         <input
// //                             type="text"
// //                             className="form-control"
// //                             name="contact"
// //                             value={editedApplicant.contact || applicant.contact}
// //                             onChange={handleInputChange}
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>Degree Name:</label>
// //                         <input
// //                             type="text"
// //                             className="form-control"
// //                             name="degreeName"
// //                             value={editedApplicant.degreeName || applicant.degreeName}
// //                             onChange={handleInputChange}
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>Institute:</label>
// //                         <input
// //                             type="text"
// //                             className="form-control"
// //                             name="institute"
// //                             value={editedApplicant.institute || applicant.institute}
// //                             onChange={handleInputChange}
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>CGPA:</label>
// //                         <input
// //                             type="text"
// //                             className="form-control"
// //                             name="cgpa"
// //                             value={editedApplicant.cgpa || applicant.cgpa}
// //                             onChange={handleInputChange}
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>Passing Year:</label>
// //                         <input
// //                             type="number"
// //                             className="form-control"
// //                             name="passingYear"
// //                             value={editedApplicant.passingYear || applicant.passingYear}
// //                             onChange={handleInputChange}
// //                         />
// //                     </div>
// //                     <div className="form-group">
// //                         <label>Address:</label>
// //                         <textarea
// //                             className="form-control"
// //                             name="address"
// //                             rows="3"
// //                             value={editedApplicant.address || applicant.address}
// //                             onChange={handleInputChange}
// //                         ></textarea>
// //                     </div>
// //                     <button className="btn btn-primary" onClick={handleUpdateProfile}>
// //                         Update Profile
// //                     </button>
// //                 </div>
// //             ))}
// //         </div>

// //     );
// // };

// // export default ApplicantProfile;

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
