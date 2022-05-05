package ui;

import exceptions.LessonDoesNotExistException;
import exceptions.NotEnoughTimeToTeachException;
import exceptions.TeacherDoesNotExistException;
import exceptions.TeacherNotCertifiedToTeachException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// a lesson planner helper to assist lesson planner
public class LessonPlannerHelper {

    private static final String JSON_STORE = "./data/lessonPlanner.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private LessonPlan lp;
    private Scanner input;

    private static final String STUDENT_COMMAND = "student";
    private static final String PRINT_STUDENTS_COMMAND = "print students";
    private static final String ADD_STUDENT_COMMAND = "add student";
    private static final String REMOVE_STUDENT_COMMAND = "remove student";
    private static final String GRADUATE_ELIGIBLE_STUDENTS_COMMAND = "graduate eligible students";
    private static final String TIME_LEFT_TO_LEARN_COMMAND = "time left to learn";

    private static final String TEACHER_COMMAND = "teacher";
    private static final String PRINT_TEACHERS_COMMAND = "print teachers";
    private static final String ADD_TEACHER_COMMAND = "add teacher";
    private static final String REMOVE_TEACHER_COMMAND = "remove teacher";
    private static final String IS_CERTIFIED_TO_TEACH_COMMAND = "is certified to teach";
    private static final String GAIN_CERTIFICATION_COMMAND = "gain certification";

    private static final String LESSON_COMMAND = "lesson";
    private static final String PRINT_LESSONS_COMMAND = "print lessons";
    private static final String ADD_LESSON_COMMAND = "add lesson";
    private static final String REMOVE_LESSON_COMMAND = "remove lesson";
    private static final String STUDENTS_NEEDED_TO_LEARN_COMMAND = "students needed to learn";
    private static final String TEACH_LESSON_COMMAND = "teach lesson";
    private static final String PRINT_TEACHING_HISTORY_COMMAND = "print teaching history";

    private static final String DATA_COMMAND = "data";
    private static final String LOAD_COMMAND = "load";
    private static final String SAVE_COMMAND = "save";

    private static final String QUIT_COMMAND = "quit";
    private static final String BACK_COMMAND = "back";
    private static final String STOP_COMMAND = "*";

    // EFFECTS: creates a lesson planner helper with a lesson plan stored and input scanner instantiated
    public LessonPlannerHelper() throws FileNotFoundException {
        printDividingLine();
        System.out.println("Welcome to the Lesson Planner! What should the title for the Lesson Planner be?");
        printDividingLine();

        this.input = new Scanner(System.in);
        this.lp = new LessonPlan(getStringUserInput());
        this.jsonReader = new JsonReader(JSON_STORE);
        this.jsonWriter = new JsonWriter(JSON_STORE);

        chooseSubjectOptions();

        System.out.println("Thank you for trying the Lesson Planner!");
        printDividingLine();
    }

    // EFFECTS: prints subject options, and lets user choose what category to access
    public void chooseSubjectOptions() {
        printSubjectOptions();
        handleSubjectOptions();
    }

    // EFFECTS: prints user to choose between lesson, student, and teacher options, or quit
    public void printSubjectOptions() {
        System.out.println("What would you like to access?");
        printCommand(LESSON_COMMAND, "access lesson commands");
        printCommand(STUDENT_COMMAND, "access student commands");
        printCommand(TEACHER_COMMAND, "access teacher commands");
        printCommand(DATA_COMMAND, "access data commands");
        printCommand(QUIT_COMMAND, "quit");
        printDividingLine();
    }

    // EFFECTS: allows user to choose between lesson, student, and teacher options, or quit
    public void handleSubjectOptions() {
        String response = getStringUserInput();

        if (!response.equals(QUIT_COMMAND)) {
            switch (response) {
                case LESSON_COMMAND:
                    chooseLessonOptions();
                    break;
                case STUDENT_COMMAND:
                    chooseStudentOptions();
                    break;
                case TEACHER_COMMAND:
                    chooseTeacherOptions();
                    break;
                case DATA_COMMAND:
                    chooseDataOptions();
                    break;
                default:
                    System.out.println("I did not understand what category you wanted. Please try again.");
                    handleSubjectOptions();
                    break;
            }
        }
    }

    // EFFECTS: prints lesson options, and lets user choose what command to do
    public void chooseLessonOptions() {
        printLessonOptions();
        handleLessonOptions();
    }

    // EFFECTS: prints user all commands related to lessons
    public void printLessonOptions() {
        System.out.println("What lesson command would you like to do?");
        printCommand(PRINT_LESSONS_COMMAND, "print all lessons");
        printCommand(ADD_LESSON_COMMAND, "add a lesson to the lesson plan");
        printCommand(REMOVE_LESSON_COMMAND, "remove a lesson from the lesson plan");
        printCommand(STUDENTS_NEEDED_TO_LEARN_COMMAND, "see which students need to a learn a certain lesson");
        printCommand(TEACH_LESSON_COMMAND, "teach a lesson");
        printCommand(PRINT_TEACHING_HISTORY_COMMAND, "print teaching history");
        printCommand(BACK_COMMAND, "go back");
        printDividingLine();
    }

    // EFFECTS: allows user to choose what lesson command to run
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void handleLessonOptions() {
        switch (getStringUserInput()) {
            case PRINT_LESSONS_COMMAND:
                handlePrintLessons();
                break;
            case ADD_LESSON_COMMAND:
                handleAddLesson();
                break;
            case REMOVE_LESSON_COMMAND:
                handleRemoveLesson();
                break;
            case STUDENTS_NEEDED_TO_LEARN_COMMAND:
                handleStudentsNeededToLearn();
                break;
            case TEACH_LESSON_COMMAND:
                handleTeachLesson();
                break;
            case PRINT_TEACHING_HISTORY_COMMAND:
                handlePrintTeachingHistory();
                break;
            case BACK_COMMAND:
                chooseSubjectOptions();
                break;
            default:
                System.out.println("I did not understand what lesson command you wanted. Please try again.");
                handleLessonOptions();
                break;
        }
    }

    // EFFECTS: prints all lesson information and sends user back to lesson options menu
    public void handlePrintLessons() {
        printLessons();
        chooseLessonOptions();
    }

    // MODIFIES: lp
    // EFFECTS: adds a lesson to lp and sends user back to lesson options menu
    public void handleAddLesson() {
        System.out.println("What is the title of the lesson that you want to add?");
        String title = getStringUserInput();
        while (lp.findLesson(title) != null) {
            System.out.println("Please add a title that is not the same as another existing title.");
            title = getStringUserInput();
        }

        System.out.println("What is the time (in minutes) it takes to complete this lesson?");
        int time = getTimeUserInput();

        System.out.println("What is the list of requirements for the lesson");
        List<String> requirements = getListOfStringUserInput();

        lp.addLesson(title, time, requirements);

        System.out.println("New lesson has been added!");
        System.out.println("Title: " + title);
        System.out.println("Time: " + time);
        printListOfString("Requirements", requirements);
        printDividingLine();
        chooseLessonOptions();
    }

    // MODIFIES: lp
    // EFFECTS: removes a lesson from lp and sends user back to lesson options menu
    public void handleRemoveLesson() {
        System.out.println("What is the title of the lesson that you want to remove?");
        String title = getStringUserInput();
        if (lp.removeLesson(title)) {
            System.out.println("Lesson has been successfully removed from the lesson plan");
        } else {
            System.out.println("There is no lesson with the title of " + title + ". No lesson has been removed.");
        }
        printDividingLine();
        chooseLessonOptions();
    }

    // MODIFIES: lp
    // EFFECTS: shows what students need to learn a certain lesson and sends user back to lesson options menu
    public void handleStudentsNeededToLearn() {
        System.out.println("What lesson do you want to check?");
        String title = getStringUserInput();
        if (lp.findLesson(title) != null) {
            printListOfString("Students that need to learn " + title, lp.studentsNeededToLearn(title));
        } else {
            System.out.println("That lesson does not exist.");
        }
        printDividingLine();
        chooseLessonOptions();
    }

    // MODIFIES: lp
    // EFFECTS: teaches a lesson and sends user back to lesson options menu
    public void handleTeachLesson() {
        System.out.println("What lesson do you want to teach?");
        String lessonTitle = getStringUserInput();
        System.out.println("Which teacher do you want to assign to teach?");
        String teacherName = getStringUserInput();
        System.out.println("Which students do you want to teach?");
        List<String> studentNames = getListOfStringUserInput();
        System.out.println("How much time (in minutes) is allowed to teach the lesson?");
        int time = getTimeUserInput();
        String date = getDateUserInput();

        try {
            lp.teachLesson(lessonTitle, teacherName, studentNames, time, date);
            printTeach(lessonTitle, teacherName, studentNames, time, date);
        } catch (TeacherDoesNotExistException e) {
            System.out.println("Lesson could not be taught, because no teacher named " + teacherName + " exists!");
        } catch (LessonDoesNotExistException e) {
            System.out.println("Lesson could not be taught, because no lesson titled " + lessonTitle + " exists!");
        } catch (NotEnoughTimeToTeachException e) {
            System.out.println("Lesson could not be taught, because there is not enough time to teach!");
        } catch (TeacherNotCertifiedToTeachException e) {
            System.out.println("Lesson could not be taught, because " + teacherName + " is not qualified to teach!");
        }

        printDividingLine();
        chooseLessonOptions();
    }

    // EFFECTS: prints a formatted String of the teaching
    public void printTeach(String lessonTitle, String teacherName, List<String> studentNames, int time, String date) {
        System.out.println("Lesson has been successfully taught!");
        System.out.println("Lesson: " + lessonTitle);
        System.out.println("Teacher: " + teacherName);
        printListOfString("Students Attended", studentNames);
        System.out.println("Time: " + time);
        System.out.println("Date: " + date);
    }

    // EFFECTS: prints teaching history
    public void handlePrintTeachingHistory() {
        printTeachingHistory();
        chooseLessonOptions();
    }

    // EFFECTS: prints student options, and lets user choose what command to do
    public void chooseStudentOptions() {
        printStudentOptions();
        handleStudentOptions();
    }

    // EFFECTS: prints user all commands related to students
    public void printStudentOptions() {
        System.out.println("What student command would you like to do?");
        printCommand(PRINT_STUDENTS_COMMAND, "print all students");
        printCommand(ADD_STUDENT_COMMAND, "add a student to the lesson plan");
        printCommand(REMOVE_STUDENT_COMMAND, "remove a student from the lesson plan");
        printCommand(GRADUATE_ELIGIBLE_STUDENTS_COMMAND, "graduate all eligible students");
        printCommand(TIME_LEFT_TO_LEARN_COMMAND, "see the hours a student has left of learning");
        printCommand(BACK_COMMAND, "go back");
        printDividingLine();
    }

    // EFFECTS: allows user to choose what student command to run
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void handleStudentOptions() {
        switch (getStringUserInput()) {
            case PRINT_STUDENTS_COMMAND:
                handlePrintStudents();
                break;
            case ADD_STUDENT_COMMAND:
                handleAddStudent();
                break;
            case REMOVE_STUDENT_COMMAND:
                handleRemoveStudent();
                break;
            case GRADUATE_ELIGIBLE_STUDENTS_COMMAND:
                handleGraduateEligibleStudents();
                break;
            case TIME_LEFT_TO_LEARN_COMMAND:
                handleTimeLeftToLearn();
                break;
            case BACK_COMMAND:
                chooseSubjectOptions();
                break;
            default:
                System.out.println("I did not understand what student command you wanted. Please try again.");
                handleStudentOptions();
                break;
        }
    }

    // EFFECTS: prints all students and sends user back to students option menu
    public void handlePrintStudents() {
        printStudents();
        chooseStudentOptions();
    }

    // MODIFIES: lp
    // EFFECTS: adds a student to lp and sends user back to students option menu
    public void handleAddStudent() {
        System.out.println("What is the name of the student that you want to add?");
        String name = getStringUserInput();
        while (lp.findStudent(name) != null) {
            System.out.println("Please add a name that is not the same as another existing student.");
            name = getStringUserInput();
        }

        lp.addStudent(name);

        System.out.println("New student has been added!");
        System.out.println("Name: " + name);
        printDividingLine();
        chooseStudentOptions();
    }

    // MODIFIES: lp
    // EFFECTS: removes a student from lp and sends user back to students option menu
    public void handleRemoveStudent() {
        System.out.println("What is the name of the student that you want to remove?");
        String name = getStringUserInput();
        if (lp.removeStudent(name)) {
            System.out.println("Student has been successfully removed from the lesson plan.");
        } else {
            System.out.println("There is no student with the name of " + name + ". No student has been removed.");
        }
        printDividingLine();
        chooseStudentOptions();
    }

    // MODIFIES: lp
    // EFFECTS: graduates all eligible students in lp and sends user back to students option menu
    public void handleGraduateEligibleStudents() {
        printListOfString("Graduated Students", lp.graduateEligibleStudents());
        printDividingLine();
        chooseStudentOptions();
    }

    // MODIFIES: lp
    // EFFECTS: shows how much time a student has left to learn and sends user back to students option menu
    public void handleTimeLeftToLearn() {
        System.out.println("What is the name of the student that you want to check?");
        String title = getStringUserInput();
        Student student = lp.findStudent(title);
        if (student == null) {
            System.out.println("No student exists with the name of " + title + ".");
        } else {
            System.out.println(title + " has " + student.timeLeftToLearn() + " minutes of learning left");
        }
        printDividingLine();
        chooseStudentOptions();
    }

    // EFFECTS: prints teacher options, and lets user choose what command to do
    public void chooseTeacherOptions() {
        printTeacherOptions();
        handleTeacherOptions();
    }

    // EFFECTS: prints user all commands related to teachers
    public void printTeacherOptions() {
        System.out.println("What teacher command would you like to do?");
        printCommand(PRINT_TEACHERS_COMMAND, "print all teachers");
        printCommand(ADD_TEACHER_COMMAND, "add a teacher to the lesson plan");
        printCommand(REMOVE_TEACHER_COMMAND, "remove a teacher from the lesson plan");
        printCommand(IS_CERTIFIED_TO_TEACH_COMMAND, "see if a teacher is certified to teach a certain lesson");
        printCommand(GAIN_CERTIFICATION_COMMAND, "give a teacher a new certification");
        printCommand(BACK_COMMAND, "go back");
        printDividingLine();
    }

    // EFFECTS: allows user to choose what teacher command to run
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void handleTeacherOptions() {
        switch (getStringUserInput()) {
            case PRINT_TEACHERS_COMMAND:
                handlePrintTeachers();
                break;
            case ADD_TEACHER_COMMAND:
                handleAddTeacher();
                break;
            case REMOVE_TEACHER_COMMAND:
                handleRemoveTeacher();
                break;
            case IS_CERTIFIED_TO_TEACH_COMMAND:
                handleIsCertifiedToTeach();
                break;
            case GAIN_CERTIFICATION_COMMAND:
                handleGainCertification();
                break;
            case BACK_COMMAND:
                chooseSubjectOptions();
                break;
            default:
                System.out.println("I did not understand what teacher command you wanted. Please try again.");
                handleTeacherOptions();
                break;
        }
    }

    // EFFECTS: prints all teachers then sends user back to teachers option menu
    public void handlePrintTeachers() {
        printTeachers();
        chooseTeacherOptions();
    }

    // MODIFIES: lp
    // EFFECTS: adds a teacher to lp and sends user back to teachers option menu
    public void handleAddTeacher() {
        System.out.println("What is the name of the teacher that you want to add?");
        String name = getStringUserInput();
        while (lp.findTeacher(name) != null) {
            System.out.println("Please add a name that is not the same as another existing teacher.");
            name = getStringUserInput();
        }

        System.out.println("What is the list of certifications for this teacher?");
        List<String> certificates = getListOfStringUserInput();

        lp.addTeacher(name, certificates);

        System.out.println("New teacher has been added!");
        System.out.println("Name: " + name);
        printListOfString("Certificates", certificates);
        printDividingLine();
        chooseTeacherOptions();
    }

    // MODIFIES: lp
    // EFFECTS: removes a teacher from lp and sends user back to teachers option menu
    public void handleRemoveTeacher() {
        System.out.println("What is the name of the teacher that you want to remove?");
        String name = getStringUserInput();
        if (lp.removeTeacher(name)) {
            System.out.println("Teacher has been successfully removed from the lesson plan");
        } else {
            System.out.println("There is no teacher with the name of " + name + ". No teacher has been removed.");
        }
        printDividingLine();
        chooseTeacherOptions();
    }

    // MODIFIES: lp
    // EFFECTS: shows if a teacher is certified to teach a certain lesson and sends user back to teachers option menu
    public void handleIsCertifiedToTeach() {
        System.out.println("What is the name of the teacher that you want to check?");
        String name = getStringUserInput();
        Teacher teacher = lp.findTeacher(name);
        if (teacher == null) {
            System.out.println("No teacher exists with the name of " + name);
        } else {
            System.out.println("What lesson do you want to test?");
            String title = getStringUserInput();
            Lesson lesson = lp.findLesson(title);
            if (lesson == null) {
                System.out.println("No lesson exists with the title of " + title);
            } else {
                if (teacher.isCertifiedToTeach(lesson.getRequirements())) {
                    System.out.println(name + " can teach " + title + ".");
                } else {
                    System.out.println(name + " cannot teach " + title + ".");
                }
            }
        }
        printDividingLine();
        chooseTeacherOptions();
    }

    // MODIFIES: lp
    // EFFECTS: adds a certification to a teacher and sends user back to teachers option menu
    public void handleGainCertification() {
        System.out.println("What is the name of the teacher that you want to certify?");
        String name = getStringUserInput();
        Teacher teacher = lp.findTeacher(name);
        if (teacher == null) {
            System.out.println("No teacher exists with the name of " + name);
        } else {
            System.out.println("What certifications do you want to add?");
            List<String> certifications = getListOfStringUserInput();
            for (String certification : certifications) {
                teacher.gainCertification(certification);
            }
            printListOfString("Certifications Added", certifications);
        }

        printDividingLine();
        chooseTeacherOptions();
    }

    // EFFECTS: prints teacher options, and lets user choose what command to do
    public void chooseDataOptions() {
        printDataOptions();
        handleDataOptions();
    }

    // EFFECTS: prints user all commands related to data
    public void printDataOptions() {
        System.out.println("What data command would you like to do?");
        printCommand(LOAD_COMMAND, "load lesson planner data");
        printCommand(SAVE_COMMAND, "save lesson planner data");
        printCommand(BACK_COMMAND, "go back");
        printDividingLine();
    }

    // EFFECTS: allows user to choose what data command to run
    public void handleDataOptions() {
        switch (getStringUserInput()) {
            case LOAD_COMMAND:
                handleLoad();
                break;
            case SAVE_COMMAND:
                handleSave();
                break;
            case BACK_COMMAND:
                chooseSubjectOptions();
                break;
            default:
                System.out.println("I did not understand what data command you wanted. Please try again.");
                handleDataOptions();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads lesson plan from file
    public void handleLoad() {
        try {
            lp = jsonReader.read();
            System.out.println("Loaded " + lp.getLessonPlanTitle() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        printDividingLine();
        chooseSubjectOptions();
    }

    public void handleSave() {
        try {
            this.jsonWriter.open();
            this.jsonWriter.write(this.lp);
            this.jsonWriter.close();
            System.out.println("Saved " + this.lp.getLessonPlanTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        printDividingLine();
        chooseSubjectOptions();
    }

    // EFFECTS: asks for user input until it is not empty, and returns it
    public String getStringUserInput() {
        String response = "";

        while (response.length() == 0) {
            response = input.nextLine();
        }

        return response;
    }

    // EFFECTS: allows user to create a list of strings
    public List<String> getListOfStringUserInput() {
        List<String> listResponse = new ArrayList<String>();
        System.out.println("Add a new item to the list, or type [" + STOP_COMMAND + "] to stop adding to the list.");
        String currentResponse = getStringUserInput();

        while (!currentResponse.equals(STOP_COMMAND)) {
            listResponse.add(currentResponse);
            currentResponse = getStringUserInput();
        }

        return listResponse;
    }

    // EFFECTS: returns time if it is valid, else asks to input another value
    public int getTimeUserInput() {
        int time = input.nextInt();

        while (time <= 0) {
            System.out.println("Please add a valid time (greater than 0).");
            time = input.nextInt();
        }

        return time;
    }

    // EFFECTS: returns date in the form of MM/DD/YYYY, asking for valid year, month, and day
    public String getDateUserInput() {
        System.out.println("What is the year of the date of the lesson?");
        int year = getIntUserInput(0, 9999, "year");
        String yearString = yearIntToString(year);

        System.out.println("What is the month (in numerical form) of the date of the lesson?");
        int month = getIntUserInput(1, 12, "month");
        String monthString = monthIntToString(month);

        System.out.println("What is the day of the date of the lesson?");
        int day = getIntUserInput(1, getHighBasedOnMonthAndYear(month, year), "day");
        String dayString = dayIntToString(day);

        return monthString + "/" + dayString + "/" + yearString;
    }

    // EFFECTS: pads year to be in the form of YYYY
    public String yearIntToString(int year) {
        String yearString = "" + year;
        yearString = padString(yearString, year, 1000);
        yearString = padString(yearString, year, 100);
        yearString = padString(yearString, year, 10);

        return yearString;
    }

    // EFFECTS: pads month to be in the form of MM
    public String monthIntToString(int month) {
        String monthString = "" + month;
        monthString = padString(monthString, month, 10);

        return monthString;
    }

    // EFFECTS: pads day to be in the form of DD
    public String dayIntToString(int day) {
        String dayString = "" + day;
        dayString = padString(dayString, day, 10);

        return dayString;
    }

    // EFFECTS: sees how many days of the month should be allowed based on given month and year
    public int getHighBasedOnMonthAndYear(int month, int year) {
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (year % 4 == 0) {
                return 29;
            }
            return 28;
        }
        return 31;
    }

    // EFFECTS: returns int if it is valid, else asks to input another value
    public int getIntUserInput(int low, int high, String type) {
        int result = input.nextInt();

        while (result < low || result > high) {
            System.out.println("Your " + type + " should be between " + low + " and " + high + ". Please try again.");
            result = input.nextInt();
        }

        return result;
    }

    // EFFECTS: adds "0" to numberAsString if given number is lower than given high
    public String padString(String numberAsString, int number, int high) {
        if (number < high) {
            numberAsString = "0" + numberAsString;
        }
        return numberAsString;
    }

    // EFFECTS: formats and prints command with the given description
    public void printCommand(String command, String description) {
        System.out.println("Type [" + command + "] to " + description + ".");
    }

    // EFFECTS: formats and prints list of string
    public void printListOfString(String header, List<String> listOfString) {
        if (listOfString.size() == 0) {
            System.out.println(header + ": None");
        } else {
            System.out.println(header + ":");
            for (String item : listOfString) {
                System.out.println("- " + item);
            }
        }
    }

    // EFFECTS: formats and prints all lessons' information in the lesson plan
    public void printLessons() {
        System.out.println("ALL LESSONS\n");

        for (Lesson lesson : lp.getLessonsToTeach()) {
            System.out.println("Title: " + lesson.getTitle());

            System.out.println("Minutes to Teach: " + lesson.getTimeToTeach());

            printListOfString("Requirements", lesson.getRequirements());

            System.out.println();
        }

        printDividingLine();
    }

    // EFFECTS: formats and prints all students' information in the lesson plan
    public void printStudents() {
        System.out.println("ALL STUDENTS\n");

        for (Student student : lp.getStudents()) {
            System.out.println("Name: " + student.getName());

            List<String> lessonTitlesToLearn = new ArrayList<String>();
            for (Lesson lessonToLearn : student.getLessonsToLearn()) {
                lessonTitlesToLearn.add(lessonToLearn.getTitle());
            }
            printListOfString("Lessons to Learn", lessonTitlesToLearn);

            List<String> attendances = new ArrayList<String>();
            for (AttendanceEntry attendance : student.getAttendance()) {
                attendances.add(attendance.toString());
            }
            printListOfString("Attendance History", attendances);

            System.out.println();
        }

        printDividingLine();
    }

    // EFFECTS: formats and prints all teachers' information in the lesson plan
    public void printTeachers() {
        System.out.println("ALL TEACHERS\n");

        for (Teacher teacher : lp.getTeachers()) {
            System.out.println("Name: " + teacher.getName());

            printListOfString("Certifications", teacher.getCertifications());

            System.out.println();
        }

        printDividingLine();
    }

    // EFFECTS: prints all teaching entries in teachingHistory
    public void printTeachingHistory() {
        System.out.println("TEACHING HISTORY\n");

        for (TeachingEntry teachingEntry : lp.getTeachingHistory()) {
            System.out.println(teachingEntry);
        }

        printDividingLine();
    }

    // EFFECTS: prints a line
    public void printDividingLine() {
        System.out.println("-----------------------------------------------------------------------------------------");
    }

}
