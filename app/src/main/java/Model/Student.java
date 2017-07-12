package Model;

/**
 * Created by Maham on 5/25/2017.
 */

public class Student {
    public String Name;
    public String Email;
    public String RollNumber;
    public String Password;
    public Student() {
    }
    public Student(String s1,String s2,String s3,String s4){
        Name = s1;
        Email = s2;
        RollNumber = s3;
        Password = s4;
    }

    public String getName() {
        return Name;
    }
    public String getEmail() { return Email; }
    public String getRoll_No() {
        return RollNumber;
    }
    public String getPassword() {
        return Password;
    }

    public void setName(String s) {
        Name = s;
    }
    public void setEmail(String i) {
        Email = i;
    }
    public void setRoll_No(String i) {
        RollNumber = i;
    }
    public void setPassword(String i) {
        Password = i;
    }

}
