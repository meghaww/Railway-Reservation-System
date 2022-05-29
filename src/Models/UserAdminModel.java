package Models;

public class UserAdminModel {
    Integer account_id;
    String Fname;
    String Lname;
    String Username;
    String password;

    public UserAdminModel(Integer account_id, String fname, String lname, String username, String password) {
        this.account_id = account_id;
        this.Fname = fname;
        this.Lname = lname;
        this.Username = username;
        this.password = password;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
