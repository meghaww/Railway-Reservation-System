package Models;

public class AlterUserDetModel {
    Integer account_id;
    String firstname;
    String lastname;
    String username;

    public AlterUserDetModel(Integer account_id, String firstname, String lastname, String username) {
        this.account_id = account_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
