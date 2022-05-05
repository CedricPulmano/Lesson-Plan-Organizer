package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// A student with a name, a list of lessons to learn, and a list of attendances for lessons already learned
public class Student {

    private String name;
    private List<Lesson> lessonsToLearn;
    private List<AttendanceEntry> attendance;

    // EFFECTS: creates student with the given name, as well as empty lessons to learn list and attendance
    public Student(String name) {
        this.name = name;
        lessonsToLearn = new ArrayList<Lesson>();
        attendance = new ArrayList<AttendanceEntry>();
    }

    // REQUIRES: date is formatted as "MM/DD/YYYY"
    // MODIFIES: this
    // EFFECTS: creates new attendance entry and adds it to lessonsLearned history
    public void addAttendanceEntry(String lessonTitle, String teacherName, String date) {
        attendance.add(new AttendanceEntry(lessonTitle, teacherName, date));
    }

    // MODIFIES: this
    // EFFECTS: adds given lesson to lessonsToLearn, returning true if it adds a lesson, else returning false
    public Boolean addLessonToLearn(Lesson lesson) {
        return lessonsToLearn.add(lesson);
    }

    // MODIFIES: this
    // EFFECTS: removes given lesson from lessonsToLearn, returning true if it removes a lesson, else returning false
    public Boolean removeLessonToLearn(Lesson lesson) {
        return lessonsToLearn.remove(lesson);
    }

    // EFFECTS: returns the time it takes to learn the lessons that the student has left in lessonsToLearn
    public int timeLeftToLearn() {
        int sum = 0;
        for (Lesson lesson : lessonsToLearn) {
            sum += lesson.getTimeToTeach();
        }
        return sum;
    }

    // EFFECTS: returns student in this lesson plan as a JSONObject
    public JSONObject toJson() {
        JSONObject studentJson = new JSONObject();

        studentJson.put("name", name);

        return studentJson;
    }

    public String getName() {
        return name;
    }

    public List<Lesson> getLessonsToLearn() {
        return lessonsToLearn;
    }

    public List<AttendanceEntry> getAttendance() {
        return attendance;
    }

}
