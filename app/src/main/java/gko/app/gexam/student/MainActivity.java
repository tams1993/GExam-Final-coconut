package gko.app.gexam.student;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import gko.app.gexam.Database.Json_to_SQlite;
import gko.app.gexam.Database.OpenHelper;
import gko.app.gexam.R;
import gko.app.gexam.committed.Committy_login;
import gko.app.gexam.student.generator.AlertDialoge;


public class MainActivity extends ActionBarActivity {

    private Button btnLogin;
    private Spinner spinner,spnCom;
    private EditText edtUser, edtPass;
    private TextView txtCom, txtStatus, txtRegister;
    private Handler mHandler = new Handler();
    private CheckBox chbActiveCourse;
    private String strSubjectName, strStudentUser, strStudentPass, strTruePass, strStatus, strStd_id, strTest_code, strName,strSurname,strPhone, strEmail, strClass_name,
                    ALERT_ERROR_TITLE = "ການເຊື່ອມຕໍ່ຜິດພາດ!!!",ALERT_ERROR_MESSAGE = "ກະລຸນາກວດອຸປະກອນຂອງທ່ານວ່າເຊື່ອມຕໍ່ອິນເຕີເນັດຫຼືຍັງກ່ອນເຂົ້ານຳໃຊ້";


    private String std_id,subject_name;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private RelativeLayout mRoot;





    private Json_to_SQlite json_to_sQlite = new Json_to_SQlite();

    public static final String URL_JSON = "http://gexam.esy.es/GExam/db_connect.php";

    private Runnable decor_view_settings = new Runnable()
    {
        public void run()
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    };


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


        View decorView = getWindow().getDecorView();




        if (hasFocus) {

            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);


            mHandler.postDelayed(decor_view_settings, 500);

        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= 21) {
//
//            TransitionInflater inflater = TransitionInflater.from(this);
//            Transition transition = inflater.inflateTransition(R.transition.transtion_main_activity);
//            getWindow().setExitTransition(transition);
//
//        }

        if (Build.VERSION.SDK_INT >= 21) {

            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);

//            TransitionInflater inflater = TransitionInflater.from(this);
//            Transition transition = inflater.inflateTransition(R.transition.transtion_main_activity);
//            getWindow().setExitTransition(transition);


        }

        setContentView(R.layout.activity_main);



        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        spinner = (Spinner) findViewById(R.id.spinner);
        txtCom = (TextView) findViewById(R.id.txtCommittee);
        chbActiveCourse = (CheckBox) findViewById(R.id.chbActiveCourse);
        spnCom = (Spinner) findViewById(R.id.spnCom);
        txtStatus = (TextView) findViewById(R.id.txtStatus);



//        this.deleteDatabase("GExam.db");

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

//        if (mWifi.isConnected()) {

            if (Online()) {

//                new SimpleTask().execute(URL_JSON);
                SpinnerList();
                txtStatus.setText("ອອນໄລນ");

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        strSubjectName = parent.getItemAtPosition(position).toString();


                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {



                    }
                });


            } else {

                AlertDialoge.AlertConnection(this, ALERT_ERROR_TITLE, ALERT_ERROR_MESSAGE);



            }


//        } if (!mWifi.isConnected()){
//            txtStatus.setText("ອອຟໄລນ");
//            Toast.makeText(this,"plese connect to wifi",Toast.LENGTH_LONG).show();
//            AlertDialoge.AlertConnection(this, ALERT_ERROR_TITLE, ALERT_ERROR_MESSAGE);
//
//        }









        sp = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        editor = sp.edit();

        editor.clear();








        FontsOverride.setDefaultFont(this, "DEFAULT", "phetsarath.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "phetsarath.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "phetsarath.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "phetsarath.ttf");






        txtCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null);

                Intent intent = new Intent(MainActivity.this, Committy_login.class);
                startActivity(intent, compat.toBundle());

//                callLoginDialog();

            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://gexam.esy.es"));
                startActivity(browserIntent);

            }
        });





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                 strStudentUser = edtUser.getText().toString().trim();
                 strStudentPass = edtPass.getText().toString().trim();

                Log.d("GExam", "User " + strStudentUser);
                Log.d("GExam", "Pass " + strStudentPass);

                if ((strStudentUser.equals("")) | (strStudentPass.equals(""))) {

                    Toast.makeText(getApplicationContext(), "ກະລຸນາປ້ອນ ຊື່ຜູ້ໃຊ້ ແລະ ລະຫັດຜ່ານໃຫ້ຄົບຖ້ວນ", Toast.LENGTH_LONG).show();




                } else {

                    CheckUserPassword();





                }


            }
        });


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


         if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.ACTION_DOWN  || keyCode == KeyEvent.ACTION_UP)
        {
            mHandler.postDelayed(decor_view_settings, 500);
        }

        return super.onKeyDown(keyCode, event);

    }

    private class SimpleTask extends AsyncTask<String, Void, String> {


        ProgressDialog objPD;
        @Override
        protected void onPreExecute() {
            // Create Show ProgressBar


            objPD = new ProgressDialog(MainActivity.this);
            objPD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            objPD.setTitle("Loading...");
            objPD.setMessage("ກຳລັງໂຫຼດຂໍ້ມູນ...");
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

            json_to_sQlite.Student_Illegal(jsonString, MainActivity.this);

            json_to_sQlite.Classrooms(jsonString, MainActivity.this);
            json_to_sQlite.Course(jsonString, MainActivity.this);
            json_to_sQlite.Exam_Question(jsonString, MainActivity.this);
            json_to_sQlite.Exam_Rule(jsonString, MainActivity.this);
            json_to_sQlite.Questions(jsonString, MainActivity.this);
            json_to_sQlite.Students(jsonString, MainActivity.this);
            json_to_sQlite.Student_Answer(jsonString, MainActivity.this);
            json_to_sQlite.Student_Unblock(jsonString, MainActivity.this);
            json_to_sQlite.Subject(jsonString, MainActivity.this);
            json_to_sQlite.Teacher(jsonString, MainActivity.this);
            json_to_sQlite.Answer_Option(jsonString, MainActivity.this);



            objPD.dismiss();

            SpinnerList();

        }
    }

    private void SpinnerList() {

        String spnDefault = "Choose Course";

        List<SpinnerObject> label = getAllLabelsSpinner();
        ArrayAdapter<SpinnerObject> spinnerArrayAdapter = new ArrayAdapter<SpinnerObject>(this, android.R.layout.simple_spinner_item, label);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view

        spinner.setPrompt(spnDefault);


        spinner.setAdapter(spinnerArrayAdapter);



//        MyArrayAdapter adapter = new MyArrayAdapter(this, android.R.layout.simple_spinner_item, label);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);

    }

    public String JSON() {

        InputStream objInputStream = null;
        String strJSON = "";

        try {

            HttpClient objHttpClient = new DefaultHttpClient();


            HttpParams params = objHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 10*1000);
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




    public List< SpinnerObject> getAllLabelsSpinner(){
        List < SpinnerObject > labels = new ArrayList<SpinnerObject>();
        // Select All Query
            String selectQuery = "SELECT * FROM course c INNER JOIN subject s on c.subject_id = s._id INNER JOIN teacher t ON c.teacher_id = t._id INNER JOIN classrooms cl On c.class_id = cl._id where c.status =?";


        OpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{"1"});

        // looping through all rows and adding to list
        if ( cursor.moveToFirst () ) {
            do {

                labels.add (new SpinnerObject(cursor.getInt(0),cursor.getString(10),cursor.getString(cursor.getColumnIndex("name")),cursor.getInt(2),cursor.getInt(3),cursor.getInt(cursor.getColumnIndex("subject_id")),cursor.getInt(cursor.getColumnIndex("teacher_id")),cursor.getString(cursor.getColumnIndex("class"))));

            } while (cursor.moveToNext());





        }

//        show what inside List<SpinnerObject>

        int listSize = labels.size();

        for (int i = 0; i<listSize; i++){
            Log.d("Member name: ", String.valueOf(labels.get(i)));
        }










        // closing connection
        cursor.close();
        db.close();

        // returning labels
        return labels;
    }


    public String[] getStudent(String strUser, int course_id){
        // Select All Query
        String selectQuery = "SELECT * FROM student_unblock s INNER JOIN course c on s.course_id = c._id INNER JOIN students st on s.std_id = st._id INNER JOIN subject su ON c.subject_id = su._id where st.username =? and c._id =?";


        OpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{strUser, String.valueOf(course_id)});

        cursor.moveToFirst();

        String[] arrayData = null;



        if (cursor != null) {





            arrayData = new String[cursor.getColumnCount()];



            arrayData[0] = cursor.getString(cursor.getColumnIndex("status"));
            arrayData[1] = cursor.getString(cursor.getColumnIndex("student_id"));
            arrayData[2] = cursor.getString(cursor.getColumnIndex("test_code"));
            arrayData[3] = cursor.getString(cursor.getColumnIndex("name"));
            arrayData[4] = cursor.getString(cursor.getColumnIndex("surname"));
            arrayData[5] = cursor.getString(cursor.getColumnIndex("email"));
            arrayData[6] = cursor.getString(cursor.getColumnIndex("username"));
            arrayData[7] = cursor.getString(cursor.getColumnIndex("password"));
            arrayData[8] = cursor.getString(cursor.getColumnIndex("phone"));
            arrayData[9] = cursor.getString(cursor.getColumnIndex("class_id"));
            arrayData[10] = cursor.getString(cursor.getColumnIndex("subject_name"));

            arrayData[11] = cursor.getString(cursor.getColumnIndex("std_id"));

            int teacher_id = cursor.getInt(cursor.getColumnIndex("teacher_id"));
            int subject_id = cursor.getInt(cursor.getColumnIndex("subject_id"));

            editor.putInt("teacher_id", teacher_id);
            editor.putInt("subject_id", subject_id);

            editor.commit();





        }


        cursor.close();

        return arrayData;
    }

    private void CheckUserPassword() {


        try {

            int course_id_inSpinner = Integer.parseInt(String.valueOf(((SpinnerObject) spinner.getSelectedItem()).getCourse_id()));

            String arrayData[] = getStudent(strStudentUser,course_id_inSpinner);
            strTruePass = arrayData[7];
            strStatus = arrayData[0];
            strStd_id = arrayData[1];
            strTest_code = arrayData[2];
            strName = arrayData[3];
            strSurname = arrayData[4];
            strEmail = arrayData[5];
            strPhone = arrayData[8];
            strClass_name = arrayData[9];
            std_id = arrayData[11];


            StudentSharedPrefference();


            Log.d("GExam", "std_id = " + std_id);







            if (strStudentPass.equals(strTruePass)) {

                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
                Intent intent = new Intent(MainActivity.this, RuleActivity.class);
                startActivity(intent,compat.toBundle());

                int course_id = Integer.parseInt(String.valueOf(((SpinnerObject) spinner.getSelectedItem()).getCourse_id()));

                int interval_time = Integer.parseInt(String.valueOf(((SpinnerObject) spinner.getSelectedItem()).getIntervaltime()));
                int question_amount = Integer.parseInt(String.valueOf(((SpinnerObject) spinner.getSelectedItem()).getQuestionamount()));
                int teacher_id = Integer.parseInt(String.valueOf(((SpinnerObject) spinner.getSelectedItem()).getTeacher_id()));
                int subject_id = Integer.parseInt(String.valueOf(((SpinnerObject) spinner.getSelectedItem()).getSubject_id()));
                String subject_name = ((SpinnerObject) spinner.getSelectedItem()).getSubject_name();
                String classname = ((SpinnerObject) spinner.getSelectedItem()).getClassname();


                String teacher_name = String.valueOf(((SpinnerObject) spinner.getSelectedItem()).getTeachername());



                editor.putString("subject_name", subject_name);
                editor.putString("classname", classname);
                editor.putString("teacher_name", teacher_name);
                editor.putInt("course_id", course_id);
                editor.putInt("interval_time", interval_time);
                editor.putInt("question_amount", question_amount);
                editor.putInt("teacher_id", teacher_id);
                editor.putInt("subject_id", subject_id);


                editor.commit();


            } else {

                new AlertDialog.Builder(this)
                        .setTitle("ເກີດຂໍ້ຜິດພາດ!!!")
                        .setMessage("ບໍ່ມີຊື່ຜູ້ໃຊ້ ຫຼືລະຫັດບໍ່ຖືກຕ້ອງໃນ course ນີ້")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .show();

            }

        } catch (Exception e) {



            new AlertDialog.Builder(this)
                    .setTitle("ເກີດຂໍ້ຜິດພາດ!!!")
                    .setMessage("ບໍ່ມີຊື່ຜູ້ໃຊ້ ຫຼືລະຫັດບໍ່ຖືກຕ້ອງໃນ course ນີ້")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();


            Log.d("GExam", "Error Login  " + e.toString());

        }


    }



    public void StudentSharedPrefference() {



        editor.putString("Student_status", strStatus);
        editor.putString("Student_ID", strStd_id);
        editor.putString("Student_Test_Code", strTest_code);
        editor.putString("Student_Name", strName);
        editor.putString("Student_Surname", strSurname);
        editor.putString("Student_Email", strEmail);
        editor.putString("Student_Class_name", strClass_name);
        editor.putString("Student_Phone", strPhone);

        editor.putInt("std_id", Integer.parseInt(std_id));

        editor.commit();


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


    private class MyArrayAdapter extends ArrayAdapter<SpinnerObject> {


        Typeface myFont = Typeface.createFromAsset(getAssets(), "phetsarath.ttf");

        Context context;
        String[] objects;
        String firstElement;
        boolean isFirstTime;

        public MyArrayAdapter(Context context, int textViewResourceId, List<SpinnerObject> spinnerObjects) {
            super(context, textViewResourceId, spinnerObjects);
        }

        public TextView getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTypeface(myFont);
            return view;
        }

        public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTypeface(myFont);
            return view;
        }

        @Override
        public int getCount() {
            // don't display last item. It is used as hint.
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }


}
