package com.example.saleh.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saleh on 06/06/18.
 */

    public final class QueryUtils {
        private static final String LOG_TAG = QueryUtils.class.getSimpleName();

        private QueryUtils() {
        }

        public static List<News> fetchNewsData(String requestUrl) {
            URL url = createUrl(requestUrl);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
            List<News> news = extractFeatureFromJson(jsonResponse);

            // Return the list of {@link Earthquake}s
            return news;
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private static URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Problem building the URL ", e);
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private static String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";

            // If the URL is null, then return early.
            if (url == null) {
                return jsonResponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // Closing the input stream could throw an IOException, which is why
                    // the makeHttpRequest(URL url) method signature specifies than an IOException
                    // could be thrown.
                    inputStream.close();
                }
            }
            return jsonResponse;
        }
        private static String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
        private static List<News> extractFeatureFromJson(String newsJSON) {
            if (TextUtils.isEmpty(newsJSON)) {
                return null;
            }
            List<News> news1 = new ArrayList<>();
            try {
                 String author;
                JSONObject baseJsonResponse = new JSONObject(newsJSON);
                JSONObject response = baseJsonResponse.getJSONObject("response");
                JSONArray newsArray = response.getJSONArray("results");
                for (int i = 0; i < newsArray.length(); i++) {
                    JSONObject currentNews = newsArray.getJSONObject(i);
                    JSONArray tagsArray = currentNews.getJSONArray("tags");
                    if(tagsArray.length() != 0) {
                      JSONObject currenttag =tagsArray.getJSONObject(0);
                       author = currenttag.getString("webTitle");

                    }
                    else {
                        author ="no author";                    }

                    String title1 = currentNews.getString("webTitle");
                    String section1 = currentNews.getString("sectionName");
                    String time = currentNews.getString("webPublicationDate");
                    String url = currentNews.getString("webUrl");
                    News news = new News(title1,author, section1, time, url);

                    // Add the new {@link Earthquake} to the list of earthquakes.
                    news1.add(news);
                }

            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the news JSON results", e);
            }
            return news1;
        }

    }

