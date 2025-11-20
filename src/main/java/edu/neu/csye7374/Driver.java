package edu.neu.csye7374;

import java.util.Scanner;

/**
 * 
 * @author Yash Zaveri
 * 
 */

public class Driver {
	public static void main(String[] args) {

         //Add your code in between these two print statements
		System.out.println("============Main Execution Start===================\n");

        Scanner sc = new Scanner(System.in);
        System.out.println("Select mode:");
        System.out.println("1. Console RPG (Demo.gameRun)");
        System.out.println("2. GUI RPG (GameGUI)");
        System.out.print("Enter choice (1 or 2): ");

        int choice = 1;
        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 1;
        }

        if (choice == 2) {
            // Launch Swing GUI on the Event Dispatch Thread
            javax.swing.SwingUtilities.invokeLater(() -> {
                GameGUI gui = new GameGUI();
                gui.setVisible(true);
            });
        } else {
            Demo.gameRun();
        }
		 
		System.out.println("\n\n============Main Execution End===================");
	}

}
