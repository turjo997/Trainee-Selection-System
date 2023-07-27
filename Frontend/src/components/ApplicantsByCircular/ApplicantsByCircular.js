import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ApplicantsByCircular = () => {
  const [circulars, setCirculars] = useState([]);
  const [selectedCircular, setSelectedCircular] = useState(null);
  const [applicants, setApplicants] = useState([]);
  const examId = 1;

  useEffect(() => {
    fetchCirculars();
  }, []);

  const fetchCirculars = () => {
    axios
      .get('http://localhost:8082/admin/getAllCircular')
      .then((response) => {
        setCirculars(response.data.data);
      })
      .catch((error) => {
        console.error('Error fetching circulars:', error);
      });
  };

  const fetchApplicantsByCircular = (circularId) => {
    axios
      .get(`http://localhost:8082/admin/getApplicants/${circularId}`)
      .then(async (response) => {
        setSelectedCircular({ ...response.data, circularId }); // Set the selected circular with the circularId
        // Add status property to each applicant object
        const applicantsWithStatus = await Promise.all(response.data.map(async (applicant) => {
          const res = await axios.get(`http://localhost:8082/admin/approve/get/${applicant.applicantId}/${circularId}`);
          return {
            ...applicant,
            status: res.data ? 'Approved' : 'Not Approved', // Set status based on API response
          };
        }));
        setApplicants(applicantsWithStatus);
      })
      .catch((error) => {
        console.error('Error fetching applicants for circular:', error);
      });
  };

  // Function to handle approval button click for an applicant
  const handleApprove = (applicantId, circularId) => {
    const userId = localStorage.getItem('userId'); // Get userId from local storage
    console.log(applicantId);
    console.log(circularId);

    // Call the API to check if the applicant is approved or not
    axios
      .get(`http://localhost:8082/admin/approve/get/${applicantId}/${circularId}`)
      .then((response) => {
        const isApproved = response.data;

        console.log(isApproved);

        if (isApproved) {
          // If the applicant is already approved, update the status to 'Approved'
          setApplicants((prevApplicants) =>
            prevApplicants.map((applicant) => {
              if (applicant.applicantId === applicantId) {
                return { ...applicant, status: 'Approved' };
              }
              return applicant;
            })
          );
        } else {
          // If the applicant is not approved, call the second API to approve the applicant
          const data = {
            userId,
            applicantId,
            circularId,
          };

          axios
            .post('http://localhost:8082/admin/approve', data)
            .then((response) => {
              // If the approval is successful, update the status to 'Approved'
              setApplicants((prevApplicants) =>
                prevApplicants.map((applicant) => {
                  if (applicant.applicantId === applicantId) {
                    return { ...applicant, status: 'Approved' };
                  }
                  return applicant;
                })
              );
            })
            .catch((error) => {
              console.error('Error approving applicant:', error);
            });
        }
      })
      .catch((error) => {
        console.error('Error checking approval status:', error);
      });
  };

  return (
    <div className="container">
      <h1 className="text-center mb-4">All Circulars</h1>
      <div className="row">
        <div className="col-md-6">
          <h2>Circulars</h2>
          <ul className="list-group">
            {circulars.map((circular) => (
              <li
                key={circular.circularId}
                className="list-group-item d-flex justify-content-between align-items-center"
                style={{ cursor: 'pointer' }}
                onClick={() => fetchApplicantsByCircular(circular.circularId)}
              >
                {circular.circularTitle}
                <span className="badge bg-primary rounded-pill">View Applicants</span>
              </li>
            ))}
          </ul>
        </div>
      </div>
      {selectedCircular && (
        <div className="row mt-4">
          <div className="col-md-12">
            <h2>Applicants for {selectedCircular.circularTitle}</h2>
            <table className="table table-bordered">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Contact</th>
                  <th>Status</th>
                  <th>Approved</th>
                </tr>
              </thead>
              <tbody>
                {applicants.map((applicant) => (
                  <tr key={applicant.applicantId}>
                    <td>{applicant.firstName + ' ' + applicant.lastName}</td>
                    <td>{applicant.email}</td>
                    <td>{applicant.contact}</td>
                    <td>{applicant.status}</td>
                    <td>
                      {applicant.status === 'Not Approved' && (
                        <button
                          className="btn btn-success"
                          onClick={() => handleApprove(applicant.applicantId, selectedCircular.circularId)}
                        >
                          Approve
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
};

export default ApplicantsByCircular;
