package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// A teacher with a name, and a list of certifications
public class Teacher {

    private String name;
    private List<String> certifications;

    // EFFECTS: creates teacher with given name and list of certifications
    public Teacher(String name, List<String> certifications) {
        this.name = name;
        this.certifications = certifications;
    }

    // EFFECTS: returns true if teacher can teach given lesson, else returns false
    public Boolean isCertifiedToTeach(List<String> requirements) {
        for (String requirement : requirements) {

            Boolean matchFound = false;

            for (String certification : certifications) {
                if (certification.equals(requirement)) {
                    matchFound = true;
                }
            }

            if (!matchFound) {
                return false;
            }

        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: adds given certification to certifications list
    public void gainCertification(String certification) {
        certifications.add(certification);
    }

    // EFFECTS: returns teacher in this lesson plan as a JSONObject
    public JSONObject toJson() {
        JSONObject teacherJson = new JSONObject();

        teacherJson.put("name", name);

        JSONArray certificationsJson = new JSONArray();
        for (String certification : certifications) {
            JSONObject certificationJson = new JSONObject();
            certificationJson.put("certification", certification);
            certificationsJson.put(certificationJson);
        }
        teacherJson.put("certifications", certificationsJson);

        return teacherJson;
    }

    public String getName() {
        return name;
    }

    public List<String> getCertifications() {
        return certifications;
    }

}
