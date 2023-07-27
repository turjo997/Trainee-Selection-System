import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Navbar.css';

const Navbar = ({ isLoggedIn, setIsLoggedIn }) => {
    const navigate = useNavigate();

    const [userId, setUserId] = useState(0);

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
        <nav className="navbar navbar-expand-lg custom-navbar bg-dark">
            <div className="container-fluid">

                <div className="navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <Link className="navbar-brand text-white" to="/">
                                TSS
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

                        </>
                    )}

                    {isLoggedIn && userRole === 'ADMIN' && (
                        <>

                            <li className="nav-item">
                                <Link className="nav-link text-white" to="/admin/final/trainees">
                                    Final Trainees
                                </Link>
                            </li>

                            <li class="nav-item dropdown">
                                <Link class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Upload Marks
                                </Link>
                                <ul class="dropdown-menu">
                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="/admin/marks/technical">
                                            <span style={{ fontWeight: 'bold' }}>Upload Marks (Technical)</span>
                                        </Link>
                                    </li>

                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="/admin/marks/hr">
                                            <span style={{ fontWeight: 'bold' }}>Upload Marks (Hr)</span>
                                        </Link>
                                    </li>

                                </ul>
                            </li>

                            <li class="nav-item dropdown">
                                <Link class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Approve Applicants
                                </Link>
                                <ul class="dropdown-menu">

                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="/circulars/applicants">

                                            <span style={{ fontWeight: 'bold' }}>Approve for written</span>
                                        </Link>
                                    </li>


                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="/approve/technical">

                                            <span style={{ fontWeight: 'bold' }}>Approve for Technical</span>
                                        </Link>
                                    </li>


                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="/approve/hr">
                                            <span style={{ fontWeight: 'bold' }}>Approve for HR</span>
                                        </Link>
                                    </li>

                                </ul>
                            </li>


                            <li class="nav-item dropdown">
                                <Link class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Views
                                </Link>
                                <ul class="dropdown-menu">

                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="applicants">

                                            <span style={{ fontWeight: 'bold' }}>Applicants</span>
                                        </Link>
                                    </li>

                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="circulars">

                                            <span style={{ fontWeight: 'bold' }}>Circulars</span>
                                        </Link>
                                    </li>

                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="evaluators">

                                            <span style={{ fontWeight: 'bold' }}>Evaluators</span>
                                        </Link>
                                    </li>

                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="categories">

                                            <span style={{ fontWeight: 'bold' }}> Exam Categories</span>
                                        </Link>
                                    </li>

                                </ul>
                            </li>


                            <li class="nav-item dropdown">
                                <Link class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Add
                                </Link>
                                <ul class="dropdown-menu">

                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="create/evaluator">

                                            <span style={{ fontWeight: 'bold' }}>Add Evaluator</span>
                                        </Link>
                                    </li>
                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="create/circular">

                                            <span style={{ fontWeight: 'bold' }}>Add Circular</span>
                                        </Link>
                                    </li>
                                    <li className="nav-item">
                                        <Link className="nav-link text-black" to="create/examCategory">

                                            <span style={{ fontWeight: 'bold' }}>Add Exam Categories</span>
                                        </Link>
                                    </li>
                                </ul>
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
                                <Link className="nav-link text-white" to="/admin/send/email">
                                    Send Email
                                </Link>
                            </li>
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




