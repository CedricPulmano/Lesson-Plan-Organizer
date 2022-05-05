package ui;

import model.AttendanceEntry;
import model.Lesson;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// creates JComponents
public class ElementCreator {

    // EFFECTS: creates a main label
    public static JLabel makeMainLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
        return label;
    }

    // EFFECTS: creates a sub label
    public static JLabel makeSubLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        return label;
    }

    // EFFECTS: creates a main button
    public static JButton makeMainButton(String text, String toolTipText, Color background) {
        JButton button = new JButton(text);
        button.setToolTipText(toolTipText);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("MS Gothic", Font.PLAIN, 35));
        return button;
    }

    // EFFECTS: creates a sub button
    public static JButton makeSubButton(String text, String toolTipText) {
        JButton button = new JButton(text);
        button.setToolTipText(toolTipText);
        button.setBackground(new Color(248, 237, 89));
        button.setForeground(Color.BLACK);
        button.setPreferredSize(new Dimension(100, 30));
        button.setFont(new Font("MS Gothic", Font.BOLD, 15));
        return button;
    }

    // EFFECTS: creates a list panel, used in the main frame
    public static JPanel makeListPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    // EFFECTS: creates a sub panel
    public static JPanel makeSubPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new GridBagLayout());
        return panel;
    }

    // EFFECTS: creates a main frame
    public static JFrame makeMainFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setSize(750, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        return frame;
    }

    // EFFECTS: creates a sub frame
    public static JFrame makeSubFrame(String title, JPanel panel) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(750, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(panel);
        return frame;
    }

    // EFFECTS: creates a wide frame, mainly used in viewing tables
    public static JFrame makeWideFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(1750, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        return frame;
    }

    // EFFECTS: creates a text field
    public static JTextField makeTextField() {
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(250, 40));
        text.setFont(new Font("Consolas", Font.PLAIN, 35));
        return text;
    }

    // MODIFIES: label
    // EFFECTS: changes label to send an error message
    public static void setErrorText(JLabel label, String message) {
        label.setText(message);
        label.setForeground(Color.RED);
    }

    // MODIFIES: label
    // EFFECTS: changes label to send a worked message
    public static void setWorkedText(JLabel label, String message) {
        label.setText(message);
        label.setForeground(Color.GREEN);
    }

    // EFFECTS: creates a table
    public static JTable makeTable(Object[][] data, String[] columnNames) {
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(250, 70));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 30));
        return table;
    }

    // EFFECTS: creates a scroll pane
    public static JScrollPane makeScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.add(table);
        scrollPane.setViewportView(table);
        return scrollPane;
    }

    // EFFECTS: formats list of strings into a string
    public static String formatListOfStrings(List<String> listOfStrings) {
        String response = "";
        if (listOfStrings.size() == 0) {
            response = "None";
        } else {
            for (int i = 0; i < listOfStrings.size() - 1; i++) {
                response += listOfStrings.get(i) + ", ";
            }
            response += listOfStrings.get(listOfStrings.size() - 1);
        }
        return response;
    }

    // EFFECTS: formats list of lessons into a string
    public static String formatListOfLessons(List<Lesson> listOfLessons) {
        List<String> listOfStrings = new ArrayList<String>();
        for (Lesson lesson : listOfLessons) {
            listOfStrings.add(lesson.getTitle());
        }
        return formatListOfStrings(listOfStrings);
    }

    // EFFECTS: formats list of lessons into a string
    public static String formatListOfEntries(List<AttendanceEntry> listOfEntries) {
        List<String> listOfStrings = new ArrayList<String>();
        for (AttendanceEntry entry : listOfEntries) {
            listOfStrings.add(entry.getLessonTitle());
        }
        return formatListOfStrings(listOfStrings);
    }

    // EFFECTS: Sets up constraints
    public static GridBagConstraints setupConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        return constraints;
    }


    // EFFECTS: Adds two UI elements to the given panel at the given column
    public static void addToPanel(JPanel panel, GridBagConstraints constraints,
                                  JComponent item1, JComponent item2, int column) {
        constraints.gridy = column;
        constraints.gridx = 0;
        panel.add(item1, constraints);
        constraints.gridx = 1;
        panel.add(item2, constraints);
    }

    // EFFECTS: Adds three UI elements to the given panel at the given column
    public static void addToPanel(JPanel panel, GridBagConstraints constraints,
                                  JComponent item1, JComponent item2, JComponent item3, int column) {
        constraints.gridy = column;
        constraints.gridx = 0;
        panel.add(item1, constraints);
        constraints.gridx = 1;
        panel.add(item2, constraints);
        constraints.gridx = 2;
        panel.add(item3, constraints);
    }

    public static ImageIcon makeSmallIcon(String destination) {
        ImageIcon icon = new ImageIcon(destination);
        Image image = icon.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        return new ImageIcon(image);
    }
}
