package model;

// A student's attendance entry with information about which lesson was taught, who taught it, and when
public class AttendanceEntry {

    private String lessonTitle;
    private String teacherName;
    private String date;

    // REQUIRES: date is formatted in "MM/DD/YYYY"
    // EFFECTS: creates attendance entry that stores lesson title, teacher name, and date of attending
    public AttendanceEntry(String lessonTitle, String teacherName, String date) {
        this.lessonTitle = lessonTitle;
        this.teacherName = teacherName;
        this.date = date;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getDate() {
        return date;
    }

    // EFFECTS: returns information about lesson, teacher, and date of attendance
    public String toString() {
        return "On " + date + ", " + teacherName + " taught " + lessonTitle;
    }

}
