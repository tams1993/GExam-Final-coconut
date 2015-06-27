package gko.app.gexam.student;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import gko.app.gexam.Database.Json_to_SQlite;
import gko.app.gexam.R;
import gko.app.gexam.student.generator.AlertDialoge;

public class Splashscreen extends ActionBarActivity {

    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;
    private final int SPLASH_DISPLAY_LENGTH = 3000;


    private Json_to_SQlite json_to_sQlite = new Json_to_SQlite();

    public static final String URL_JSON = "http://gexam.esy.es/GExam/db_connect.php";

    private String ALERT_ERROR_TITLE = "??????????????????!!!",ALERT_ERROR_MESSAGE = "?????????????????????????????????????????????????????????????????";



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();

        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }


    public void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        FontsOverride.setDefaultFont(this, "DEFAULT", "phetsarath.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "phetsarath.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "phetsarath.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "phetsarath.ttf");


//        if (Online()) {
//
//            this.deleteDatabase("GExam.db");
//            new SimpleTask().execute(URL_JSON);
//
//
//
//        } else {
//
//            AlertDialoge.AlertConnection(this, ALERT_ERROR_TITLE, ALERT_ERROR_MESSAGE);
//
//
//
//        }




        handler = new Handler();

        runnable = new Runnable() {
            public void run() {

                if (Online()) {

                    Splashscreen.this.deleteDatabase("GExam.db");
                    new SimpleTask().execute(URL_JSON);



                } else {

                    AlertDialog dialog = new AlertDialog.Builder(Splashscreen.this).setMessage((Html.fromHtml(getString(R.string.progress_message)))).setTitle((Html.fromHtml(getString(R.string.progress_title))))
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).show();
//                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
//                    Typeface face= Typeface.createFromAsset(getAssets(), "phetsarath.ttf");
//                    textView.setTypeface(face);

//                    AlertDialoge.AlertConnection(Splashscreen.this, ALERT_ERROR_TITLE, ALERT_ERROR_MESSAGE);



                }

            }
        };
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }


    private class SimpleTask extends AsyncTask<String, Void, String> {


        ProgressDialog objPD;
        @Override
        protected void onPreExecute() {
            // Create Show ProgressBar


            objPD = new ProgressDialog(Splashscreen.this);
            objPD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            objPD.setTitle("Loading...");
            objPD.setMessage(Html.fromHtml(getString(R.string.progress_txt)));
            objPD.setCancelable(false);
            objPD.setIndeterminate(false);


            objPD.show();

        }

        protected String doInBackground(String... urls) {



            return JSON();
        }

        protected void onPostExecute(String jsonString)  {
            // Dismiss ProgressBar
//            Log.d("GExam", jsonString);
//            Toast.makeText(MainActivity.this, jsonString, Toast.LENGTH_LONG).show();

            json_to_sQlite.Student_Illegal(jsonString, Splashscreen.this);

            json_to_sQlite.Classrooms(jsonString, Splashscreen.this);
            json_to_sQlite.Course(jsonString, Splashscreen.this);
            json_to_sQlite.Exam_Question(jsonString, Splashscreen.this);
            json_to_sQlite.Exam_Rule(jsonString, Splashscreen.this);
            json_to_sQlite.Questions(jsonString, Splashscreen.this);
            json_to_sQlite.Students(jsonString, Splashscreen.this);
            json_to_sQlite.Student_Answer(jsonString, Splashscreen.this);
            json_to_sQlite.Student_Unblock(jsonString, Splashscreen.this);
            json_to_sQlite.Subject(jsonString, Splashscreen.this);
            json_to_sQlite.Teacher(jsonString, Splashscreen.this);
            json_to_sQlite.Answer_Option(jsonString, Splashscreen.this);


            Intent intent = new Intent(Splashscreen.this, MainActivity.class);
            startActivity(intent);

            finish();
            objPD.dismiss();




        }
    }



    public String JSON() {

        InputStream objInputStream = null;
        String strJSON = "";

        try {

            HttpClient objHttpClient = new DefaultHttpClient();


            HttpParams params = objHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
            HttpConnectionParams.setSoTimeout(params, 10000);

            HttpPost objHttpPost = new HttpPost(URL_JSON);



            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();

            Log.d("GExam", "Connected HTTP Success !");


        } catch (Exception e) {





            Log.d("GExam", "Error Connect to : " + e.toString());
        }



        //create strJSON
        try {

            BufferedReader objBufferesReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStrBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferesReader.readLine()) != null) {
                objStrBuilder.append(strLine);
            }

            objInputStream.close();
            strJSON = objStrBuilder.toString();

            Log.d("Emergency", "Connected JSON Success !");


        } catch (Exception e) {

            Log.d("Emergency", "Error Convert To JSON :" + e.toString());
        }


        return strJSON;

    }
    private boolean Online() {

        Boolean result = false;

        ConnectivityManager objConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo objNetworkInfo = objConnectivityManager.getActiveNetworkInfo();

        if (objNetworkInfo != null && objNetworkInfo.isConnected()) {

            result = true;

        } else {
            result = false;
        }

        return result;
    }


}



