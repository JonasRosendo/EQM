package br.com.softpeach.www.eqm.model;

/**
 * Created by jonas on 15/02/2018.
 */

public class Meetings {

    private int id;
    private String day;
    private String month;
    private String year;
    private String creation_date;

    public Meetings()
    {
    }

    public Meetings(String day, String month, String year, String creation_date) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.creation_date = creation_date;
    }

    public Meetings(int id, String day, String month, String year, String creation_date) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.creation_date = creation_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
