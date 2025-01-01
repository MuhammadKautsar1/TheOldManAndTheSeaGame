package model;

public class User {
    private int uid; 
    private String uname;
    private String pass;

    public User(String uname, String pass, String password) {
        this.setPass(pass);
        this.setUname(uname);
    }

    public User(int uid, String uname, String pass) {
        this.setUid(uid);
        this.setPass(pass);
        this.setUname(uname);
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
