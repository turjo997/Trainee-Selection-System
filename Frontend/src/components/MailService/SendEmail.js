import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './SendEmail.css';

const SendEmail = () => {
    const [jobCirculars, setJobCirculars] = useState([]);
    const [examCategories, setExamCategories] = useState([]);
    const [selectedJobCircular, setSelectedJobCircular] = useState('');
    const [selectedExamCategory, setSelectedExamCategory] = useState('');
    const [applicants, setApplicants] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
        fetchCirculars();
        fetchExamCategories();
    }, []);

    const fetchCirculars = async () => {
        try {
            const response = await axios.get('http://localhost:8082/admin/getAllCircular');
            setJobCirculars(response.data.data);
        } catch (error) {
            console.error('Error fetching circulars:', error);
        }
    };

    const fetchExamCategories = async () => {
        try {
            const response = await axios.get('http://localhost:8082/admin/getAllExamCategory');
            setExamCategories(response.data.data);
        } catch (error) {
            console.error('Error fetching exam categories:', error);
        }
    };

    const fetchApplicants = async () => {
        if (!selectedJobCircular) {
            setErrorMessage('Please select a Job Circular.');
            return;
        }

        try {
            const response = await axios.get(`http://localhost:8082/admin/getApplicants/written/${selectedJobCircular}`);
            setApplicants(response.data);
        } catch (error) {
            console.error('Error fetching applicants:', error);
        }
    };

    const handleJobCircularChange = (event) => {
        setSelectedJobCircular(event.target.value);
        setApplicants([]); // Reset applicants when job circular is changed
    };

    const handleExamCategoryChange = (event) => {
        setSelectedExamCategory(event.target.value);
    };

    const handleSendMail = async (applicantId) => {
        if (!selectedJobCircular || !selectedExamCategory) {
            setErrorMessage('Please select a Job Circular and Exam Category.');
            return;
        }

        try {
            const userId = localStorage.getItem('userId');

            const data = {
                userId,
                circularId: selectedJobCircular,
                examId: selectedExamCategory,
                applicantId,
            };

            console.log(data);

            const response = await axios.post('http://localhost:8082/admin/sendMail', data);

            if (response.status === 200) {
                setSuccessMessage('Email sent successfully!');
            } else {
                setErrorMessage('Error sending email. Please try again later.');
            }
        } catch (error) {
            console.error('Error sending email:', error);
            setErrorMessage('Error sending email. Please try again later.');
        }
    };

    return (
        <div className='email-container'>
            <div>
                <label>Select Job Circular:</label>
                <select value={selectedJobCircular} onChange={handleJobCircularChange}>
                    <option value="">Select Job Circular</option>
                    {jobCirculars.map((jobCircular) => (
                        <option key={jobCircular.circularId} value={jobCircular.circularId}>
                            {jobCircular.circularTitle}
                        </option>
                    ))}
                </select>
            </div>
            <br />
            <div>

                <label>Select Exam Category:</label>
                <select value={selectedExamCategory} onChange={handleExamCategoryChange}>
                    <option value="">Select Exam Category</option>
                    {examCategories.map((examCategory) => (
                        <option key={examCategory.examId} value={examCategory.examId}>
                            {examCategory.examTitle}
                        </option>
                    ))}
                </select>
            </div>
            <br />

            <button onClick={fetchApplicants}>Show Applicants</button>

            {/* {errorMessage && <p>{errorMessage}</p>} */}
            {successMessage && <p className="success-message">{successMessage}</p>}
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            {applicants.length > 0 && (
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {applicants.map((applicant) => (
                            <tr key={applicant.applicantId}>

                                <td>{applicant.firstName + ' ' + applicant.lastName}</td>
                                <td>{applicant.email}</td>
                                <td>
                                    <button onClick={() => handleSendMail(applicant.applicantId)}>Send Mail</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}


        </div>
    );
};

export default SendEmail;
