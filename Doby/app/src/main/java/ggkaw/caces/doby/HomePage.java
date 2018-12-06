package ggkaw.caces.doby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import java.util.Vector;

public class HomePage extends AppCompatActivity {

    public CourseWrapper cwrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        // Check if file where stuff is saved exists
        // If it does, read from file
        // otherwise, do nuttin

        Intent intent = getIntent();
        String checkFlag = intent.getStringExtra("Flag");
        try {
            if(checkFlag == null) {
                cwrap = new CourseWrapper();
                // this is where we will load from file if file exists
            }
            else { // coming from another page
                cwrap = (CourseWrapper)intent.getSerializableExtra("CourseWrap");
            }
        } catch (Throwable t) {

        }

    }// end of onCreate

    public void LaunchNewClassPage(View view) {
        Intent NewClassIntent = new Intent(this, NewClassPage.class);

        NewClassIntent.putExtra("Course Wrapper", cwrap); // Passing course class from this page to home page ...

        startActivity(NewClassIntent);
    }

    public void LaunchAssignmentPage(View view) {
        Intent NewAssignIntent = new Intent(this, AssignmentPage.class);
        // all we need to pass is string of Course names

        NewAssignIntent.putExtra("Course Wrapper", cwrap);

        Vector<String> courseNames = new Vector<String>();

        for(int i = 0; i < cwrap.allCourses.size(); i++) {
            courseNames.add(cwrap.allCourses.elementAt(i).name);
        }

        String[] stringNames = courseNames.toArray(new String[courseNames.size()]);

        NewAssignIntent.putExtra("Course Names", stringNames);

        startActivity(NewAssignIntent);
    }
}
