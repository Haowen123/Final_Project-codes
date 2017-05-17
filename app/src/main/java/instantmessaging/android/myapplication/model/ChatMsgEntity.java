package instantmessaging.android.myapplication.model;

/**
 * Created by dell on 2017/4/18.
 */

public class ChatMsgEntity {
    private static final String TAG = ChatMsgEntity.class.getSimpleName();
    private String name;
    private String date;
    private String text;
    private boolean isComMeg = true;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean getMsgType() {
        return isComMeg;
    }
    public void setMsgType(boolean isComMsg) {
        isComMeg = isComMsg;
    }
    public ChatMsgEntity() {
    }
    public ChatMsgEntity(String name, String date, String text, boolean isComMsg) {
        this.name = name;
        this.date = date;
        this.text = text;
        this.isComMeg = isComMsg;
    }
}