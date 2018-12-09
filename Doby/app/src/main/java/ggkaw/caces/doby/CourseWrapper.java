package ggkaw.caces.doby;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by sadie.la on 12/4/2018.
 */
public class CourseWrapper implements Serializable {
    Vector<Course> allCourses; // holds all courses added
    Vector<CourseInstance> allInstances; // holds every calendar event

    CourseWrapper() {
        this.allCourses = new Vector<Course>();
        this.allInstances = new Vector<CourseInstance>();
    }

    // Copy Constructor
    CourseWrapper(CourseWrapper c) {
        this.allCourses = new Vector<Course>();
        this.allInstances = new Vector<CourseInstance>();
        for(int i = 0; i < c.allCourses.size(); i++) {
            this.allCourses.add(c.allCourses.elementAt(i));
        }
        for(int j = 0; j < c.allInstances.size(); j++) {
            this.allInstances.add(c.allInstances.elementAt(j));
        }
    }

    public void addCourse(Course course) {
        this.allCourses.add(course);
        for(int i = 0; i < course.classTimes.size(); i++) {
            this.allInstances.add(course.classTimes.get(i));
        }
    }

    public void addCourseInstances(Vector<CourseInstance> newInstances) {
        for (int i = 0; i < newInstances.size(); i++) {
            for(int j = 0; j < this.allCourses.size(); j++) {
                if(this.allCourses.elementAt(j).name == newInstances.elementAt(i).name) {
                    this.allCourses.elementAt(j).addInstance(newInstances.elementAt(i));
                    break; // will not have the same name as multiple courses
                }
            }
            this.allInstances.add(newInstances.elementAt(i));
        }
    }

    public void printCourses() {
        for(int i = 0; i < this.allCourses.size(); i++) {
            System.out.println("####-" + i + "-####");
            allCourses.elementAt(i).printCourseInfo(i);
            System.out.println("****-" + i + "-****");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String saveCourses(){
        String text = "";
            int i;
            for(i = 0; i < this.allCourses.size(); i++) {
                text = text.concat("####-" + i + "-####\n");
                text = text.concat(allCourses.elementAt(i).returnCourseInfo(i));
                text = text.concat("END-OF-CLASS\n");
            }

        return ":NumCourses:" + i + "\n" + text;
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static CourseWrapper loadCourses(){

        String courseName = "";
        double multiplier;
        String startDate = "";
        String endDate = "";
        String name =  "";
        String day = "";
        String date = "";
        String startTime = "";
        String endTime = "";
        String startap = "";
        String endap = "";
        String type = "";
        String current = "";

        int numOfCourses;
        CourseWrapper courseWrapper = new CourseWrapper();
        Course temp = new Course();
        File file = new File("C:\\Users\\sadie.la\\Documents\\fall2018\\EC327\\Robin\\EC307Proj\\Doby\\app\\src\\main\\java\\ggkaw\\caces\\doby\\save_test.txt");
        try(Scanner sc = new Scanner(file)) {
            current = sc.nextLine();
            numOfCourses = Integer.parseInt(current.substring(14));

            for(int i = 0; i < numOfCourses; i++) {
                while (sc.hasNextLine()) {
                    current = sc.nextLine();
                    if(current.contains("####-")){
                        current = sc.nextLine();
                        current = current.substring(19);
                        courseName = current.substring(0,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        multiplier = Double.parseDouble(current.substring(0,current.indexOf("$")));
                        current = current.substring(current.indexOf("$")+1);
                        startDate = current.substring(0,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        endDate = current;
                        temp = new Course(courseName,multiplier,startDate,endDate);
                        courseWrapper.addCourse(temp);
                    }

                    if (current.contains(":Instance:")) {
                        courseName = current.substring(10,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        name = current.substring(0,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        day = current.substring(0,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        date = current.substring(0,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        startTime = current.substring(0,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        endTime = current.substring(0,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        startap = current.substring(0,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        endap = current.substring(0,current.indexOf("$"));
                        current = current.substring(current.indexOf("$")+1);
                        type = current;
                        temp.addInstance(new CourseInstance(courseName,name,day,date,startTime,endTime,startap,endap,type));
                    }
                    if(current.contains("END-OF-CLASS")) break;
                }
            }
        } catch(java.io.FileNotFoundException sc) {
            return null;
        }

        return courseWrapper;
    }

    public Vector<CourseInstance> getTodaysSchedule(String date) {
        // given a date in the format mm/dd/yyyy, return a vector of all CourseInstances occuring on that day
        Calendar calVal;
        String dateStr;
        Vector<CourseInstance> todaysSchedule = new Vector<CourseInstance>();
        for(int i = 0; i < this.allInstances.size(); i++) {
            calVal = this.allInstances.elementAt(i).startTime;
            dateStr = CourseInstance.calDateToString(calVal);
            if(dateStr.equals(date)) {
                todaysSchedule.add(this.allInstances.elementAt(i));
            }
        }
        return todaysSchedule;
    }

    public String stringTodaysSchedule(String date) {
        // print classes first, then assignments
        Vector<CourseInstance> instances = this.getTodaysSchedule(date);
        String schedule = "";
        CourseInstance currentInstance;
        for(int i = 0; i < instances.size(); i++) {
            currentInstance = instances.elementAt(i);
            schedule = schedule + currentInstance.getInfo();
            //schedule.concat(currentInstance.courseName + ":" + currentInstance.type +  )
        }
        return schedule;
    }

//    public static void main(String[] args) {
//        Course EK307 = new Course("EK307", 2, "9/1/2018", "12/17/2018");
//        Course.addInstances(EK307,"Monday", "Lecture", "10:10", "11:55", "AM", "AM");
//        // add a hw
//        CourseInstance hw1EK307 = new CourseInstance("EK307", "Wednesday", "12/5/2018", "Homework");
//        EK307.addInstance(hw1EK307);
//
//        Course EC327 = new Course("EC327", 4, "9/1/2018", "12/17/2018");
//        Course.addInstances(EC327, "Monday", "Lecture", "2:30", "4:15", "PM", "PM");
//        CourseInstance hw1EC327 = new CourseInstance("EC327", "Monday", "12/10/2018", "Homework");
//        EC327.addInstance(hw1EC327);
//
//        CourseWrapper cwrap = new CourseWrapper();
//        cwrap.addCourse(EK307);
//        cwrap.addCourse(EC327);
//
//        // Try printing
//        cwrap.printCourses();
//
//
//    }
}

