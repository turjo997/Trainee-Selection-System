# Final Project
## Trainee Selection System
<hr>
<br>

## Setup Environment
<hr>

- JDK 17
- Gradle 8.1.1
- Spring Boot 3.0.6
  

<br> 

## Database Details
<hr>

- MySql Server Port : 8082
- MySql Username : root
- MySql Password : root
- Database Name : trainee_selection_system

<br>

## Functionalities
<hr>

<br>

-  Feature 01: Applicants Registration
 
     Applicants should be able to register themselves to our portal. For the registration purpose they have to insert below informations:
   <br> 
    ● First name
   <br> 
    ● Last name
   <br> 
    ● Gender
   <br> 
    ● Date of birth
   <br> 
    ● Valid G-mail account
   <br> 
    ● Contact number
   <br> 
    ● Degree name
   <br> 
    ● Educational institute
   <br> 
    ● CGPA
   <br> 
    ● Passing year
   <br> 
    ● Present address
   <br> 

    During the applicant registration process, there should be a way to upload candidate’s photo
    and CV / resume.

     <br>

- Feature 02:  Apply for the Desired Circular
 
     There should a panel / dashboard, by which registered applicants can apply for desired job circular.

    To make it clear, let’s say Applicant A has registered to our portal. Now he can go through
    different job posts that are currently accepting applicants. After that he can apply for the
    desired circular.

     <br>

- Feature 03: Approval of Applicants
 
     Members from the admin panel can view all the applicant’s information. They can sort this data by
    <br>
    <br> 
    ● Particular job post
  <br> 
    ● Gender
  <br> 
    ● Degree name
  <br> 
    ● Educational institute
  <br> 
    ● CGPA
  <br> 
    ● Passing year
    <br>
  <br> 
    Once they find any applicant suitable for selection, they can mark that applicant APPROVED FOR INTERVIEW for that particular job circular. Note that, one applicant might apply for multiple circular. So the approval system should be job circular wise. That means if one applicant is marked APPROVED FOR INTERVIEW for Java circular should not be treated as APPROVED FOR INTERVIEW for Android circular.

     <br>

- Feature 04: Admit Card Generation
 
   There should be a functionally to automatically prepare system generated admit card for the selected applicants. All the admit card should contain unique serial number / bar code, QR code for personal identification.

     <br>

- Feature 05: Track Participants of the Exams (Hidden Code on Copies)

    During the written exam, BJIT executives would put a unique code on each participants answer sheet for identification. Our system should be able to generate this code. And store it for future references.
   
     <br>

- Feature 06: Upload Marks of the Participants

    BJIT admins will assign evaluators for the marking. Those evaluator will have the option to upload each candidate’s mark to the portal. Those marking will be done based on different categories (Need to define the categories later).

- Feature 07: Internal Mailing System to Send Necessary Emails

    We need to integrate mailing service to this portal. So that we can send mails to the applicants and inform about their current status of the application (i.e, selected for interview, passed written exam, passed technical viva etc).

- Feature 08: Applicant Dashboard, Notice Board

    There should be an application dashboard, or notice board section. Where the applicants will be able to see the notices / notifications. (i.e, selected for interview, passed written exam, passed technical viva etc).

- Feature 09: Upload Marks and Prepare Results

    In this panel BJIT admins can upload marks for the technical viva and HR viva rounds.
    
- Feature 10:  Select Final Trainees List

     There should be dashboard / page where BJIT admins can see the finally selected candidates for a particular job circular. This page should be similar to a rank list. Where candidates will be sorted according to their scores.

   <br>
   


## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Gradle 8+


To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/turjo997/Trainee-Selection-System.git`
* Navigate to the project directory: cd Trainee-Selection-System
* Add database "trainee_selection_system" to MySql 
* Build the project: gradle clean install
* Run the Backend project: gradle bootRun or you can run via your preferred IDE's    RUN/PLAY button
* Navigate to the Front-End project terminal
* install node modules: npm i
* Run the Front-End project: npm start

-> The application will be available at http://localhost:3000.




<br>
