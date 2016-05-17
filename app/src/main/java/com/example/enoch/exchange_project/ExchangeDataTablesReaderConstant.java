package com.example.enoch.exchange_project;

import android.provider.BaseColumns;


/**
 * Created by Enoch on 11-5-2016.
 */

//Here we create a Contract Class to help us use the Column names everywhere in our package
public final class ExchangeDataTablesReaderConstant {

    ExchangeDataTablesReaderConstant(){

    }

    //Table will to do columns are instantiated here
    public static abstract class Am_Willing_ToDO implements BaseColumns{
        public static final String TABLE_NAME_AM_WILLING_TO_DO = "am_willing_todo";
        public static final String COLUMN_members_id = "members_id";
        public static final String COLUMN_my_needs_id = "my_needs_id";
        public static final String COLUMN_other_skills = "other_skills";
        public static final String COLUMN_skills_ids = "skills_ids";
        public static final String COLUMN_accepted = "accepted";
    }


    //Table Members columns are instantiated here
    public static abstract class Members implements BaseColumns{
        public static final String TABLE_NAME_members = "members";
        public static final String COLUMN_name = "name";
        public static final String COLUMN_address = "address";
        public static final String COLUMN_city = "city";
        public static final String COLUMN_postcode = "postcode";
        public static final String COLUMN_email = "email";
        public static final String COLUMN_password = "password";
        public static final String COLUMN_createdDate = "createdDate";
        public static final String COLUMN_last_login_date = "last_login_date";

    }

    //Table My_Needs columns are instantiated here
    public static abstract class My_Needs implements BaseColumns{
        public static final String TABLE_NAME_my_needs = "my_needs",
                COLUMN_members_id= "members_id",
                COLUMN_title = "title",
                COLUMN_description = "description",
                COLUMN_image1 = "image1",
                COLUMN_image2 = "image2",
                COLUMN_image3 = "image3",
                COLUMN_skills_ids = "skills_ids",
                COLUMN_create_date = "create_date",
                COLUMN_end_date = "end_date",
                COLUMN_ended = "ended";
    }

    //Table profiles columns are instantiated here
    public static abstract class Profile implements BaseColumns{
        public static final String TABLE_NAME_profile = "profile",
                COLUMN_members_id = "members_id",
                COLUMN_picture = "picture",
                COLUMN_biography = "biography";

    }

    //Table skills columns are instantiated here
    public static abstract class Skills implements BaseColumns{
        public static final String TABLE_NAME_skills = "skills",
                COLUMN_members_id = "members_id",
                COLUMN_skill = "skill",
                COLUMN_description = "description";
    }
}


