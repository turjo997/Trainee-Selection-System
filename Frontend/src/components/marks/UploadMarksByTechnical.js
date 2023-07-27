import React, { useEffect, useState } from 'react';
import './UploadMarks.css';
import axios from 'axios';

const UploadMarksByTechnical = () => {
  const [jobCirculars, setJobCirculars] = useState([]);
  const [selectedJobCircular, setSelectedJobCircular] = useState('');
  const [applicants, setApplicants] = useState([]);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [userId] = useState(localStorage.getItem('userId'));
  const [isFetchButtonClicked, setIsFetchButtonClicked] = useState(false);

  const examId = 3;

  useEffect(() => {
    fetchCirculars();

  }, []);

  const fetchCirculars = async () => {
    try {
      const response = await axios.get('http://localhost:8082/admin/getAllCircular');
      setJobCirculars(response.data.data);
    } catch (error) {
      console.error('Error fetching circulars:', error);
    }
  };

  const fetchStatusForApplicant = async (applicantId, circularId) => {
    try {
      const response = await axios.get(`http://localhost:8082/admin/get/${applicantId}/${circularId}/${examId}`);
      console.log(response.data);
      return response.data; // Assuming the API response provides a boolean value
    } catch (error) {
      console.error('Error fetching status for applicant:', error);
      return false; // Return false by default if an error occurs
    }
  };

  useEffect(() => {
    fetchApplicants();
  }, [selectedJobCircular]);

  const handleJobCircularChange = (event) => {
    setSelectedJobCircular(event.target.value);
  };

  const handleFetchApplicants = () => {
    setIsFetchButtonClicked(true);
    fetchApplicants();
  };

  const fetchApplicants = async () => {
    if (!selectedJobCircular) {
      setErrorMessage('Please select a Job Circular before fetching applicants.');
      return;
    }

    console.log(selectedJobCircular);

    try {
      const response = await axios.get(`http://localhost:8082/admin/getApplicants/${selectedJobCircular}/${examId}`);
      const fetchedApplicants = response.data; // Assuming the API response provides an array of applicants
      if (fetchedApplicants.length === 0) {
        setErrorMessage('No applicants found for the selected Job Circular.');
        return null;
      } else {
        setErrorMessage('');

        // Fetch the status and marks for each applicant and update the status in the applicants array
        const applicantsWithStatusAndMarks = await Promise.all(
          fetchedApplicants.map(async (applicant) => {
            const statusResponse = await fetchStatusForApplicant(applicant.applicantId, selectedJobCircular);
            const status = statusResponse ? 'Submitted' : 'Not Submitted';
            return {
              ...applicant,
              status,
              //marks,
            };
          })
        );

        setApplicants(applicantsWithStatusAndMarks);
      }
    } catch (error) {
      console.error('Error fetching applicants:', error);
      setErrorMessage('Error fetching applicants. Please try again later.');
    }
  };

  const handleSaveMarks = async (applicant) => {

    if (applicant.marks < 0 || applicant.marks > 100) {
      setErrorMessage('Please enter valid marks (0 to 100).');
      return;
    }
    try {
      const data = {
        userId,
        applicantId: applicant.applicantId,
        circularId: selectedJobCircular,
        examId: 3,
        marks: applicant.marks,
      };

      console.log(data);

      const response = await axios.post('http://localhost:8082/admin/uploadMarks', data);

      console.log(response.status);


      if (response.status === 200) {
        // If marks are successfully uploaded, update the status to 'Submitted' for the specific applicant
        setApplicants((prevApplicants) =>
          prevApplicants.map((app) => (app.applicantId === applicant.applicantId ? { ...app, status: 'Submitted' } : app))
        );
        setSuccessMessage('Marks saved successfully!');
      }

    } catch (error) {
      console.error('Error uploading marks:', error);
      setErrorMessage('Error uploading marks. Please try again later.');
    }
  };

  return (
    <div className="upload-marks-container">
      <h2 className="title">Upload Marks</h2>
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


      <button className="fetch-applicants-button" onClick={handleFetchApplicants}>
        Fetch Applicants
      </button>

      {isFetchButtonClicked && !selectedJobCircular && (
        <p className="error-message">Please select a Job Circular before fetching applicants.</p>
      )}

      {errorMessage && <p className="error-message">{errorMessage}</p>}
      {successMessage && <p className="success-message">{successMessage}</p>}

      {applicants.length > 0 && (
        <div className="applicants-table-container">
          <h3>Applicants:</h3>
          <table className="applicants-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Marks</th>
                <th>Status</th>
                <th>Save</th>
              </tr>
            </thead>
            <tbody>
              {applicants.map((applicant) => (
                <tr key={applicant.applicantId}>
                  <td>{applicant.firstName + ' ' + applicant.lastName}</td>
                  <td>{applicant.email}</td>
                  <td>
                    <input
                      type="number"
                      value={applicant.marks}
                      onChange={(e) =>
                        setApplicants(
                          applicants.map((app) =>
                            app.applicantId === applicant.applicantId ? { ...app, marks: e.target.value } : app
                          )
                        )
                      }
                      min="0"
                      max="100"
                      step="1"
                      disabled={applicant.status === 'Submitted'} // Disable the input for Submitted applicants
                    />
                  </td>
                  <td>{applicant.status}</td>
                  <td>
                    {applicant.status === 'Not Submitted' && (
                      <button
                        className="save-marks-button"
                        onClick={() => handleSaveMarks(applicant)}
                        disabled={applicant.status === 'Submitted'} // Disable the Save button for Submitted applicants
                      >
                        Save
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}


    </div>
  );
};


export default UploadMarksByTechnical;