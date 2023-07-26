import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <nav className="navbar navbar-expand-lg bg-dark">
            <div className="container-fluid">
                <div className="navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <Link className="navbar-brand text-white" to="product">Home</Link>
                        </li>
                    </ul>
                    <ul className="navbar-nav ms-auto">

                        <li className="nav-item">
                            <Link className="nav-link text-white" to="applicants">Applicants</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-white" to="create/evaluator">Add Evaluator</Link>
                        </li>
                        <li class="nav-item">
                            <Link className="nav-link text-white" to="evaluators">Evaluators</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-white" to="create/circular">Add Circular</Link>
                        </li>

                        <li className="nav-item">
                            <Link className="nav-link text-white" to="circulars">Circulars</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-white" to="create/examCategory">Add Exam Categories</Link>
                        </li>

                        <li className="nav-item">
                            <Link className="nav-link text-white" to="categories">Exam Categories</Link>
                        </li>

                        <li className="nav-item">
                            <Link className="nav-link text-white" to="/circulars/applicants">Applicants By Circular</Link>
                        </li>
                        

                        <li className="nav-item">
                            <Link className="nav-link text-white" to="login">Signin</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-white" to="register">SignUp</Link>
                        </li>
                        

                        <li className="nav-item">
                            <Link className="nav-link text-white" to="openings">Current Openings</Link>
                        </li>

                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Header;