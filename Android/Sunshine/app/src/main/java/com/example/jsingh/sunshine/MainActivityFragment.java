package com.example.jsingh.sunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.net.ssl.HttpsURLConnection;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayAdapter arrayAdapter;
    public MainActivityFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.refresh:
                FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
                fetchWeatherTask.execute("43434");
                arrayAdapter.notifyDataSetChanged();
                return true;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);
        ArrayList arrayList = new ArrayList();

        arrayAdapter = new ArrayAdapter(getContext(),R.layout.list_item,arrayList);

        ListView listView = (ListView) listFragment.findViewById(R.id.listView);

        listView.setAdapter(arrayAdapter);


        return listFragment;
    }


    //Handles data oin the background
    public class FetchWeatherTask extends AsyncTask<String,Void,String[]>{

        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection httpsURLConnection=null;
            BufferedReader bufferedReader = null;
            String jsonString="";

            try {

              String apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY;

                //Build a URL
                if(params.length==0){
                    return null;
                }

                final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";

                final String FORMAT_PARAM = "mode";
                final String COUNT_PARAM = "cnt";
                final String UNIT_PARAM = "units";
                final String POSTAL_CODE_PARAM = "q";


                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(POSTAL_CODE_PARAM,params[0])
                        .appendQueryParameter(FORMAT_PARAM,"json")
                        .appendQueryParameter(UNIT_PARAM,"metric")
                        .appendQueryParameter(COUNT_PARAM,"7")
                        .appendQueryParameter("APPID",apiKey).build();

                URL url = new URL(uri.toString());

                Log.v("====+++++++++++++====",url.toString());
                //Establish an HTTP URL CONNECTION with the URL created
                httpsURLConnection = (HttpURLConnection) url.openConnection();
                //HOW TO RECEIVE DATA FROM THE HTTP CONNECTION
                httpsURLConnection.setRequestMethod("GET");
                //START THE CONNECTION
                httpsURLConnection.connect();

                //GET DATA FROM HTTP CONNECTION AS An INPUT STREAM
                InputStream inputStream = httpsURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();


                if(inputStream==null){
                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line=bufferedReader.readLine())!=null){
                    buffer.append(line);
                }

                if(buffer.length()==0){
                    return null;
                }

                jsonString = buffer.toString();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(httpsURLConnection!=null){
                    httpsURLConnection.disconnect();
                }
                if(bufferedReader!=null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try{
                return getWeatherDataFromJson(jsonString,7);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null ;
        }

        private String getReadableDateString(long time){
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if(strings!=null){
                arrayAdapter.addAll(strings);
            }
        }

        /**
         * Prepare the weather high/lows for presentation.
         */
        private String formatHighLows(double high, double low) {
            // For presentation, assume the user doesn't care about tenths of a degree.
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            Calendar date = Calendar.getInstance();

            String[] resultStrs = new String[numDays];
            for(int i = 0; i < weatherArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String day;
                String description;
                String highAndLow;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);
                date.add(Calendar.DATE,i);
                day = getReadableDateString(date.getTimeInMillis());

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);

                highAndLow = formatHighLows(high, low);
                resultStrs[i] = day + " - " + description + " - " + highAndLow;
            }

            for (String s : resultStrs) {
                Log.v("====", "Forecast entry: " + s);
            }
            return resultStrs;

        }
    }

}
