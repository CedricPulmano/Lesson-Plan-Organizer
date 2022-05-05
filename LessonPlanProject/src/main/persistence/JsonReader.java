package persistence;

import model.LessonPlan;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Based off of JSONSerializationDemo
// A JsonReader that reads lesson planner data from JSON file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public LessonPlan read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLessonPlan(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses lesson plan from JSON object and returns it
    private LessonPlan parseLessonPlan(JSONObject jsonObject) {
        LessonPlan lp = new LessonPlan(jsonObject.getString("name"));
        addLessons(lp, jsonObject);
        addTeachers(lp, jsonObject);
        addStudents(lp, jsonObject);
        addTeachingHistory(lp, jsonObject);
        return lp;
    }

    // MODIFIES: lp
    // EFFECTS: parses lessons from JSON object and adds them to lesson plan
    private void addLessons(LessonPlan lp, JSONObject jsonObject) {
        for (Object json : jsonObject.getJSONArray("lessons")) {
            addLesson(lp, (JSONObject) json);
        }
    }

    // MODIFIES: lp
    // EFFECTS: parses lesson from JSON object and adds it to lesson plan
    private void addLesson(LessonPlan lp, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int timeToTeach = jsonObject.getInt("timeToTeach");
        List<String> requirements = new ArrayList<String>();
        for (Object json : jsonObject.getJSONArray("requirements")) {
            JSONObject nextRequirement = (JSONObject) json;
            requirements.add(nextRequirement.getString("requirement"));
        }
        lp.addLesson(title, timeToTeach, requirements);
    }

    // MODIFIES: lp
    // EFFECTS: parses teachers from JSON object and adds them to lesson plan
    private void addTeachers(LessonPlan lp, JSONObject jsonObject) {
        for (Object json : jsonObject.getJSONArray("teachers")) {
            JSONObject nextTeacher = (JSONObject) json;
            addTeacher(lp, nextTeacher);
        }
    }

    // MODIFIES: lp
    // EFFECTS: parses teacher from JSON object and adds them to lesson plan
    private void addTeacher(LessonPlan lp, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        List<String> certifications = new ArrayList<String>();
        for (Object json : jsonObject.getJSONArray("certifications")) {
            JSONObject nextCertification = (JSONObject) json;
            certifications.add(nextCertification.getString("certification"));
        }
        lp.addTeacher(name, certifications);
    }

    // MODIFIES: lp
    // EFFECTS: parses students from JSON object and adds them to lesson plan
    private void addStudents(LessonPlan lp, JSONObject jsonObject) {
        for (Object json : jsonObject.getJSONArray("students")) {
            JSONObject nextStudent = (JSONObject) json;
            addStudent(lp, nextStudent);
        }
    }

    // MODIFIES: lp
    // EFFECTS: parses student from JSON object and adds them to lesson plan with no lessons learned yet
    private void addStudent(LessonPlan lp, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        lp.addStudent(name);
    }

    // MODIFIES: lp
    // EFFECTS: parses teaching history from JSON object and adds them to lesson plan, as well as teaches lessons
    private void addTeachingHistory(LessonPlan lp, JSONObject jsonObject) {
        for (Object json : jsonObject.getJSONArray("teaching history")) {
            JSONObject nextTeachingEntry = (JSONObject) json;
            addTeachingEntry(lp, nextTeachingEntry);
        }
    }

    // MODIFIES: lp
    // EFFECTS: parses teaching entry from JSON object and adds them to lesson plan, as well as teaches lesson
    private void addTeachingEntry(LessonPlan lp, JSONObject jsonObject) {
        String lessonTitle = jsonObject.getString("lessonTitle");
        String teacherName = jsonObject.getString("teacherName");
        List<String> studentNames = new ArrayList<String>();
        for (Object json : jsonObject.getJSONArray("studentNames")) {
            JSONObject nextStudent = (JSONObject) json;
            studentNames.add(nextStudent.getString("name"));
        }
        String date = jsonObject.getString("date");
        try {
            lp.teachLesson(lessonTitle, teacherName, studentNames, Integer.MAX_VALUE, date);
        } catch (Exception e) {
            System.err.println("Load unsuccessful");
        }
    }

}
