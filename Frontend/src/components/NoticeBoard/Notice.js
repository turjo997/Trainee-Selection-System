import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Notice.css';

const Notice = () => {
  const [jobCirculars, setJobCirculars] = useState([]);
  const [examCategories, setExamCategories] = useState([]);
  const [selectedJobCircular, setSelectedJobCircular] = useState('');
  const [selectedExamCategory, setSelectedExamCategory] = useState('');
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [showPopup, setShowPopup] = useState(false);
  const [error, setError] = useState('');

  const userId = localStorage.getItem('userId');
  const [successMessage, setSuccessMessage] = useState('');

  useEffect(() => {
    // Fetch job circulars from the API
    axios.get('http://localhost:8082/admin/getAllCircular')
      .then(response => {
        setJobCirculars(response.data.data);
      })
      .catch(error => {
        console.error('Error fetching job circulars:', error);
      });

    // Fetch exam categories from the API
    axios.get('http://localhost:8082/admin/getAllExamCategory')
      .then(response => {
        setExamCategories(response.data.data);
      })
      .catch(error => {
        console.error('Error fetching exam categories:', error);
      });
  }, []);

  const handleJobCircularChange = (event) => {
    setSelectedJobCircular(event.target.value);
    setError('');
  };

  const handleExamCategoryChange = (event) => {
    setSelectedExamCategory(event.target.value);
    setError('');
  };

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
    setError('');
  };

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
    setError('');
  };

  const handleAddNotice = () => {
    if (!selectedJobCircular || !selectedExamCategory || !title || !description) {
      setError('Please fill in all the fields.');
      return;
    }

    const noticeData = {
      userId: userId,
      examId: selectedExamCategory,
      circularId: selectedJobCircular,
      title,
      description,
    };

    console.log(noticeData);
    // Send the notice data to the backend using the API
    axios.post('http://localhost:8082/admin/send/notice', noticeData)
      .then(response => {
        console.log('Notice added successfully:', response.data);
        // Show the success message in the UI
        setSuccessMessage('Notice added successfully!');
        setShowPopup(false); // Close the popup after adding the notice
        // Optionally, you can clear the input fields here
        setSelectedJobCircular('');
        setSelectedExamCategory('');
        setTitle('');
        setDescription('');
        setError('');
      })
      .catch(error => {
        console.error('Error adding notice:', error);
      });
  };

  return (
    <div className="notice-container">
      <h2 className="title">Notice Board</h2>

      {error && <p className="alert alert-danger">{error}</p>}
      {successMessage && <p className="alert alert-success">{successMessage}</p>}

      <div className="select-wrapper">
        <h3>Select Job Circular:</h3>
        <select className="select-box" value={selectedJobCircular} onChange={handleJobCircularChange}>
          <option value="">Select Job Circular</option>
          {jobCirculars.map((jobCircular) => (
            <option key={jobCircular.circularId} value={jobCircular.circularId}>
              {jobCircular.circularTitle}
            </option>
          ))}
        </select>
      </div>
      <div className="select-wrapper">
        <h3>Select Exam Category:</h3>
        <select className="select-box" value={selectedExamCategory} onChange={handleExamCategoryChange}>
          <option value="">Select Exam Category</option>
          {examCategories.map((examCategory) => (
            <option key={examCategory.examId} value={examCategory.examId}>
              {examCategory.examTitle}
            </option>
          ))}
        </select>
      </div>

      <button className="add-button" onClick={() => setShowPopup(true)}>Add Notice</button>

      {showPopup && (
        <div className="popup">
          <div className="popup-content">
            <h3>Add Notice for {selectedJobCircular} - {selectedExamCategory}:</h3>
            <div className="form-field">
              <label>Title:</label>
              <input type="text" className="input-box" value={title} onChange={handleTitleChange} />
            </div>
            <div className="form-field">
              <label>Description:</label>
              <textarea className="input-box" value={description} onChange={handleDescriptionChange} />
            </div>
            {/* {error && <p className="error-message">{error}</p>}
            {successMessage && <p className="success-message">{successMessage}</p>} */}

            <div className="button-container">
              <button className="submit-button" onClick={handleAddNotice}>Add Notice</button>
              <button className="cancel-button" onClick={() => setShowPopup(false)}>Cancel</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Notice;
