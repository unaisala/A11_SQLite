package eus.ehu.domain;

public class Pilot {
    String name;
    String nationality;
    int points;

    public Pilot(String name, String nat, int pts) {
        this.name = name;
        this.nationality = nat;
        this.points = pts;
    }

    public void addPoints(int morePoints) {
        this.points += morePoints;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d points", name, nationality, points);
    }
}
