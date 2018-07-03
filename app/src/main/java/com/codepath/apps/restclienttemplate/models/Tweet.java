package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Tweet {
    // list out the attributes
    public String body;
    public long uid; //database ID for tweet
    public String screen_name;
    public User user;
    public String createdAt;
    public String relative_time;

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            String purge[] = {" minutes ago", " minute ago", " hours ago", " hour ago"};
            for (int i=0; i<purge.length; i++){
                if (purge[i].startsWith("m")){
                    relativeDate = relativeDate.replace(purge[i], "m");
                } else {
                    relativeDate = relativeDate.replace(purge[i], "h");
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // deserialize JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.relative_time = tweet.getRelativeTimeAgo(tweet.createdAt);

        return tweet;
    }
}