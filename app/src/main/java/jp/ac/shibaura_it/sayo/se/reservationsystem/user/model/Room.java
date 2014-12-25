package jp.ac.shibaura_it.sayo.se.reservationsystem.user.model;

/**
 * Created by Shuya on 14/12/24.
 */
public class Room {
    private String name;
    private int capasity;

    public Room(String name, int capasity) {
        this.name = name;
        this.capasity = capasity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapasity() {
        return capasity;
    }

    public void setCapasity(int capasity) {
        this.capasity = capasity;
    }

    @Override
    public String toString() {
        return this.name + "(" + capasity + ")";
    }
}
