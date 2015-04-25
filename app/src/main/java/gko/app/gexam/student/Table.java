package gko.app.gexam.student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gko.app.gexam.Database.OpenHelper;
import gko.app.gexam.student.Rule;

/**
 * Created by MR.T on 4/2/2015.
 */
public class Table {

    private OpenHelper openHelper;
    private SQLiteDatabase writeSQlite, readSQlite;
    public static final String COLUMN_CLASSROOMS_ID = "_id";
    public static final String COLUMN_CLASS_NAME = "class";

    public static final String COLUMN_COURSE_ID = "_id";
    public static final String COLUMN_COURSE_DATE = "date";
    public static final String COLUMN_COURSE_INTERVAL_TIME = "interval_time";
    public static final String COLUMN_COURSE_QUESTION_AMOUNT = "question_amount";
    public static final String COLUMN_COURSE_TEST_CODE = "test_code";
    public static final String COLUMN_COURSE_STATUS = "status";
    public static final String COLUMN_TEACHER_NAME = "teacher_name";
    public static final String COLUMN_SUBJECT_CODE = "subject_code";

    public static final String COLUMN_EXAM_QUESTION_ID = "_id";
    public static final String COLUMN_EXAM_QUESTION_TEST_CODE = "test_id";
    public static final String COLUMN_EXAM_QUESTION_QUESTION_ID = "question_id";
    public static final String COLUMN_EXAM_QUESTION_STUDENT_ANS_ID = "student_ans_id";


    public static final String COLUMN_SUBJECT_ID= "_id";
    public static final String COLUMN_SUBJECT_SUBJECT_NAME = "subject_name";
    public static final String COLUMN_SUBJECT_CREDIT = "credit";

    public static final String COLUMN_QUESTION_ID = "_id";
    public static final String COLUMN_QUESTION_QUESTION = "question";
    public static final String COLUMN_QUESTION_PHOTO = "photo";
    public static final String COLUMN_QUESTION_SUBJECT_CODE= "subject_code";
    public static final String COLUMN_QUESTION_TEAHCER_NAME= "teacher_name";
    public static final String COLUMN_QUESTION_ANS_OPTION_ID= "ans_option_id";

    public static final String COLUMN_EXAM_RULE_ID = "_id";
    public static final String COLUMN_EXAM_RULE_TEST_CODE = "test_code";
    public static final String COLUMN_EXAM_RULE_RULE = "rule";
    public static final String COLUMN_EXAM_RULE_TEACHER_NAME= "teacher_name";

    public static final String COLUMN_STUDENT_ANSWER_ID = "_id";
    public static final String COLUMN_STUDENT_ANSWER_ANS_OPTION_ID = "question_id";
    public static final String COLUMN_STUDENT_ANSWER_QUESTION_ID = "ans_option_id";

    public static final String COLUMN_STUDENT_UNBLOCK_ID = "_id";
    public static final String COLUMN_STUDENT_UNBLOCK_STD_ID = "std_id";
    public static final String COLUMN_STUDENT_UNBLOCK_TESTCODE= "test_code";

    public static final String COLUMN_STUDENT_ILLEGAL_ID = "_id";
    public static final String COLUMN_STUDENT_ILLEGAL_STATUS = "status";
    public static final String COLUMN_STUDENT_ILLEGAL_STD_ID = "std_id";
    public static final String COLUMN_STUDENT_ILLEGAL_TEST_ID = "test_code";



    public static final String COLUMN_STUDENTS_ID = "_id";
    public static final String COLUMN_STUDENTS_NAME = "name";
    public static final String COLUMN_STUDENTS_SURNAME = "surname";
    public static final String COLUMN_STUDENTS_PHONE = "phone";
    public static final String COLUMN_STUDENTS_EMAIL = "email";
    public static final String COLUMN_STUDENTS_USERNAME = "username";
    public static final String COLUMN_STUDENTS_PASSWORD = "password";
    public static final String COLUMN_STUDENTS_PHOTO = "photo";
    public static final String COLUMN_STUDENTS_STUDENT_ID = "student_id";
    public static final String COLUMN_STUDENTS_STUDENT_CLASS_NAME = "class_name";


    public static final String COLUMN_TEACHER_ID = "_id";
    public static final String COLUMN_TEACHER_TEACHER_NAME = "name";
    public static final String COLUMN_TEACHER_SURNAME = "surname";
    public static final String COLUMN_TEACHER_PHONE = "phone";
    public static final String COLUMN_TEACHER_EMAIL = "email";
    public static final String COLUMN_TEACHER_USERNAME = "username";
    public static final String COLUMN_TEACHER_PASSWORD = "password";
    public static final String COLUMN_TEACHER_INVITE_CODE = "invite_code";
    public static final String COLUMN_TEACHER_ACTIVE = "active";



    public Table(Context context) {

        openHelper = new OpenHelper(context);
        writeSQlite = openHelper.getWritableDatabase();
        readSQlite = openHelper.getReadableDatabase();

    }

    public long addClassrooms(int strClassroomsID, String strClassName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CLASSROOMS_ID, strClassroomsID);
        contentValues.put(COLUMN_CLASS_NAME, strClassName);



        return writeSQlite.insert("classrooms", null, contentValues);


    }

    public long addStudent_unblock(int id, String std_id, String test_code) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUDENT_UNBLOCK_ID, id);
        contentValues.put(COLUMN_STUDENT_UNBLOCK_STD_ID, std_id);
        contentValues.put(COLUMN_STUDENT_UNBLOCK_TESTCODE, test_code);



        return writeSQlite.insert("student_unblock", null, contentValues);


    }

    public long addStudent_Answer(int student_answer_id, int question_id, int ans_option_id) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUDENT_ANSWER_ID, student_answer_id);
        contentValues.put(COLUMN_STUDENT_ANSWER_QUESTION_ID, question_id);
        contentValues.put(COLUMN_STUDENT_ANSWER_ANS_OPTION_ID, ans_option_id);



        return writeSQlite.insert("student_answer", null, contentValues);


    }

    public long addStudent_Illegal(int id, int status, String std_id, int test_code) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUDENT_ILLEGAL_ID, id);
        contentValues.put(COLUMN_STUDENT_ILLEGAL_STATUS, status);
        contentValues.put(COLUMN_STUDENT_ILLEGAL_STD_ID, std_id);
        contentValues.put(COLUMN_STUDENT_ILLEGAL_TEST_ID, test_code);



        return writeSQlite.insert("student_illegal", null, contentValues);


    }

    public long addSubject(int id, String subject_name, int credit, String test_code) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SUBJECT_ID, id);
        contentValues.put(COLUMN_SUBJECT_SUBJECT_NAME, subject_name);
        contentValues.put(COLUMN_SUBJECT_CREDIT, credit);
        contentValues.put(COLUMN_SUBJECT_CODE, test_code);



        return writeSQlite.insert("subject", null, contentValues);


    }

    public long addCourse(int course_id, String strdate, String strInterval_time, int question_amount, String test_code, int status, String teacher_name, String subject_code) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COURSE_ID, course_id);
        contentValues.put(COLUMN_COURSE_DATE, strdate);
        contentValues.put(COLUMN_COURSE_INTERVAL_TIME, strInterval_time);
        contentValues.put(COLUMN_COURSE_QUESTION_AMOUNT, question_amount);
        contentValues.put(COLUMN_COURSE_TEST_CODE, test_code);
        contentValues.put(COLUMN_COURSE_STATUS, status);
        contentValues.put(COLUMN_TEACHER_NAME, teacher_name);
        contentValues.put(COLUMN_SUBJECT_CODE, subject_code);



        return writeSQlite.insert("course", null, contentValues);


    }

    public long addTeacher(int id, String name, String surname, int phone,String email, String username, String password, String invite_code, int active) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEACHER_ID, id);
        contentValues.put(COLUMN_TEACHER_TEACHER_NAME, name);
        contentValues.put(COLUMN_TEACHER_SURNAME, surname);
        contentValues.put(COLUMN_TEACHER_PHONE, phone);
        contentValues.put(COLUMN_TEACHER_EMAIL, email);
        contentValues.put(COLUMN_TEACHER_USERNAME, username);
        contentValues.put(COLUMN_TEACHER_PASSWORD, password);
        contentValues.put(COLUMN_TEACHER_INVITE_CODE, invite_code);
        contentValues.put(COLUMN_TEACHER_ACTIVE, active);



        return writeSQlite.insert("teacher", null, contentValues);


    }

    public long addExam_Question(int id, int test_code, int question_id, int student_ans_id) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXAM_QUESTION_ID, id);
        contentValues.put(COLUMN_EXAM_QUESTION_TEST_CODE, test_code);
        contentValues.put(COLUMN_EXAM_QUESTION_QUESTION_ID, question_id);
        contentValues.put(COLUMN_EXAM_QUESTION_STUDENT_ANS_ID, student_ans_id);



        return writeSQlite.insert("exam_question", null, contentValues);


    }

public long addExam_Rule(int id, int test_code, String rule, String teacher_name) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXAM_RULE_ID, id);
        contentValues.put(COLUMN_EXAM_RULE_TEST_CODE, test_code);
        contentValues.put(COLUMN_EXAM_RULE_RULE, rule);
        contentValues.put(COLUMN_EXAM_RULE_TEACHER_NAME, teacher_name);



        return writeSQlite.insert("exam_rule", null, contentValues);


    }

public long addQuestion(int id, String question, String photo, String subject_code, String teacher_name, String ans_option_id) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_QUESTION_ID, id);
        contentValues.put(COLUMN_QUESTION_PHOTO, photo);
        contentValues.put(COLUMN_QUESTION_QUESTION, question);
        contentValues.put(COLUMN_QUESTION_SUBJECT_CODE, subject_code);
        contentValues.put(COLUMN_QUESTION_TEAHCER_NAME, teacher_name);
        contentValues.put(COLUMN_QUESTION_ANS_OPTION_ID, ans_option_id);



        return writeSQlite.insert("questions", null, contentValues);


    }

public long addStudents(int id, String name, String surname, int phone, String email, String username, String password, String photo, String student_id, String class_name) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUDENTS_ID, id);
        contentValues.put(COLUMN_STUDENTS_NAME, photo);
        contentValues.put(COLUMN_STUDENTS_SURNAME, surname);
        contentValues.put(COLUMN_STUDENTS_PHONE, phone);
        contentValues.put(COLUMN_STUDENTS_EMAIL, email);
        contentValues.put(COLUMN_STUDENTS_USERNAME, username);
        contentValues.put(COLUMN_STUDENTS_PASSWORD, password);
        contentValues.put(COLUMN_STUDENTS_PHOTO, photo);
        contentValues.put(COLUMN_STUDENTS_STUDENT_ID, student_id);
        contentValues.put(COLUMN_STUDENTS_STUDENT_CLASS_NAME, class_name);



        return writeSQlite.insert("students", null, contentValues);


    }

    public String[] AuthenStudent(String strUser) {


        try {


            String arrayData[] = null;
            Cursor objCursor = readSQlite.query("students", new String[]
                    {COLUMN_STUDENTS_ID,COLUMN_STUDENTS_USERNAME,COLUMN_STUDENTS_PASSWORD},COLUMN_STUDENTS_USERNAME+"=?",new String[]{String.valueOf(strUser)},null,null,null,null);

            if (objCursor != null) {

                if (objCursor.moveToFirst()) {

                    arrayData = new String[objCursor.getColumnCount()];

                    arrayData[0] = objCursor.getString(0);
                    arrayData[1] = objCursor.getString(1);
                    arrayData[2] = objCursor.getString(2);

                }

            }
            objCursor.close();
            return arrayData;

        } catch (Exception e) {
            Log.d("Error Authen", "No user in database");
            return null;
        }

    }

    public List<Rule> rule(Context context) {




            String selectQuery = "SELECT rule FROM exam_rule r INNER JOIN course c on r.test_code = c.test_code";




            OpenHelper openHelper = new OpenHelper(context);
            openHelper.getWritableDatabase();
            Cursor c = writeSQlite.rawQuery(selectQuery,null);
            Log.e("Data Count", String.valueOf(c.getCount()));


        List<Rule> RuleArray = new ArrayList<>();
        c.moveToFirst();

        while (!c.isAfterLast()) {

            Rule current = new Rule();
            current.title = c.getString(c.getColumnIndex("rule"));

            RuleArray.add(current);

            c.moveToNext();


        }

        Log.e("Data Count", String.valueOf(RuleArray));

        return RuleArray;


    }



    public Cursor spinnerList() {




        String selectQuery = "SELECT * FROM course c JOIN subject s on c.subject_code = s.subject_code where c.subject_code=?";




        Cursor c = writeSQlite.rawQuery(selectQuery,null);

        if (c != null) {
            c.moveToFirst();

        }

        Log.e("spinnerList", String.valueOf(c.getCount()));


//        List<String> spinnerArray = new ArrayList<>();
//        c.moveToFirst();
//
//        while (!c.isAfterLast()) {
//
//
//
//
//            spinnerArray.add( c.getString(c.getColumnIndex("subject_name")));
//
//            c.moveToNext();
//
//
//        }
//
//        Log.e("spinnerList", String.valueOf(spinnerArray));

        return c;


    }



    public Cursor ReadAllDataCourse() {

        Cursor objCursor = readSQlite.query("course",
                new String[]{COLUMN_COURSE_ID,COLUMN_COURSE_TEST_CODE,COLUMN_COURSE_INTERVAL_TIME,COLUMN_COURSE_QUESTION_AMOUNT,COLUMN_COURSE_STATUS,COLUMN_SUBJECT_CODE},
                null,null,null,null,null);
        if (objCursor != null) {
            objCursor.moveToFirst();

        }

        return objCursor;

    }   //  end of ReadAllDataCourse


}