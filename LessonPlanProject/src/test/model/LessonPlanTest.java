package model;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LessonPlanTest {

    LessonPlan lp;

    @BeforeEach
    public void setup() {
        lp = new LessonPlan("Lesson Plan");
    }

    @Test
    public void testConstructor() {
        assertEquals("Lesson Plan", lp.getLessonPlanTitle());
        assertEquals(0, lp.getLessonsToTeach().size());
        assertEquals(0, lp.getTeachers().size());
        assertEquals(0, lp.getStudents().size());
    }

    @Test
    public void testAddLessonWithNoStudents() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Agility");
        lp.addLesson("Vertical Maneuvering", 400, requirements);
        assertEquals(1, lp.getLessonsToTeach().size());
        assertEquals("Vertical Maneuvering", lp.getLessonsToTeach().get(0).getTitle());
        assertEquals(400, lp.getLessonsToTeach().get(0).getTimeToTeach());
        assertEquals(1, lp.getLessonsToTeach().get(0).getRequirements().size());
    }

    @Test
    public void testAddLessonWithStudents() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Power");
        lp.addStudent("Eren Jaeger");
        lp.addStudent("Reiner Braun");
        lp.addLesson("Brazilian Jiu Jitsu", 120, requirements);

        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals("Brazilian Jiu Jitsu", lp.getStudents().get(0).getLessonsToLearn().get(0).getTitle());
        assertEquals(120, lp.getStudents().get(0).getLessonsToLearn().get(0).getTimeToTeach());
        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().get(0).getRequirements().size());
        assertEquals("Power", lp.getStudents().get(0).getLessonsToLearn().get(0).getRequirements().get(0));

        assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals("Brazilian Jiu Jitsu", lp.getStudents().get(1).getLessonsToLearn().get(0).getTitle());
        assertEquals(120, lp.getStudents().get(1).getLessonsToLearn().get(0).getTimeToTeach());
        assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().get(0).getRequirements().size());
        assertEquals("Power", lp.getStudents().get(1).getLessonsToLearn().get(0).getRequirements().get(0));
    }

    @Test
    public void testAddStudentWithNoLessons() {
        lp.addStudent("Armin Arlert");
        lp.addStudent("Mikasa Ackerman");
        assertEquals(2, lp.getStudents().size());

        assertEquals("Armin Arlert", lp.getStudents().get(0).getName());
        assertEquals(0, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(0, lp.getStudents().get(0).getAttendance().size());

        assertEquals("Mikasa Ackerman", lp.getStudents().get(1).getName());
        assertEquals(0, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(0, lp.getStudents().get(1).getAttendance().size());
    }

    @Test
    public void testAddStudentWithLessons() {
        List<String> requirements = new ArrayList<String>();
        List<String> otherRequirements = new ArrayList<String>();
        requirements.add("Leadership");
        requirements.add("Focus");
        otherRequirements.add("Loyalty");

        lp.addLesson("Titan Killing", 15, requirements);
        lp.addLesson("Team Trust", 90, otherRequirements);
        lp.addStudent("Levi Ackerman");

        assertEquals(2, lp.getStudents().get(0).getLessonsToLearn().size());

        assertEquals("Titan Killing", lp.getStudents().get(0).getLessonsToLearn().get(0).getTitle());
        assertEquals(15, lp.getStudents().get(0).getLessonsToLearn().get(0).getTimeToTeach());
        assertEquals(2, lp.getStudents().get(0).getLessonsToLearn().get(0).getRequirements().size());
        assertEquals("Leadership", lp.getStudents().get(0).getLessonsToLearn().get(0).getRequirements().get(0));
        assertEquals("Focus", lp.getStudents().get(0).getLessonsToLearn().get(0).getRequirements().get(1));

        assertEquals("Team Trust", lp.getStudents().get(0).getLessonsToLearn().get(1).getTitle());
        assertEquals(90, lp.getStudents().get(0).getLessonsToLearn().get(1).getTimeToTeach());
        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().get(1).getRequirements().size());
        assertEquals("Loyalty", lp.getStudents().get(0).getLessonsToLearn().get(1).getRequirements().get(0));
    }

    @Test
    public void testAddTeacher() {
        List<String> certifications = new ArrayList<String>();
        certifications.add("Loyalty");
        certifications.add("Leadership");
        lp.addTeacher("Erwin Smith", certifications);
        assertEquals(1, lp.getTeachers().size());
        assertEquals("Erwin Smith", lp.getTeachers().get(0).getName());
        assertEquals(2, lp.getTeachers().get(0).getCertifications().size());
        assertEquals("Loyalty", lp.getTeachers().get(0).getCertifications().get(0));
        assertEquals("Leadership", lp.getTeachers().get(0).getCertifications().get(1));

        lp.addTeacher("Keith Sadies", new ArrayList<String>());
        assertEquals(2, lp.getTeachers().size());
        assertEquals("Keith Sadies", lp.getTeachers().get(1).getName());
        assertEquals(0, lp.getTeachers().get(1).getCertifications().size());
    }

    @Test
    public void testRemoveLessonFalse() {
        lp.addLesson("Crying", 12, new ArrayList<String>());
        lp.addStudent("Cedric Pulmano");
        assertFalse(lp.removeLesson("Suicide"));
        assertEquals(1, lp.getLessonsToTeach().size());
    }

    @Test
    public void testRemoveLessonTrueWithNoStudents() {
        lp.addLesson("Crying", 12, new ArrayList<String>());
        assertTrue(lp.removeLesson("Crying"));
        assertEquals(0, lp.getLessonsToTeach().size());
    }

    @Test
    public void testRemoveLessonTrueWithStudents() {
        lp.addLesson("Crying", 12, new ArrayList<String>());
        lp.addLesson("Wailing", 2, new ArrayList<String>());
        lp.addStudent("Cedric Pulmano");
        assertTrue(lp.removeLesson("Crying"));
        assertEquals(1, lp.getLessonsToTeach().size());
        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals("Wailing", lp.getStudents().get(0).getLessonsToLearn().get(0).getTitle());
    }

    @Test
    public void testRemoveStudentFalse() {
        lp.addStudent("Adolf Hitler");
        lp.addStudent("Benito Mussolini");
        lp.addStudent("Hirohito");
        assertFalse(lp.removeStudent("Winston Churchill"));
        assertEquals(3, lp.getStudents().size());
        assertEquals("Adolf Hitler", lp.getStudents().get(0).getName());
        assertEquals("Benito Mussolini", lp.getStudents().get(1).getName());
        assertEquals("Hirohito", lp.getStudents().get(2).getName());
    }

    @Test
    public void testRemoveStudentTrue() {
        lp.addStudent("Adolf Hitler");
        lp.addStudent("Benito Mussolini");
        lp.addStudent("Hirohito");
        assertTrue(lp.removeStudent("Benito Mussolini"));
        assertEquals(2, lp.getStudents().size());
        assertEquals("Adolf Hitler", lp.getStudents().get(0).getName());
        assertEquals("Hirohito", lp.getStudents().get(1).getName());
    }

    @Test
    public void testRemoveTeacherFalse() {
        lp.addTeacher("Germany", new ArrayList<String>());
        lp.addTeacher("Italy", new ArrayList<String>());
        lp.addTeacher("Japan", new ArrayList<String>());
        assertFalse(lp.removeTeacher("Canada"));
        assertEquals(3, lp.getTeachers().size());
        assertEquals("Germany", lp.getTeachers().get(0).getName());
        assertEquals("Italy", lp.getTeachers().get(1).getName());
        assertEquals("Japan", lp.getTeachers().get(2).getName());
    }

    @Test
    public void testRemoveTeacherTrue() {
        lp.addTeacher("Germany", new ArrayList<String>());
        lp.addTeacher("Italy", new ArrayList<String>());
        lp.addTeacher("Japan", new ArrayList<String>());
        assertTrue(lp.removeTeacher("Italy"));
        assertEquals(2, lp.getTeachers().size());
        assertEquals("Germany", lp.getTeachers().get(0).getName());
        assertEquals("Japan", lp.getTeachers().get(1).getName());
    }

    @Test
    public void testFindLessonDoesNotExist() {
        List<String> requirements = new ArrayList<String>();
        lp.addLesson("Testing", 90, requirements);
        lp.addLesson("Data Abstraction", 105, requirements);
        lp.addLesson("Hating on DrRacket", 5, requirements);
        assertTrue(null == lp.findLesson("Keyboard Shortcuts"));
    }

    @Test
    public void testFindLessonExists() {
        List<String> requirements = new ArrayList<String>();
        lp.addLesson("Testing", 90, requirements);
        lp.addLesson("Data Abstraction", 105, requirements);
        lp.addLesson("Hating on DrRacket", 5, requirements);
        Lesson result = lp.findLesson("Data Abstraction");
        assertEquals("Data Abstraction", result.getTitle());
        assertEquals(105, result.getTimeToTeach());
        assertEquals(0, result.getRequirements().size());
    }

    @Test
    public void testFindTeacherDoesNotExist() {
        List<String> certifications = new ArrayList<String>();
        certifications.add("Singing");
        certifications.add("Guitar Playing");
        lp.addTeacher("Robert Plant", certifications);
        lp.addTeacher("Kurt Cobain", certifications);
        assertTrue(null == lp.findTeacher("Freddie Mercury"));
    }

    @Test
    public void testFindTeacherExists() {
        List<String> certifications = new ArrayList<String>();
        certifications.add("Singing");
        certifications.add("Guitar Playing");
        lp.addTeacher("Robert Plant", certifications);
        lp.addTeacher("Kurt Cobain", certifications);
        Teacher result = lp.findTeacher("Kurt Cobain");
        assertEquals("Kurt Cobain", result.getName());
        assertEquals(2, result.getCertifications().size());
    }

    @Test
    public void testFindStudentDoesNotExist() {
        lp.addStudent("Quentin Tarantino");
        lp.addStudent("Sergio Leone");
        lp.addStudent("Damien Chazelle");
        assertTrue(null == lp.findStudent("John Carpenter"));
    }

    @Test
    public void testFindStudentExists() {
        lp.addStudent("Quentin Tarantino");
        lp.addStudent("Sergio Leone");
        lp.addStudent("Damien Chazelle");
        Student result = lp.findStudent("Damien Chazelle");
        assertEquals("Damien Chazelle", result.getName());
    }

    @Test
    public void testGraduateEligibleStudentsNoStudents(){
        lp.addLesson("Jellyfishing", 10, new ArrayList<String>());
        lp.addStudent("Spongebob");
        lp.addStudent("Patrick");
        lp.addStudent("Squidward");
        assertEquals(0, lp.graduateEligibleStudents().size());
    }

    @Test
    public void testGraduateEligibleStudentsNoLessons(){
        lp.addStudent("Spongebob");
        lp.addStudent("Patrick");
        lp.addStudent("Squidward");
        List<String> graduatedStudents = lp.graduateEligibleStudents();
        assertEquals(3, graduatedStudents.size());
        assertEquals("Spongebob", graduatedStudents.get(0));
        assertEquals("Patrick", graduatedStudents.get(1));
        assertEquals("Squidward", graduatedStudents.get(2));
        assertEquals(0, lp.getStudents().size());
    }

    @Test
    public void testGraduateEligibleStudentsSomeGraduate(){
        lp.addLesson("Jellyfishing", 10, new ArrayList<String>());
        lp.addStudent("Spongebob");
        lp.addStudent("Patrick");
        lp.addStudent("Squidward");
        lp.addTeacher("Sandy", new ArrayList<String>());
        List<String> listOfStudents = new ArrayList<String>();
        listOfStudents.add("Spongebob");
        listOfStudents.add("Patrick");
        try {
            lp.teachLesson("Jellyfishing", "Sandy", listOfStudents, 10, "06/09/2001");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }
        List<String> eligibleStudents = lp.graduateEligibleStudents();
        assertEquals(2, eligibleStudents.size());
        assertEquals("Spongebob", eligibleStudents.get(0));
        assertEquals("Patrick", eligibleStudents.get(1));
        assertEquals(1, lp.getStudents().size());
        assertEquals("Squidward", lp.getStudents().get(0).getName());
    }

    @Test
    public void testStudentsNeededToLearnNoCorrespondingLesson() {
        lp.addLesson("Teamfighting", 60, new ArrayList<String>());
        lp.addLesson("Ward Hopping", 90, new ArrayList<String>());
        lp.addStudent("Darius");
        lp.addStudent("Draven");
        lp.addTeacher("Riven", new ArrayList<String>());
        assertEquals(0, lp.studentsNeededToLearn("Flashing Mastery").size());
    }

    @Test
    public void testStudentsNeededToLearnNoStudents() {
        lp.addLesson("Teamfighting", 60, new ArrayList<String>());
        lp.addLesson("Ward Hopping", 90, new ArrayList<String>());
        lp.addStudent("Darius");
        lp.addStudent("Draven");
        lp.addTeacher("Riven", new ArrayList<String>());
        List<String> listOfStudents = new ArrayList<String>();
        listOfStudents.add("Darius");
        listOfStudents.add("Draven");
        try {
            lp.teachLesson("Teamfighting", "Riven", listOfStudents, 420, "07/27/1914");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }
        assertEquals(0, lp.studentsNeededToLearn("Teamfighting").size());
    }

    @Test
    public void testStudentsNeededToLearnSomeStudents() {
        lp.addLesson("Teamfighting", 60, new ArrayList<String>());
        lp.addLesson("Ward Hopping", 90, new ArrayList<String>());
        lp.addStudent("Darius");
        lp.addStudent("Draven");
        lp.addTeacher("Riven", new ArrayList<String>());
        List<String> listOfStudents = new ArrayList<String>();
        listOfStudents.add("Draven");
        try {
            lp.teachLesson("Teamfighting", "Riven", listOfStudents, 100, "07/27/1914");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }
        List<String> studentsToLearnTeamfighting = lp.studentsNeededToLearn("Teamfighting");
        assertEquals(1, studentsToLearnTeamfighting.size());
        assertEquals("Darius", studentsToLearnTeamfighting.get(0));
    }

    @Test
    public void testStudentsNeededToLearnAllStudents() {
        lp.addLesson("Teamfighting", 60, new ArrayList<String>());
        lp.addLesson("Ward Hopping", 90, new ArrayList<String>());
        lp.addStudent("Darius");
        lp.addStudent("Draven");
        List<String> studentsToLearnTeamfighting = lp.studentsNeededToLearn("Teamfighting");
        assertEquals(2, studentsToLearnTeamfighting.size());
        assertEquals("Darius", studentsToLearnTeamfighting.get(0));
        assertEquals("Draven", studentsToLearnTeamfighting.get(1));
    }

    @Test
    public void testTeachLessonTeacherDoesNotExistException() {
        lp.addLesson("Flanking", 120, new ArrayList<String>());
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Gnar");
        students.add("Gangplank");

        try {
            lp.teachLesson("Flanking", "Zyra", students, 4000, "10/09/1444");
            fail("Should have thrown TeacherDoesNotExistException");
        } catch (TeacherDoesNotExistException e) {
            // Should be caught here
        } catch (LessonDoesNotExistException e) {
            fail("Should have thrown TeacherDoesNotExistException");
        } catch (NotEnoughTimeToTeachException e) {
            fail("Should have thrown TeacherDoesNotExistException");
        } catch (TeacherNotCertifiedToTeachException e) {
            fail("Should have thrown TeacherDoesNotExistException");
        }

        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(2).getLessonsToLearn().size());
    }

    @Test
    public void testTeachLessonLessonDoesNotExistException() {
        lp.addLesson("Flanking", 120, new ArrayList<String>());
        lp.addTeacher("Zyra", new ArrayList<String>());
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Gnar");
        students.add("Gangplank");

        try {
            lp.teachLesson("Planting Flowers", "Zyra", students, 4000, "10/09/1444");
            fail("Should have thrown LessonDoesNotExistException");
        } catch (TeacherDoesNotExistException e) {
            fail("Should have thrown LessonDoesNotExistException");
        } catch (LessonDoesNotExistException e) {
            // Should be caught here
        } catch (NotEnoughTimeToTeachException e) {
            fail("Should have thrown LessonDoesNotExistException");
        } catch (TeacherNotCertifiedToTeachException e) {
            fail("Should have thrown LessonDoesNotExistException");
        }

        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(2).getLessonsToLearn().size());
    }

    @Test
    public void testTeachLessonNotEnoughTimeToTeachException() {
        lp.addLesson("Flanking", 120, new ArrayList<String>());
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        lp.addTeacher("Lee Sin", new ArrayList<String>());
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Gnar");
        students.add("Gangplank");

        try {
            lp.teachLesson("Flanking", "Lee Sin", students, 119, "10/09/1444");
            fail("Should have thrown NotEnoughTimeToTeachException");
        } catch (TeacherDoesNotExistException e) {
            fail("Should have thrown NotEnoughTimeToTeachException");
        } catch (LessonDoesNotExistException e) {
            fail("Should have thrown NotEnoughTimeToTeachException");
        } catch (NotEnoughTimeToTeachException e) {
            // Should be caught here
        } catch (TeacherNotCertifiedToTeachException e) {
            fail("Should have thrown NotEnoughTimeToTeachException");
        }

        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(2).getLessonsToLearn().size());
    }

    @Test
    public void testTeachLessonTeacherNotQualifiedException() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Patience");
        lp.addLesson("Flanking", 120, requirements);
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        List<String> certifications = new ArrayList<String>();
        certifications.add("Muay Thai");
        lp.addTeacher("Lee Sin", certifications);
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Gnar");
        students.add("Gangplank");

        try {
            lp.teachLesson("Flanking", "Lee Sin", students, 4000, "10/09/1444");
            fail("Should have thrown TeacherNotCertifiedToTeachException");
        } catch (TeacherDoesNotExistException e) {
            fail("Should have thrown TeacherNotCertifiedToTeachException");
        } catch (LessonDoesNotExistException e) {
            fail("Should have thrown TeacherNotCertifiedToTeachException");
        } catch (NotEnoughTimeToTeachException e) {
            fail("Should have thrown TeacherNotCertifiedToTeachException");
        } catch (TeacherNotCertifiedToTeachException e) {
            // Should be caught here
        }

        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(2).getLessonsToLearn().size());
    }

    @Test
    public void testTeachLessonNoNewStudentsJustEnoughTime() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Muay Thai");
        lp.addLesson("Flanking", 120, requirements);
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        List<String> certifications = new ArrayList<String>();
        certifications.add("Muay Thai");
        lp.addTeacher("Lee Sin", certifications);
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Gnar");

        try {
            lp.teachLesson("Flanking", "Lee Sin", students, 120, "10/09/1444");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }

        assertEquals(0, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(0, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(2).getLessonsToLearn().size());

        assertEquals(1, lp.getStudents().get(0).getAttendance().size());
        assertEquals(1, lp.getStudents().get(1).getAttendance().size());
        assertEquals(0, lp.getStudents().get(2).getAttendance().size());

        assertEquals("Flanking", lp.getStudents().get(0).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(0).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(0).getAttendance().get(0).getDate());
        assertEquals("Flanking", lp.getStudents().get(1).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(1).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(1).getAttendance().get(0).getDate());
    }

    @Test
    public void testTeachLessonTrueNoNewStudentsExtraTime() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Muay Thai");
        lp.addLesson("Flanking", 120, requirements);
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        List<String> certifications = new ArrayList<String>();
        certifications.add("Muay Thai");
        lp.addTeacher("Lee Sin", certifications);
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Gnar");

        try {
            lp.teachLesson("Flanking", "Lee Sin", students, 121, "10/09/1444");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }

        assertEquals(0, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(0, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(2).getLessonsToLearn().size());

        assertEquals(1, lp.getStudents().get(0).getAttendance().size());
        assertEquals(1, lp.getStudents().get(1).getAttendance().size());
        assertEquals(0, lp.getStudents().get(2).getAttendance().size());

        assertEquals("Flanking", lp.getStudents().get(0).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(0).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(0).getAttendance().get(0).getDate());
        assertEquals("Flanking", lp.getStudents().get(1).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(1).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(1).getAttendance().get(0).getDate());
    }

    @Test
    public void testTeachLessonTrueMultipleLessonsInLessonPlan() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Muay Thai");
        lp.addLesson("Flanking", 120, requirements);
        lp.addLesson("Crowd Controlling", 90, new ArrayList<String>());
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        List<String> certifications = new ArrayList<String>();
        certifications.add("Muay Thai");
        lp.addTeacher("Lee Sin", certifications);
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Gnar");

        try {
            lp.teachLesson("Flanking", "Lee Sin", students, 121, "10/09/1444");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }

        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(2, lp.getStudents().get(2).getLessonsToLearn().size());

        assertEquals(1, lp.getStudents().get(0).getAttendance().size());
        assertEquals(1, lp.getStudents().get(1).getAttendance().size());
        assertEquals(0, lp.getStudents().get(2).getAttendance().size());

        assertEquals("Flanking", lp.getStudents().get(0).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(0).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(0).getAttendance().get(0).getDate());
        assertEquals("Flanking", lp.getStudents().get(1).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(1).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(1).getAttendance().get(0).getDate());
    }

    @Test
    public void testTeachLessonTrueMultipleTeachingsOfSameLessonTestingTeachingEntry() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Muay Thai");
        lp.addLesson("Flanking", 120, requirements);
        lp.addLesson("Crowd Controlling", 90, new ArrayList<String>());
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        List<String> certifications = new ArrayList<String>();
        certifications.add("Muay Thai");
        lp.addTeacher("Lee Sin", certifications);
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Gnar");

        try {
            lp.teachLesson("Flanking", "Lee Sin", students, 121, "10/09/1444");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }

        List<String> newStudents = new ArrayList<String>();
        newStudents.add("Gangplank");
        newStudents.add("Azir");

        try {
            lp.teachLesson("Flanking", "Lee Sin", newStudents, 121, "10/10/1444");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }

        assertEquals(2, lp.getTeachingHistory().size());

        assertEquals("Flanking", lp.getTeachingHistory().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getTeachingHistory().get(0).getTeacherName());
        assertEquals(2, lp.getTeachingHistory().get(0).getStudentNames().size());
        assertEquals("Azir", lp.getTeachingHistory().get(0).getStudentNames().get(0));
        assertEquals("Gnar", lp.getTeachingHistory().get(0).getStudentNames().get(1));
        assertEquals("10/09/1444", lp.getTeachingHistory().get(0).getDate());

        assertEquals("Flanking", lp.getTeachingHistory().get(1).getLessonTitle());
        assertEquals("Lee Sin", lp.getTeachingHistory().get(1).getTeacherName());
        assertEquals(2, lp.getTeachingHistory().get(1).getStudentNames().size());
        assertEquals("Gangplank", lp.getTeachingHistory().get(1).getStudentNames().get(0));
        assertEquals("Azir", lp.getTeachingHistory().get(1).getStudentNames().get(1));
        assertEquals("10/10/1444", lp.getTeachingHistory().get(1).getDate());
    }

    @Test
    public void testTeachLessonTrueMultipleTeachingsOfSameLesson() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Muay Thai");
        lp.addLesson("Flanking", 120, requirements);
        lp.addLesson("Crowd Controlling", 90, new ArrayList<String>());
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        List<String> certifications = new ArrayList<String>();
        certifications.add("Muay Thai");
        lp.addTeacher("Lee Sin", certifications);
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Gnar");

        try {
            lp.teachLesson("Flanking", "Lee Sin", students, 121, "10/09/1444");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }

        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(2, lp.getStudents().get(2).getLessonsToLearn().size());

        assertEquals(1, lp.getStudents().get(0).getAttendance().size());
        assertEquals(1, lp.getStudents().get(1).getAttendance().size());
        assertEquals(0, lp.getStudents().get(2).getAttendance().size());

        assertEquals("Flanking", lp.getStudents().get(0).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(0).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(0).getAttendance().get(0).getDate());
        assertEquals("Flanking", lp.getStudents().get(1).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(1).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(1).getAttendance().get(0).getDate());

        List<String> newStudents = new ArrayList<String>();
        newStudents.add("Gangplank");
        newStudents.add("Azir");

        try {
            lp.teachLesson("Flanking", "Lee Sin", newStudents, 121, "10/10/1444");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }

        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(2).getLessonsToLearn().size());

        assertEquals(1, lp.getStudents().get(0).getAttendance().size());
        assertEquals(1, lp.getStudents().get(1).getAttendance().size());
        assertEquals(1, lp.getStudents().get(2).getAttendance().size());

        assertEquals("Flanking", lp.getStudents().get(0).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(0).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(0).getAttendance().get(0).getDate());
        assertEquals("Flanking", lp.getStudents().get(2).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(2).getAttendance().get(0).getTeacherName());
        assertEquals("10/10/1444", lp.getStudents().get(2).getAttendance().get(0).getDate());
    }

    @Test
    public void testTeachLessonTrueMakeNewStudent() {
        List<String> requirements = new ArrayList<String>();
        requirements.add("Muay Thai");
        lp.addLesson("Flanking", 120, requirements);
        lp.addLesson("Whining", 15, new ArrayList<String>());
        lp.addStudent("Azir");
        lp.addStudent("Gnar");
        lp.addStudent("Gangplank");
        List<String> certifications = new ArrayList<String>();
        certifications.add("Muay Thai");
        lp.addTeacher("Lee Sin", certifications);
        List<String> students = new ArrayList<String>();
        students.add("Azir");
        students.add("Irelia");

        try {
            lp.teachLesson("Flanking", "Lee Sin", students, 121, "10/09/1444");
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }

        assertEquals(4, lp.getStudents().size());
        assertEquals("Irelia", lp.getStudents().get(3).getName());

        assertEquals(1, lp.getStudents().get(0).getLessonsToLearn().size());
        assertEquals(2, lp.getStudents().get(1).getLessonsToLearn().size());
        assertEquals(2, lp.getStudents().get(2).getLessonsToLearn().size());
        assertEquals(1, lp.getStudents().get(3).getLessonsToLearn().size());

        assertEquals(1, lp.getStudents().get(0).getAttendance().size());
        assertEquals(0, lp.getStudents().get(1).getAttendance().size());
        assertEquals(0, lp.getStudents().get(2).getAttendance().size());
        assertEquals(1, lp.getStudents().get(3).getLessonsToLearn().size());

        assertEquals("Flanking", lp.getStudents().get(0).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(0).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(0).getAttendance().get(0).getDate());
        assertEquals("Flanking", lp.getStudents().get(3).getAttendance().get(0).getLessonTitle());
        assertEquals("Lee Sin", lp.getStudents().get(3).getAttendance().get(0).getTeacherName());
        assertEquals("10/09/1444", lp.getStudents().get(3).getAttendance().get(0).getDate());
    }

    @Test
    public void testIsLessonTeachableTeacherDoesNotExistException() {
        List<String> certifications = new ArrayList<String>();
        certifications.add("Almighty");
        certifications.add("Literally God");
        lp.addTeacher("God", certifications);
        lp.addLesson("Smiting Evil", 2, new ArrayList<String>());

        try {
            lp.isLessonTeachable(lp.findTeacher("Satan"), lp.findLesson("Smiting Evil"), 1000000);
            fail("Should have thrown TeacherDoesNotExistException");
        } catch (TeacherDoesNotExistException e) {
            // Should be caught here
        } catch (LessonDoesNotExistException e) {
            fail("Should have thrown TeacherDoesNotExistException");
        } catch (NotEnoughTimeToTeachException e) {
            fail("Should have thrown TeacherDoesNotExistException");
        } catch (TeacherNotCertifiedToTeachException e) {
            fail("Should have thrown TeacherDoesNotExistException");
        }
    }

    @Test
    public void testIsLessonTeachableLessonDoesNotExistException() {
        List<String> certifications = new ArrayList<String>();
        certifications.add("Almighty");
        certifications.add("Literally God");
        lp.addTeacher("God", certifications);
        lp.addLesson("Smiting Evil", 2, new ArrayList<String>());

        try {
            lp.isLessonTeachable(lp.findTeacher("God"), lp.findLesson("Being Evil >:)"), 1000000);
            fail("Should have thrown LessonDoesNotExistException");
        } catch (TeacherDoesNotExistException e) {
            fail("Should have thrown LessonDoesNotExistException");
        } catch (LessonDoesNotExistException e) {
            // Should be caught here
        } catch (NotEnoughTimeToTeachException e) {
            fail("Should have thrown LessonDoesNotExistException");
        } catch (TeacherNotCertifiedToTeachException e) {
            fail("Should have thrown LessonDoesNotExistException");
        }
    }

    @Test
    public void testIsLessonTeachableNotEnoughTimeException() {
        List<String> certifications = new ArrayList<String>();
        certifications.add("Almighty");
        certifications.add("Literally God");
        lp.addTeacher("God", certifications);
        lp.addLesson("Smiting Evil", 2, new ArrayList<String>());

        try {
            lp.isLessonTeachable(lp.findTeacher("God"), lp.findLesson("Smiting Evil"), -1);
            fail("Should have thrown NotEnoughTimeException");
        } catch (TeacherDoesNotExistException e) {
            fail("Should have thrown NotEnoughTimeException");
        } catch (LessonDoesNotExistException e) {
            fail("Should have thrown NotEnoughTimeException");
        } catch (NotEnoughTimeToTeachException e) {
            // Should be caught here
        } catch (TeacherNotCertifiedToTeachException e) {
            fail("Should have thrown NotEnoughTimeException");
        }
    }

    @Test
    public void testIsLessonTeachableTeacherNotCertifiedException() {
        List<String> certifications = new ArrayList<String>();
        certifications.add("Almighty");
        certifications.add("Literally God");
        lp.addTeacher("God", certifications);
        List<String> requirements = new ArrayList<String>();
        requirements.add("Cursed");
        lp.addLesson("Smiting Evil", 2, requirements);

        try {
            lp.isLessonTeachable(lp.findTeacher("God"), lp.findLesson("Smiting Evil"), 100000000);
            fail("Should have thrown TeacherNotCertifiedException");
        } catch (TeacherDoesNotExistException e) {
            fail("Should have thrown TeacherNotCertifiedException");
        } catch (LessonDoesNotExistException e) {
            fail("Should have thrown TeacherNotCertifiedException");
        } catch (NotEnoughTimeToTeachException e) {
            fail("Should have thrown TeacherNotCertifiedException");
        } catch (TeacherNotCertifiedToTeachException e) {
            // Should be caught here
        }
    }

    @Test
    public void testIsLessonTeachableTrue() {
        List<String> certifications = new ArrayList<String>();
        certifications.add("Almighty");
        certifications.add("Literally God");
        lp.addTeacher("God", certifications);
        lp.addLesson("Smiting Evil", 2, new ArrayList<String>());

        try {
            lp.isLessonTeachable(lp.findTeacher("God"), lp.findLesson("Smiting Evil"), 1000000);
        } catch (TeachLessonErrorException e) {
            fail("No exception should have been thrown");
        }
    }

    public void isLessonTeachable(Teacher teacherObject, Lesson lessonObject, int teachingTime)
            throws TeacherDoesNotExistException, LessonDoesNotExistException,
            NotEnoughTimeToTeachException, TeacherNotCertifiedToTeachException {
        if (teacherObject == null) {
            throw new TeacherDoesNotExistException();
        } else if (lessonObject == null) {
            throw new LessonDoesNotExistException();
        } else if (teachingTime < lessonObject.getTimeToTeach()) {
            throw new NotEnoughTimeToTeachException();
        } else if (!teacherObject.isCertifiedToTeach(lessonObject.getRequirements())) {
            throw new TeacherNotCertifiedToTeachException();
        }
    }

}
