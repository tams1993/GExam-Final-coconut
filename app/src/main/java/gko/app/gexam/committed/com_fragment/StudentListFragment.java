package gko.app.gexam.committed.com_fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gko.app.gexam.Database.OpenHelper;
import gko.app.gexam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentListFragment extends Fragment {


    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View layout = inflater.inflate(R.layout.fragment_student_list, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.studentList);
        adapter = new StudentAdapter(getActivity(),getAllStudent(),"phetsarath.ttf");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "phetsarath.ttf");

        return layout;
    }



//    public List<Student> getData() {
//
//        List<Student> data = new ArrayList<>();
////
//
//        String[] Student = {"tam", "pe", "dog", "cat"};
//
//        for (int i = 0; i < Student.length; i++) {
//
//
//            Student current = new Student();
//            current.student = Student[i];
//            data.add(current);
//
//        }
//
//        return data;
//    }


    public List<Student> getAllStudent(){
        List < Student > labels = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT * FROM student_unblock sub INNER JOIN students st ON sub.std_id = st._id INNER JOIN course c ON c._id = sub.course_id";


        OpenHelper openHelper = new OpenHelper(getActivity());
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        Log.d("GExam", "All Student =  " + String.valueOf(cursor.getCount()));

        // looping through all rows and adding to list
        if ( cursor.moveToFirst () ) {
            do {
                labels.add (new Student(cursor.getString(cursor.getColumnIndex("name"))));

            } while (cursor.moveToNext());





        }

        // closing connection
        cursor.close();
        db.close();

        // returning labels
        return labels;
    }



}
