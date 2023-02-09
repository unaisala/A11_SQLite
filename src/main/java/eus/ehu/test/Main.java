package eus.ehu.test;

import eus.ehu.data_access.DbAccessManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DbAccessManager dataManager = DbAccessManager.getInstance();

        /*
        dataManager.storePilot("Lewis Hamilton", "British", 380);
        // Include here additional instructions for another 6 pilots. You can
        // get info at https://www.formula1.com/en/drivers.html
        dataManager.storePilot("Valtteri Bottas", "Finnish", 280);
        dataManager.storePilot("Max Verstappen", "Dutch", 250);
        dataManager.storePilot("Charles Leclerc", "Monegasque", 200);
        dataManager.storePilot("Lando Norris", "British", 150);
        dataManager.storePilot("Carlos Sainz", "Spanish", 100);
        dataManager.storePilot("Daniel Ricciardo", "Australian", 50);
        */

        System.out.println(dataManager.getAllPilots());
        System.out.println(dataManager.getPilotsByNationality("British"));
        System.out.println(dataManager.getPilotsWithPoints(250));

        Scanner in = new Scanner(System.in);

        /*
        System.out.println("Introduce the name of the pilot you want to delete from the database:");
        String name = in.nextLine();
        System.out.println(dataManager.deletePilotByName(name));
        */

        /*
        System.out.println("Introduce the name of the pilot to who you want to add points:");
        String name = in.nextLine();
        System.out.println("Introduce the number of points you want to add:");
        int points = in.nextInt();
        dataManager.addPointsToPilot(points, name);
        System.out.println(dataManager.getAllPilots());
         */

        System.out.println("Introduce the name of the pilot you want to find:");
        String name = in.nextLine();
        try{
            System.out.println(dataManager.getPilotByName(name));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


}
