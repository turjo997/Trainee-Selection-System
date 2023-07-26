// import axios from 'axios';
// import React, { useState } from 'react';

// const CreateCircular = () => {
//   const [circularTitle, setCircularTitle] = useState('');
//   const [jobType, setJobType] = useState('');
//   const [openDate, setOpenDate] = useState('');
//   const [closeDate, setCloseDate] = useState('');
//   const [jobDescription, setJobDescription] = useState('');
//   const [status, setStatus] = useState('');

//   const API_BASE_URL = "http://localhost:8082/admin";

//   const handleSubmit = (e) => {
//     e.preventDefault();

//     if (!validateForm()) {
//       // Form is not valid, display an error message or take appropriate action
//       return;
//     }

//     // Convert the date strings to Date objects
//     const openDateObj = new Date(openDate);
//     const closeDateObj = new Date(closeDate);

//     // Create a data object with the form values
//     const data = {
//       adminId: 5,
//       circularTitle,
//       jobType,
//       openDate: openDateObj,
//       closeDate: closeDateObj,
//       jobDescription,
//       status
//     };

//     // Send the form data to your backend API using Axios
//     axios.post(API_BASE_URL + '/create/circular', data)
//       .then((response) => {
//         // Handle the success response
//         console.log('Data saved successfully:', response.data);
//         // Reset the form fields or show a success message upon successful submission
//       })
//       .catch((error) => {
//         // Handle the error response
//         console.error('Error saving data:', error);
//         // Display an error message or take appropriate action
//       });
//   };

//   const validateForm = () => {
//     // Perform validation for each form field
//     if (circularTitle.trim() === '') {
//       // Circular Title is required
//       return false;
//     }

//     if (jobType.trim() === '') {
//       // Job Type is required
//       return false;
//     }

//     if (openDate.trim() === '') {
//       // Open Date is required
//       return false;
//     }

//     if (closeDate.trim() === '') {
//       // Close Date is required
//       return false;
//     }

//     if (jobDescription.trim() === '') {
//       // Job Description is required
//       return false;
//     }

//     if (status.trim() === '') {
//       // Status is required
//       return false;
//     }

//     // Perform additional validations for each field if necessary

//     // Form is valid
//     return true;
//   };

//   return (
//     <div className="container">
//       <div>
//         <form onSubmit={handleSubmit}>
//           <div className="mb-3">
//             <label htmlFor="circularTitle" className="form-label">
//               Circular Title
//             </label>
//             <input
//               type="text"
//               className="form-control"
//               id="circularTitle"
//               value={circularTitle}
//               onChange={(e) => setCircularTitle(e.target.value)}
//               placeholder="Please enter the circular title"
//               required
//             />
//           </div>
//           <div className="mb-3">
//             <label htmlFor="jobType" className="form-label">
//               Job Type
//             </label>
//             <select
//               className="form-select"
//               id="jobType"
//               value={jobType}
//               onChange={(e) => setJobType(e.target.value)}
//               required>
//               <option value="">Select job type</option>
//               <option value="Full-time">Full-time</option>
//               <option value="Intern">Intern</option>
//               <option value="Half-time">Half-time</option>
//             </select>
//           </div>

//           <div className="mb-3">
//             <label htmlFor="openDate" className="form-label">
//               Open Date
//             </label>
//             <input
//               type="date"
//               className="form-control"
//               id="openDate"
//               value={openDate}
//               onChange={(e) => setOpenDate(e.target.value)}
//               required
//             />
//           </div>
//           <div className="mb-3">
//             <label htmlFor="closeDate" className="form-label">
//               Close Date
//             </label>
//             <input
//               type="date"
//               className="form-control"
//               id="closeDate"
//               value={closeDate}
//               onChange={(e) => setCloseDate(e.target.value)}
//               required
//             />
//           </div>
//           <div className="mb-3">
//             <label htmlFor="jobDescription" className="form-label">
//               Job Description
//             </label>
//             <textarea
//               className="form-control"
//               id="jobDescription"
//               value={jobDescription}
//               onChange={(e) => setJobDescription(e.target.value)}
//               placeholder="Please enter the job description"
//               required
//             />
//           </div>
//           <div className="mb-3">
//             <label htmlFor="status" className="form-label">
//               Status
//             </label>
//             <select
//               className="form-select"
//               id="status"
//               value={status}
//               onChange={(e) => setStatus(e.target.value)}
//               required
//             >
//               <option value="Active">Active</option>
//               <option value="Inactive">Inactive</option>
//             </select>
//           </div>
//           <button type="submit" className="btn btn-primary">
//             Submit
//           </button>
//         </form>
//       </div>
//     </div>
//   );
// };

// export default CreateCircular;

import axios from 'axios';
import React, { useState } from 'react';

const CreateCircular = () => {
  const [circularTitle, setCircularTitle] = useState('');
  const [jobType, setJobType] = useState('');
  const [openDate, setOpenDate] = useState('');
  const [closeDate, setCloseDate] = useState('');
  const [jobDescription, setJobDescription] = useState('');
  const [status, setStatus] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errors, setErrors] = useState({});

  const userId = localStorage.getItem('userId');

  const API_BASE_URL = "http://localhost:8082/admin";

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!validateForm()) {
      // Form is not valid, display an error message or take appropriate action
      return;
    }


    // Create a data object with the form values
    const data = {
      userId: userId,
      circularTitle,
      jobType,
      openDate,
      closeDate,
      jobDescription,
      status
    };

    console.log(data);

    // Send the form data to your backend API using Axios
    axios.post(API_BASE_URL + '/create/circular', data)
      .then((response) => {
        // Handle the success response
        console.log('Data saved successfully:', response.data);
        setSuccessMessage('Circular created successfully!');
        setErrors({});
        // Reset the form fields or show a success message upon successful submission
        setCircularTitle('');
        setJobType('');
        setOpenDate('');
        setCloseDate('');
        setJobDescription('');
        setStatus('');
      })
      .catch((error) => {
        // Handle the error response
        console.error('Error saving data:', error);
        setSuccessMessage('');
        setErrors({ apiError: 'Error creating circular. Please try again later.' });
      });
  };

  const validateForm = () => {
    const errors = {};

    if (circularTitle.trim() === '') {
      errors.circularTitle = 'Circular Title is required.';
    }

    if (jobType.trim() === '') {
      errors.jobType = 'Job Type is required.';
    }

    if (openDate.trim() === '') {
      errors.openDate = 'Open Date is required.';
    }

    if (closeDate.trim() === '') {
      errors.closeDate = 'Close Date is required.';
    }

    if (jobDescription.trim() === '') {
      errors.jobDescription = 'Job Description is required.';
    }

    if (status.trim() === '') {
      errors.status = 'Status is required.';
    }

    // Update the errors state
    setErrors(errors);

    // Form is valid if there are no errors
    return Object.keys(errors).length === 0;
  };

  return (
    <div className="container">
      <div>
        {successMessage && <p className="success-message">{successMessage}</p>}
        {errors.apiError && <p className="error-message">{errors.apiError}</p>}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="circularTitle" className="form-label">
              Circular Title
            </label>
            <input
              type="text"
              className="form-control"
              id="circularTitle"
              value={circularTitle}
              onChange={(e) => setCircularTitle(e.target.value)}
              placeholder="Please enter the circular title"
              required
            />
            {errors.circularTitle && <p className="error-message">{errors.circularTitle}</p>}
          </div>
          <div className="mb-3">
            <label htmlFor="jobType" className="form-label">
              Job Type
            </label>
            <select
              className="form-select"
              id="jobType"
              value={jobType}
              onChange={(e) => setJobType(e.target.value)}
              required
            >
              <option value="">Select job type</option>
              <option value="Full-time">Full-time</option>
              <option value="Intern">Intern</option>
              <option value="Half-time">Half-time</option>
            </select>
            {errors.jobType && <p className="error-message">{errors.jobType}</p>}
          </div>

          <div className="mb-3">
            <label htmlFor="openDate" className="form-label">
              Open Date
            </label>
            <input
              type="date"
              className="form-control"
              id="openDate"
              value={openDate}
              onChange={(e) => setOpenDate(e.target.value)}
              required
            />
            {errors.openDate && <p className="error-message">{errors.openDate}</p>}
          </div>
          <div className="mb-3">
            <label htmlFor="closeDate" className="form-label">
              Close Date
            </label>
            <input
              type="date"
              className="form-control"
              id="closeDate"
              value={closeDate}
              onChange={(e) => setCloseDate(e.target.value)}
              required
            />
            {errors.closeDate && <p className="error-message">{errors.closeDate}</p>}
          </div>
          <div className="mb-3">
            <label htmlFor="jobDescription" className="form-label">
              Job Description
            </label>
            <textarea
              className="form-control"
              id="jobDescription"
              value={jobDescription}
              onChange={(e) => setJobDescription(e.target.value)}
              placeholder="Please enter the job description"
              required
            />
            {errors.jobDescription && <p className="error-message">{errors.jobDescription}</p>}
          </div>
          <div className="mb-3">
            <label htmlFor="status" className="form-label">
              Status
            </label>
            <select
              className="form-select"
              id="status"
              value={status}
              onChange={(e) => setStatus(e.target.value)}
              required
            >
              <option value="">Select status</option>
              <option value="Active">Active</option>
              <option value="Inactive">Inactive</option>
            </select>
            {errors.status && <p className="error-message">{errors.status}</p>}
          </div>
          <button type="submit" className="btn btn-primary">
            Submit
          </button>
        </form>
      </div>
    </div>
  );
};

export default CreateCircular;
