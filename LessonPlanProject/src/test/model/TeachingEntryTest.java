package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeachingEntryTest {

    private TeachingEntry t1;

    @Test
    public void testConstructorWithoutStudents() {
        List<String> studentNames = new ArrayList<String>();
        t1 = new TeachingEntry("Anger Management", "Ourselves", studentNames, "02/20/2022");

        assertEquals("Anger Management", t1.getLessonTitle());
        assertEquals("Ourselves", t1.getTeacherName());
        assertEquals(0, t1.getStudentNames().size());
        assertEquals("02/20/2022", t1.getDate());
    }

    @Test
    public void testConstructorWithStudents() {
        List<String> studentNames = new ArrayList<String>();
        studentNames.add("Cedric Pulmano");
        studentNames.add("Cedric Daui Gorio Pulmano");
        studentNames.add("EdgyCedgie");
        t1 = new TeachingEntry("Anger Management", "Ourselves", studentNames, "02/20/2022");

        assertEquals("Anger Management", t1.getLessonTitle());
        assertEquals("Ourselves", t1.getTeacherName());
        assertEquals(3, t1.getStudentNames().size());
        assertEquals("Cedric Pulmano", t1.getStudentNames().get(0));
        assertEquals("Cedric Daui Gorio Pulmano", t1.getStudentNames().get(1));
        assertEquals("EdgyCedgie", t1.getStudentNames().get(2));
        assertEquals("02/20/2022", t1.getDate());
    }

    @Test
    public void testToStringNoStudents() {
        List<String> studentNames = new ArrayList<String>();
        t1 = new TeachingEntry("Anger Management", "Ourselves", studentNames, "02/20/2022");

        String response = "Anger Management was taught by Ourselves to nobody on 02/20/2022";
        assertEquals(response, t1.toString());
    }

    @Test
    public void testToStringOneStudent() {
        List<String> studentNames = new ArrayList<String>();
        studentNames.add("Cedric Pulmano");
        t1 = new TeachingEntry("Anger Management", "Ourselves", studentNames, "02/20/2022");

        String response = "Anger Management was taught by Ourselves to Cedric Pulmano on 02/20/2022";
        assertEquals(response, t1.toString());
    }

    @Test
    public void testToStringMultipleStudents() {
        List<String> studentNames = new ArrayList<String>();
        studentNames.add("Cedric Pulmano");
        studentNames.add("EdgyCedgie");
        studentNames.add("C");
        t1 = new TeachingEntry("Anger Management", "Ourselves", studentNames, "02/20/2022");

        String response = "Anger Management was taught by Ourselves to Cedric Pulmano, EdgyCedgie, and C on 02/20/2022";
        assertEquals(response, t1.toString());
    }

}
