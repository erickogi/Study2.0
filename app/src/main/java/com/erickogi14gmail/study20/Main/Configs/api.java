package com.erickogi14gmail.study20.Main.Configs;

/**
 * Created by kimani kogi on 5/13/2017.
 */

public class api {

    // public final static String API_KEY = "a063b2f6f85b4df6b9bce476d25f3e60";
    public final static String COURSES_END_POINT = "http://erickogi.co.ke/study/api/v1/?action=get_courses";
    public final static String ASSIGNMENTS_END_POINT = "http://erickogi.co.ke/study/api/v1/?action=get_assignments";
    public final static String ASSIGNMENT_END_POINT = "http://erickogi.co.ke/study/api/v1/?action=get_assignments&course_code=";


    public final static String CONTENT_END_POINT = "http://erickogi.co.ke/study/api/v1/?action=get_content&course_code=";
//INTENTS KEYS
    public final static String COURSE_CODE = "course_code";
    public final static String ASSIGNMENT_ID = "assignment_id";
    public final static String POST_URL = "post";


//    public final static int ALL_TECH_SOURCES_PARSING_CODE = 200;
//
//    //COURSES
//    public final static String KEY_CATEGORY_TECH = "technology";
//    public final static String KEY_URL_TAG = "key_url";
//    public final static String KEY_URL_TO_IMAGE_TAG = "key_url_to_image";
//
//
//    //CONTENT
//    public final static String KEY_SOURCE_ID = "id";
//    public final static String KEY_SOURCE_NAME = "name";
//    public final static String KEY_SOURCE_DESCRIPTION = "description";
//    public final static String KEY_SOURCE_URL = "url";
//    public final static String KEY_SOURCE_CATEGORY = "category";
//    public final static String KEY_SOURCE_LANGUAGE = "language";
//    public final static String KEY_SOURCE_COUNTRY = "country";


    public static final String LOGIN_URL = "http://erickogi.co.ke/study/api/v1/login.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAME = "name";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "fdaradlogin";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String KEY_SHARED_PREF = "user_key";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";


////////NEWS API news


    public final static String API_KEY = "a063b2f6f85b4df6b9bce476d25f3e60";
    public final static String ARTICLES_END_POINT = "https://newsapi.org/v1/articles?source=";
    public final static String SOURCES_END_POINT = "https://newsapi.org/v1/sources";

    public final static int ALL_SORUCES_PARSING_CODE = 100;
    public final static int ALL_TECH_SOURCES_PARSING_CODE = 200;

    public final static String KEY_CATEGORY_TECH = "technology";
    public final static String KEY_URL_TAG = "key_url";
    public final static String KEY_URL_TO_IMAGE_TAG = "key_url_to_image";


    //SOURCE
    public final static String KEY_SOURCE_ID = "id";
    public final static String KEY_SOURCE_NAME = "name";
    public final static String KEY_SOURCE_DESCRIPTION = "description";
    public final static String KEY_SOURCE_URL = "url";
    public final static String KEY_SOURCE_CATEGORY = "category";
    public final static String KEY_SOURCE_LANGUAGE = "language";
    public final static String KEY_SOURCE_COUNTRY = "country";


    //ARTICLES
    public final static String KEY_ARTICLE_AUTOR = "author";
    public final static String KEY_ARTICLE_TITLE = "title";
    public final static String KEY_ARTICLE_DESCRIPTION = "description";
    public final static String KEY_ARTICLE_URL = "url";
    public final static String KEY_ARTICLE_URLTOIMAGE = "urlToImage";
    public final static String KEY_ARTICLE_PUBLISHEDAT = "publishedAt";
    //REQUEST CODES
    public final static int KEY_ARTICLE_REQUEST = 200;
    public final static int KEY_SOURCES_REQUEST = 300;

    //CATEGORY INTENTS KEYS
    public static final String KEY_CATEGORY_INTENT = "com.erickogi14gmail.demo_news_api_android1_category";
    public static final String KEY_CATEGORY_LABEL = "com.erickogi14gmail.demo_news_api_android1_label";


    public final static String KEY_CATEGORY_BUSINESS = "business";
    public final static String KEY_CATEGORY_ENTERTAINMENT = "entertainment";
    public final static String KEY_CATEGORY_POLITICS = "politics";
    public final static String KEY_CATEGORY_SPORTS = "sport";
    public final static String KEY_CATEGORY_MUSIC = "music";
    public final static String KEY_CATEGORY_SCIENCE = "science-and-nature";


    ///
}
