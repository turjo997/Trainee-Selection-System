
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ApproveByTechnical.css';

const ApproveByHr = () => {
  const [jobCirculars, setJobCirculars] = useState([]);
  const [examCategories, setExamCategories] = useState([]);
  const [selectedJobCircular, setSelectedJobCircular] = useState('');
  const [selectedExamCategory, setSelectedExamCategory] = useState(1); // Set the default examId to 1
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

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

  const handleJobCircularChange = (event) => {
    setSelectedJobCircular(event.target.value);
  };

  const handleExamCategoryChange = (event) => {
    setSelectedExamCategory(Number(event.target.value)); // Convert the value to a number
  };

  const handleApproveMarks = async () => {
    if (!selectedJobCircular || !selectedExamCategory) {
      setErrorMessage('Please select both Job Circular and Exam Category.');
      return;
    }

    try {
      const data = {
        userId: localStorage.getItem('userId'),
        circularId: selectedJobCircular,
        examId: selectedExamCategory,
      };

      const response = await axios.post(
        `http://localhost:8082/admin/approve/exam/${data.userId}/${data.circularId}/${data.examId}`
      );

      if (response.status === 200) {
        setSuccessMessage('Approved successfully!');
        setErrorMessage('');
      } else {
        setErrorMessage('Error approving marks. Please try again later.');
        setSuccessMessage('');
      }
    } catch (error) {
      console.error('Error approving marks:', error);
      setErrorMessage('Error approving marks. Please try again later.');
      setSuccessMessage('');
    }
  };

  return (
    <div className="approve-marks-container">
      <h2 className="title">Approve for Technical Test</h2>
      <div className="form-field">
        <label>Select Job Circular:</label>
        <select className="select-box" value={selectedJobCircular} onChange={handleJobCircularChange}>
          <option value="">Select Job Circular</option>
          {jobCirculars.map((jobCircular) => (
            <option key={jobCircular.circularId} value={jobCircular.circularId}>
              {jobCircular.circularTitle}
            </option>
          ))}
        </select>
      </div>
      <div className="form-field">
        <label>Select Exam Category:</label>
        <select className="select-box" value={selectedExamCategory} onChange={handleExamCategoryChange}>
          <option value="">Select Exam Category</option>
          {examCategories.filter(examCategory => examCategory.examId === 4).map((examCategory) => (
            <option key={examCategory.examId} value={examCategory.examId}>
              {examCategory.examTitle}
            </option>
          ))}
        </select>
      </div>

      <button className="approve-marks-button" onClick={handleApproveMarks}>
        Approve
      </button>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      {successMessage && <p className="success-message">{successMessage}</p>}
    </div>
  );
};

export default ApproveByHr;