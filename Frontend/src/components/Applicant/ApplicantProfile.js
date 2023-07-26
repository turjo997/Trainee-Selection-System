import React, { useEffect, useState } from 'react';
import './ApplicantProfile.css'; // Add custom CSS file for styling
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ApplicantProfile = () => {
    const [applicants, setApplicants] = useState([]);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [gender, setGender] = useState('male');
    const [dob, setDob] = useState('');
    const [contact, setContact] = useState('');
    const [degreeName, setDegreeName] = useState('');
    const [institute, setInstitute] = useState('');
    const [cgpa, setCgpa] = useState('');
    const [passingYear, setPassingYear] = useState('');
    const [address, setAddress] = useState('');
    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);

    const [editedApplicant, setEditedApplicant] = useState({});

    const [isEditMode, setIsEditMode] = useState(false); // New state variable


    const userId = localStorage.getItem('userId');

    const navigate = useNavigate();

    useEffect(() => {
        fetchApplicants();
    }, []);


    const fetchApplicants = () => {
        axios
            .get(`http://localhost:8082/applicant/get/${userId}`)
            .then((response) => {
                const responseData = response.data.data;


                const applicantsArray = Array.isArray(responseData) ? responseData : [responseData];
                setApplicants(applicantsArray);
                setEditedApplicant({});

                console.log(response.data.data)
                // const applicantsArray = Array.isArray(responseData) ? responseData : [responseData];
                // setApplicants(applicantsArray);
                // setEditedApplicant({});

            })
            .catch((error) => {
                console.error('Error fetching applicants:', error);
            });
    };


    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setEditedApplicant((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleUpdateProfile = () => {
        // Send a PUT request to update the profile information
        axios
            .put(`http://localhost:8082/applicant/update/${userId}`, editedApplicant)
            .then((response) => {
                console.log('Profile updated successfully:', response.data);
                fetchApplicants(); // Refresh the applicant data after update
            })
            .catch((error) => {
                console.error('Error updating profile:', error);
            });
    };

    return (


        <div className="container">
            <h1 className="profile-heading">Applicant Profile</h1>
            {applicants.map((applicant) => (
                <div key={applicant.applicantId} className="applicant-card">
                    <h2 className="applicant-name">{applicant.firstName + ' ' + applicant.lastName}</h2>
                    <div className="form-group">
                        <label>Email:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="email"
                            value={editedApplicant.email || applicant.email}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Date of Birth:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="dob"
                            value={editedApplicant.dob || applicant.dob}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Gender:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="gender"
                            value={editedApplicant.gender || applicant.gender}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Contact:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="contact"
                            value={editedApplicant.contact || applicant.contact}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Degree Name:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="degreeName"
                            value={editedApplicant.degreeName || applicant.degreeName}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Institute:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="institute"
                            value={editedApplicant.institute || applicant.institute}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>CGPA:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="cgpa"
                            value={editedApplicant.cgpa || applicant.cgpa}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Passing Year:</label>
                        <input
                            type="number"
                            className="form-control"
                            name="passingYear"
                            value={editedApplicant.passingYear || applicant.passingYear}
                            onChange={handleInputChange}
                        />
                    </div>
                    <div className="form-group">
                        <label>Address:</label>
                        <textarea
                            className="form-control"
                            name="address"
                            rows="3"
                            value={editedApplicant.address || applicant.address}
                            onChange={handleInputChange}
                        ></textarea>
                    </div>
                    <button className="btn btn-primary" onClick={handleUpdateProfile}>
                        Update Profile
                    </button>
                </div>
            ))}
        </div>

    );
};

export default ApplicantProfile;







