package br.com.softpeach.www.eqm.model;

/**
 * Created by jonas on 15/02/2018.
 */

public class Elder {

    private int id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String creation_date;

    public Elder()
    {
    }

    public Elder(String first_name, String middle_name, String last_name, String creation_date) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.creation_date = creation_date;
    }

    public Elder(int id, String first_name, String middle_name, String last_name, String creation_date) {
        this.id = id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.creation_date = creation_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
