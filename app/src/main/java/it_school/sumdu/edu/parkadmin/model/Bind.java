package it_school.sumdu.edu.parkadmin.model;

public class Bind extends Car {

    private boolean isTaken;

    public Bind(int id, String color, String mark, String model, boolean isTaken)
    {
        super(id, color, mark, model);
        this.isTaken = isTaken;
    }

    public boolean isTaken()
    {
        return isTaken;
    }
}