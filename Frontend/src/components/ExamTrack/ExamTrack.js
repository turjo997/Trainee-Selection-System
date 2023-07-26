import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ExamTrack.css';

const ExamTrack = () => {
  const [jobCirculars, setJobCirculars] = useState([]);
  const [selectedJobCircular, setSelectedJobCircular] = useState('');
  const [userId, setUserId] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const API_BASE_URL = 'http://localhost:8082/admin';

  // Fetch the list of job circulars from the API on component mount
  useEffect(() => {
    fetchJobCirculars();
    // Fetch the userId from local storage
    const storedUserId = localStorage.getItem('userId');
    setUserId(storedUserId);
  }, []);

  const fetchJobCirculars = () => {
    axios
      .get(API_BASE_URL + '/getAllCircular')
      .then((response) => {

        console.log(response.data.data);
        setJobCirculars(response.data.data);
      })
      .catch((error) => {
        console.error('Error fetching job circulars:', error);
      });
  };

  const handleJobCircularChange = (event) => {
    setSelectedJobCircular(event.target.value);
  };

  const handleGenerateCode = () => {
    // Check if a job circular is selected
    if (!selectedJobCircular) {
      setErrorMessage('Please select a job circular before generating the code.');
      setSuccessMessage('');
      return;
    }

    // Convert the selectedJobCircular to a number
    const circularId = parseInt(selectedJobCircular, 10);

    // Check if userId is a number and selectedJobCircular is a number greater than 0
    if (!userId || isNaN(userId) || circularId <= 0 || isNaN(circularId)) {
      setErrorMessage('Invalid userId or circularId.');
      setSuccessMessage('');
      return;
    }

    const data = {
      userId: parseInt(userId, 10),
      circularId: circularId
    };

    console.log(data);

    // Implement the logic to generate the code for the selected job circular
    axios
      .post(API_BASE_URL + '/track', data)
      .then((response) => {
        setSuccessMessage('Code generated successfully');
        setErrorMessage('');
      })
      .catch((error) => {
        setErrorMessage('Error generating code. Please try again later.');
        setSuccessMessage('');
        console.error('Error generating code:', error);
      });
  };

  return (
    <div className="exam-container">
      <h2 className="title">Tracking Exam</h2>
      {successMessage && <p className="success-message">{successMessage}</p>}
      {errorMessage && <p className="error-message">{errorMessage}</p>}
      <div className="select-wrapper">
        <h3>Select Job Circular:</h3>
        <select className="select-box" value={selectedJobCircular}
          onChange={handleJobCircularChange}>
          <option value="">Select Job Circular</option>
          {jobCirculars.map((jobCircular) => (
            <option key={jobCircular.circularId}
              value={jobCircular.circularId}>
              {jobCircular.circularTitle}
            </option>
          ))}
        </select>
      </div>

      <button className="generate-code-button" onClick={handleGenerateCode}>
        Generate Code
      </button>
    </div>
  );
};

export default ExamTrack;



// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './ExamTrack.css';

// const ExamTrack = () => {
//   const [circulars, setCirculars] = useState([]);
//   const [selectedCircular, setSelectedCircular] = useState('');
//   const [successMessage, setSuccessMessage] = useState('');
//   const [errorMessage, setErrorMessage] = useState('');
//   const userId = localStorage.getItem('userId');
  

//   console.log(userId);

//   useEffect(() => {
//     fetchCirculars();
//   }, []);

//   const fetchCirculars = async () => {
//     try {
//       const response = await axios.get('http://localhost:8082/admin/getAllCircular');
//       setCirculars(response.data.data);
//     } catch (error) {
//       console.error('Error fetching circulars:', error);
//     }
//   };

//   const handleGenerateToken = async () => {
//     if (!selectedCircular) {
//       setErrorMessage('Please select a circular.');
//       setSuccessMessage('');
//       return;
//     }

//     try {
      
//       const data = {
//         circularId: selectedCircular,
//         userId: userId,
//       };

//       console.log(data);

//       const response = await axios.post('http://localhost:8082/admin/track', data);

//       if (response.status === 200) {
//         setSuccessMessage('Token generated successfully!');
//         setErrorMessage('');
//       } else {
//         setErrorMessage('Error generating token. Please try again later.');
//         setSuccessMessage('');
//       }
//     } catch (error) {
//       console.error('Error generating token:', error);
//       setErrorMessage('Error generating token. Please try again later.');
//       setSuccessMessage('');
//     }
//   };

//   return (
//     <div>
//       <h2>Generate Circular Token</h2>
//       <div>
//         <label>Select Circular:</label>
//         <select value={selectedCircular} onChange={(e) => setSelectedCircular(e.target.value)}>
//           <option value="">Select Circular</option>
//           {circulars.map((circular) => (
//             <option key={circular.circularId} value={circular.circularId}>
//               {circular.circularTitle}
//             </option>
//           ))}
//         </select>
//       </div>
//       <button onClick={handleGenerateToken}>Generate Token</button>
//       {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
//       {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
//     </div>
//   );
// };

// export default ExamTrack;
