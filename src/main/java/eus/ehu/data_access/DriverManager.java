package eus.ehu.data_access;

public class DriverManager {

    // This method opens the database that is held in a single file
    public void getConnection(){
        DbAccessManager db = DbAccessManager.getInstance();
        db.setDbpath("formula1.db");
        db.open();
    }
}
