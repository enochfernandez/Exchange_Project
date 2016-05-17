package com.example.enoch.exchange_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.enoch.exchange_project.ExchangeDataTablesReaderConstant.Members;

import java.security.SecureRandom;

/**
 * Created by Enoch on 11-5-2016.
 */
public class ExchangeProjectSqliteDatabase extends SQLiteOpenHelper {

    private static final String COMMA_SEP = ",";
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Community_Exchange.db";
    private String create_table_members = "CREATE TABLE " + Members.TABLE_NAME_members + " (" +
            Members._ID  +   " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            Members.COLUMN_name +  " TEXT(100) DEFAULT NULL" + COMMA_SEP +
            Members.COLUMN_address +   " TEXT(200)"+ COMMA_SEP +
            Members.COLUMN_city +  " TEXT(90)" + COMMA_SEP +
            Members.COLUMN_postcode + " TEXT(10)" + COMMA_SEP +
            Members.COLUMN_email + " TEXT(100)" + COMMA_SEP +
            Members.COLUMN_password +  " TEXT(100)" + COMMA_SEP +
            Members.COLUMN_createdDate + " TEXT(100)" + COMMA_SEP +
            Members.COLUMN_last_login_date + " TEXT(100)"
    + ");";

    private String triggerCreateDateDrop = "DROP TRIGGER IF EXISTS newCreateDateForMembersTable;";

    private String triggerCreateDate = "CREATE TRIGGER newCreateDateForMembersTable AFTER INSERT ON " + Members.TABLE_NAME_members + " BEGIN UPDATE Members SET "+ Members.COLUMN_createdDate +"=datetime('now'); END;";

    private static final String SQL_DELETE_ENTRIES_MEMBERS_TABLE =
            "DROP TABLE IF EXISTS " + Members.TABLE_NAME_members;

    public ExchangeProjectSqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table_members);
        db.execSQL(triggerCreateDate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        db.execSQL(SQL_DELETE_ENTRIES_MEMBERS_TABLE);
        db.execSQL(triggerCreateDateDrop);

        onCreate(db);
    }
}
