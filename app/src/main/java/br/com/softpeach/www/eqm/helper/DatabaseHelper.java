package br.com.softpeach.www.eqm.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.softpeach.www.eqm.model.Attendance;
import br.com.softpeach.www.eqm.model.Elder;
import br.com.softpeach.www.eqm.model.Meetings;

/**
 * Created by jonas on 15/02/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "eqm.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //cria as tabelas no banco de dados
        db.execSQL("CREATE TABLE " + TableElders.TABLE_NAME + " ("
                + TableElders.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TableElders.FIRST_NAME + " TEXT NOT NULL,"
                + TableElders.MIDDLE_NAME + " TEXT NOT NULL,"
                + TableElders.LAST_NAME + " TEXT NOT NULL,"
                + TableElders.CREATION_DATE + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TableMeetings.TABLE_NAME + " ("
                + TableMeetings.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TableMeetings.DAY + " TEXT NOT NULL,"
                + TableMeetings.MONTH + " TEXT NOT NULL,"
                + TableMeetings.YEAR + " TEXT NOT NULL,"
                + TableMeetings.CREATION_DATE + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TableAttendance.TABLE_NAME + " ("
                + TableAttendance.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TableAttendance.ELDER_ID + " INTEGER NOT NULL,"
                + TableAttendance.MEETING_ID + " INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE " + TableTotalYearStatistics.TABLE_NAME + "("
                + TableTotalYearStatistics.STATISTIC_YEAR + "  INTEGER PRIMARY KEY,"
                + TableTotalYearStatistics.TOTAL_ELDERS + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_MEETINGS + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_YEAR + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_JAN + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_FEB + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_MAR + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_APR + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_MAY + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_JUN + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_JUL + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_AUG + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_SEP + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_OCT + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_NOV + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_DEC + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_FIRST_QUARTER + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_SECOND_QUARTER + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_THIRD_QUARTER + " INTEGER NOT NULL,"
                + TableTotalYearStatistics.TOTAL_ATT_FOURTH_QUARTER + " INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE " + TableEldersStatistics.TABLE_NAME + "("
                + TableEldersStatistics.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TableEldersStatistics.STATISTIC_YEAR + "  INTEGER NOT NULL,"
                + TableEldersStatistics.ELDER_ID + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_YEAR + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_JAN + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_FEB + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_MAR + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_APR + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_MAY + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_JUN + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_JUL + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_AUG + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_SEP + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_OCT + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_NOV + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_DEC + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_FIRST_QUARTER + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_SECOND_QUARTER + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_THIRD_QUARTER + " INTEGER NOT NULL,"
                + TableEldersStatistics.TOTAL_ATT_FOURTH_QUARTER + " INTEGER NOT NULL)");
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //criar drop
    }

    //------------------------------------CRUD Elders table-----------------------------------------------

    //Add Elder to the table, used in AddElderActivity
    public Elder addElder(String first_name, String middle_name, String last_name, String currentTime)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableElders.FIRST_NAME, first_name);
        values.put(TableElders.MIDDLE_NAME, middle_name);
        values.put(TableElders.LAST_NAME, last_name);
        values.put(TableElders.CREATION_DATE, currentTime);

        long elderId = db.insert(TableElders.TABLE_NAME, null, values);

        String query = "SELECT * FROM " + TableElders.TABLE_NAME + " WHERE " + TableElders.ID + " = " + elderId;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        Elder elder = new Elder();
        elder.setId(c.getInt(c.getColumnIndex(TableElders.ID)));
        elder.setFirst_name(c.getString(c.getColumnIndex(TableElders.FIRST_NAME)));
        elder.setMiddle_name(c.getString(c.getColumnIndex(TableElders.MIDDLE_NAME)));
        elder.setLast_name(c.getString(c.getColumnIndex(TableElders.LAST_NAME)));
        elder.setCreation_date(c.getString(c.getColumnIndex(TableElders.CREATION_DATE)));
        c.close();

        return elder;
    }

    //return all elder's data from database, used in SearchActivity,
    public List<Elder> getAllElders() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TableElders.TABLE_NAME + " ORDER BY " + TableElders.FIRST_NAME + " ASC";
        List<Elder> elderList = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Elder elder = new Elder();
                elder.setId(c.getInt(c.getColumnIndex(TableElders.ID)));
                elder.setFirst_name(c.getString(c.getColumnIndex(TableElders.FIRST_NAME)));
                elder.setMiddle_name(c.getString(c.getColumnIndex(TableElders.MIDDLE_NAME)));
                elder.setLast_name(c.getString(c.getColumnIndex(TableElders.LAST_NAME)));
                elder.setCreation_date(c.getString(c.getColumnIndex(TableElders.CREATION_DATE)));
                elderList.add(elder);
            } while (c.moveToNext());
        }

        c.close();
        return elderList;
    }


    public void deleteElder(long elder_id)
    {
        SQLiteDatabase db  = this.getWritableDatabase();
        db.delete(TableElders.TABLE_NAME, TableElders.ID + "=?", new String[]{String.valueOf(elder_id)});
    }

    //-------------------------------CRUD Meetings-------------------------------------
    public long addMeeting(int day, int month, int year, String creation_date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableMeetings.DAY, day);
        values.put(TableMeetings.MONTH, month);
        values.put(TableMeetings.YEAR, year);
        values.put(TableMeetings.CREATION_DATE, creation_date);

        long meeting_id = db.insert(TableMeetings.TABLE_NAME, null, values);
        return meeting_id;
    }

    public List<Meetings> getAllMeetings()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TableMeetings.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        List<Meetings> meetingsList = new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                Meetings meetings = new Meetings();
                meetings.setId(c.getInt(c.getColumnIndex(TableMeetings.ID)));
                meetings.setDay(c.getString(c.getColumnIndex(TableMeetings.DAY)));
                meetings.setMonth(c.getString(c.getColumnIndex(TableMeetings.MONTH)));
                meetings.setYear(c.getString(c.getColumnIndex(TableMeetings.YEAR)));
                meetings.setCreation_date(c.getString(c.getColumnIndex(TableMeetings.CREATION_DATE)));

                meetingsList.add(meetings);
            }while (c.moveToNext());
        }

        c.close();
        return meetingsList;
    }

    public List<Integer> getAllMonths()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableMeetings.MONTH + " FROM " + TableMeetings.TABLE_NAME + " ORDER BY " + TableMeetings.MONTH + " ASC";

        Cursor c = db.rawQuery(query, null);

        List<Integer> resultList = new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                int result = c.getInt(c.getColumnIndex(TableMeetings.MONTH));
                resultList.add(result);
            }while (c.moveToNext());
        }

        c.close();
        return resultList;
    }

    public List<Meetings> getAllMeetingsByYear(String selected_year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] selectionArgs = new String[]{selected_year};
        Cursor cursor = db.query(TableMeetings.TABLE_NAME, null,
                TableMeetings.YEAR + "=?", selectionArgs,
                null,null,null);

        List<Meetings> meetingsList = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do {
                Meetings meetings = new Meetings();
                meetings.setId(cursor.getInt(cursor.getColumnIndex(TableMeetings.ID)));
                meetings.setDay(cursor.getString(cursor.getColumnIndex(TableMeetings.DAY)));
                meetings.setMonth(cursor.getString(cursor.getColumnIndex(TableMeetings.MONTH)));
                meetings.setYear(cursor.getString(cursor.getColumnIndex(TableMeetings.YEAR)));

                meetingsList.add(meetings);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return meetingsList;
    }

    public void deleteMeeting(long meeting_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableMeetings.TABLE_NAME, TableMeetings.ID + "=?",
                new String[] {String.valueOf(meeting_id)});
        db.delete(TableAttendance.TABLE_NAME, TableAttendance.MEETING_ID + "=?",
                new String[] {String.valueOf(meeting_id)});
    }

    //----------------------------------CRUD Attendances----------------------------------------

    public void addAtendance(long elder_id, long meeting_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableAttendance.ELDER_ID, elder_id);
        values.put(TableAttendance.MEETING_ID, meeting_id);

        db.insert(TableAttendance.TABLE_NAME, null, values);
        Log.i("LOG_ATD","Elder id " + elder_id + " adicionado a reunião " + meeting_id);
    }

    public List<Attendance> getAllAttendances()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TableAttendance.TABLE_NAME + " ORDER BY " + TableAttendance.ELDER_ID +","+ TableAttendance.MEETING_ID + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        List<Attendance> attendanceList = new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do {
                Attendance attendance = new Attendance();
                attendance.setId(cursor.getInt(cursor.getColumnIndex(TableAttendance.ID)));
                attendance.setElder_id(cursor.getInt(cursor.getColumnIndex(TableAttendance.ELDER_ID)));
                attendance.setMeeting_id(cursor.getInt(cursor.getColumnIndex(TableAttendance.MEETING_ID)));

                attendanceList.add(attendance);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return attendanceList;
    }

    public List<Attendance> getAllAttendancesByYear(String selected_year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + "," + TableMeetings.TABLE_NAME
                + " WHERE " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID + " AND " + TableMeetings.YEAR + " = " + selected_year;

        final List<Attendance> attendanceList = new ArrayList<>();
        @SuppressLint("Recycle")
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                Attendance attendance = new Attendance();
                try
                {
                    if (!c.isNull(0))
                    {
                        attendance.setId(c.getInt(c.getColumnIndex(TableAttendance.ID)));
                    }

                    if (!c.isNull(1))
                    {
                        attendance.setElder_id(c.getInt(c.getColumnIndex(TableAttendance.ELDER_ID)));
                    }

                    if (!c.isNull(2))
                    {
                        attendance.setMeeting_id(c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID)));
                    }
                }catch (Throwable throwable)
                {
                    throwable.printStackTrace();
                }

                attendanceList.add(attendance);
            } while (c.moveToNext());
        }

        c.close();

        return attendanceList;
    }

    public void deleteAttendance(int attendance_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableAttendance.TABLE_NAME, TableAttendance.ID + "=?", new String[] {String.valueOf(attendance_id)});
    }

    private List<Integer> getAttendanceJan(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID  + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '1'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }
        c.close();

        return resultList;
    }

    private List<Integer> getAttendanceFeb(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '2'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceMar(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '3'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceApr(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '4'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceMay(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '5'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceJun(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '6'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceJul(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '7'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceAug(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '8'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceSep(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '9'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceOct(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '10'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceNov(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '11'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceDec(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " = '12'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getAttendanceFirstQuarter(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' WHERE " + TableMeetings.MONTH + " BETWEEN '1' AND '3'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();

        return resultList;
    }

    private List<Integer> getAttendanceSecondQuarter(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " BETWEEN '4' AND '6'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();

        return resultList;
    }

    private List<Integer> getAttendanceThirdQuarter(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " BETWEEN '7' AND '9'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();

        return resultList;
    }

    private List<Integer> getAttendanceFourthQuarter(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME
                + " INNER JOIN " + TableMeetings.TABLE_NAME + " ON " + TableMeetings.ID + " = " + TableAttendance.MEETING_ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableMeetings.MONTH + " BETWEEN '10' AND '12'";

        List<Integer> resultList = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            do {
                int result  = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();

        return resultList;
    }

    //---------------------------------CRUD total statistic year------------------------------------
    //----------------------------Only methods to handle this table---------------------------------

    public long addTotalStatistics(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Elder> total_elders = this.getAllElders();
        List<Meetings> total_meetings = this.getAllMeetingsByYear(year);
        List<Attendance> att_year = this.getAllAttendancesByYear(year);
        List<Integer> att_jan = this.getAttendanceJan(year);
        List<Integer> att_feb = this.getAttendanceFeb(year);
        List<Integer> att_mar = this.getAttendanceMar(year);
        List<Integer> att_apr = this.getAttendanceApr(year);
        List<Integer> att_may = this.getAttendanceMay(year);
        List<Integer> att_jun = this.getAttendanceJun(year);
        List<Integer> att_jul = this.getAttendanceJul(year);
        List<Integer> att_aug = this.getAttendanceAug(year);
        List<Integer> att_sep = this.getAttendanceSep(year);
        List<Integer> att_oct = this.getAttendanceOct(year);
        List<Integer> att_nov = this.getAttendanceNov(year);
        List<Integer> att_dec = this.getAttendanceDec(year);
        List<Integer> first_quarter = this.getAttendanceFirstQuarter(year);
        List<Integer> second_quarter = this.getAttendanceSecondQuarter(year);
        List<Integer> third_quarter = this.getAttendanceThirdQuarter(year);
        List<Integer> fourth_quarter = this.getAttendanceFourthQuarter(year);

        ContentValues values = new ContentValues();
        values.put(TableTotalYearStatistics.STATISTIC_YEAR, year);
        values.put(TableTotalYearStatistics.TOTAL_ELDERS, total_elders.size());
        values.put(TableTotalYearStatistics.TOTAL_MEETINGS, total_meetings.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_YEAR, att_year.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_JAN, att_jan.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_FEB, att_feb.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_MAR, att_mar.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_APR, att_apr.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_MAY, att_may.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_JUN, att_jun.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_JUL, att_jul.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_AUG, att_aug.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_SEP, att_sep.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_OCT, att_oct.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_NOV, att_nov.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_DEC, att_dec.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_FIRST_QUARTER, first_quarter.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_SECOND_QUARTER, second_quarter.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_THIRD_QUARTER, third_quarter.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_FOURTH_QUARTER, fourth_quarter.size());

        long id = db.insert(TableTotalYearStatistics.TABLE_NAME, null, values);
        return id;
    }

    public void updateTotalStatistics(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Elder> total_elders = this.getAllElders();
        List<Meetings> total_meetings = this.getAllMeetingsByYear(year);
        List<Attendance> att_year = this.getAllAttendancesByYear(year);
        List<Integer> att_jan = this.getAttendanceJan(year);
        List<Integer> att_feb = this.getAttendanceFeb(year);
        List<Integer> att_mar = this.getAttendanceMar(year);
        List<Integer> att_apr = this.getAttendanceApr(year);
        List<Integer> att_may = this.getAttendanceMay(year);
        List<Integer> att_jun = this.getAttendanceJun(year);
        List<Integer> att_jul = this.getAttendanceJul(year);
        List<Integer> att_aug = this.getAttendanceAug(year);
        List<Integer> att_sep = this.getAttendanceSep(year);
        List<Integer> att_oct = this.getAttendanceOct(year);
        List<Integer> att_nov = this.getAttendanceNov(year);
        List<Integer> att_dec = this.getAttendanceDec(year);
        List<Integer> first_quarter = this.getAttendanceFirstQuarter(year);
        List<Integer> second_quarter = this.getAttendanceSecondQuarter(year);
        List<Integer> third_quarter = this.getAttendanceThirdQuarter(year);
        List<Integer> fourth_quarter = this.getAttendanceFourthQuarter(year);

        ContentValues values = new ContentValues();
        values.put(TableTotalYearStatistics.TOTAL_ELDERS, total_elders.size());
        values.put(TableTotalYearStatistics.TOTAL_MEETINGS, total_meetings.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_YEAR, att_year.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_JAN, att_jan.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_FEB, att_feb.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_MAR, att_mar.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_APR, att_apr.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_MAY, att_may.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_JUN, att_jun.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_JUL, att_jul.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_AUG, att_aug.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_SEP, att_sep.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_OCT, att_oct.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_NOV, att_nov.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_DEC, att_dec.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_FIRST_QUARTER, first_quarter.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_SECOND_QUARTER, second_quarter.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_THIRD_QUARTER, third_quarter.size());
        values.put(TableTotalYearStatistics.TOTAL_ATT_FOURTH_QUARTER, fourth_quarter.size());

        db.update(TableTotalYearStatistics.TABLE_NAME, values, TableTotalYearStatistics.STATISTIC_YEAR + "=?", new String[]{String.valueOf(year)});

    }

    public int getTotalElders(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ELDERS + " FROM " + TableTotalYearStatistics.TABLE_NAME
                + " WHERE " + TableTotalYearStatistics.STATISTIC_YEAR + " = '" + year + "'";

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ELDERS));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getTotalAttendance(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_YEAR + " FROM " + TableTotalYearStatistics.TABLE_NAME
                + " WHERE " + TableTotalYearStatistics.STATISTIC_YEAR + " = '" + year + "'";

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_YEAR));

            }while (c.moveToNext());
        }

        c.close();

        return result;
    }

    public int getTotalMeetings(String year)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_MEETINGS + " FROM " + TableTotalYearStatistics.TABLE_NAME
                + " WHERE " + TableTotalYearStatistics.STATISTIC_YEAR + " = '" + year + "'";

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_MEETINGS));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthJan()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_JAN + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);

        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_JAN));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthFeb()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_FEB + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_FEB));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthMar()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_MAR + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_MAR));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthApr() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_APR + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_APR));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthMay()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_MAY + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_MAY));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthJun() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_JUN + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if (c.moveToFirst()) {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_JUN));
            } while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthJul()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_JUL + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_JUL));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthAug()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_AUG + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_AUG));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthSep()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_SEP + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_SEP));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthOct()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_OCT + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_OCT));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthNov()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_NOV + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_NOV));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getMonthDec()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_DEC + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_DEC));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getFirstQuarter()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_FIRST_QUARTER + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_FIRST_QUARTER));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getSecondQuarter()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_SECOND_QUARTER + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_SECOND_QUARTER));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getThirdQuarter()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_THIRD_QUARTER + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_THIRD_QUARTER));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public int getFourthQuarter()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.TOTAL_ATT_FOURTH_QUARTER + " FROM " + TableTotalYearStatistics.TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        int result = 0;
        if(c.moveToFirst())
        {
            do {
                result = c.getInt(c.getColumnIndex(TableTotalYearStatistics.TOTAL_ATT_FOURTH_QUARTER));
            }while (c.moveToNext());
        }

        c.close();
        return result;
    }

    public List<String> getAllStatisticsYear()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableTotalYearStatistics.STATISTIC_YEAR + " FROM " + TableTotalYearStatistics.TABLE_NAME + " ORDER BY " + TableTotalYearStatistics.STATISTIC_YEAR + " ASC";

        @SuppressLint("Recycle")
        Cursor c = db.rawQuery(query, null);
        List<String> yearList = new ArrayList<>();
        if (c.moveToFirst())
        {
            do {
                String year = c.getString(c.getColumnIndex(TableTotalYearStatistics.STATISTIC_YEAR));
                yearList.add(year);
            }while (c.moveToNext());
        }

        c.close();
        return yearList;
    }

    public int getLastYearOnDatabase()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT MAX(" + TableTotalYearStatistics.STATISTIC_YEAR + ") FROM " + TableTotalYearStatistics.TABLE_NAME;

        @SuppressLint("Recycle")
        Cursor c = db.rawQuery(query, null);
        String result = "0";
        if(c.moveToFirst())
        {
            try {
                if(!c.isNull(0))
                {
                    result = c.getString(c.getColumnIndex(TableTotalYearStatistics.STATISTIC_YEAR));
                }
            }catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        return Integer.parseInt(result);
    }

    //------------------------------Table Elder Individual Statistics-------------------------------


    public long addStatisticsElders(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Integer> totalAttendanceYear = this.getTotalElderAttYear(year, elder_id);
        List<Integer> totalJan = this.getElderAttJan(year, elder_id);
        List<Integer> totalFeb = this.getElderAttFeb(year, elder_id);
        List<Integer> totalMar = this.getElderAttMar(year, elder_id);
        List<Integer> totalApr = this.getElderAttApr(year, elder_id);
        List<Integer> totalMay = this.getElderAttMay(year, elder_id);
        List<Integer> totalJun = this.getElderAttJun(year, elder_id);
        List<Integer> totalJul = this.getElderAttJul(year, elder_id);
        List<Integer> totalAug = this.getElderAttAug(year, elder_id);
        List<Integer> totalSep = this.getElderAttSep(year, elder_id);
        List<Integer> totalOct = this.getElderAttOct(year, elder_id);
        List<Integer> totalNov = this.getElderAttNov(year, elder_id);
        List<Integer> totalDec = this.getElderAttDec(year, elder_id);
        List<Integer> totalFirst = this.getElderAttFirstQuarter(year, elder_id);
        List<Integer> totalSecond = this.getElderAttSecondQuarter(year, elder_id);
        List<Integer> totalThird = this.getElderAttThirdQuarter(year, elder_id);
        List<Integer> totalFourth = this.getElderAttFourthQuarter(year, elder_id);

        ContentValues values = new ContentValues();
        values.put(TableEldersStatistics.STATISTIC_YEAR, year);
        values.put(TableEldersStatistics.ELDER_ID, elder_id);
        values.put(TableEldersStatistics.TOTAL_ATT_YEAR, totalAttendanceYear.size());
        values.put(TableEldersStatistics.TOTAL_ATT_JAN, totalJan.size());
        values.put(TableEldersStatistics.TOTAL_ATT_FEB, totalFeb.size());
        values.put(TableEldersStatistics.TOTAL_ATT_MAR, totalMar.size());
        values.put(TableEldersStatistics.TOTAL_ATT_APR, totalApr.size());
        values.put(TableEldersStatistics.TOTAL_ATT_MAY, totalMay.size());
        values.put(TableEldersStatistics.TOTAL_ATT_JUN, totalJun.size());
        values.put(TableEldersStatistics.TOTAL_ATT_JUL, totalJul.size());
        values.put(TableEldersStatistics.TOTAL_ATT_AUG, totalAug.size());
        values.put(TableEldersStatistics.TOTAL_ATT_SEP, totalSep.size());
        values.put(TableEldersStatistics.TOTAL_ATT_OCT, totalOct.size());
        values.put(TableEldersStatistics.TOTAL_ATT_NOV, totalNov.size());
        values.put(TableEldersStatistics.TOTAL_ATT_DEC, totalDec.size());
        values.put(TableEldersStatistics.TOTAL_ATT_FIRST_QUARTER, totalFirst.size());
        values.put(TableEldersStatistics.TOTAL_ATT_SECOND_QUARTER, totalSecond.size());
        values.put(TableEldersStatistics.TOTAL_ATT_THIRD_QUARTER, totalThird.size());
        values.put(TableEldersStatistics.TOTAL_ATT_FOURTH_QUARTER, totalFourth.size());

        long result = db.insert(TableEldersStatistics.TABLE_NAME, null, values);

        return result;
    }

    public long updateStatisticsElders(String year, int elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Integer> totalAttendanceYear = this.getTotalElderAttYear(year, elder_id);
        List<Integer> totalJan = this.getElderAttJan(year, elder_id);
        List<Integer> totalFeb = this.getElderAttFeb(year, elder_id);
        List<Integer> totalMar = this.getElderAttMar(year, elder_id);
        List<Integer> totalApr = this.getElderAttApr(year, elder_id);
        List<Integer> totalMay = this.getElderAttMay(year, elder_id);
        List<Integer> totalJun = this.getElderAttJun(year, elder_id);
        List<Integer> totalJul = this.getElderAttJul(year, elder_id);
        List<Integer> totalAug = this.getElderAttAug(year, elder_id);
        List<Integer> totalSep = this.getElderAttSep(year, elder_id);
        List<Integer> totalOct = this.getElderAttOct(year, elder_id);
        List<Integer> totalNov = this.getElderAttNov(year, elder_id);
        List<Integer> totalDec = this.getElderAttDec(year, elder_id);
        List<Integer> totalFirst = this.getElderAttFirstQuarter(year, elder_id);
        List<Integer> totalSecond = this.getElderAttSecondQuarter(year, elder_id);
        List<Integer> totalThird = this.getElderAttThirdQuarter(year, elder_id);
        List<Integer> totalFourth = this.getElderAttFourthQuarter(year, elder_id);


        ContentValues values = new ContentValues();

        values.put(TableEldersStatistics.TOTAL_ATT_YEAR, totalAttendanceYear.size());
        values.put(TableEldersStatistics.TOTAL_ATT_JAN, totalJan.size());
        values.put(TableEldersStatistics.TOTAL_ATT_FEB, totalFeb.size());
        values.put(TableEldersStatistics.TOTAL_ATT_MAR, totalMar.size());
        values.put(TableEldersStatistics.TOTAL_ATT_APR, totalApr.size());
        values.put(TableEldersStatistics.TOTAL_ATT_MAY, totalMay.size());
        values.put(TableEldersStatistics.TOTAL_ATT_JUN, totalJun.size());
        values.put(TableEldersStatistics.TOTAL_ATT_JUL, totalJul.size());
        values.put(TableEldersStatistics.TOTAL_ATT_AUG, totalAug.size());
        values.put(TableEldersStatistics.TOTAL_ATT_SEP, totalSep.size());
        values.put(TableEldersStatistics.TOTAL_ATT_OCT, totalOct.size());
        values.put(TableEldersStatistics.TOTAL_ATT_NOV, totalNov.size());
        values.put(TableEldersStatistics.TOTAL_ATT_DEC, totalDec.size());
        values.put(TableEldersStatistics.TOTAL_ATT_FIRST_QUARTER, totalFirst.size());
        values.put(TableEldersStatistics.TOTAL_ATT_SECOND_QUARTER, totalSecond.size());
        values.put(TableEldersStatistics.TOTAL_ATT_THIRD_QUARTER, totalThird.size());
        values.put(TableEldersStatistics.TOTAL_ATT_FOURTH_QUARTER, totalFourth.size());

        long result = db.update(TableEldersStatistics.TABLE_NAME, values, TableEldersStatistics.ELDER_ID + "=? AND " + TableEldersStatistics.STATISTIC_YEAR + "=?",
                new String[]{String.valueOf(elder_id), year});

        return result;
    }

    private List<Integer> getTotalElderAttYear(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.ELDER_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN " + TableMeetings.TABLE_NAME
                + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID + " WHERE " + TableMeetings.YEAR + " = '" + year + "' AND "
                + TableAttendance.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.ELDER_ID));
                resultList.add(result);
            }while (c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttJan(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " WHERE " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '1'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttFeb(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '2'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttMar(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '3'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttApr(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '4'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttMay(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '5'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttJun(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '6'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttJul(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '7'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttAug(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '8'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttSep(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '9'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttOct(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '10'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttNov(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '11'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttDec(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " = '12'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttFirstQuarter(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " BETWEEN '1' AND '3'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttSecondQuarter(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " BETWEEN '4' AND '6'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttThirdQuarter(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " BETWEEN '7' AND '9'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }

    private List<Integer> getElderAttFourthQuarter(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableAttendance.MEETING_ID + " FROM " + TableAttendance.TABLE_NAME + " INNER JOIN "
                + TableMeetings.TABLE_NAME + " ON " + TableAttendance.MEETING_ID + " = " + TableMeetings.ID
                + " AND " + TableMeetings.YEAR + " = '" + year + "' AND " + TableAttendance.ELDER_ID + " = '" + elder_id
                + "' AND " + TableMeetings.MONTH + " BETWEEN '10' AND '12'";

        Cursor c = db.rawQuery(query, null);
        List<Integer> resultList = new ArrayList<>();

        if(c.moveToFirst())
        {
            do{
                int result = c.getInt(c.getColumnIndex(TableAttendance.MEETING_ID));
                resultList.add(result);
            }while(c.moveToNext());
        }

        c.close();
        return resultList;
    }


    public int getElderYear(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_YEAR + " FROM " + TableEldersStatistics.TABLE_NAME
                + " WHERE " + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND "
                + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        @SuppressLint("Recycle")
        Cursor c = db.rawQuery(query, null);

        int result = 0;
        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_YEAR));
        }

        c.close();
        return result;
    }


    public int getElderMonthJan(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_JAN + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_JAN));
        }

        c.close();
        return result;
    }

    public int getElderMonthFeb(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_FEB + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_FEB));
        }

        c.close();
        return result;
    }

    public int getElderMonthMar(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_MAR + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_MAR));
        }

        c.close();
        return result;
    }

    public int getElderMonthApr(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_APR + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_APR));
        }

        c.close();
        return result;
    }

    public int getElderMonthMay(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_MAY + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_MAY));
        }

        c.close();
        return result;
    }

    public int getElderMonthJun(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_JUN + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_JUN));
        }

        c.close();
        return result;
    }

    public int getElderMonthJul(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_JUL + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_JUL));
        }

        c.close();
        return result;
    }

    public int getElderMonthAug(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_AUG + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_AUG));
        }

        c.close();
        return result;
    }

    public int getElderMonthSep(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_SEP + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_SEP));
        }

        c.close();
        return result;
    }

    public int getElderMonthOct(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_OCT + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_OCT));
        }

        c.close();
        return result;
    }

    public int getElderMonthNov(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_NOV + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_NOV));
        }

        c.close();
        return result;
    }

    public int getElderMonthDec(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_DEC + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_DEC));
        }

        c.close();
        return result;
    }


    public int getElderFirstQuarter(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_FIRST_QUARTER + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_FIRST_QUARTER));
        }

        c.close();
        return result;
    }

    public int getElderSecondQuarter(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_SECOND_QUARTER + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_SECOND_QUARTER));
        }

        c.close();
        return result;
    }

    public int getElderThirdQuarter(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_THIRD_QUARTER + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_THIRD_QUARTER));
        }

        c.close();
        return result;
    }

    public int getElderFourthQuarter(String year, long elder_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TableEldersStatistics.TOTAL_ATT_FOURTH_QUARTER + " FROM " + TableEldersStatistics.TABLE_NAME + " WHERE "
                + TableEldersStatistics.STATISTIC_YEAR + " = '" + year + "' AND " + TableEldersStatistics.ELDER_ID + " = '" + elder_id + "'";

        Cursor c = db.rawQuery(query, null);

        int result = 0;

        if(c.moveToFirst())
        {
            result = c.getInt(c.getColumnIndex(TableEldersStatistics.TOTAL_ATT_FOURTH_QUARTER));
        }

        c.close();
        return result;
    }

    //pegacano atual
    public String getCurrentYear()
    {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    //pega data atual
    public String currentTime()
    {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(c.getTime());
        return date;
    }
}
