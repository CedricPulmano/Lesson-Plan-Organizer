package ui;

import java.io.FileNotFoundException;

// a console lesson planner with more functions
public class LessonPlannerMainConsole {

    public static void main(String[] args) {

        try {
            new LessonPlannerHelper();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }

}