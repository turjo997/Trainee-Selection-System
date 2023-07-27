import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Upload = () => {
    const [imageFile, setImageFile] = useState(null);
    const [cvFile, setCvFile] = useState(null);
    const [successMsg, setSuccessMsg] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [isUploaded, setIsUploaded] = useState(false);
    const userId = localStorage.getItem('userId');

    useEffect(() => {
        // Reset the success message after 3 seconds
        if (successMsg) {
            const timer = setTimeout(() => {
                setSuccessMsg('');
            }, 23000);

            return () => clearTimeout(timer);
        }
    }, [successMsg]);

    const handleImageFileChange = (e) => {
        setImageFile(e.target.files[0]);
    };

    const handleCvFileChange = (e) => {
        setCvFile(e.target.files[0]);
    };

    const handleUpload = async () => {
        try {
            if (isUploaded) {
                setErrorMsg('You have already uploaded the files');
                return;
            }

            const formData = new FormData();
            formData.append('image', imageFile);
            formData.append('cv', cvFile);

            const response = await axios.post(`http://localhost:8082/applicant/upload/${userId}`, formData);
            console.log("data saved");
            setSuccessMsg('File uploaded successfully');
            setErrorMsg('');
            setIsUploaded(true); // Set isUploaded to true after successful upload
        } catch (error) {

            console.log(error);
            setSuccessMsg('');
            setErrorMsg('Error uploading files');
        }
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <h2 className="mb-3">Upload Files</h2>
                    {successMsg && <div className="alert alert-success">{successMsg}</div>}
                    {errorMsg && <div className="alert alert-danger">{errorMsg}</div>}
                    <div className="mb-3">
                        <label htmlFor="imageFile" className="form-label">Choose Image File:</label>
                        <input type="file" className="form-control" id="imageFile" onChange={handleImageFileChange} />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="cvFile" className="form-label">Choose CV File:</label>
                        <input type="file" className="form-control" id="cvFile" onChange={handleCvFileChange} />
                    </div>
                    <button type="button" className="btn btn-primary" onClick={handleUpload}>Upload</button>
                </div>
            </div>
        </div>
    );
};

export default Upload;
