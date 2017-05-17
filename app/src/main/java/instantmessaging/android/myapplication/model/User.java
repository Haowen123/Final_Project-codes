package instantmessaging.android.myapplication.model;

import org.json.JSONObject;

/**
 * Created by dell on 2017/4/20.
 */

public class User {
      private String userid;
      private int messages;
      private String lastmessage;


//Text Message
    public User(String userid, int messages, String lastmessage) {
        this.userid = userid;
        this.messages = messages;
        this.lastmessage = lastmessage;
    }
    public String toJson(){
        JSONObject jsonObject=new JSONObject();

        return jsonObject.toString();
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public int getMessages() {
        return messages;
    }

    public String getUserid() {
        return userid;
    }
}
