import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const JobDetailsPage = () => {
    const { circularId } = useParams();
    const [jobDetails, setJobDetails] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        fetchJobDetails();
    }, [successMessage]); // Refetch job details whenever successMessage changes

    const fetchJobDetails = () => {
        axios
            .get(`http://localhost:8082/get/${circularId}`)
            .then((response) => {
                setJobDetails(response.data.data);
            })
            .catch((error) => {
                console.error('Error fetching job details:', error);
            });
    };

    const handleApplyNow = () => {
        const data = {
            userId: localStorage.getItem('userId'),
            circularId: circularId,
        };

        axios
            .post(`http://localhost:8082/applicant/apply`, data)
            .then((response) => {
                // Handle the success response
                console.log('Data saved successfully:', response.data);
                setSuccessMessage('Successfully applied for the job circular. Please stay with us.'); // Set the success message from the API response
                setErrorMessage(''); // Clear any previous error message
            })
            .catch((error) => {
                // Handle the error response
                console.error('Error saving data:', error);
                setErrorMessage('Failed to apply'); // Set the error message
                setSuccessMessage(''); // Clear any previous success message
            });
    };

    if (!jobDetails) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container">
            <h1 className="text-center mb-4">Job Circular Details</h1>

            <div className="card mb-4">
                <div className="card-body">
                    <h5 className="card-title">{jobDetails.circularTitle}</h5>
                    <p className="card-text">Job Type: {jobDetails.jobType}</p>
                    <p className="card-text">Open Date: {jobDetails.openDate}</p>
                    <p className="card-text">Close Date: {jobDetails.closeDate}</p>
                    <p className="card-text">Job Description: {jobDetails.jobDescription}</p>
                    {!successMessage && ( // Show the Apply Now button if no success message
                        <>
                            <button className="btn btn-primary" onClick={handleApplyNow}>
                                Apply Now
                            </button>
                            {errorMessage && (
                                <div className="alert alert-danger mt-3" role="alert">
                                    {errorMessage}
                                </div>
                            )}
                        </>
                    )}
                </div>
            </div>
            {successMessage && ( // Show the success message if available
                <div className="alert alert-success mt-3" role="alert">
                    {successMessage}
                </div>
            )}
        </div>
    );
};

export default JobDetailsPage;
