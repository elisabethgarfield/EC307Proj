package ggkaw.caces.doby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

public class AssignmentPage extends AppCompatActivity {

    Vector<CourseInstance> newInstances = new Vector<CourseInstance>();
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_page);
        String[] courseNames = getIntent().getStringArrayExtra("Course Names");
        Spinner s = (Spinner) findViewById(R.id.Course_Assign_Spin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courseNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        CalendarView mCalendarView = (CalendarView) findViewById(R.id.Calendar_View);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = (month+1)+ "/" +  dayOfMonth+ "/" + year;
                //dateView.setText(date);

            }
        });





//    CalendarView Cview = (CalendarView) findViewById(R.id.Calendar_View);
//    Cview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//        GregorianCalendar calendar;
//        @Override
//        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//            this.calendar = new GregorianCalendar( year, month, dayOfMonth );
//        }
//    });
//
//    Calendar c = Calendar.getInstance();
//    c.setTimeInMillis(Cview.getDate());
//
//    String s = "THE DAY OF THE MONTH SELECTED IS ";
//    s.concat(Integer.toString(c.get(Calendar.DAY_OF_MONTH));
//
//    Log.d("CALENDAR DAY", s);
////    System.out.println(c.get(Calendar.DAY_OF_MONTH));



    }
//


    public void LaunchAssignmentTask(View view) {
        // add new course to vector of new course instances
        Spinner type = (Spinner) findViewById(R.id.Assign_Type_Spin);
        EditText assignmentName = (EditText) findViewById(R.id.Assign_Name);
        Spinner courseName =  (Spinner) findViewById(R.id.Course_Assign_Spin);

        String sType = type.getSelectedItem().toString();
        String sAssignmentName = assignmentName.getText().toString();
        String sCourseName = courseName.getSelectedItem().toString();

        newInstances.add(new CourseInstance(sCourseName, sAssignmentName, selectedDate, sType));
    }

    public void LaunchDoneTask(View view) {
        CourseWrapper cwrap = new CourseWrapper((CourseWrapper) getIntent().getSerializableExtra("Course Wrapper"));
        cwrap.addCourseInstances(newInstances);

        Intent sendNewWrapper = new Intent(this, HomePage.class);
        //CourseWrapper cwrap = (CourseWrapper) sendNewWrapper.getSerializableExtra("Course Wrapper");

        sendNewWrapper.putExtra("Flag", "Assignment Added");
        sendNewWrapper.putExtra("CourseWrap", cwrap); // Passing course class from this page to home page ...
        startActivity(sendNewWrapper);


    }
}
