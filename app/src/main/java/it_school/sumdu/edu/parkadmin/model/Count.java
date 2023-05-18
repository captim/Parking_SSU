package it_school.sumdu.edu.parkadmin.model;

public class Count {
    private long ownerRow;
    private long carRow;
    private long bindRow;

    public Count(long ownerRow, long carRow, long bindRow)
    {
        this.ownerRow = ownerRow;
        this.carRow = carRow;
        this.bindRow = bindRow;
    }

    public long getOwnerRow()
    {
        return ownerRow;
    }

    public long getCarRow()
    {
        return carRow;
    }

    public long getBindRow()
    {
        return bindRow;
    }
}
