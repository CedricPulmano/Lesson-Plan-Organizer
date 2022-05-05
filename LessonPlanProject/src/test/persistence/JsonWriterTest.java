package persistence;

import exceptions.TeachLessonErrorException;
import model.LessonPlan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            new LessonPlan("My work room");
            JsonWriter writer = new JsonWriter("./data/\u0000illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException var3) {
        }
    }

    @Test
    void testWriterEmptyLessonPlan() {
        try {
            LessonPlan lp = new LessonPlan("My lesson plan!");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLessonPlan.json");
            writer.open();
            writer.write(lp);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyLessonPlan.json");
            lp = reader.read();
            assertEquals("My lesson plan!", lp.getLessonPlanTitle());
            assertEquals(0, lp.getLessonsToTeach().size());
            assertEquals(0, lp.getStudents().size());
            assertEquals(0, lp.getTeachers().size());
            assertEquals(0, lp.getTeachingHistory().size());
        } catch (IOException var4) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLessonPlan() {
        try {
            LessonPlan lp = new LessonPlan("Evolution Plan!");
            lp.addLesson("Starting Fires", 90, new ArrayList<String>());
            lp.addStudent("Neanderthal Ned");
            List<String> toolsRequirements = new ArrayList<String>();
            toolsRequirements.add("Craftsmanship");
            toolsRequirements.add("Strength");
            lp.addLesson("Making Tools", 200, toolsRequirements);
            lp.addTeacher("Caveman Carl", new ArrayList<String>());
            List<String> joeCertifications = new ArrayList<String>();
            joeCertifications.add("Dexterity");
            joeCertifications.add("Strength");
            joeCertifications.add("Craftsmanship");
            lp.addTeacher("Stone Age Sam", joeCertifications);
            List<String> fireAttendees = new ArrayList<String>();
            fireAttendees.add("Ooga Booga Oleg");
            fireAttendees.add("Blacksmith Bart");
            List<String> toolsAttendees = new ArrayList<String>();
            toolsAttendees.add("Blacksmith Bart");
            try {
                lp.teachLesson("Starting Fires", "Caveman Carl", fireAttendees,
                        90, "07/27/0001");
            } catch (TeachLessonErrorException e) {
                fail("No exception should be thrown");
            }
            try {
                lp.teachLesson("Making Tools", "Stone Age Sam", toolsAttendees,
                        200, "09/30/1450");
            } catch (TeachLessonErrorException e) {
                fail("No exception should be thrown");
            }

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLessonPlan.json");
            writer.open();
            writer.write(lp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLessonPlan.json");
            lp = reader.read();
            assertEquals("Evolution Plan!", lp.getLessonPlanTitle());

            assertEquals(2, lp.getLessonsToTeach().size());
            assertEquals("Starting Fires", lp.getLessonsToTeach().get(0).getTitle());
            assertEquals(90, lp.getLessonsToTeach().get(0).getTimeToTeach());
            assertEquals(0, lp.getLessonsToTeach().get(0).getRequirements().size());
            assertEquals("Making Tools", lp.getLessonsToTeach().get(1).getTitle());
            assertEquals(200, lp.getLessonsToTeach().get(1).getTimeToTeach());
            assertEquals(2, lp.getLessonsToTeach().get(1).getRequirements().size());
            assertEquals("Craftsmanship", lp.getLessonsToTeach().get(1).getRequirements().get(0));
            assertEquals("Strength", lp.getLessonsToTeach().get(1).getRequirements().get(1));

            assertEquals(3, lp.getStudents().size());
            assertEquals("Neanderthal Ned", lp.getStudents().get(0).getName());
            assertEquals(2, lp.getStudents().get(0).getLessonsToLearn().size());
            assertEquals(0, lp.getStudents().get(0).getAttendance().size());
            assertEquals("Starting Fires", lp.getStudents().get(0).getLessonsToLearn().get(0).getTitle());
            assertEquals("Making Tools", lp.getStudents().get(0).getLessonsToLearn().get(1).getTitle());
            assertEquals("Ooga Booga Oleg", lp.getStudents().get(1).getName());
            assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
            assertEquals(1, lp.getStudents().get(1).getAttendance().size());
            assertEquals("Making Tools", lp.getStudents().get(1).getLessonsToLearn().get(0).getTitle());
            assertEquals("Starting Fires", lp.getStudents().get(1).getAttendance().get(0).getLessonTitle());
            assertEquals("Blacksmith Bart", lp.getStudents().get(2).getName());
            assertEquals(0, lp.getStudents().get(2).getLessonsToLearn().size());
            assertEquals(2, lp.getStudents().get(2).getAttendance().size());
            assertEquals("Starting Fires", lp.getStudents().get(2).getAttendance().get(0).getLessonTitle());
            assertEquals("Making Tools", lp.getStudents().get(2).getAttendance().get(1).getLessonTitle());

            assertEquals(2, lp.getTeachers().size());
            assertEquals("Caveman Carl", lp.getTeachers().get(0).getName());
            assertEquals(0, lp.getTeachers().get(0).getCertifications().size());
            assertEquals("Stone Age Sam", lp.getTeachers().get(1).getName());
            assertEquals(3, lp.getTeachers().get(1).getCertifications().size());
            assertEquals("Dexterity", lp.getTeachers().get(1).getCertifications().get(0));
            assertEquals("Strength", lp.getTeachers().get(1).getCertifications().get(1));
            assertEquals("Craftsmanship", lp.getTeachers().get(1).getCertifications().get(2));

            assertEquals(2, lp.getTeachingHistory().size());
        } catch (IOException var5) {
            fail("Exception should not have been thrown");
        }
    }

}
