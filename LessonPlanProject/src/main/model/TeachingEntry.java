package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// A teaching entry of a lesson taught with a record of lesson, teacher, students, and date
public class TeachingEntry {

    private String lessonTitle;
    private String teacherName;
    private List<String> studentNames;
    private String date;

    // EFFECTS: creates a teaching entry that records the lesson, teacher name, student names, and date of teaching
    public TeachingEntry(String lessonTitle, String teacherName, List<String> studentNames, String date) {
        this.lessonTitle = lessonTitle;
        this.teacherName = teacherName;
        this.studentNames = studentNames;
        this.date = date;
    }

    // EFFECTS: returns teaching entry in this lesson plan as a JSONObject
    public JSONObject toJson() {
        JSONObject teachingEntryJson = new JSONObject();

        teachingEntryJson.put("lessonTitle", lessonTitle);

        teachingEntryJson.put("teacherName", teacherName);

        JSONArray studentNamesJson = new JSONArray();
        for (String studentName : studentNames) {
            JSONObject studentNameJson = new JSONObject();
            studentNameJson.put("name", studentName);
            studentNamesJson.put(studentNameJson);
        }
        teachingEntryJson.put("studentNames", studentNamesJson);

        teachingEntryJson.put("date", date);

        return teachingEntryJson;
    }

    // EFFECTS: returns a formatted string of teaching entry
    public String toString() {
        String students = "";
        if (studentNames.size() == 0) {
            students = "nobody";
        } else if (studentNames.size() == 1) {
            students = studentNames.get(0);
        } else {
            for (int i = 0; i < studentNames.size() - 1; i++) {
                students += studentNames.get(i);
                students += ", ";
            }
            students += "and ";
            students += studentNames.get(studentNames.size() - 1);
        }

        return lessonTitle + " was taught by " + teacherName + " to " + students + " on " + date;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public String getDate() {
        return date;
    }

}