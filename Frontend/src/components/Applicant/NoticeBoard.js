import React, { useEffect, useState } from 'react';
import axios from 'axios';

const NoticeBoard = () => {

    const userId = localStorage.getItem('userId');
    const [notices, setNotices] = useState([]);


    console.log(userId);

    useEffect(() => {
        fetchNotices();
    }, []);

    const fetchNotices = () => {
        axios
            .get(`http://localhost:8082/applicant/notifications/${userId}`)
            .then((response) => {
                console.log(response.data.data);
                const responseData = response.data.data;
                const noiceArray = Array.isArray(responseData) ? responseData : [responseData];

                setNotices(noiceArray);
            })
            .catch((error) => {
                console.error('Error fetching notices:', error);
            });
    };

    return (

        <div className="container mt-5">
            <h2 className="mb-3">Notice Board</h2>
            {notices.map((notice) => (
                <div key={notice.notificationId} className="card mb-3">
                    <div className="card-header">
                        <h5>{notice.title}</h5>
                    </div>
                    <div className="card-body">
                        <p>{notice.description}</p>
                    </div>
                </div>
            ))}
            {notices.length === 0 && <p>No notices available</p>}
        </div>
    );
};

export default NoticeBoard;
