package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttendanceEntryTest {

    AttendanceEntry a1;

    @BeforeEach
    public void setup() {
        a1 = new AttendanceEntry("Java Stinks", "Felix Grund", "01/31/2022");
    }

    @Test
    public void testConstructor() {
        assertEquals("Java Stinks", a1.getLessonTitle());
        assertEquals("Felix Grund", a1.getTeacherName());
        assertEquals("01/31/2022", a1.getDate());
    }

    @Test
    public void testToString() {
        String message = "On 01/31/2022, Felix Grund taught Java Stinks";
        assertEquals(message, a1.toString());
    }

}
