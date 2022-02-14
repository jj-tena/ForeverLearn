# ForeverLearn

## Context
This project includes the realization of two Bachelor's Degree Final Project, a first one for Computer Science that proposes a base platform and a second one for Software Engineering that expands and completes the work done in the first one.

The project will be developed by myself, José Justo Tena Agudo, with the mentoring of my tutor Oriol Borrás Gené, from whom the original idea comes from.
## Description
MOOCs, as their acronym indicates, are massive open online courses that seek to bring education to the widest possible audience. In our case we will focus on cMOOCs, a variant that focuses on the knowledge community that can arise by taking advantage of the large volume of students in this type of courses, but presents the difficulty of evaluating students not based on the content learned but on their contribution to the community, it was due to this difficulty that they fell into oblivion without ever having a real implementation.

ForeverLearn seeks to create a virtual space that allows its users to teach, search and enroll in cMOOCs, which will be accompanied by a forum system in which students can discuss, clarify their doubts and expand the course content. Students will be graded based on their participation in these forums, which will be incentivized through a gamification system that will give them different levels of privileges.

The project is thus divided into two main parts: firstly, a virtual classroom where the courses are created, stored and accessed, the development of which will take place during phases 1, 2, 3 and 4, secondly, a forum system that expands them and will serve as to evaluate the students, whose development will be carried out starting in phase 5.
## Phase 1
In this phase we will define the objectives to be achieved, the types of users of the system, as well as the requirements to be met to satisfy their needs, and we will begin to consider the more technical aspects of the project: languages, environments, database entities and tools, which may be appropriate for the solution we intend to implement.

All the information described below can be in greater detail in the file "Memoria.doc".
### Objectives
The application has a series of essential objectives to satisfy the concerns and challenges posed by the problem described in the previous point.

We propose a main objective that corresponds directly to the heart of our goal:
- To promote a philosophy of open learning, accessible by all and for all, through which our users can grow professionally and personally.

This main objective is accompanied by a series of secondary objectives that the system must meet, which are listed below:
- To access the content of the courses free of charge and without the need to register in order to make it a genuinely open education for all those interested in learning.
- To allow any registered user to perform all the functions that would correspond to both the role of teacher and student.
- To design courses as desired, divide them into modules or topics and add content to them.
- To find courses easily and effectively, for this there will be a section as a store that allows you to see all available courses, as well as filter by categories or directly search based on the name of these.
- To be able to register in a course of the store to participate in its system of forums and thus obtain a title that accredits its overcoming.

### User roles
The platform must be able to respond to the needs of its users and to do so it needs to understand that not everyone will interact with the system in the same way, which translates into a study of the division between them and the particularities of each type.

The system contemplates in the first place three possible roles for its users:
- Unregistered user: This is the user who enters the system for the first time or who doesn't consider enough incentives in registering in the system, due to the lack of an account to associate their actions it is the role with a smaller number of functionalities at their disposal.
- Registered user: This is the user who has decided to take the step of creating an account in our system, allowing it to use all the tools that a teacher or student may require to perform their work.
- Administrator user: This is the user with the highest privileges in the system, being able to affect with his decisions other external entities within the application such as users or courses.
Between these roles there is a "rock-paper-scissors" relationship, the unregistered user becomes a registered user at the moment he creates his account, thus obtaining a greater power of action, the freedom of action of the registered user within the system is limited by the permissions of the administrator user, the administrator user has no power over the unregistered user since he doesn't have an account that identifies it.

Special attention must be paid to the role of the registered user, since it can fulfill the functions of two types of actors within the system:
- Teacher: This is the actor who seeks to share his knowledge with other users, this impulse leads it to the decision of creating courses through which it can express the knowledge it considers worthy of being infused.
- Student: This is the actor who seeks to expand his knowledge and satisfy his curiosity through the resources provided by the previous actor.

Between these two actors there is a feedback relationship because without one there would be no sense in the other one, the teacher needs an audience to listen to its lessons, while the student requires a teacher to guide it in its learning. We could say that the heart of the system lies in this symbiosis between student and teacher.
 
### Functionality
All the functions that our platform allows its users to perform are detailed below, with emphasis on the access to these functions based on their role. 
 
The order in which the user types are treated is important, as they are arranged in such a way that each role can access its own functions and all the functions of those described before them.

- Unregistered user: Has access to the complete list of courses in the system, allowing it to search for them by different methods and access all their content, however, it will not be able to register and therefore will never be able to enroll in them, it will also be able to access the forum system that accompanies them, but only as a reader without being able to interfere with them. This decision is taken in order to encourage you to use the only other functionality at its disposal: registering in the system, but from that moment on it will abandon its role.
- Registered user: It has control over all the information related to his account, but what is really important is that, on the one hand, it has at its disposal all the necessary tools to create courses and organize them at its convenience, and on the other hand, it can register in courses and access all their content, as well as evaluate them and have a good follow-up of all those in which it has registered.
- Administrator user: Has permissions to prohibit access to the system to all users whose behavior does not meet civic standards and common sense, and block access to courses that are irrelevant or harmful.

### Technology stack
For the development of the project we have decided to use the following set of technologies:
- The application code is packaged using Maven.
- The frontend of the application is written in HTML, CSS and JavaScript, without using any modern framework.
- The backend of the application is written in Java, using Spring Boot as framework.
- The persistence of the data is implemented through a relational database, using MySQL as the management system. 
- The version control system used is Git.
- The public repository which stores the proyect is on Github.
## Phase 2
In this phase I will display the frontend of the application, therefore the layout of web pages and the navigation scheme between screens. 

The application interface will be designed taking into account the principles of usability and accessibility.
### Pages
These are the pages that make up the frontend of the website.
#### Admin courses
Screen for the administrator to manage the courses as he/she wishes, being able to view, search, ban, unban and delete them.

![admin-courses](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/admin-courses.png)
#### Admin options
Page with the actions that can be performed by the administrator user.

![admin-options](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/admin-options.png)
#### Admin profile
Administrator user options page.

![admin-profile](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/admin-profile.png)
#### Admin users
Pantalla para que el administrador gestione como desee los usuarios, pudiendo verlos, buscarlos, banearlos, desbanearlos y borrarlos.

![admin-users](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/admin-users.png)
#### Course
Page where the user can view the content of a course.

![course](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/course.png)
#### Help center
This page answers questions about the operation and concept of the system.

![help-center](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/help-center.png)
#### Index
The home page that the user receives when it searches the system.

![index](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/index.png)
#### Instructor courses
Page where the user can see the list of courses that he/she has created.

![instructor-courses](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/instructor-courses.png)
#### Instructor create course
Page for a user to create a course.

![instructor-create-course](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/instructor-create-course.png)
#### Instructor edit course
Page for the user to edit one of the courses he/she has created.

![instructor-edit-course](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/instructor-edit-course.png)
#### Instructor profile
Page where you can view profile information and courses created by a user other than yourself.

![instructor-profile](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/instructor-profile.png)
#### Lesson
Page where the user can view the content of a lesson of a course topic.

![lesson](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/lesson.png)
#### Library
Page to view and filter courses in a simple way.

![library](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/library.png)
#### Library list
Page to view and filter courses in more detail.

![library-list](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/library-list.png)
#### Login
Page for the user to log in to the system.

![login](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/login.png)
#### Privacy policy
Page where you can see the privacy policy of the system.

![privacy-policy](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/privacy-policy.png)
#### Reset password
Page for the user to reset the password in case he/she does not remember the password when trying to log in.

![reset-password](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/reset-password.png)
#### Signup
Page for the user to register in the system.

![signup](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/signup.png)
#### Student courses completed
Page where the user can view their list of completed courses.

![student-courses-completed](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/student-courses-completed.png)
#### Student courses enrolled
Page where the user can see the list of courses in which he/she has enrolled.

![student-courses-enrolled](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/student-courses-enrolled.png)
#### Student courses wished
Page where the user can see the list of courses they have liked.

![student-courses-wished](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/student-courses-wished.png)
#### Terms of service
Page where you can see the terms of use of the system.

![terms-of-service](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/terms-of-service.png)
#### User edit account
Page for the user to configure their base information.

![user-edit-account](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/user-edit-account.png)
#### User edit account password
Page for the user to edit his or her password.

![user-edit-account-password](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/user-edit-account-password.png)
#### User edit account profile
Page for the user to configure their profile information.

![user-edit-account-profile](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/user-edit-account-profile.png)
#### User profile
Non-administrator user options page.

![user-profile](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/user-profile.png)
### Navigation diagram
Once we have stablished which are the pages that compose our system, we shall take a look at the flow between pages.

![navigation-diagram](https://github.com/jj-tena/ForeverLearn/blob/main/images/phase2/navigationDiagram.png)

## Phase 3
