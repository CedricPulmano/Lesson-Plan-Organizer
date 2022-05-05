package model;

import exceptions.LessonDoesNotExistException;
import exceptions.NotEnoughTimeToTeachException;
import exceptions.TeacherDoesNotExistException;
import exceptions.TeacherNotCertifiedToTeachException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// A lesson plan with lessons to teach, students, and teachers
public class LessonPlan {

    private String lessonPlanTitle;
    private List<Lesson> lessonsToTeach;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<TeachingEntry> teachingHistory;

    // EFFECTS: creates a lesson plan with empty lessons, students, and teachers lists
    public LessonPlan(String name) {
        this.lessonPlanTitle = name;
        this.lessonsToTeach = new ArrayList<Lesson>();
        this.students = new ArrayList<Student>();
        this.teachers = new ArrayList<Teacher>();
        this.teachingHistory = new ArrayList<TeachingEntry>();
    }

    // REQUIRES: time > 0, title is unique to the lessonsToTeach list
    // MODIFIES: this
    // EFFECTS: creates new lesson with the given title, time, and requirements, adding it to lessonsToTeach as well as
    //          to all the students' lessonsToLearn in the lesson plan's student list
    public void addLesson(String title, int time, List<String> requirements) {
        Lesson newLesson = new Lesson(title, time, requirements);
        lessonsToTeach.add(newLesson);
        for (Student student : students) {
            student.addLessonToLearn(newLesson);
        }
    }

    // REQUIRES: name is unique to the students list
    // MODIFIES: this
    // EFFECTS: creates new student and adds to student list
    public void addStudent(String name) {
        Student newStudent = new Student(name);
        students.add(newStudent);
        for (Lesson lesson : lessonsToTeach) {
            newStudent.addLessonToLearn(lesson);
        }
        EventLog.getInstance().logEvent(new Event("Added Student named " + name + "!"));
    }

    // REQUIRES: name is unique to the teachers list
    // MODIFIES: this
    // EFFECTS: creates new teacher with the given certifications and adds to teacher list
    public void addTeacher(String name, List<String> certifications) {
        teachers.add(new Teacher(name, certifications));
    }

    // MODIFIES: this
    // EFFECTS: removes lesson with corresponding title from lessonsToLearn for all students and lessonsToTeach
    //          returns true if a lesson is removed, else returns false
    public Boolean removeLesson(String lessonTitle) {
        Lesson lessonToRemove = findLesson(lessonTitle);
        if (lessonsToTeach.remove(lessonToRemove)) {
            for (Student student : students) {
                student.removeLessonToLearn(lessonToRemove);
            }
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes student from student list if name corresponds to a student name
    //          returns true if a student is removed, else returns false
    public Boolean removeStudent(String name) {
        return students.remove(findStudent(name));
    }

    // MODIFIES: this
    // EFFECTS: removes teacher from teacher list if name corresponds to a teacher name
    //          returns true if a teacher is removed, else returns false
    public Boolean removeTeacher(String name) {
        return teachers.remove(findTeacher(name));
    }

    // EFFECTS: finds the lesson in the lesson plan that has the same title as the given title,
    //          and if no lesson exists, returns null
    public Lesson findLesson(String title) {
        for (Lesson lesson : lessonsToTeach) {
            if (title.equals(lesson.getTitle())) {
                return lesson;
            }
        }
        return null;
    }

    // EFFECTS: finds the teacher in the lesson plan that has the same name as the given name,
    //          and if no teacher exists, return null
    public Teacher findTeacher(String name) {
        for (Teacher teacher : teachers) {
            if (name.equals(teacher.getName())) {
                return teacher;
            }
        }
        return null;
    }

    // EFFECTS: finds the student in the lesson plan that has the same name as the given name,
    //          and if no student exists, return null
    public Student findStudent(String name) {
        for (Student student : students) {
            if (name.equals(student.getName())) {
                return student;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: checks students in lesson plan, removes those from list that have graduated and returns their names
    public List<String> graduateEligibleStudents() {
        List<String> graduatedStudentNames = new ArrayList<String>();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getLessonsToLearn().size() == 0) {
                graduatedStudentNames.add(students.get(i).getName());
                students.remove(i);
                i--;
            }
        }
        return graduatedStudentNames;
    }

    // EFFECTS: returns list of names of students that still need to learn this lesson
    public List<String> studentsNeededToLearn(String lessonName) {
        List<String> studentsWithLesson = new ArrayList<String>();
        for (Student student : students) {
            for (Lesson lesson : student.getLessonsToLearn()) {
                if (lessonName.equals(lesson.getTitle())) {
                    studentsWithLesson.add(student.getName());
                }
            }
        }
        return studentsWithLesson;
    }

    // REQUIRES: minutesToTeach > 0, date is formatted as "MM/DD/YYYY", lesson corresponds to the title of a lesson
    // MODIFIES: this, attendees, EventLog.getInstance()
    // EFFECTS: teach lesson by updating each students' attendance record
    //          if teacher is not registered in lesson plan, throw TeacherDoesNotExistException
    //          if lesson is not registered in lesson plan, throw LessonDoesNotExistException
    //          if teacher is not certified to teach lesson, throw TeacherNotCertifiedToTeach
    //          if student is not in lesson plan's list of students, register as new student
    //          if student has already attended that lesson, no new attendance needs to be recorded
    public void teachLesson(String lesson, String teacher, List<String> attendees, int teachingTime, String date)
            throws TeacherDoesNotExistException, LessonDoesNotExistException,
            NotEnoughTimeToTeachException, TeacherNotCertifiedToTeachException {
        Teacher teacherObject = findTeacher(teacher);
        Lesson lessonObject = findLesson(lesson);

        isLessonTeachable(teacherObject, lessonObject, teachingTime);

        for (String studentName : attendees) {
            Student studentObject = findStudent(studentName);
            if (studentObject == null) {
                addStudent(studentName);
                studentObject = students.get(students.size() - 1);
            }
            if (studentObject.removeLessonToLearn(findLesson(lesson))) {
                studentObject.addAttendanceEntry(lesson, teacher, date);
            }
        }

        teachingHistory.add(new TeachingEntry(lesson, teacher, attendees, date));
        logTeachLesson(lesson, attendees);
    }

    // MODIFIES: EventLog.getInstance()
    // EFFECTS: logs teaching event to the event log
    private void logTeachLesson(String lesson, List<String> attendees) {
        String message = "Taught " + lesson + " to: ";
        for (int i = 0; i < attendees.size() - 1; i++) {
            message += attendees.get(i);
            message += ", ";
        }
        if (attendees.size() != 0) {
            message += attendees.get(attendees.size() - 1);
        }
        EventLog.getInstance().logEvent(new Event(message));
    }

    // EFFECTS: if teacher is not registered in lesson plan, throw TeacherDoesNotExistException
    //          if lesson is not registered in lesson plan, throw LessonDoesNotExistException
    //          if teacher is not certified to teach lesson, throw TeacherNotCertifiedToTeach
    //          if student is not in lesson plan's list of students, register as new student
    //          if student has already attended that lesson, no new attendance needs to be recorded
    public void isLessonTeachable(Teacher teacherObject, Lesson lessonObject, int teachingTime)
            throws TeacherDoesNotExistException, LessonDoesNotExistException,
            NotEnoughTimeToTeachException, TeacherNotCertifiedToTeachException {
        if (lessonObject == null) {
            throw new LessonDoesNotExistException();
        } else if (teacherObject == null) {
            throw new TeacherDoesNotExistException();
        } else if (!teacherObject.isCertifiedToTeach(lessonObject.getRequirements())) {
            throw new TeacherNotCertifiedToTeachException();
        } else if (teachingTime < lessonObject.getTimeToTeach()) {
            throw new NotEnoughTimeToTeachException();
        }
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", lessonPlanTitle);
        json.put("lessons", lessonsToJson());
        json.put("teachers", teachersToJson());
        json.put("students", studentsToJson());
        json.put("teaching history", teachingHistoryToJson());
        return json;
    }

    // EFFECTS: returns lessons in this lesson plan as a JSONArray
    public JSONArray lessonsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Lesson l : lessonsToTeach) {
            jsonArray.put(l.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns teachers in this lesson plan as a JSONArray
    public JSONArray teachersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Teacher t : teachers) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns students in this lesson plan as a JSONArray
    public JSONArray studentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Student s : students) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns teaching history in this lesson plan as a JSONArray
    public JSONArray teachingHistoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TeachingEntry te : teachingHistory) {
            jsonArray.put(te.toJson());
        }

        return jsonArray;
    }

    public String getLessonPlanTitle() {
        return lessonPlanTitle;
    }

    public List<Lesson> getLessonsToTeach() {
        return lessonsToTeach;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<TeachingEntry> getTeachingHistory() {
        return teachingHistory;
    }

}
