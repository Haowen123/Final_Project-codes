package instantmessaging.android.myapplication.model;

/**
 * Created by dell on 2017/4/23.
 */

public class FriendInfo {
    private ConnectionManager connectionManager;

    private String username;
    private String name;
    private String mood;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getJid(){
        return username+"@dellpc";
    }

}
