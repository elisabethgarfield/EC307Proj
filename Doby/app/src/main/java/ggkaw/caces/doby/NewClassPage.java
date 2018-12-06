package ggkaw.caces.doby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static ggkaw.caces.doby.Course.addInstances;

public class NewClassPage extends AppCompatActivity {

    // declare the instance of Course class you will use
    Course createdCourse;
//    private static final String DEG_TAG = "Read DICK!";
//
//    private void writeToFile(String data, Context context) {
//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
//            outputStreamWriter.write(data);
//            outputStreamWriter.close();
//        }
//        catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }
//    private String readFromFile(Context context) {
//
//        String ret = "";
//
//        try {
//            InputStream inputStream = context.openFileInput("config.txt");
//
//            if ( inputStream != null ) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while ( (receiveString = bufferedReader.readLine()) != null ) {
//                    stringBuilder.append(receiveString);
//                }
//
//                inputStream.close();
//                ret = stringBuilder.toString();
//            }
//        }
//        catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        }
//
//        return ret;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class_page);
    }

    public void addAndStore(View view) {
        TextView existingSections = (TextView) findViewById(R.id.Dynam_Text_View);
        Spinner classTypeSpin = (Spinner) findViewById(R.id.Class_Type_Drop);
        Spinner classDaySpin = (Spinner) findViewById(R.id.Day_Drop);
        Spinner AMPMStart = (Spinner) findViewById(R.id.AM_PM_Start_Spin);
        Spinner AMPMEnd = (Spinner) findViewById(R.id.AM_PM_End_Spin);
        EditText startTimeText = (EditText) findViewById(R.id.Start_Time_Edit);
        EditText endTimeText = (EditText) findViewById(R.id.End_Time_Edit);

        String classType = classTypeSpin.getSelectedItem().toString();
        String classDay = classDaySpin.getSelectedItem().toString();
        String APStart = AMPMStart.getSelectedItem().toString();
        String APEnd = AMPMEnd.getSelectedItem().toString();
        String startTime =  startTimeText.getText().toString();
        String endTime =  endTimeText.getText().toString();

        addInstances(createdCourse, classDay, classType, startTime,endTime,APStart,APEnd);

        Toast.makeText(this, "Lecture/Lab Added", 300);
//        String


//        Context context = this;
//        writeToFile("Dick", context);
//        Log.d(DEG_TAG,readFromFile(context));

        // create new course instance and add it to the Course class
//        createdCourse.addInstance(CourseInstance());


//        existingSections.append(classTypeSpin.getSelectedItem().toString());
//        existingSections.append(" ");
//        existingSections.append(classDaySpin.getSelectedItem().toString());
//        existingSections.append(" ");
//        existingSections.append(timeText.getText().toString());
//        existingSections.append(AMPMStart.getSelectedItem().toString());
//        existingSections.append(" to ");
//        existingSections.append(durationText.getText().toString());
//        existingSections.append(AMPMEnd.getSelectedItem().toString());
//        existingSections.append("\n-------------\n");

    }

    public void SetNewClass(View view) {
        // throw error if you have already added the class

        EditText startDateText = (EditText) findViewById(R.id.Start_Date_Edit);
        EditText endDateText = (EditText) findViewById(R.id.End_Date_Edit);
        EditText multiplier = (EditText) findViewById(R.id.multiplier);
        EditText courseName = (EditText) findViewById(R.id.Class_Name_Edit);

        String sstartDate = startDateText.getText().toString();
        String sendDate = endDateText.getText().toString();
        String scourseName = courseName.getText().toString();
        double mult = Double.parseDouble(multiplier.getText().toString());

        createdCourse = new Course(scourseName, mult, sstartDate, sendDate); // CREATE NEW CONSTRUCTOR w/ 2 args
    }

    public void launchDoneTask(View view) {
        CourseWrapper cwrap = new CourseWrapper((CourseWrapper) getIntent().getSerializableExtra("Course Wrapper"));
        cwrap.addCourse(createdCourse);

        Intent sendNewWrapper = new Intent(this, HomePage.class);
        //

        sendNewWrapper.putExtra("Flag", "Assignment Added");
        sendNewWrapper.putExtra("CourseWrap", cwrap); // Passing course class from this page to home page ...
        startActivity(sendNewWrapper);
    }
}
