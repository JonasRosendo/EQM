package br.com.softpeach.www.eqm.model;

/**
 * Created by jonas on 02/03/2018.
 */

public class Attendance {

    private int id;
    private int elder_id;
    private int meeting_id;

    public Attendance() {
    }

    public Attendance(int elder_id, int meeting_id) {
        this.elder_id = elder_id;
        this.meeting_id = meeting_id;
    }

    public Attendance(int id, int elder_id, int meeting_id) {
        this.id = id;
        this.elder_id = elder_id;
        this.meeting_id = meeting_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getElder_id() {
        return elder_id;
    }

    public void setElder_id(int elder_id) {
        this.elder_id = elder_id;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
        this.meeting_id = meeting_id;
    }
}