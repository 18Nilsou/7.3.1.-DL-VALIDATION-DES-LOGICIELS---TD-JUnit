package fr.imt;

public class Subscriber implements ISubscriber {
    
    private int id;
    private String username;
    private String password;

    public Subscriber(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public boolean checkLogin(String password, String username) {
        return this.password.equals(password) && this.username.equals(username);
    }
}
