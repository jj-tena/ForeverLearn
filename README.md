# ForeverLearn

<p align="center">
  <img width="300" height="300" src="https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase3/logo/logoNegro.png">
</p>

## Context
This project includes the realization of two Bachelor's Degree Final Project, a first one for Computer Science that proposes a base platform and a second one for Software Engineering that expands and completes the work done in the first one.

The project will be developed by myself, José Justo Tena Agudo, with the mentoring of my tutor Oriol Borrás Gené, from whom the original idea comes from.
## Description
MOOCs, as their acronym indicates, are massive open online courses that seek to bring education to the widest possible audience. In our case we will focus on cMOOCs, a variant that focuses on the knowledge community that can arise by taking advantage of the large volume of students in this type of courses, but presents the difficulty of evaluating students not based on the content learned but on their contribution to the community, it was due to this difficulty that they fell into oblivion without ever having a real implementation.

ForeverLearn seeks to create a virtual space that allows its users to teach, search and enroll in cMOOCs, which will be accompanied by a forum system in which students can discuss, clarify their doubts and expand the course content. Students will be graded based on their participation in these forums, which will be incentivized through a gamification system that will give them different levels of privileges.

This repository is therefore divided into two projects corresponding to a first TFG in which a virtual classroom will be developed that implements all the necessary tools so that students and teachers can interact through cMOOCs, and a second TFG in which educational innovation will be developed through the evaluation of students based on their participation encouraged through gamification techniques.

# TFG 1: A virtual space for education with a focus on cMOOCs

ForeverLearn is an educational innovation project that aims to create a platform for both MOOC-based learning and delivery
for both the delivery and learning based on MOOCs, offering tools for the creation and
for the creation and learning of this type of courses.

The project is framed within the OER initiative, eliminating any features that prevent access to MOOCs.
that prevents access to the courses, so they will be free of charge and all their content will be accessible without the need to register.
content without the need to register. The platform has focused on cMOOCs,
a variant of MOOCs that emphasises the community that can be created around the course due to the large number of courses.
around the course due to its large number of learners.

ForeverLearn offers users the freedom to be both teachers and learners.
As teachers, they will have at their disposal multiple tools for creating and managing the
content of their courses, such as the possibility of inserting embedded content from other websites.
websites. As students, they will be able to rate, enrol and manage their list of desired courses.
This first work therefore serves to establish a platform for open courseware
MOOC modality and the principles of the OER movement, which allows its users to be teachers by creating
users to be teachers creating courses, students being able to receive training from them, and to
has multiple administration options.

This work will be carried out taking into account that it should serve as a basis to be expanded in a second final project.
expanded in a second final degree project that will follow. The second
work will focus mainly on educational innovation, implementing a course evaluation system based on the
based on the participation of the users, motivated by Gamification techniques, in pursuit of the
Gamification techniques, in order to create a knowledge network that involves the course.

With all of the above, the objective is to create a system that enables education through
that integrates the latest educational currents and that represents a viable alternative to the classical
to the classical type of evaluation.

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

![admin-courses](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/admin-courses.png)
#### Admin options
Page with the actions that can be performed by the administrator user.

![admin-options](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/admin-options.png)
#### Admin profile
Administrator user options page.

![admin-profile](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/admin-profile.png)
#### Admin users
Pantalla para que el administrador gestione como desee los usuarios, pudiendo verlos, buscarlos, banearlos, desbanearlos y borrarlos.

![admin-users](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/admin-users.png)
#### Course
Page where the user can view the content of a course.

![course](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/course.png)
#### Help center
This page answers questions about the operation and concept of the system.

![help-center](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/help-center.png)
#### Index
The home page that the user receives when it searches the system.

![index](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/index.png)
#### Instructor courses
Page where the user can see the list of courses that he/she has created.

![instructor-courses](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/instructor-courses.png)
#### Instructor create course
Page for a user to create a course.

![instructor-create-course](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/instructor-create-course.png)
#### Instructor edit course
Page for the user to edit one of the courses he/she has created.

![instructor-edit-course](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/instructor-edit-course.png)
#### Instructor profile
Page where you can view profile information and courses created by a user other than yourself.

![instructor-profile](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/instructor-profile.png)
#### Lesson
Page where the user can view the content of a lesson of a course topic.

![lesson](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/lesson.png)
#### Library
Page to view and filter courses in a simple way.

![library](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/library.png)
#### Library list
Page to view and filter courses in more detail.

![library-list](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/library-list.png)
#### Login
Page for the user to log in to the system.

![login](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/login.png)
#### Privacy policy
Page where you can see the privacy policy of the system.

![privacy-policy](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/privacy-policy.png)
#### Reset password
Page for the user to reset the password in case he/she does not remember the password when trying to log in.

![reset-password](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/reset-password.png)
#### Signup
Page for the user to register in the system.

![signup](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/signup.png)
#### Student courses completed
Page where the user can view their list of completed courses.

![student-courses-completed](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/student-courses-completed.png)
#### Student courses enrolled
Page where the user can see the list of courses in which he/she has enrolled.

![student-courses-enrolled](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/student-courses-enrolled.png)
#### Student courses wished
Page where the user can see the list of courses they have liked.

![student-courses-wished](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/student-courses-wished.png)
#### Terms of service
Page where you can see the terms of use of the system.

![terms-of-service](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/terms-of-service.png)
#### User edit account
Page for the user to configure their base information.

![user-edit-account](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/user-edit-account.png)
#### User edit account password
Page for the user to edit his or her password.

![user-edit-account-password](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/user-edit-account-password.png)
#### User edit account profile
Page for the user to configure their profile information.

![user-edit-account-profile](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/user-edit-account-profile.png)
#### User profile
Non-administrator user options page.

![user-profile](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/user-profile.png)
### Navigation diagram
Once we have stablished which are the pages that compose our system, we shall take a look at the flow between pages.

![navigation-diagram](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase2/navigationDiagram.png)

## Phase 3

In this phase the backend of the application was built, developing all the functionality of the project and the design of the database.
Through the following points we will see details about the implementation from different perspectives.

### System architecture
The application has been built following the Model-View-Controller architecture pattern, MVC for its acronym, whose main idea is to separate the code into different layers delimited by the responsibilities they will be in charge of. Specifically, we find three layers as its name indicates: model, view and controller.
- Model: this is the layer that is in charge of the application's data, which are normally found in databases, so this layer will be in charge of providing methods to create, modify, consult and delete the stored data. The model must contain the data that will be sent to the view layer to be displayed to the user.
- View: this is the layer that contains the system interface and therefore the code of the screens that make up the application. It is also responsible for rendering the data sent in the model to correctly display the different types of data and information it contains.
- Controller: this is the layer in charge of responding to the requests that the user of the application will request through its interaction with the view. This layer is the one that provides functionality to the system, for this it serves as a bridge between the view and the model, receiving requests from the view that asks the model and implementing in between the logic and algorithms that support the application.
Finally we can appreciate in a simpler way the functioning of this architecture by means of the following diagram:

![architecture-diagram](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase3/architecture/architectureDiagram.png)

### Database
For the persistence of the data it was decided to use a relational database, due to the importance and abundance of relationships that exist between the entities that exist in the system as we will see later on. The need to store all these links between the data was the main factor that influenced the decision.
In the project we find the following tables:
- course: entity representing a course in the system.
- category: entity representing the possible categories with which the courses can be classified.
- requirement: entity representing a requirement that the students of a course should fulfil or at least take into account when enrolling and trying to pass the course.
- course_requirements: this table relates a requirement to a course, each requirement must be associated with a single course, whereas multiple requirements can be associated with the same course.
- objective: entity that represents one of the objectives that the course intends its students to achieve by passing the course.
- course_objectives: this table relates an objective to a course, each objective must be associated with a single course, and multiple requirements can be associated with the same course.
- lesson: entity that represents a lesson, the lessons are the content of the courses and where the educational material that the teacher tries to teach to his students is actually found.
- theme_lessons: this table relates a lesson to a theme, each lesson must be associated with a single theme, whereas multiple lessons can be associated with the same theme.
- theme: entity that represents a theme that makes up the course syllabus and therefore the bulk of its content.
- course_themes: this table relates a theme to a course, each theme must be associated with a single course, while multiple requirements can be associated with the same course.
- user: entity that represents the information associated with a user of the application.
- user_user_courses: this table relates a user to the courses that he/she has created, therefore it is a relationship in which a single user can be associated to a multitude of different courses.
- user_enrolled_courses: this table relates a user to the courses he/she has enrolled in, so it is a relationship in which a single user can be associated to many different courses.
- user_completed_courses: this table relates a user to the courses he/she has completed, so it is a relationship in which a single user can be associated to many different courses.
- user_wished_courses: this table relates a user to the courses he has added to his wish list, so it is a relationship in which a single user can be associated to many different courses.
- user_liked_courses: this table relates a user to the courses they have liked, so it is a relationship in which a single user can be associated to many different courses.
- user_disliked_courses: this table relates a user to the courses that he/she has disliked, so it is a relationship in which a single user can be associated to a multitude of different courses.
Finally, we can find the relationship entity model that represents all of the above: 

![database-diagram](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase3/database/drawDatabaseDiagram.png)

### Backend implementation
The backend of the system follows a layered design: controller layer, service layer, repository layer and model layer, each of them is in charge of a responsibility, but in general terms we could say that the application logic is integrated in the controller layer, while the business logic is present in the service, model and repository layers.
- Controller layer: this layer is responsible for handling user requests, receiving the appropriate data, calling the service layer methods that perform the functionality the user is requesting and finally returning a view appropriate to the user's demand.
- Service layer: this layer implements the functionalities of the application, any action that the user wants to perform will involve a call from the controller layer to one of the methods present here.
- Repository layer: the system uses a programming model called object-relational mapping (ORM), which consists of transforming the database tables into entities that abstract the developer from the language of the database used and allow him to perform CRUD operations (create, read, modify and delete) more easily. This layer defines all the possible CRUD operations that we wish to perform on the model data in the database.
- Model layer: this layer defines the different entities that we will store in the database and the relationships between them, therefore they will be the objects with which we will operate in the system. All the layers must have access to this layer: the controller needs to know which objects it will receive and integrate in the view that it will return in the user's requests, the service must know the model to be able to operate on it and carry out the application's functionalities, and the repository needs to know the model to know how the information in the database it is trying to access is structured.
In the following diagram we can see the interrelation between the different layers of the system:

![backend-diagram](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase3/backend/backendDiagram.png)

## Phase 4

This will be the last phase of this first final degree project, in which we will finish polishing the code, document the missing details and focus on testing for each of the layers that make up the application.

### Extended backend implementation
This model is an extension of the backend implementation diagram presented at the end of the previous phase. 
The current diagram shows all the classes that are contained under each of the layers, giving a more detailed view of the implementation.

![extended-backend-diagram](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase4/class/classDiagram.png)

### Use cases
Use cases are an artefact that defines a sequence of actions that leads to an observable result, we will use them to represent the way in which a user interacts with the system in pursuit of a given objective. They graphically represent the functional requirements, as they define the functionalities that the system offers and that the user will use to achieve his objectives. 
Use cases always pursue a specific objective, however, on many occasions we must achieve small objectives in order to reach a larger one, and this is where the relationships that link use cases that form part of the same flow of events come into play.
Below is a diagram that shows the possible flows that can occur in the system from the use cases:

![use-case-diagram](https://github.com/jj-tena/ForeverLearn/blob/main/TFG1/images/phase4/useCase/useCaseDiagram.png)

### Testing

My intention from the very beginning of the system was to deliver a truly solid and polished piece of work, one that is robust against the possible errors that users may encounter and provoke. In this section we will see what efforts have been dedicated to testing.
Unit tests are one of the most widespread types of testing, used to check the correct functioning of code fragments, usually referring to methods of the application. They are so called because they test isolated code, that is, only that unit of code.
In order to execute this type of tests in Spring Boot projects, two main technologies are usually used:
- JUnit: this is the testing library par excellence for the Java language.
- Mockito: it is a testing framework that allows us to establish which answers the application units return, it is a way of simulating their behaviour


We have already seen how the structure of the backend is based on a division into three layers: controller layer, service layer and repository layer, which communicate in that order. Each layer has different responsibilities and therefore what we seek to demonstrate through unit testing will be different for each.
We will begin by talking about the base: the repository layer, which is in charge of operating with the database and for this purpose it offers a series of default methods such as deleting or saving entities, however, when it comes to performing more complex operations such as searches based on combinations of specific parameters of the application, it will be the responsibility of the programmer to define these operations.
We do not need to unit test the default operations, as these have already been tested by the technology manufacturer. Testing in this layer therefore focuses on the operations defined by the developer, as it is their responsibility to ensure that they work correctly.
To test the correct functioning of these operations we will need a database with which they can operate, the problem is that we do not want our tests to affect the real system and that the database of the application stores information coming from the testing. 
To solve this problem, it was decided to use an independent database that only runs when the tests are executed. In order not to increase the number of dependencies or add unnecessary complexity to the project, it was decided to use an H2 database, which is characterised by being an in-memory database that does not require dependencies of any kind.
In this way we can test the operations that have been defined in the repository layer by interacting with the H2 database and demonstrate that they comply with the desired operation.
The next layer to be discussed is the service layer, this layer implements the business logic of the system and relies on the layer seen above to operate with the database. This dependency on the repository layer clashes with the unit testing philosophy of testing methods that are independent on their own without the need for other modules. 
The solution to this problem is provided by the Mockito framework, we will use it for two purposes: we define what the repository layer methods that are invoked return and we set up argument catchers that trap the values that are passed as parameters to the repository methods.
With all this, we will check that the filters and operations implemented by the service are correctly applied and simulate all those methods of the repository layer that are necessary to test the service layer correctly.
Finally we have the controller layer, this receives url addresses, decides which operations of the service layer are executed, brings information from this with which it loads the model and integrates it into a view that returns to the user. Again, the dependency on the service layer is solved by Mockito, since this layer will be simulated, the data with which we load the model will also be simulated. 
We can consider that the real interest of testing this layer in a unitary way lies in making sure that for each address available in the system, the corresponding view is returned, and that is what has been done.
Finally, what about the integration between the layers? The unit tests of each layer respect this philosophy thanks to the fact that we have simulated the behaviour of the layers on which they depend using Mockito, this simulation has been carried out exactly according to what would be returned by the layer and given that we have verified that each one works correctly, we can affirm that if it were not a simulation, the flow of information and compatibility between the layers would occur without the appearance of any type of problem.

### Conclusion

I feel really satisfied with the final state of this first project, despite needing improvements and advances that will be developed in the following Final Degree Project to be complete, a good base has been built from which to start.
With the work carried out in this first project, we now have a public and open course repository without restrictions of any kind, which allows its users to put themselves in the place of both the teacher, giving them all the necessary tools to model their courses as they wish, and the student, allowing them to enrol, evaluate courses, manage a list of desired courses, etc. 
Although the real educational innovation will be developed in the next work, here we have added small advances that are not common in this kind of site.
ForeverLearn has the potential to be more than just a Final Degree Project, the system we are creating as well as the concepts of: CMOOC, Open Educational Resources and Gamification that it integrates can become a serious alternative to classic assessment systems.
In recent years a great deal of work has been done in educational innovation exploring the possibilities offered by computer science and in the long term my intention is that ForeverLearn can contribute its grain of sand on that path. 

# TFG 2: A connectivist approach to education with a focus on CMOOC

Currently, ForeverLearn is the result of a previous TFG in which a virtual classroom was developed in which users can act as teachers by creating and
where users can act as teachers creating and managing their courses, or as learners with various course
courses, or as students with various course monitoring tools.

The present work continues the development of ForeverLearn with the aim of
implement a space within each course in which learners can interact and store the content produced by
1store the content produced by themselves, in this document we detail the design of this area and the development of the
of this area and its development.

Learners' participation should be motivated by a gamification system that rewards their actions and encourages them to
that rewards their actions and encourages them to continue working within the context of the course.

In order to achieve this purpose, several successful techniques have been studied and new ideas have been proposed to reinforce and adapt
new ideas have been proposed that reinforce and adapt the above-mentioned procedures to the original idea of the system.
original idea of the system.

The further intention of creating this space within the course, as well as to motivate
students to collaborate and contribute to it, is to be able to evaluate them through their participation in it, here.
participation in it, this is where the genuine educational innovation lies, in proposing a way of
proposing a different way of assessment than traditional exams. The design and
the design and operation of the assessment system implemented by ForeverLearn will be detailed below.
ForeverLearn.

Finally, as soon as a student passes the proposed evaluation criteria, he/she will have access to the course diploma.
the student will have access to the course diploma which certifies his or her success in the course and
will reflect the metrics associated with their participation, every time they wish to access their diploma.
will show the updated data with their work up to that exact moment.

## Phase 1

This phase begins the development of this second work, in which the solutions to the difficulties to be solved are defined, it ensures that the proposed functionalities are capable of responding to the proposed objectives.
objectives, and time is dedicated to reflect on how to integrate these ideas in a coherent and
these ideas in a coherent and practical way.

During this phase, the development of the code does not progress, but it is necessary to dedicate a space
time is necessary to delineate the features that you want to implement and specify how they will be implemented.
specify how they will be implemented. When you have agreed on the idea of the desired system at the end of this
at the end of this work, you can begin to implement it with the knowledge that you are following the
implementation can begin with the certainty that you are following a well-defined plan.

### Problem description

CMOOCs are collaborative courses that reject the traditional ways of content delivery and student assessment in favour of the community created among learners and the knowledge created among them.

The main difficulty revolves around the creation of a community formed by the students of the course, because in order for students to take an active role in this community, they must be provided with tools that allow them to they need to be provided with tools that allow them to interact with others and to have a speaker through which they can a loudspeaker through which they can communicate their ideas and share their thoughts with others.

Specifically, within each course there should be a space in which enrolled users can create their own content from the basis provided by the teacher, react to that of others and be able to interact with others.

In this sense, one of the main obstacles faced by the first CMOOCs was the dispersion of the content generated by the students, each one publishing their own productions on different sites. It was difficult to keep track of the network of content surrounding the course. Efforts should therefore be made to ensure that the content and the interrelationships of the students is easily located and accessible by anyone with an interest in following the course.

Users should not only have the tools to participate, but they should also have incentives to do so, beyond being the key to passing, rewards should be proposed that will be accompanied by a system for accompanied by a system of status levels, through which students will be able to climb up the ladder thanks to their participation.

Given that the focus is not so much on individual learning, but rather on the knowledge network that will be formed, ForeverLearn must find methods of assessing learners based on other parameters, which are more difficult to quantify and automate.

### Objectives

Continuing the ForeverLearn project involves setting a series of objectives through which to answer the problem posed as well as to develop the remaining features to consider the system complete.

- Establish a space within each course in which to place all the production and interaction between students, which facilitates the monitoring of the knowledge network created by them.
- Develop tools through which users can publish self-made material in order to expand the content of the course.
- To provide methods of communication so that students in the same course can interact with each other, thus encouraging discussion around the teacher's material and other students' productions, collaboration in order to answer questions that may hinder progress in the course and above all to recognise the value that can arise from their interrelationships.
- Encourage student participation through various gamification techniques that reward their work, thus increasing the volume and value of the content generated by them within the course.
- Evaluate students through their participation, rather than by taking exams, by establishing a series of requirements that the user will need to pass in order to obtain their diploma.
- Generate dynamic course completion certificates, capable of reflecting the user's effort through metrics that show the quality of their productions as well as their reach within the course network. The dynamic behaviour of the diploma is due to the fact that, every time the user wants to download his certificate, it will show the data regarding his performance updated up to that very moment.

Finally, there is a personal goal: to improve myself as a professional by acquiring and applying the knowledge necessary to meet all the proposed objectives, giving the best of myself in order to create a job I can be proud of.

## Phase 2

The bulk of the content of this work will be developed in this phase.
of this work, specifically all the functionality related to gamification and student assessment will be implemented.

ForeverLearn supports a large part of its educational innovation in these two components, consequently many of the ideas and decisions taken in the design of these modules are not supported by previous systems.

In order to endorse and refine the educational approach, it was decided to follow a User Centered Design approach for this phase.

### User Centered Design (UCD)

The user centered design process is a product creation methodology that puts the end-user at the centre of the design process in order to make him/her a part of the design process with the aim of offering a product that meets his/her needs with the highest possible degree of satisfaction.

ForeverLearn proposes various aspects of educational innovation that represent a break with traditional implementation methods and therefore cannot rely on the decisions made by previous systems, for which it aims to bring together ideas from different currents such as CMOOCs and Gamification.

User-centred design was selected to implement the modules of user participation within the course and Gamification to incentivise them, due to the need to back up one's own ideas through a committee of experts in educational innovation to assess their quality and provide feedback that can improve the course of the project.

For the sake of order, it has been decided to divide this process into 3 stages:

1. First an initial prototype is developed that integrates the original design ideas and decisions.
2. The initial prototype will go through a series of interviews in which different experts will comment on their first impressions and offer their feedback. Their opinions will then be assessed and the feasibility of their proposals will be studied, and this work will be reflected in an intermediate prototype.
3. The intermediate prototype will again pass through the hands of the specialists who are giving their assistance. After that, their final verdicts will be examined and final changes will be included to shape the final design.

For each of the stages, the creative decisions involved, the features included and the contributions of all those who helped are detailed below.

### Initial prototype

This first point of the design process will be particularly extensive because it must explain all the ideas that will form the basis from which it will begin to iterate.

It is essential to create a space within the course in which users can make contact and upload their productions, this has been called "Student Area", within this space the functionalities of participation and incentivisation of the students will be developed.

Firstly, everything related to the participation of the user enrolled in a course is explained. To do so, access the "Student area" within the course, where you will find two ways of interaction:

Forums. The forums section allows the student to create posts, which are publications in which the user can contribute their grain of sand to the network of course content by offering their point of view on aspects of the course, sharing documents from other authors, starting debates, etc.

Each post will have its own comments section that expands the conversation around the topic, so that when a user enters the forum section they will be able to participate in two ways participate in two ways: by writing a new post or by commenting on existing posts.

The forum section will consist of a list of posts in which only users will be able to participate, both to create original content and to comment, only those enrolled in the course. the course. In order to offer a barrier-free education, all the content of this section will be accessible without the
all the content of this section without the need to register in the system.

Questions. The questions section is focused on the resolution of doubts and problems that may arise during the problems that may arise while following the course material, for which students can upload their questions.

Unlike the previous section, the main objective of this section is not to expand the course content, but rather to facilitate the
the course content, but to facilitate progress through the course in order to reduce dropout. In order to achieve this purpose, the questions section
section allows you to participate in two ways: by uploading new questions or by answering existing ones.

The user who has uploaded a question will be able to highlight from among all the answers the one he/she considers to be the best answer.
the one he/she considers to be the best answer. A question has a series of answers that aim to solve the problem, unlike the comments of a post, not all the answers are unlike the comments of a post, not all questions are equally valid, which is why we give the is the opportunity to highlight the one that offers the best solution to the question posed. The best answer will be the first one that appears in the list of answers to the question, in order to facilitate the way for those with the same doubt.

The questions section will function as a list of questions in which only registered users will be able to ask and answer only those users registered in the course will be able to ask and answer questions. As in the section, ForeverLearn's commitment to the openness of education is favoured by an unrestricted access to this section.

The list of posts/issues will be organised into 3 levels of distinction

Post/Question of interest. This type of post/question is the first to be shown in the list, these are posts/questions that have aroused the interest of the course teacher and have been highlighted by him/her, so they have a certified quality.
To differentiate them from the other types they are highlighted in red.

Featured post/question. This type of post/question is shown after the previous ones, only the user who wrote the post can mark it in this way, to do so he/she will have to use one of his/her bonuses to highlight posts/issues,
The concept and operation of these bonuses will be explained later. 
The posts/issues will be highlighted in yellow.

Standard Post/Issue. This is the last type of post/issue to be shown in the list. Given
they have not stood out above the others, they will be shown in white.

Once the activities that users can carry out in the courses have been developed, we can discuss the Gamification techniques proposed to encourage these actions.

