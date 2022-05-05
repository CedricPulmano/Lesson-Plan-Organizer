package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LessonTest {

    Lesson l1;

    @Test
    public void testConstructorNoRequirements() {
        l1 = new Lesson("Why UBC Should Lower Tuition", 15, new ArrayList<String>());

        assertEquals("Why UBC Should Lower Tuition", l1.getTitle());
        assertEquals(15, l1.getTimeToTeach());
        assertEquals(0, l1.getRequirements().size());
    }

    @Test
    public void testConstructorWithRequirements() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Attended UBC");
        requirements.add("Attended SFU");
        l1 = new Lesson("Why UBC is better than SFU", 30, requirements);

        assertEquals("Why UBC is better than SFU", l1.getTitle());
        assertEquals(30, l1.getTimeToTeach());
        assertEquals(2, l1.getRequirements().size());
        assertEquals("Attended UBC", l1.getRequirements().get(0));
        assertEquals("Attended SFU", l1.getRequirements().get(1));
    }

}
