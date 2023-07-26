import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ViewApplicant = () => {
  const [applicants, setApplicants] = useState([]);

  useEffect(() => {
    fetchApplicans();
  }, []);

  const fetchApplicans = () => {
    axios
      .get('http://localhost:8082/admin/getAllApplicant')
      .then((response) => {
        setApplicants(response.data.data);
      })
      .catch((error) => {
        console.error('Error fetching applicants:', error);
      });
  };

  return (
    <div className="container">
      <h1>Applicants</h1>
      {applicants.length > 0 ? (
        <table className="table">
          <thead>
            <tr>
              <th>Applicant Name</th>
              <th>Applicant Email</th>
              <th>Date of Birth</th>
              <th>Gender</th>
              <th>Address</th>
              <th>Contact</th>
              <th>Instiute</th>
              <th>Degree</th>
              <th>Result</th>
              <th>Passing Year</th>
            </tr>
          </thead>
          <tbody>
            {applicants.map((applicant) => (
              <tr key={applicant.applicantId}>
                <td>{applicant.firstName + ' ' + applicant.lastName}</td>
                <td>{applicant.user.email}</td>
                <td>{applicant.dob}</td>
                <td>{applicant.gender}</td>
                <td>{applicant.address}</td>
                <td>{applicant.contact}</td>
                <td>{applicant.institute}</td>
                <td>{applicant.degreeName}</td>
                <td>{applicant.cgpa}</td>
                <td>{applicant.passingYear}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No Applicants found.</p>
      )}
    </div>
  );
};

export default ViewApplicant;
