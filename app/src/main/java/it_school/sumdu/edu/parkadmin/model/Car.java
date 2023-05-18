package it_school.sumdu.edu.parkadmin.model;

public class Car {
    private int id;
    private String number;
    private String mark;
    private String model;

    public Car(int id, String number, String mark, String model) {
        this.id = id;
        this.number = number;
        this.mark = mark;
        this.model = model;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getMark()
    {
        return mark;
    }

    public void setMark(String mark)
    {
        this.mark = mark;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }
}

