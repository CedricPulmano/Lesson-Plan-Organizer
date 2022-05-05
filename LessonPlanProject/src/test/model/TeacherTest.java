package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeacherTest {

    Teacher t1;
    Teacher t2;

    @BeforeEach
    public void setup() {
        List<String> noCertifications = new ArrayList<String>();
        List<String> accomplishedCertifications = new ArrayList<String>();
        accomplishedCertifications.add("Chemistry Degree");
        accomplishedCertifications.add("Meth Making");

        t1 = new Teacher("Herbert Garrison", noCertifications);
        t2 = new Teacher("Walter White", accomplishedCertifications);
    }

    @Test
    public void testConstructorNoCertifications() {
        assertEquals("Herbert Garrison", t1.getName());
        assertEquals(0, t1.getCertifications().size());
    }

    @Test
    public void testConstructorWithCertifications() {
        assertEquals("Walter White", t2.getName());
        assertEquals(2, t2.getCertifications().size());
        assertEquals("Chemistry Degree", t2.getCertifications().get(0));
        assertEquals("Meth Making", t2.getCertifications().get(1));
    }

    @Test
    public void testIsCertifiedToTeachTrueEmptyRequirements() {
        List<String> noRequirements = new ArrayList<String>();
        assertTrue(t1.isCertifiedToTeach(noRequirements));
        assertTrue(t2.isCertifiedToTeach(noRequirements));
    }

    @Test
    public void testIsCertifiedToTeachFalseNoRequirementsMet() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Challenger in League of Legends");

        assertFalse(t1.isCertifiedToTeach(requirements));
        assertFalse(t2.isCertifiedToTeach(requirements));
    }

    @Test
    public void testIsCertifiedToTeachFalseSomeRequirementsMet() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Meth Making");
        requirements.add("Surviving Cancer");

        assertFalse(t2.isCertifiedToTeach(requirements));
    }

    @Test
    public void testIsCertifiedToTeachTrueAllRequirementsMet() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Meth Making");

        assertTrue(t2.isCertifiedToTeach(requirements));

        requirements.add("Chemistry Degree");

        assertTrue(t2.isCertifiedToTeach(requirements));
    }

    @Test
    public void testGainCertification() {
        t1.gainCertification("Puppet Playing");
        assertEquals(1, t1.getCertifications().size());
        assertEquals("Puppet Playing", t1.getCertifications().get(0));

        t2.gainCertification("Shooting Bad Guys");
        assertEquals(3, t2.getCertifications().size());
        assertEquals("Shooting Bad Guys", t2.getCertifications().get(2));
    }

}
