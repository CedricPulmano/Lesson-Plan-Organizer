package ui;

import model.EventLog;
import model.LessonPlan;

import java.io.FileNotFoundException;

// a lesson planner that has a lesson plan
public class LessonPlanner {

    public static void main(String[] args) {

        // GUI APPLICATION
        new LessonPlannerGUI();

        // CONSOLE APPLICATION
        /*
        try {
            new LessonPlannerHelper();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
        */

    }

}
