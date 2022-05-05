package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// A lesson with a name, and the time it takes to teach it (in minutes)
public class Lesson {

    private String title;
    private int timeToTeach;
    private List<String> requirements;

    // EFFECTS: creates a lesson with a title, timeToTeach, and lits of requirements
    public Lesson(String title, int timeToTeach, List<String> requirements) {
        this.title = title;
        this.timeToTeach = timeToTeach;
        this.requirements = requirements;
    }

    // EFFECTS: returns lesson in this lesson plan as a JSONObject
    public JSONObject toJson() {
        JSONObject lessonJson = new JSONObject();

        lessonJson.put("title", title);

        lessonJson.put("timeToTeach", timeToTeach);

        JSONArray requirementsJson = new JSONArray();
        for (String requirement : requirements) {
            JSONObject requirementJson = new JSONObject();
            requirementJson.put("requirement", requirement);
            requirementsJson.put(requirementJson);
        }
        lessonJson.put("requirements", requirementsJson);

        return lessonJson;
    }

    public String getTitle() {
        return title;
    }

    public int getTimeToTeach() {
        return timeToTeach;
    }

    public List<String> getRequirements() {
        return requirements;
    }

}
