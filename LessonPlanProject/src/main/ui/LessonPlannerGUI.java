package ui;

import exceptions.LessonDoesNotExistException;
import exceptions.NotEnoughTimeToTeachException;
import exceptions.TeacherDoesNotExistException;
import exceptions.TeacherNotCertifiedToTeachException;
import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LessonPlannerGUI implements ActionListener, WindowListener {

    private LessonPlan lessonPlan;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/lessonPlanner.json";

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel lastActionLabel;
    private JButton addStudentButton;
    private JButton addTeacherButton;
    private JButton addLessonButton;
    private JButton printStudentButton;
    private JButton printTeacherButton;
    private JButton printLessonButton;
    private JButton teachLessonButton;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    private JLabel happyFaces;

    private JLabel addStudentNameLabel;
    private JTextField addStudentNameField;
    private JLabel addStudentLabel;
    private JButton addStudentSubmitButton;
    private JPanel addStudentPanel;

    private JLabel addTeacherNameLabel;
    private JTextField addTeacherNameField;
    private JLabel addCertificationsLabel;
    private JTextField addCertificationsField;
    private JButton addCertificationButton;
    private List<String> certifications;
    private JLabel addTeacherLabel;
    private JButton addTeacherSubmitButton;
    private JPanel addTeacherPanel;

    private JLabel addLessonTitleLabel;
    private JTextField addLessonTitleField;
    private JLabel addRequirementsLabel;
    private JTextField addRequirementsField;
    private JButton addRequirementsButton;
    private List<String> requirements;
    private JLabel addMinutesLabel;
    private JTextField addMinutesField;
    private JLabel addLessonLabel;
    private JButton addLessonSubmitButton;
    private JPanel addLessonPanel;

    private JLabel teachLessonTitleLabel;
    private JTextField teachLessonTitleField;
    private JLabel teachTeacherNameLabel;
    private JTextField teachTeacherNameField;
    private JLabel teachAddStudentsLabel;
    private JTextField teachAddStudentsField;
    private JButton teachAddStudentsButton;
    private List<String> studentsToTeach;
    private JLabel teachTimeToTeachLabel;
    private JTextField teachTimeToTeachField;
    private JLabel teachDayLabel;
    private JTextField teachDayField;
    private JLabel teachMonthLabel;
    private JTextField teachMonthField;
    private JLabel teachYearLabel;
    private JTextField teachYearField;
    private JLabel teachLessonLabel;
    private JButton teachLessonSubmitButton;
    private JPanel teachLessonPanel;

    // EFFECTS: Creates GUI with buttons for all main functions
    public LessonPlannerGUI() {
        lessonPlan = new LessonPlan("Brand Spanking New Lesson Plan!");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        titleLabel = ElementCreator.makeMainLabel("Main Commands");
        lastActionLabel = ElementCreator.makeMainLabel("");

        createMenu();

        createMainComponents();

        addAllToMainPanel();

        mainFrame = ElementCreator.makeMainFrame(lessonPlan.getLessonPlanTitle());
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(mainPanel);
        mainFrame.addWindowListener(this);
        mainFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds components to mainPanel
    private void addAllToMainPanel() {
        mainPanel = ElementCreator.makeListPanel();
        mainPanel.add(titleLabel);
        mainPanel.add(addStudentButton);
        mainPanel.add(addTeacherButton);
        mainPanel.add(addLessonButton);
        mainPanel.add(printStudentButton);
        mainPanel.add(printTeacherButton);
        mainPanel.add(printLessonButton);
        mainPanel.add(teachLessonButton);
        mainPanel.add(happyFaces);
        mainPanel.add(lastActionLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates menu for mainFrame
    private void createMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("Data Options");
        saveItem = new JMenuItem("Save", ElementCreator.makeSmallIcon("data/saveIcon.gif"));
        saveItem.addActionListener(this);
        loadItem = new JMenuItem("Load", ElementCreator.makeSmallIcon("data/loadIcon.gif"));
        loadItem.addActionListener(this);
        menu.add(saveItem);
        menu.add(loadItem);
        menuBar.add(menu);
    }

    // MODIFIES: this
    // EFFECTS: creates all buttons for main frame
    private void createMainComponents() {
        createAddButtons();
        createPrintButtons();
        createTeachButton();
        happyFaces = ElementCreator.makeSubLabel("No Students.");
        happyFaces.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
    }

    // MODIFIES: this
    // EFFECTS: creates teach button for main frame
    private void createTeachButton() {
        String tt = "Click here to teach a lesson.";
        teachLessonButton = ElementCreator.makeMainButton("Teach Lesson", tt, Color.BLACK);
        teachLessonButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: creates print buttons for main frame
    private void createPrintButtons() {
        String tt = "Click here to print students.";
        printStudentButton = ElementCreator.makeMainButton("Print Students", tt, new Color(60, 60, 180));
        printStudentButton.addActionListener(this);

        tt = "Click here to print teachers.";
        printTeacherButton = ElementCreator.makeMainButton("Print Teachers", tt, new Color(180, 60, 60));
        printTeacherButton.addActionListener(this);

        tt = "Click here to print lessons.";
        printLessonButton = ElementCreator.makeMainButton("Print Lessons", tt, new Color(60, 180, 60));
        printLessonButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: creates add buttons for main frame
    private void createAddButtons() {
        String tt = "Click here to add a student to the lesson plan.";
        addStudentButton = ElementCreator.makeMainButton("Add Student", tt, new Color(30, 30, 180));
        addStudentButton.addActionListener(this);

        tt = "Click here to add a teacher to the lesson plan.";
        addTeacherButton = ElementCreator.makeMainButton("Add Teacher", tt, new Color(180, 30, 30));
        addTeacherButton.addActionListener(this);

        tt = "Click here to add a lesson to the lesson plan.";
        addLessonButton = ElementCreator.makeMainButton("Add Lesson", tt, new Color(30, 180, 30));
        addLessonButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: creates window for adding students
    public void makeAddStudentFrame() {
        createAddStudentComponents();

        addAllToAddStudentPanel();

        JFrame addStudentFrame = ElementCreator.makeSubFrame("Add Student", addStudentPanel);
        addStudentFrame.addWindowListener(this);
    }

    // MODIFIES: this
    // EFFECTS: makes all the components needed for addStudentFrame
    private void createAddStudentComponents() {
        addStudentNameField = ElementCreator.makeTextField();
        addStudentNameLabel = ElementCreator.makeSubLabel("Student Name");

        addStudentLabel = ElementCreator.makeSubLabel("Press submit to add a student.");
        addStudentSubmitButton = ElementCreator.makeSubButton("SUBMIT", "Submit the information given.");
        addStudentSubmitButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to addStudentPanel
    private void addAllToAddStudentPanel() {
        addStudentPanel = ElementCreator.makeSubPanel();
        GridBagConstraints constraints = ElementCreator.setupConstraints();
        ElementCreator.addToPanel(addStudentPanel, constraints, addStudentNameLabel, addStudentNameField, 0);
        ElementCreator.addToPanel(addStudentPanel, constraints, addStudentLabel, addStudentSubmitButton, 1);
    }

    // MODIFIES: this
    // EFFECTS: adds student if valid
    public void handleAddStudent() {
        String studentName = addStudentNameField.getText();
        if (studentName.length() == 0) {
            ElementCreator.setErrorText(addStudentLabel, "Student name cannot be empty.");
            ElementCreator.setErrorText(lastActionLabel, "Could not add empty student.");
        } else if (lessonPlan.findStudent(studentName) != null) {
            ElementCreator.setErrorText(addStudentLabel, "Student name already exists.");
            ElementCreator.setErrorText(lastActionLabel, "Could not add duplicate student");
        } else {
            lessonPlan.addStudent(studentName);
            ElementCreator.setWorkedText(addStudentLabel, "Added a student named " + studentName);
            ElementCreator.setWorkedText(lastActionLabel, "Added a new student!");
            setHappyFaces();
        }
        addStudentNameField.setText("");
    }

    // MODIFIES: this
    // EFFECTS: writes the amount of students in the lesson plan and makes a happy face :)
    private void setHappyFaces() {
        happyFaces.setIcon(ElementCreator.makeSmallIcon("data/happyFace.png"));
        int students = lessonPlan.getStudents().size();
        if (students == 1) {
            happyFaces.setText("Hooray! We have " + students + " student!");
        } else {
            happyFaces.setText("Hooray! We have " + students + " students!");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates window for adding teachers
    public void makeAddTeacherFrame() {
        createAddTeacherComponents();

        addAllToAddTeacherPanel();

        JFrame addTeacherFrame = ElementCreator.makeSubFrame("Add Teacher", addTeacherPanel);
        addTeacherFrame.addWindowListener(this);
    }

    // MODIFIES: this
    // EFFECTS: makes all the components needed for addTeacherFrame
    private void createAddTeacherComponents() {
        addTeacherNameLabel = ElementCreator.makeSubLabel("Add the name of the new teacher here.");
        addTeacherNameField = ElementCreator.makeTextField();

        addCertificationsLabel = ElementCreator.makeSubLabel("Add certifications here.");
        addCertificationsField = ElementCreator.makeTextField();
        addCertificationButton = ElementCreator.makeSubButton("ADD", "Submit certificate.");
        addCertificationButton.addActionListener(this);
        certifications = new ArrayList<String>();

        addTeacherLabel = ElementCreator.makeSubLabel("Press submit to add a teacher.");
        addTeacherSubmitButton = ElementCreator.makeSubButton("SUBMIT", "Submit the information given.");
        addTeacherSubmitButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to addTeacherPanel
    private void addAllToAddTeacherPanel() {
        addTeacherPanel = ElementCreator.makeSubPanel();
        GridBagConstraints constraints = ElementCreator.setupConstraints();
        ElementCreator.addToPanel(addTeacherPanel, constraints, addTeacherNameLabel, addTeacherNameField, 0);
        ElementCreator.addToPanel(addTeacherPanel, constraints, addCertificationsLabel, addCertificationsField,
                 addCertificationButton, 1);
        ElementCreator.addToPanel(addTeacherPanel, constraints, addTeacherLabel, addTeacherSubmitButton, 2);
    }

    // MODIFIES: this
    // EFFECTS: adds teacher if valid
    public void handleAddTeacher() {
        String teacherName = addTeacherNameField.getText();
        if (teacherName.length() == 0) {
            ElementCreator.setErrorText(addTeacherLabel, "Teacher name cannot be empty.");
            ElementCreator.setErrorText(lastActionLabel, "Could not add empty teacher.");
        } else if (lessonPlan.findTeacher(teacherName) != null) {
            ElementCreator.setErrorText(addTeacherLabel, "Teacher name already exists.");
            ElementCreator.setErrorText(lastActionLabel, "Could not add duplicate teacher.");
        } else {
            lessonPlan.addTeacher(teacherName, certifications);
            ElementCreator.setWorkedText(addTeacherLabel, "Added a teacher named " + teacherName);
            ElementCreator.setWorkedText(lastActionLabel, "Added a new teacher!");
        }
        certifications = new ArrayList<String>();
        addTeacherNameField.setText("");
    }

    // MODIFIES: this
    // EFFECTS: creates window for adding lessons
    public void makeAddLessonFrame() {
        createAddLessonComponents();

        addAllToAddLessonPanel();

        JFrame addLessonFrame = ElementCreator.makeSubFrame("Add Lesson", addLessonPanel);
        addLessonFrame.addWindowListener(this);
    }

    // MODIFIES: this
    // EFFECTS: makes all the components needed for add lesson frame
    private void createAddLessonComponents() {
        addLessonTitleLabel = ElementCreator.makeSubLabel("Add the title of the new lesson here.");
        addLessonTitleField = ElementCreator.makeTextField();

        addRequirementsLabel = ElementCreator.makeSubLabel("Add requirements here.");
        addRequirementsField = ElementCreator.makeTextField();
        addRequirementsButton = ElementCreator.makeSubButton("ADD", "Submit requirement.");
        addRequirementsButton.addActionListener(this);
        requirements = new ArrayList<String>();

        addMinutesLabel = ElementCreator.makeSubLabel("Add the minutes it takes to teach here.");
        addMinutesField = ElementCreator.makeTextField();

        addLessonLabel = ElementCreator.makeSubLabel("Press submit to add a lesson.");
        addLessonSubmitButton = ElementCreator.makeSubButton("SUBMIT", "Submit the information given.");
        addLessonSubmitButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds components to teachLessonPanel
    private void addAllToAddLessonPanel() {
        addLessonPanel = ElementCreator.makeSubPanel();
        GridBagConstraints constraints = ElementCreator.setupConstraints();
        ElementCreator.addToPanel(addLessonPanel, constraints, addLessonTitleLabel, addLessonTitleField, 0);
        ElementCreator.addToPanel(addLessonPanel, constraints, addRequirementsLabel, addRequirementsField,
                addRequirementsButton, 1);
        ElementCreator.addToPanel(addLessonPanel, constraints, addMinutesLabel, addMinutesField, 2);
        ElementCreator.addToPanel(addLessonPanel, constraints, addLessonLabel, addLessonSubmitButton, 3);
    }

    // MODIFIES: this
    // EFFECTS: adds lesson if valid
    public void handleAddLesson() {
        String lessonTitle = addLessonTitleField.getText();
        int minutesToTeach;
        try {
            minutesToTeach = Integer.parseInt(addMinutesField.getText());
            if (lessonTitle.length() == 0) {
                ElementCreator.setErrorText(addLessonLabel, "Lesson title cannot be empty.");
                ElementCreator.setErrorText(lastActionLabel, "Could not add empty lesson.");
            } else if (lessonPlan.findLesson(lessonTitle) != null) {
                ElementCreator.setErrorText(addLessonLabel, "Lesson title already exists.");
                ElementCreator.setErrorText(lastActionLabel, "Could not add duplicate lesson.");
            } else if (minutesToTeach <= 0) {
                ElementCreator.setErrorText(addLessonLabel, "Time must be greater than 0.");
                ElementCreator.setErrorText(lastActionLabel, "Could not add lesson with invalid time.");
            } else {
                lessonPlan.addLesson(lessonTitle, minutesToTeach, requirements);
                ElementCreator.setWorkedText(addLessonLabel, "Added a lesson titled " + lessonTitle);
                ElementCreator.setWorkedText(lastActionLabel, "Added a new lesson!");
            }
        } catch (NumberFormatException e) {
            ElementCreator.setErrorText(addLessonLabel, "Time must be an integer.");
        }
        requirements = new ArrayList<String>();
        resetAddLessonFields();
    }

    // MODIFIES: this
    // EFFECTS: clears the fields for the add lesson frame
    private void resetAddLessonFields() {
        addLessonTitleField.setText("");
        addMinutesField.setText("");
    }

    // EFFECTS: creates a table that shows all the students' information
    public void makePrintStudentFrame() {
        String[] columnNames = {"Name", "Lessons To Learn", "Lessons Learned"};
        Object[][] data = new Object[lessonPlan.getStudents().size()][3];
        for (int i = 0; i < lessonPlan.getStudents().size(); i++) {
            Student student = lessonPlan.getStudents().get(i);
            data[i][0] = student.getName();
            data[i][1] = ElementCreator.formatListOfLessons(student.getLessonsToLearn());
            data[i][2] = ElementCreator.formatListOfEntries(student.getAttendance());
        }

        JTable studentTable = ElementCreator.makeTable(data, columnNames);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(750);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(750);

        JScrollPane pane = ElementCreator.makeScrollPane(studentTable);

        JFrame printStudentFrame = ElementCreator.makeWideFrame("All Students");
        printStudentFrame.add(pane);
    }

    // EFFECTS: creates a table that shows all the teachers' information
    public void makePrintTeacherFrame() {
        String[] columnNames = {"Name", "Certifications"};
        Object[][] data = new Object[lessonPlan.getTeachers().size()][2];
        for (int i = 0; i < lessonPlan.getTeachers().size(); i++) {
            Teacher teacher = lessonPlan.getTeachers().get(i);
            data[i][0] = teacher.getName();
            data[i][1] = ElementCreator.formatListOfStrings(teacher.getCertifications());
        }

        JTable teacherTable = ElementCreator.makeTable(data, columnNames);
        teacherTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        teacherTable.getColumnModel().getColumn(1).setPreferredWidth(1500);

        JScrollPane pane = ElementCreator.makeScrollPane(teacherTable);

        JFrame printTeacherFrame = ElementCreator.makeWideFrame("All Teachers");
        printTeacherFrame.add(pane);
    }

    // EFFECTS: creates a table that shows all the lessons' information
    public void makePrintLessonFrame() {
        String[] columnNames = {"Title", "Requirements", "Minutes"};
        Object[][] data = new Object[lessonPlan.getLessonsToTeach().size()][3];
        for (int i = 0; i < lessonPlan.getLessonsToTeach().size(); i++) {
            Lesson lesson = lessonPlan.getLessonsToTeach().get(i);
            data[i][0] = lesson.getTitle();
            data[i][1] = ElementCreator.formatListOfStrings(lesson.getRequirements());
            data[i][2] = lesson.getTimeToTeach();
        }

        JTable lessonTable = ElementCreator.makeTable(data, columnNames);
        lessonTable.getColumnModel().getColumn(0).setPreferredWidth(300);
        lessonTable.getColumnModel().getColumn(1).setPreferredWidth(1200);
        lessonTable.getColumnModel().getColumn(2).setPreferredWidth(250);

        JScrollPane pane = ElementCreator.makeScrollPane(lessonTable);

        JFrame printLessonFrame = ElementCreator.makeWideFrame("All Lessons");
        printLessonFrame.add(pane);
    }

    // MODIFIES: this
    // EFFECTS: creates window for teaching a lesson
    public void makeTeachLessonFrame() {
        createTeachComponents();

        addAllToTeachLessonPanel();

        JFrame teachLessonFrame = ElementCreator.makeSubFrame("Teach Lesson", teachLessonPanel);
        teachLessonFrame.addWindowListener(this);
    }

    // MODIFIES: this
    // EFFECTS: makes all the components needed for teachLessonFrame
    private void createTeachComponents() {
        teachLessonTitleLabel = ElementCreator.makeSubLabel("Add the title of the lesson here.");
        teachLessonTitleField = ElementCreator.makeTextField();

        teachTeacherNameLabel = ElementCreator.makeSubLabel("Add the name of the teacher here.");
        teachTeacherNameField = ElementCreator.makeTextField();

        teachAddStudentsLabel = ElementCreator.makeSubLabel("Add students here.");
        teachAddStudentsField = ElementCreator.makeTextField();
        teachAddStudentsButton = ElementCreator.makeSubButton("ADD", "Submit student name.");
        teachAddStudentsButton.addActionListener(this);
        studentsToTeach = new ArrayList<String>();

        teachTimeToTeachLabel = ElementCreator.makeSubLabel("Add time to teach here.");
        teachTimeToTeachField = ElementCreator.makeTextField();

        teachDayLabel = ElementCreator.makeSubLabel("Add day (numerical form) here.");
        teachDayField = ElementCreator.makeTextField();

        teachMonthLabel = ElementCreator.makeSubLabel("Add month (numerical form) here.");
        teachMonthField = ElementCreator.makeTextField();

        teachYearLabel = ElementCreator.makeSubLabel("Add year (numerical form) here.");
        teachYearField = ElementCreator.makeTextField();

        teachLessonLabel = ElementCreator.makeSubLabel("Press submit to teach a lesson.");
        teachLessonSubmitButton = ElementCreator.makeSubButton("SUBMIT", "Submit the information given.");
        teachLessonSubmitButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds components to teachLessonPanel
    private void addAllToTeachLessonPanel() {
        teachLessonPanel = ElementCreator.makeSubPanel();
        GridBagConstraints constraints = ElementCreator.setupConstraints();
        ElementCreator.addToPanel(teachLessonPanel, constraints, teachLessonTitleLabel, teachLessonTitleField, 0);
        ElementCreator.addToPanel(teachLessonPanel, constraints, teachTeacherNameLabel, teachTeacherNameField, 1);
        ElementCreator.addToPanel(teachLessonPanel, constraints, teachAddStudentsLabel, teachAddStudentsField,
                teachAddStudentsButton, 2);
        ElementCreator.addToPanel(teachLessonPanel, constraints, teachTimeToTeachLabel, teachTimeToTeachField, 3);
        ElementCreator.addToPanel(teachLessonPanel, constraints, teachDayLabel, teachDayField, 4);
        ElementCreator.addToPanel(teachLessonPanel, constraints, teachMonthLabel, teachMonthField, 5);
        ElementCreator.addToPanel(teachLessonPanel, constraints, teachYearLabel, teachYearField, 6);
        ElementCreator.addToPanel(teachLessonPanel, constraints, teachLessonLabel, teachLessonSubmitButton, 7);
    }

    // MODIFIES: this
    // EFFECTS: teaches lesson if valid
    public void handleTeachLesson() {
        String title = teachLessonTitleField.getText();
        String teacher = teachTeacherNameField.getText();
        String timeString = teachTimeToTeachField.getText();
        String dayString = teachDayField.getText();
        String monthString = teachMonthField.getText();
        String yearString = teachYearField.getText();

        if (anyEmptyFields(title, teacher, timeString, dayString, monthString, yearString)) {
            ElementCreator.setErrorText(teachLessonLabel, "You must fill in all fields.");
            return;
        }
        if (!DateCreator.testDateValidity(monthString, dayString, yearString, teachLessonLabel)) {
            return;
        }
        tryToTeachLesson(title, teacher, timeString, DateCreator.formatDate(monthString, dayString, yearString));
        resetTeachLessonFields();
    }

    // MODIFIES: this
    // EFFECTS: clears the fields in the teaching lesson frame
    private void resetTeachLessonFields() {
        teachLessonTitleField.setText("");
        teachTeacherNameField.setText("");
        teachTimeToTeachField.setText("");
        teachDayField.setText("");
        teachMonthField.setText("");
        teachYearField.setText("");
    }

    private void tryToTeachLesson(String title, String teacher, String timeString, String date) {
        int time = Integer.parseInt(timeString);
        String message;
        try {
            lessonPlan.teachLesson(title, teacher, studentsToTeach, time, date);
            ElementCreator.setWorkedText(teachLessonLabel, title + " has been taught successfully!");
            ElementCreator.setWorkedText(lastActionLabel, "Taught " + title);
            setHappyFaces();
            return;
        } catch (LessonDoesNotExistException e) {
            message = "No lesson titled " + title + " exists!";
        } catch (TeacherDoesNotExistException e) {
            message = "No teacher named " + teacher + " exists!";
        } catch (NotEnoughTimeToTeachException e) {
            message = "There is not enough time to teach!";
        } catch (TeacherNotCertifiedToTeachException e) {
            message = teacher + " is not qualified to teach!";
        }
        ElementCreator.setErrorText(teachLessonLabel, message);
        ElementCreator.setErrorText(lastActionLabel, "Failed to teach " + title);
    }

    private boolean anyEmptyFields(String title, String teacher,
                                   String timeString, String dayString, String monthString, String yearString) {
        return title.equals("") || teacher.equals("") || timeString.equals("")
                || dayString.equals("") || monthString.equals("") || yearString.equals("");
    }

    // MODIFIES: this
    // EFFECTS: saves lesson plan
    public void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(lessonPlan);
            jsonWriter.close();
            ElementCreator.setWorkedText(lastActionLabel,
                    "Saved " + lessonPlan.getLessonPlanTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            ElementCreator.setErrorText(lastActionLabel, "Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads lesson plan if system can read file, else creates error text
    public void handleLoad() {
        try {
            lessonPlan = jsonReader.read();
            ElementCreator.setWorkedText(lastActionLabel, "Loaded " + lessonPlan.getLessonPlanTitle() + "!");
            setHappyFaces();
        } catch (IOException e) {
            ElementCreator.setErrorText(lastActionLabel, "Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds text from label as an item in listOfString, also editing labelToChange to report success
    public void handleAddToList(List<String> listOfString, JLabel labelToChange, JTextField field) {
        String itemAddition = field.getText();
        if (itemAddition.length() == 0) {
            ElementCreator.setErrorText(labelToChange, "Cannot add an empty string.");
        } else if (listOfString.contains(itemAddition)) {
            ElementCreator.setErrorText(labelToChange, itemAddition + " is a duplicate.");
        } else {
            listOfString.add(itemAddition);
            ElementCreator.setWorkedText(labelToChange, "Added " + itemAddition + " to list.");
        }
        field.setText("");
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentButton) {
            makeAddStudentFrame();
        } else if (e.getSource() == addTeacherButton) {
            makeAddTeacherFrame();
        } else if (e.getSource() == addLessonButton) {
            makeAddLessonFrame();
        } else if (e.getSource() == printStudentButton) {
            makePrintStudentFrame();
        } else if (e.getSource() == printTeacherButton) {
            makePrintTeacherFrame();
        } else if (e.getSource() == printLessonButton) {
            makePrintLessonFrame();
        } else if (e.getSource() == teachLessonButton) {
            makeTeachLessonFrame();
        } else if (e.getSource() == addStudentSubmitButton) {
            handleAddStudent();
        } else if (e.getSource() == addCertificationButton) {
            handleAddToList(certifications, addTeacherLabel, addCertificationsField);
        } else if (e.getSource() == addTeacherSubmitButton) {
            handleAddTeacher();
        } else if (e.getSource() == addRequirementsButton) {
            handleAddToList(requirements, addLessonLabel, addRequirementsField);
        } else if (e.getSource() == addLessonSubmitButton) {
            handleAddLesson();
        } else if (e.getSource() == teachAddStudentsButton) {
            handleAddToList(studentsToTeach, teachLessonLabel, teachAddStudentsField);
        } else if (e.getSource() == teachLessonSubmitButton) {
            handleTeachLesson();
        } else if (e.getSource() == saveItem) {
            handleSave();
        } else if (e.getSource() == loadItem) {
            handleLoad();
        } else {
            ElementCreator.setErrorText(lastActionLabel, "Button not identified!");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        if (e.getSource() != mainFrame) {
            addStudentButton.setEnabled(false);
            addTeacherButton.setEnabled(false);
            addLessonButton.setEnabled(false);
            teachLessonButton.setEnabled(false);
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (e.getSource() == mainFrame) {
            printLogEvents();
        }
        addStudentButton.setEnabled(true);
        addTeacherButton.setEnabled(true);
        addLessonButton.setEnabled(true);
        teachLessonButton.setEnabled(true);
    }

    private void printLogEvents() {
        System.out.println("Closing Application.");
        Iterator<Event> events = EventLog.getInstance().iterator();
        for (Iterator<Event> it = events; it.hasNext(); ) {
            Event event = it.next();
            System.out.println(event);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
