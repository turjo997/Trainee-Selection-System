import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const CurrentOpenings = () => {
    const [circulars, setCirculars] = useState([]);
    const [appliedStatus, setAppliedStatus] = useState({});

    const userId = localStorage.getItem('userId');

    useEffect(() => {
        fetchCirculars();
    }, []);

    const fetchCirculars = () => {
        axios
            .get('http://localhost:8082/admin/getAllCircular')
            .then((response) => {
                setCirculars(response.data.data);
                checkAppliedStatus(response.data.data);
            })
            .catch((error) => {
                console.error('Error fetching circulars:', error);
            });
    };

    const checkAppliedStatus = async (circulars) => {
        try {
            const appliedStatusMap = {};
            for (const circular of circulars) {
                const response = await axios.get(`http://localhost:8082/applicant/get/${circular.circularId}/${userId}`);
                appliedStatusMap[circular.circularId] = response.data;
            }
            setAppliedStatus(appliedStatusMap);
        } catch (error) {
            console.error('Error fetching applied status:', error);
        }
    };

    return (
        <div className="container">
            <h1 className="text-center mb-4">Current Openings</h1>
            <div className="row">
                {circulars.map((circular) => (
                    <div className="col-md-4" key={circular.circularId}>
                        <div className="card mb-4">
                            <div className="card-body">
                                <h5 className="card-title">{circular.circularTitle}</h5>
                                <p className="card-text">Job Type: {circular.jobType}</p>
                                <p className="card-text">Open Date: {circular.openDate}</p>
                                <p className="card-text">Close Date: {circular.closeDate}</p>
                                <p className="card-text">Status: {circular.status}</p>
                                {appliedStatus[circular.circularId] ? (
                                    <p className="text-success">You have already applied</p>
                                ) : (
                                    <Link to={`/jobDetails/${circular.circularId}`} className="btn btn-primary">
                                        Apply Now
                                    </Link>
                                )}
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default CurrentOpenings;
