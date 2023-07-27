import React, { useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from '../components/Home';
import ViewCircular from '../components/Circular/ViewCircular';
import ViewApplicant from '../components/Applicant/ViewApplicant';
import ViewEvaluator from '../components/Evaluator/ViewEvaluator';
import ViewCategory from '../components/ExamCategory/ViewCategory';
import CreateCircular from '../components/Circular/CreateCircular';
import CreateEvaluator from '../components/Evaluator/CreateEvaluator';
import CreateCategory from '../components/ExamCategory/CreateCategory';
import SignInPage from '../components/SignIn/SignInPage';
import SignUpPage from '../components/SignUpPage/SignUpPage';
import ApplicantsByCircular from '../components/ApplicantsByCircular/ApplicantsByCircular';
import CurrentOpenings from '../components/JobOpenings/CurrentOpenings';
import JobDetails from '../components/JobOpenings/JobDetails';
import Navbar from '../layout/Navbar';

import UnAuthorized from '../components/UnAuhorized/UnAuthorized';
import ProtectedRoute from './Protected';
import ApplicantProfile from '../components/Applicant/ApplicantProfile';
import Upload from '../components/Applicant/Upload';
import NoticeBoard from '../components/Applicant/NoticeBoard';
import Notice from '../components/NoticeBoard/Notice';
import ExamTrack from '../components/ExamTrack/ExamTrack';
import UploadMarks from '../components/marks/UploadMarks';
import ApproveByTechnical from '../components/Approval/ApproveByTechnical';
import ApproveByHr from '../components/Approval/ApproveByHr';
import UploadMarksByTechnical from '../components/marks/UploadMarksByTechnical';
import UploadMarksByHr from '../components/marks/UploadMarksByHr';
import Qrcode from '../components/QrCode/Qrcode';
import AdmitCardDownload from '../components/Applicant/AdmitCardDownload';
import SendEmail from '../components/MailService/SendEmail';
import FinalTraineeList from '../components/FinalTraineeList/FinalTraineeList';

const Routing = () => {

    const token = localStorage.getItem('token');
    const [isLoggedIn, setIsLoggedIn] = useState(token);

    return (
        <BrowserRouter>
            <Navbar isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />

            <Routes>

                <Route path="/login" element={<SignInPage setIsLoggedIn={setIsLoggedIn} />} />
                <Route path="/register" element={<SignUpPage />} />

                <Route path="/applicants"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <ViewApplicant />
                    </ProtectedRoute>}></Route>


                <Route path="/track/exam"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <ExamTrack />
                    </ProtectedRoute>}></Route>


                <Route path="/admin/generate"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <Qrcode />
                    </ProtectedRoute>}></Route>


                <Route path="/admin/marks/technical"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <UploadMarksByTechnical />
                    </ProtectedRoute>}></Route>


                <Route path="/admin/marks/hr"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <UploadMarksByHr />
                    </ProtectedRoute>}></Route>



                <Route path="/approve/technical"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <ApproveByTechnical />
                    </ProtectedRoute>}></Route>

                <Route path="/approve/hr"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <ApproveByHr />
                    </ProtectedRoute>}></Route>


                <Route path="/evaluator/upload/marks"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="EVALUATOR">
                        <UploadMarks />
                    </ProtectedRoute>}></Route>


                <Route path="/evaluators"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <ViewEvaluator />
                    </ProtectedRoute>}></Route>

                <Route path="/admin/final/trainees"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <FinalTraineeList />
                    </ProtectedRoute>}></Route>


                <Route path="/create/evaluator"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <CreateEvaluator />
                    </ProtectedRoute>}>

                </Route>


                <Route path="/create/circular"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <CreateCircular />
                    </ProtectedRoute>}>

                </Route>


                <Route path="/circulars"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <ViewCircular />
                    </ProtectedRoute>}>

                </Route>

                <Route path="/create/examCategory"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <CreateCategory />
                    </ProtectedRoute>}>

                </Route>


                <Route path="/admin/send/email"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <SendEmail />
                    </ProtectedRoute>}>

                </Route>



                <Route path="/categories"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <ViewCategory />
                    </ProtectedRoute>}></Route>


                <Route path="/notices"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <Notice />
                    </ProtectedRoute>}></Route>


                <Route path="/circulars/applicants"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="ADMIN">
                        <ApplicantsByCircular />
                    </ProtectedRoute>}></Route>


                <Route path="/openings"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="APPLICANT">
                        <CurrentOpenings />
                    </ProtectedRoute>}></Route>

                <Route path="/jobDetails/:circularId"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="APPLICANT">
                        <JobDetails />
                    </ProtectedRoute>}></Route>


                <Route path="/applicant/get/:userId"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="APPLICANT">
                        <ApplicantProfile />
                    </ProtectedRoute>}></Route>


                <Route path="/applicant/upload"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="APPLICANT">
                        <Upload />
                    </ProtectedRoute>}></Route>


                <Route path="/applicant/notice"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="APPLICANT">
                        <NoticeBoard />
                    </ProtectedRoute>}></Route>


                <Route path="/applicant/admit/card"
                    element={<ProtectedRoute
                        isLoggedIn={isLoggedIn}
                        requiredRole="APPLICANT">
                        <AdmitCardDownload />
                    </ProtectedRoute>}></Route>


                <Route path="/unauthorized" element={<UnAuthorized />} />
            </Routes>
        </BrowserRouter>
    );
};

export default Routing;
