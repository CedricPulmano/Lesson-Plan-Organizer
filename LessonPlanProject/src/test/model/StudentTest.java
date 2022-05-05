package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    Student s1;

    @BeforeEach
    public void setup() {
        s1 = new Student("Arthur Morgan");
    }

    @Test
    public void testConstructor() {
        assertEquals("Arthur Morgan", s1.getName());
        assertEquals(0, s1.getLessonsToLearn().size());
        assertEquals(0, s1.getAttendance().size());
    }

    @Test
    public void testAddAttendanceEntry() {
        s1.addAttendanceEntry("Gunfighting", "Dutch van der Linde", "10/04/1887");
        s1.addAttendanceEntry("Horseback Riding", "Hosea Matthews", "03/12/1884");
        assertEquals(2, s1.getAttendance().size());
        assertEquals("Gunfighting", s1.getAttendance().get(0).getLessonTitle());
        assertEquals("Dutch van der Linde", s1.getAttendance().get(0).getTeacherName());
        assertEquals("10/04/1887", s1.getAttendance().get(0).getDate());
        assertEquals("Horseback Riding", s1.getAttendance().get(1).getLessonTitle());
        assertEquals("Hosea Matthews", s1.getAttendance().get(1).getTeacherName());
        assertEquals("03/12/1884", s1.getAttendance().get(1).getDate());
    }

    @Test
    public void testAddLessonToLearn() {
        Lesson l1 = new Lesson("Singing", 4, new ArrayList<String>());
        assertTrue(s1.addLessonToLearn(l1));
        assertEquals(1, s1.getLessonsToLearn().size());
        assertEquals("Singing", s1.getLessonsToLearn().get(0).getTitle());
        assertEquals(4, s1.getLessonsToLearn().get(0).getTimeToTeach());
        assertEquals(0, s1.getLessonsToLearn().get(0).getRequirements().size());

        List<String> requirements = new ArrayList<String>();
        requirements.add("Healthy");
        requirements.add("Good Eating");
        Lesson l2 = new Lesson("Cardio", 5, requirements);
        assertTrue(s1.addLessonToLearn(l2));
        assertEquals(2, s1.getLessonsToLearn().size());
        assertEquals("Cardio", s1.getLessonsToLearn().get(1).getTitle());
        assertEquals(5, s1.getLessonsToLearn().get(1).getTimeToTeach());
        assertEquals(2, s1.getLessonsToLearn().get(1).getRequirements().size());
    }

    @Test
    public void testRemoveLessonToLearnTrue() {
        Lesson l1 = new Lesson("Singing", 4, new ArrayList<String>());
        Lesson l2 = new Lesson("Dancing", 9, new ArrayList<String>());
        s1.addLessonToLearn(l1);
        s1.addLessonToLearn(l2);
        assertTrue(s1.removeLessonToLearn(l1));
        assertEquals(1, s1.getLessonsToLearn().size());
        assertEquals("Dancing", s1.getLessonsToLearn().get(0).getTitle());
    }

    @Test
    public void testRemoveLessonToLearnFalse() {
        Lesson l1 = new Lesson("Singing", 4, new ArrayList<String>());
        Lesson l2 = new Lesson("Dancing", 9, new ArrayList<String>());
        Lesson l3 = new Lesson("Drinking", 10, new ArrayList<String>());
        s1.addLessonToLearn(l1);
        s1.addLessonToLearn(l2);
        assertFalse(s1.removeLessonToLearn(l3));
        assertEquals(2, s1.getLessonsToLearn().size());
    }

    @Test
    public void testTimeLeftToLearnEmpty() {
        assertEquals(0, s1.timeLeftToLearn());
    }

    @Test
    public void testTimeLeftToLearnMultipleLessons() {
        List<String> emptyRequirements = new ArrayList<String>();
        Lesson l1 = new Lesson("Singing", 4, emptyRequirements);
        Lesson l2 = new Lesson("Cardio", 5, emptyRequirements);
        s1.addLessonToLearn(l1);
        s1.addLessonToLearn(l2);
        assertEquals(9, s1.timeLeftToLearn());
    }

}
