import React, { useState } from 'react';
import axios from 'axios';

const CreateCategory = () => {
    const [examTitle, setExamTitle] = useState('');
    const [description, setDescription] = useState('');
    const [passingMarks, setPassingMarks] = useState('');

    const API_BASE_URL = "http://localhost:8082/admin";

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!validateForm()) {
            // Form is not valid, display an error message or take appropriate action
            return;
        }

        const data = {
            adminId: 5,
            examTitle,
            description,
            passingMarks: parseInt(passingMarks)
        };

        axios.post(API_BASE_URL + '/create/examCategory', data)
            .then((response) => {
                // Handle the success response
                console.log('Data saved successfully:', response.data);
                // Reset the form fields or show a success message upon successful submission
            })
            .catch((error) => {
                // Handle the error response
                console.error('Error saving data:', error);
                // Display an error message or take appropriate action
            });
    };

    const validateForm = () => {
        // Perform validation for each form field
        if (examTitle.trim() === '') {
            // Exam Title is required
            return false;
        }

        if (description.trim() === '') {
            // Description is required
            return false;
        }

        if (passingMarks.trim() === '' || isNaN(parseInt(passingMarks))) {
            // Passing Marks is required and should be a number
            return false;
        }

        // Perform additional validations for each field if necessary

        // Form is valid
        return true;
    };

    return (
        <div className="container">
            <div>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="examTitle" className="form-label">
                            Exam Title
                        </label>
                        <input
                            type="text"
                            className="form-control"
                            id="examTitle"
                            value={examTitle}
                            onChange={(e) => setExamTitle(e.target.value)}
                            placeholder="Please enter the exam title"
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="description" className="form-label">
                            Description
                        </label>
                        <textarea
                            className="form-control"
                            id="description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            placeholder="Please enter the exam category description"
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="passingMarks" className="form-label">
                            Passing Marks
                        </label>
                        <input
                            type="number"
                            className="form-control"
                            id="passingMarks"
                            value={passingMarks}
                            onChange={(e) => setPassingMarks(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="btn btn-primary">
                        Submit
                    </button>
                </form>
            </div>
        </div>
    );
};


export default CreateCategory;