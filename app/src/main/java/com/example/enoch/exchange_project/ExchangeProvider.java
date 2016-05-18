package com.example.enoch.exchange_project;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;


/**
 * Created by Enoch on 13-5-2016.
 */
public class ExchangeProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.enoch.exchange_project.exchangeprovider";
    private static final String BASE_PATH_TABLE_MEMBERS = ExchangeDataTablesReaderConstant.Members.TABLE_NAME_members;
    public static final Uri CONTENT_URI_TABLE_MEMBERS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_TABLE_MEMBERS);

    //uri constant matcher to determine which table and which request is to be worked with
    private static final int TABLE_MEMBERS_MATCHER = 1;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY , BASE_PATH_TABLE_MEMBERS, TABLE_MEMBERS_MATCHER);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        ExchangeProjectSqliteDatabase databaseHelper = new ExchangeProjectSqliteDatabase(getContext());
        database = databaseHelper.getWritableDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor mCursor = null;
        switch (uriMatcher.match(uri)) {
            case TABLE_MEMBERS_MATCHER:
                mCursor = database.query(ExchangeDataTablesReaderConstant.Members.TABLE_NAME_members, projection, selection, selectionArgs, null, null, null);
        }
        return mCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri checkUri = null;
        switch (uriMatcher.match(uri)){
            case TABLE_MEMBERS_MATCHER:
                long _newInsertedId = database.insert(ExchangeDataTablesReaderConstant.Members.TABLE_NAME_members,null,values);
                checkUri = ContentUris.withAppendedId(CONTENT_URI_TABLE_MEMBERS,_newInsertedId);
        }
        return checkUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
