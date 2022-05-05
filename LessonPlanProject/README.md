# Lesson Plan Tracker

## Cedric Pulmano

---

### Purpose

My application's purpose is to be able to track
the progress of different students under a
lesson plan. A lesson plan should have a set
amount of lessons that each student must go
through. Each lesson should have a title, 
duration, and list of requirements in order for a
teacher to teach the lesson. Once a new student 
is added to the lesson plan, they should start 
off with having finished no lessons. After a 
lesson is taught, all the students that attend 
will have their attendance history updated to 
that they completed that lesson. When the student
has completed all lessons, they should be 
eligible to graduate.

People that can use it includes anyone that wants
to teach something, but has trouble keeping track
of everyone's progress. Personally, some potential 
users that I have considered are the people at my 
church. I volunteer in helping with the paperwork
that keeps track of the progress of the Bible
students at my church, and our current system 
requires a lot of **unnecessarily redundant** 
work that gets tiresome at times. Thus, I want to
try to develop a simple system that is more 
straightforward to use. The others at my church 
are not confident in their computer skills, so I 
want it to be easy for the user to use.

---

### User Stories

- As a user, I want to be able to create a new 
lesson plan, and add or remove teachers and students 
to the lesson plan.
- As a user, I want to be able to add or remove a
lesson, and have every student have that lesson
updated to the list of lessons they have to take.
- As a user, I want to submit a teacher, lesson, or
student name, and get the corresponding object if
it exists.
- As a user, I want to be able to teach a lesson by 
assigning a teacher, a list of attending students,
a timeframe for teaching, and a date. The
attendance of each student should be updated, and
any attendee names that do not correspond to a 
student should be added as a new student.
- As a user, I want to see how many hours of 
lessons a student has before they are done.
- As a user, I want to be able to graduate all 
eligible students, which are students with no more
lessons to learn.
- As a user, I want to select a lesson, and see 
which students still have to take it.
- As a user, I want to be able to save my lesson
plan's progress so far to file
- As a user, I want to be able to load my lesson
plan's progress so far from file