package instantmessaging.android.myapplication.model;

import java.util.List;

/**
 * Created by dell on 2017/4/23.
 */

public class GroupInfo {
    //groupName
    private String groupName;
    private List<FriendInfo> friends;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<FriendInfo> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendInfo> friends) {
        this.friends = friends;
    }

}
