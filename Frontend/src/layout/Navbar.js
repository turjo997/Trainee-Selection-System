import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar = ({ isLoggedIn, setIsLoggedIn }) => {
    const navigate = useNavigate();


    // Assuming you have the user ID stored in state, you can update it like this
    const [userId, setUserId] = useState(0); // Set initial value as per your requirement
    // Fetch the user ID from the local storage or wherever you are storing it
    useEffect(() => {
        const storedUserId = parseInt(localStorage.getItem('userId'));
        if (!isNaN(storedUserId)) {
            setUserId(storedUserId);
        }
    }, []);

    const logout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('userRole');
        setIsLoggedIn(false);
        navigate('/login');
    };

    const userRole = localStorage.getItem('userRole');

    return (
        <nav className="navbar navbar-expand-lg bg-dark">
            <div className="container-fluid">
                <div className="navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <Link className="navbar-brand text-white" to="/">
                                Home
                            </Link>
                        </li>
                    </ul>
                </div>

                <ul className="navbar-nav ms-auto">
                    {isLoggedIn && userRole === 'APPLICANT' && (
                        <>
                        
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="applicant/admit/card">
                                    Download Admit Card
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to="openings">
                                    Current Openings
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to={`/applicant/get/${userId}`}>
                                    Profile
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to={`/applicant/upload`}>
                                    Upload
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to={`/applicant/notice`}>
                                    NoticeBoard
                                </Link>
                            </li>



                            {/* Add other links for APPLICANT role */}
                        </>
                    )}

                    {isLoggedIn && userRole === 'EVALUATOR' && (
                        <>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="evaluators">
                                    Evaluators
                                </Link>
                            </li>


                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/evaluator/upload/marks">
                                    Upload Marks
                                </Link>
                            </li>


                            {/* Add other links for EVALUATOR role */}
                        </>
                    )}

                    {isLoggedIn && userRole === 'ADMIN' && (
                        <>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="applicants">
                                    Applicants
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to="circulars">
                                    Circulars
                                </Link>
                            </li>


                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/admin/marks/technical">
                                    Upload Marks (Technical)
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/admin/marks/hr">
                                    Upload Marks (Hr)
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/track/exam">
                                    Track Exam
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/admin/generate">
                                    Generate (QrCode)
                                </Link>
                            </li>


                            <li className="nav-item">
                                <Link className="nav-link text-white" to="notices">
                                    Add Notice
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to="categories">
                                    Exam Categories
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to="evaluators">
                                    Evaluators
                                </Link>
                            </li>


                            <li className="nav-item">
                                <Link className="nav-link text-white" to="create/evaluator">
                                    Add Evaluator
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="create/circular">
                                    Add Circular
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="create/examCategory">
                                    Add Exam Categories
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/circulars/applicants">
                                    Approve for written
                                </Link>
                            </li>


                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/approve/technical">
                                    Approve for Technical
                                </Link>
                            </li>


                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/approve/hr">
                                    Approve for HR
                                </Link>
                            </li>


                            


                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/admin/send/email">
                                    Email
                                </Link>
                            </li>




                            {/* Add other links for ADMIN role */}
                        </>
                    )}

                    {!isLoggedIn ? (
                        <>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="login">
                                    Signin
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link text-white" to="register">
                                    SignUp
                                </Link>
                            </li>
                        </>
                    ) : (
                        <li className="nav-item">
                            <button className="nav-link text-white" onClick={logout}>
                                Logout
                            </button>
                        </li>
                    )}
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;




