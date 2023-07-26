import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ViewEvaluator = () => {
  const [evaluators, setEvaluators] = useState([]);

  useEffect(() => {
    fetchEvaluators();
  }, []);

  const fetchEvaluators = () => {
    axios
      .get('http://localhost:8082/admin/getAllEvaluator')
      .then((response) => {
        setEvaluators(response.data.data);
      })
      .catch((error) => {
        console.error('Error fetching evaluators:', error);
      });
  };

  return (
    <div className="container">
      <h1>Evaluators</h1>
      {evaluators.length > 0 ? (
        <table className="table">
          <thead>
            <tr>
              <th>Evaluator Name</th>
              <th>Evaluator Email</th>
              <th>Designation</th>
              <th>Contact Number</th>
              <th>Qualification</th>
              <th>Specialization</th>
              <th>Active</th>
            </tr>
          </thead>
          <tbody>
            {evaluators.map((evaluator) => (
              <tr key={evaluator.evaluatorId}>
                <td>{evaluator.evaluatorName}</td>
                <td>{evaluator.user?.email}</td>
                <td>{evaluator.designation}</td>
                <td>{evaluator.contactNumber}</td>
                <td>{evaluator.qualification}</td>
                <td>{evaluator.specialization}</td>
                <td>{evaluator.active ? 'Active' : 'Inactive'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No evaluators found.</p>
      )}
    </div>
  );
};

export default ViewEvaluator;
