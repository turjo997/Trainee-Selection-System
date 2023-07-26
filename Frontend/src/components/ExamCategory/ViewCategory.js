import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ViewCategory = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = () => {
    axios
      .get('http://localhost:8082/admin/getAllExamCategory')
      .then((response) => {
        setCategories(response.data.data);
      })
      .catch((error) => {
        console.error('Error fetching exam categories:', error);
      });
  };

  return (
    <div className="container">
      <h1>Exam Categories</h1>
      {categories.length > 0 ? (
        <table className="table">
          <thead>
            <tr>
              <th>Exam Title</th>
              <th>Description</th>
              <th>Passing Marks</th>
            </tr>
          </thead>
          <tbody>
            {categories.map((category) => (
              <tr key={category.examId}>
                <td>{category.examTitle}</td>
                <td>{category.description}</td>
                <td>{category.passingMarks}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No exam categories found.</p>
      )}
    </div>
  );
};

export default ViewCategory;
