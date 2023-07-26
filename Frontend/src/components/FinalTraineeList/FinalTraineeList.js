import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './FinalTraineeList.css';

const FinalTraineeList = () => {
    const [circulars, setCirculars] = useState([]);
    const [selectedCircular, setSelectedCircular] = useState('');
    const [applicants, setApplicants] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');

    const API_BASE_URL = 'http://localhost:8082/admin';

    useEffect(() => {
        fetchCirculars();
    }, []);

    const fetchCirculars = async () => {
        try {
            const response = await axios.get(`${API_BASE_URL}/getAllCircular`);
            setCirculars(response.data.data);
        } catch (error) {
            console.error('Error fetching circulars:', error);
            setErrorMessage('Error fetching circulars. Please try again later.');
        }
    };

    const fetchApplicantsByCircular = async () => {
        if (!selectedCircular) {
            setErrorMessage('Please select a Circular before fetching applicants.');
            return;
        }
        axios
            .post(`${API_BASE_URL}/get/trainees/${selectedCircular}`)
            .then((response) => {
                const responseData = response.data.data;

                if (responseData.error_message) {
                    setErrorMessage(responseData.error_message);
                    setApplicants([]); // Clear the applicants array if there is an error
                } else {
                    setErrorMessage('');
                    setApplicants(responseData); // Assuming the API response provides an array of applicants
                }
            })
            .catch((error) => {
                console.error('Error fetching applicants:', error);
                setErrorMessage(error.response.data.error_message);
                setApplicants([]); // Clear the applicants array if there is an error
            });
    };

    return (
        <div className="trainees-table-container">
            <h2>Trainees Table</h2>
            <div className="select-circular">
                <label>Select Circular:</label>
                <select value={selectedCircular} onChange={(e) => setSelectedCircular(e.target.value)}>
                    <option value="">Select Circular</option>
                    {circulars.map((circular) => (
                        <option key={circular.circularId} value={circular.circularId}>
                            {circular.circularTitle}
                        </option>
                    ))}
                </select>
            </div>

            <button className="see-final-list-button" onClick={fetchApplicantsByCircular}>
                See Final List
            </button>

            {errorMessage && <p className="error-message">{errorMessage}</p>}

            {applicants.length > 0 && (
                <table className="trainees-table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Gender</th>
                            <th>Date of Birth</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        {applicants.map((applicant) => (
                            <tr key={applicant.applicantId}>
                                <td>{applicant.firstName + ' ' + applicant.lastName}</td>
                                <td>{applicant.user.email}</td>
                                <td>{applicant.gender}</td>
                                <td>{applicant.dob}</td>
                                <td>{applicant.address}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default FinalTraineeList;
