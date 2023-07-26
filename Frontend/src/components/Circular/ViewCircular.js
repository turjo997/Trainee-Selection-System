import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ViewCircular = () => {
  const [circulars, setCirculars] = useState([]);

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

  return (
    <div className="container">
      <h1>Circulars</h1>
      {circulars.length > 0 ? (
        <table className="table">
          <thead>
            <tr>
              <th>Circular Title</th>
              <th>Job Type</th>
              <th>Open Date</th>
              <th>Close Date</th>
              <th>Job Description</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {circulars.map((circular) => (
              <tr key={circular.circularId}>
                <td>{circular.circularTitle}</td>
                <td>{circular.jobType}</td>
                <td>{circular.openDate}</td>
                <td>{circular.closeDate}</td>
                <td>{circular.jobDescription}</td>
                <td>{circular.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No circulars found.</p>
      )}
    </div>
  );
};

export default ViewCircular;
