package Model;

import java.util.ArrayList;

/**
 * Created by Maham on 5/25/2017.
 */


public class ResponseBO {
    private ArrayList<Student> StudentArrayList = new ArrayList<>();
    public ArrayList<Student> getStudentArrayList() {
        return StudentArrayList;
    }

    public void setStudentArrayList(ArrayList<Student> studentArrayList) {
        this.StudentArrayList = studentArrayList;
    }


    /*private ArrayList<weather> weatherArrayList = new ArrayList<>();
    public ArrayList<weather> getWeatherArrayList() {
        return weatherArrayList;
    }

    public void setWeatherArrayList(ArrayList<weather> weatherArrayList) {
        this.weatherArrayList = weatherArrayList;
    }*/
}
