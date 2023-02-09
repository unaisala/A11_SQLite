package eus.ehu.data_access;

import eus.ehu.domain.Pilot;

import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DbAccessManager {
    Connection conn = null;
    String dbpath;

    public void open() {
        try {
            String url = "jdbc:sqlite:" + dbpath;
            conn = DriverManager.getConnection(url);

            System.out.println("Database connection established");
        } catch (Exception e) {
            System.err.println("Cannot connect to database server " + e);
        }
    }

    public void close() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        System.out.println("Database connection closed");
    }


    // singleton pattern
    private static DbAccessManager instance;

    static {
        instance = new DbAccessManager();
    }

    private DbAccessManager() {

        // this is a hard-coded value... a code smell, should fix it later
        dbpath = "formula1.db";

    }

    public static DbAccessManager getInstance() {
        return instance;
    }

    public void setDbpath(String s) {
        dbpath = s;
    }

    public void storePilot(String name, String nationality, int points) {

        this.open();
        String sql = "INSERT INTO pilots (name, nationality, points) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, nationality);
            pstmt.setInt(3, points);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.close();
    }

    public List<Pilot> getAllPilots()  {

        var pilots  = new ArrayList<Pilot>();
        this.open();

        try {
            String query = "SELECT name, nationality, points FROM pilots";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                pilots.add(new Pilot(rs.getString("name"), rs.getString("nationality"), rs.getInt("points")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.close();
        return pilots;
    }

    public List<Pilot> getPilotsByNationality(String nationality){

        this.open();

        String sql = "SELECT id, name, nationality, points "
                + "FROM pilots WHERE nationality = ?";

        var result = new ArrayList<Pilot>();

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, nationality);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Pilot(rs.getString("name"), rs.getString("nationality"), rs.getInt("points")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.close();
        return result;
    }

    //Returns a list of pilots with more than the given number of points
    public List<Pilot> getPilotsWithPoints (int points){
        this.open();

        String sql = "SELECT id, name, nationality, points "
                + "FROM pilots WHERE points >= ?";

        var result = new ArrayList<Pilot>();

        try(PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setInt(1, points);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Pilot(rs.getString("name"), rs.getString("nationality"), rs.getInt("points")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.close();
        return result;
    }

    //Deletes a pilot by name
    //When the user types a name that does not exist, the method returns 0
    public int deletePilotByName(String pilotName) {
        this.open();
        int amount = 0;
        String sql = "DELETE FROM pilots WHERE name = ?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setString(1, pilotName);
            amount = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.close();

        return amount;
    }

    //Updates the points of a pilot
    //The number of points specified by the user will be added to the current points
    public void addPointsToPilot(int morePoints, String pilotName) {
        this.open();
        String sql = "UPDATE pilots SET points = points + ? WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, morePoints);
            pstmt.setString(2, pilotName);
            pstmt.executeUpdate();
            System.out.println(pilotName + " has been updated");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.close();
    }

    //Returns a pilot by name
    public Pilot getPilotByName(String name){
        this.open();

        String sql = "SELECT id, name, nationality, points "
                + "FROM pilots WHERE name = ?";

        Pilot result = null;

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, name);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                result = new Pilot(rs.getString("name"), rs.getString("nationality"), rs.getInt("points"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.close();

        if(result == null)
            throw new IllegalArgumentException("Pilot not found");

        return result;
    }

}
