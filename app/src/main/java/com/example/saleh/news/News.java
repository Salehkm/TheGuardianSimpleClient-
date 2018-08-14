package com.example.saleh.news;

/**
 * Created by saleh on 06/06/18.
 */

public class News {
    private String mTitle;
    private String mSection;
    private String mTimeInMilliseconds;
    private String mUrl;
    private String mAuthor;
    public News(String title,String author,String section,String timeInMilliseconds ,String url) {
        mTitle=title;
        mAuthor=author;
        mSection=section;
        mTimeInMilliseconds=timeInMilliseconds;
        mUrl=url;
    }
    public String  getAuthor() {return mAuthor;}
    public String  getTitle() {return mTitle;}
    public String  getSection() {return mSection;}
    public String  getUrl() {return mUrl;}
    public String  getTimeInMilliseconds() {return mTimeInMilliseconds;}

}

