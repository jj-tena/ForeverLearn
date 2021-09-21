# ForeverLearn
## Context
This project includes the realization of two Bachelor's Degree Final Project, a first one for Software Engineering that proposes a base platform and a second one for Computer Science that expands and completes the work done in the first one.

The project will be developed by myself, José Justo Tena Agudo, with the mentoring of my tutor Oriol Borrás Gené, from whom the original idea comes from.
## Description
MOOCs, as their acronym indicates, are massive open online courses that seek to bring education to the widest possible audience. In our case we will focus on CMOOCs, a variant that focuses on the knowledge community that can arise by taking advantage of the large volume of students in this type of courses, but presents the difficulty of evaluating students not based on the content learned but on their contribution to the community, it was due to this difficulty that they fell into oblivion without ever having a real implementation.

ForeverLearn seeks to create a virtual space that allows its users to teach, search and enroll in CMOOCs, which will be accompanied by a forum system in which students can discuss, clarify their doubts and expand the course content. Students will be graded based on their participation in these forums, which will be incentivized through a gamification system that will give them different levels of privileges.

The project is thus divided into two main parts: firstly, a virtual classroom where the courses are created, stored and accessed, and secondly, a forum system that expands them and will serve as to evaluate the students.
## Phase 1
In this phase we will define the objectives to be achieved, the types of users of the system and the requirements to be met to satisfy their needs.

All the information described below can be in greater detail in the file "Memoria.doc".
### Objectives
The application has a series of essential objectives that needs to achieve in order to consider the initial problem solved.

- With the aim of promoting a philosophy of learning and personal growth, any registered user has access to all the functions that would correspond to both the role of teacher and student.
- All courses should be free of charge in order to be a genuinely open education for all those interested in learning.
- Teachers should be able to create as many courses as they wish, divide them into the modules or topics they consider and upload files: pdf, images, videos, etc.
- Students should be able to find the courses easily and effectively, for this there will be a section that allows them to see all available courses as a store, as well as filter by categories or directly search based on the name of these.
- Students should be able to register for the courses in the store and have access to all the content of the courses regardless of their enrollment.
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

