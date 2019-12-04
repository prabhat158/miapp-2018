package com.iitbombay.moodi.moodindigo.EventsDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrunz on 7/12/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "eventsManager";

    // Table Names
    private static final String TABLE_EVENT = "events";


    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_DAY = "day";

    private static final String KEY_TYPE = "type";

    // NOTES Table - column nmaes
    private static final String KEY_NAME = "name";
    private static final String KEY_SUBTITLE = "subtitle";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TIME_START = "starttime";
    private static final String KEY_TIME_END = "endtime";
    private static final String KEY_VENUE = "venue";
    private static final String KEY_GOING = "going";
    private static final String KEY_RETROFIT_ID = "retroid";






    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_EVENT = "CREATE TABLE "
            + TABLE_EVENT + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_SUBTITLE +" TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_TIME_START + " TEXT,"
            + KEY_TIME_END + " TEXT," + KEY_VENUE
            + " TEXT," + KEY_GOING + " INTEGER," + KEY_DAY+" INTEGER," + KEY_RETROFIT_ID+" INTEGER,"+KEY_TYPE+" TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_EVENT);
        Log.i("events", "done");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);

        // create new tables
        onCreate(db);
    }

    /*
 * Creating a event
 */
    public long createEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, event.getName());
        values.put(KEY_SUBTITLE, event.getSubtitle());
        values.put(KEY_DESCRIPTION, event.getDescription());
        values.put(KEY_GOING, event.getGoing());
        values.put(KEY_TIME_START, event.getTime_start());
        values.put(KEY_TIME_END, event.getTime_end());
        values.put(KEY_VENUE, event.getVenue());
        values.put(KEY_DAY, event.getDay());
        values.put(KEY_RETROFIT_ID,event.getRetrofit_id());
        values.put(KEY_TYPE, event.getType());

        // insert row
        long event_id = db.insert(TABLE_EVENT, null, values);


        return event_id;
    }

    /*
 * get single EVENT
 */
    public Event getEvent(long event_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENT + " WHERE "
                + KEY_ID + " = " +event_id;

        //Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        Event event = new Event();
        if (c.moveToFirst()) {

            event.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            event.setName((c.getString(c.getColumnIndex(KEY_NAME))));
            event.setSubtitle((c.getString(c.getColumnIndex(KEY_SUBTITLE))));
            event.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            event.setVenue((c.getString(c.getColumnIndex(KEY_VENUE))));
            event.setTime_start((c.getString(c.getColumnIndex(KEY_TIME_START))));
            event.setTime_start((c.getString(c.getColumnIndex(KEY_TIME_END))));
            event.setGoing((c.getInt(c.getColumnIndex(KEY_GOING))));
            event.setRetrofit_id(c.getInt(c.getColumnIndex(KEY_RETROFIT_ID)));
            event.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
            event.setDay(c.getInt(c.getColumnIndex(KEY_DAY)));
        }


        return event;
    }
    public void updateEventifFound(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_EVENT + " WHERE "
                + KEY_RETROFIT_ID + " = " + event.getRetrofit_id();

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            if (c.moveToFirst()) {

                Event event1 = new Event();

                event1.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                event1.setGoing((c.getInt(c.getColumnIndex(KEY_GOING))));

                db.delete(TABLE_EVENT, KEY_ID + " = ?",
                        new String[]{String.valueOf(event1.getId())});
                ContentValues values = new ContentValues();
                values.put(KEY_NAME, event.getName());
                values.put(KEY_SUBTITLE, event.getSubtitle());
                values.put(KEY_DESCRIPTION, event.getDescription());
                values.put(KEY_TIME_START, event.getTime_start());
                values.put(KEY_TIME_END, event.getTime_end());
                values.put(KEY_GOING, event1.getGoing());
                values.put(KEY_VENUE, event.getVenue());
                values.put(KEY_RETROFIT_ID, event.getRetrofit_id());
                values.put(KEY_TYPE, event.getType());
                values.put(KEY_DAY, event.getDay());
                db.insert(TABLE_EVENT, null, values);

            } else {
                ContentValues values = new ContentValues();
                values.put(KEY_NAME, event.getName());
                values.put(KEY_SUBTITLE, event.getSubtitle());
                values.put(KEY_DESCRIPTION, event.getDescription());
                values.put(KEY_TIME_START, event.getTime_start());
                values.put(KEY_TIME_END, event.getTime_end());
                values.put(KEY_GOING, event.getGoing());
                values.put(KEY_VENUE, event.getVenue());
                values.put(KEY_RETROFIT_ID, event.getRetrofit_id());
                values.put(KEY_TYPE, event.getType());
                values.put(KEY_DAY, event.getDay());
                db.insert(TABLE_EVENT, null, values);

            }
        }
    }

    /*
 * getting all events
 * */

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();
        String selectQuery = "SELECT  * FROM " + TABLE_EVENT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                event.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                event.setSubtitle((c.getString(c.getColumnIndex(KEY_SUBTITLE))));
                event.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                event.setVenue((c.getString(c.getColumnIndex(KEY_VENUE))));
                event.setTime_start((c.getString(c.getColumnIndex(KEY_TIME_START))));
                event.setTime_end((c.getString(c.getColumnIndex(KEY_TIME_END))));
                event.setGoing((c.getInt(c.getColumnIndex(KEY_GOING))));
                event.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                event.setDay(c.getInt(c.getColumnIndex(KEY_DAY)));

                event.setRetrofit_id((c.getInt(c.getColumnIndex(KEY_RETROFIT_ID))));

                // adding to event list
                events.add(event);
            } while (c.moveToNext());
        }

        return events;
    }

    /*
 * getting all events under single tag
 * */
    public List<Event> getAllEventsByTag(String type, int day) {
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENT + "te WHERE te."
                + KEY_TYPE + " = '" + type + "' AND te."
                + KEY_DAY + " = '" + day + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                event.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                event.setSubtitle((c.getString(c.getColumnIndex(KEY_SUBTITLE))));
                event.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                event.setVenue((c.getString(c.getColumnIndex(KEY_VENUE))));
                event.setTime_start((c.getString(c.getColumnIndex(KEY_TIME_START))));
                event.setTime_end((c.getString(c.getColumnIndex(KEY_TIME_END))));
                event.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
                event.setGoing((c.getInt(c.getColumnIndex(KEY_GOING))));
                event.setDay((c.getInt(c.getColumnIndex(KEY_DAY))));

                event.setRetrofit_id((c.getInt(c.getColumnIndex(KEY_RETROFIT_ID))));

                // adding to event list
                events.add(event);
            } while (c.moveToNext());
        }

        return events;
    }

    public List<Event> getOrderedEventsByTag(String type, int day) {
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENT + " WHERE "
                + KEY_TYPE + " = '" + type + "' AND "
                + KEY_DAY + " = '" + day + "' ORDER BY " + KEY_TIME_START + "+0 " + " ASC";


        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {

            Log.e("check","yo");
            do {
                Event event = new Event();
                event.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                event.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                event.setSubtitle((c.getString(c.getColumnIndex(KEY_SUBTITLE))));
                event.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                event.setVenue((c.getString(c.getColumnIndex(KEY_VENUE))));
                event.setTime_start((c.getString(c.getColumnIndex(KEY_TIME_START))));
                event.setTime_end((c.getString(c.getColumnIndex(KEY_TIME_END))));
                event.setGoing((c.getInt(c.getColumnIndex(KEY_GOING))));
                event.setType((c.getString(c.getColumnIndex(KEY_TYPE))));
                event.setDay((c.getInt(c.getColumnIndex(KEY_DAY))));
                event.setRetrofit_id((c.getInt(c.getColumnIndex(KEY_RETROFIT_ID))));

                // adding to event list

                Log.e("check",event.toString());
                events.add(event);
            } while (c.moveToNext());
        }

        return events;
    }

    public List<Event> getOrderedEventsByDay(int day) {
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENT + " WHERE "
                + KEY_DAY +" = '"+day+"'";
        Log.e("Query order", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                event.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                event.setSubtitle((c.getString(c.getColumnIndex(KEY_SUBTITLE))));
                event.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                event.setVenue((c.getString(c.getColumnIndex(KEY_VENUE))));
                event.setTime_start((c.getString(c.getColumnIndex(KEY_TIME_START))));
                event.setTime_end((c.getString(c.getColumnIndex(KEY_TIME_END))));
                event.setGoing((c.getInt(c.getColumnIndex(KEY_GOING))));
                event.setType((c.getString(c.getColumnIndex(KEY_TYPE))));
                event.setDay((c.getInt(c.getColumnIndex(KEY_DAY))));
                event.setRetrofit_id((c.getInt(c.getColumnIndex(KEY_RETROFIT_ID))));

                // adding to event list
                events.add(event);
            } while (c.moveToNext());
        }

        return events;
    }
    public List<Event> getOrderedGoingEventsByDay(int day) {
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENT + " WHERE "
                + KEY_DAY + " = '" + day + "' AND "+ KEY_GOING + " = '"+ 1 + "' ORDER BY "+ KEY_TIME_START + "+0 " + "ASC";

        Log.e("Query order", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                event.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                event.setSubtitle((c.getString(c.getColumnIndex(KEY_SUBTITLE))));
                event.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                event.setVenue((c.getString(c.getColumnIndex(KEY_VENUE))));
                event.setTime_start((c.getString(c.getColumnIndex(KEY_TIME_START))));
                event.setTime_end((c.getString(c.getColumnIndex(KEY_TIME_END))));
                event.setGoing((c.getInt(c.getColumnIndex(KEY_GOING))));
                event.setType((c.getString(c.getColumnIndex(KEY_TYPE))));
                event.setDay((c.getInt(c.getColumnIndex(KEY_DAY))));
                event.setRetrofit_id((c.getInt(c.getColumnIndex(KEY_RETROFIT_ID))));
                // adding to event list
                events.add(event);
            } while (c.moveToNext());
        }

        return events;
    }

    /*
     * Updating a event
     */
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, event.getName());
        values.put(KEY_SUBTITLE, event.getSubtitle());
        values.put(KEY_DESCRIPTION, event.getDescription());
        values.put(KEY_TIME_START, event.getTime_start());
        values.put(KEY_TIME_END, event.getTime_end());
        values.put(KEY_GOING, event.getGoing());
        values.put(KEY_VENUE, event.getVenue());
        values.put(KEY_DAY, event.getDay());
        values.put(KEY_TYPE, event.getType());
        values.put(KEY_RETROFIT_ID, event.getRetrofit_id());


        // updating row
        return db.update(TABLE_EVENT, values, KEY_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
    }
    public void deleteEvent(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENT, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});


    }


    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
