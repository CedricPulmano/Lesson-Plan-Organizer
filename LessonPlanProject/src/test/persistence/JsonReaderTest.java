package persistence;

import model.LessonPlan;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    public void testReaderNonExistent() {
        JsonReader jsonReader = new JsonReader("./data/meaningoflife.json");

        try {
            LessonPlan lp = jsonReader.read();
            fail("IOException should have been thrown");
        } catch (IOException e) {

        }
    }

    @Test
    public void testReaderEmpty() {
        JsonReader jsonReader = new JsonReader("./data/testEmptyLessonPlan.json");

        try {
            LessonPlan lp = jsonReader.read();
            assertEquals("Lesson Plan", lp.getLessonPlanTitle());
            assertEquals(0, lp.getLessonsToTeach().size());
            assertEquals(0, lp.getStudents().size());
            assertEquals(0, lp.getTeachers().size());
            assertEquals(0, lp.getTeachingHistory().size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testReaderErrorTeaching() {
        JsonReader jsonReader = new JsonReader("./data/testLessonPlanErrorTeaching.json");

        try {
            LessonPlan lp = jsonReader.read();
            // Should print error message of "Load unsuccessful"
        } catch (Exception e) {
            fail("No exception should have been thrown");
        }
    }

    @Test
    public void testReaderGeneral() {
        JsonReader jsonReader = new JsonReader("./data/testGeneralLessonPlan.json");

        try {
            LessonPlan lp = jsonReader.read();
            assertEquals("Bilgewater Pirates", lp.getLessonPlanTitle());

            assertEquals(2, lp.getLessonsToTeach().size());
            assertEquals("Powder Keg Barrel Technology", lp.getLessonsToTeach().get(0).getTitle());
            assertEquals(90, lp.getLessonsToTeach().get(0).getTimeToTeach());
            assertEquals(2, lp.getLessonsToTeach().get(0).getRequirements().size());
            assertEquals("Pirate Captain", lp.getLessonsToTeach().get(0).getRequirements().get(0));
            assertEquals("Loves Oranges", lp.getLessonsToTeach().get(0).getRequirements().get(1));
            assertEquals("Walking the Plank", lp.getLessonsToTeach().get(1).getTitle());
            assertEquals(15, lp.getLessonsToTeach().get(1).getTimeToTeach());
            assertEquals(0, lp.getLessonsToTeach().get(1).getRequirements().size());

            assertEquals(3, lp.getStudents().size());
            assertEquals("Lucian", lp.getStudents().get(0).getName());
            assertEquals(0, lp.getStudents().get(0).getLessonsToLearn().size());
            assertEquals(2, lp.getStudents().get(0).getAttendance().size());
            assertEquals("Powder Keg Barrel Technology", lp.getStudents().get(0).getAttendance().get(0)
                    .getLessonTitle());
            assertEquals("Walking the Plank", lp.getStudents().get(0).getAttendance().get(1).getLessonTitle());
            assertEquals("Graves", lp.getStudents().get(1).getName());
            assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
            assertEquals("Powder Keg Barrel Technology", lp.getStudents().get(1).getAttendance().get(0)
                    .getLessonTitle());
            assertEquals(1, lp.getStudents().get(1).getAttendance().size());
            assertEquals("Walking the Plank", lp.getStudents().get(1).getLessonsToLearn().get(0).getTitle());
            assertEquals("Pyke", lp.getStudents().get(2).getName());
            assertEquals(2, lp.getStudents().get(2).getLessonsToLearn().size());
            assertEquals("Powder Keg Barrel Technology", lp.getStudents().get(2).getLessonsToLearn().get(0)
                    .getTitle());
            assertEquals("Walking the Plank", lp.getStudents().get(2).getLessonsToLearn().get(1).getTitle());
            assertEquals(0, lp.getStudents().get(2).getAttendance().size());

            assertEquals(2, lp.getTeachers().size());
            assertEquals("Gangplank", lp.getTeachers().get(0).getName());
            assertEquals(3, lp.getTeachers().get(0).getCertifications().size());
            assertEquals("Pirate Captain", lp.getTeachers().get(0).getCertifications().get(0));
            assertEquals("Loves Oranges", lp.getTeachers().get(0).getCertifications().get(1));
            assertEquals("Greedy", lp.getTeachers().get(0).getCertifications().get(2));
            assertEquals("Miss Fortune", lp.getTeachers().get(1).getName());
            assertEquals(0, lp.getTeachers().get(1).getCertifications().size());

            assertEquals(2, lp.getTeachingHistory().size());
            assertEquals("Powder Keg Barrel Technology", lp.getTeachingHistory().get(0).getLessonTitle());
            assertEquals("Gangplank", lp.getTeachingHistory().get(0).getTeacherName());
            assertEquals(2, lp.getTeachingHistory().get(0).getStudentNames().size());
            assertEquals("Lucian", lp.getTeachingHistory().get(0).getStudentNames().get(0));
            assertEquals("Graves", lp.getTeachingHistory().get(0).getStudentNames().get(1));
            assertEquals("03/25/1594", lp.getTeachingHistory().get(0).getDate());
            assertEquals("Walking the Plank", lp.getTeachingHistory().get(1).getLessonTitle());
            assertEquals("Miss Fortune", lp.getTeachingHistory().get(1).getTeacherName());
            assertEquals(1, lp.getTeachingHistory().get(1).getStudentNames().size());
            assertEquals("Lucian", lp.getTeachingHistory().get(1).getStudentNames().get(0));
            assertEquals("08/26/1603", lp.getTeachingHistory().get(1).getDate());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

}
