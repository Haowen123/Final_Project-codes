package instantmessaging.android.myapplication.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import instantmessaging.android.myapplication.R;
import instantmessaging.android.myapplication.model.FriendInfo;
import instantmessaging.android.myapplication.model.GroupInfo;

/**
 * Created by dell on 2017/4/23.
 */

public class FriendAdapter extends BaseExpandableListAdapter {

   private LayoutInflater Inflater;
    //Friend Groups
    private List<GroupInfo> groupData;
    //Friend Group
    private List<List<FriendInfo>> friendData;

    public FriendAdapter(Context context, Collection<RosterGroup> groups){
        Inflater= LayoutInflater.from(context);
        setData(groups);

    }
// get all friend information
    public void setData(Collection<RosterGroup> groups) {

        //init();
        groupData=new ArrayList<GroupInfo>();
        friendData=new ArrayList<List<FriendInfo>>();

        for (RosterGroup group:groups){
            GroupInfo groupInfo=new GroupInfo();
            groupInfo.setGroupName(group.getName());
            groupData.add(groupInfo);

            //get all friends
            List<FriendInfo> friendsList=new ArrayList<FriendInfo>();
            List<RosterEntry> entries=group.getEntries();

            for(RosterEntry rosterEntry: entries){
                //show userList
                if(TextUtils.equals("both", rosterEntry.getType().name())){
                    FriendInfo friendInfo=new FriendInfo();
                    friendInfo.setUsername(rosterEntry.getName());
                    friendInfo.setName(rosterEntry.getName());
                    friendInfo.setMood("Hello World");
                    friendsList.add(friendInfo);
                }
            }
            groupInfo.setFriends(friendsList);
            friendData.add(friendsList);
            }



    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }


    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition)
    {
        List<FriendInfo> list=friendData.get(groupPosition);
        if(list!=null&&!list.isEmpty()){
            return list.size();
        }
       return 0;
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Object getGroup(int groupPosition) {

        return groupData.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return friendData.get(groupPosition).get(childPosition);
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * {@link #getChildView(int, int, boolean, View, ViewGroup)}.
     *
     * @param groupPosition the position of the group for which the View is
     *                      returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getGroupView(int, boolean, View, ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    //Friend group list
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View layout=Inflater.inflate(R.layout.friend_group_item,null);
        TextView group_name=(TextView)layout.findViewById(R.id.group_name);
        //setting the groupNamw

        GroupInfo groupInfo=(GroupInfo)getGroup(groupPosition);
        group_name.setText(groupInfo.getGroupName());
        ImageView group_icon=(ImageView)layout.findViewById(R.id.group_icon);

//        if(isExpanded){
//            group_icon.setBackgroundResource(R.drawable.open);
//        }else {
//            group_icon.setBackgroundResource(R.drawable.close);
//        }
       return layout;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View layout=Inflater.inflate(R.layout.friend_name,null);
        FriendInfo friendInfo=(FriendInfo)getChild(groupPosition,childPosition);

        TextView friend_name=(TextView)layout.findViewById(R.id.user_name);
        friend_name.setText(friendInfo.getName());


        return layout;
    }

    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Called when a group is expanded.
     *
     * @param groupPosition The group being expanded.
     */
    @Override
    public void onGroupExpanded(int groupPosition) {


    }

    /**
     * Called when a group is collapsed.
     *
     * @param groupPosition The group being collapsed.
     */
    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    /**
     * Gets an ID for a child that is unique across any item (either group or
     * child) that is in this list. Expandable lists require each item (group or
     * child) to have a unique ID among all children and groups in the list.
     * This method is responsible for returning that unique ID given a child's
     * ID and its group's ID. Furthermore, if {@link #hasStableIds()} is true, the
     * returned ID must be stable as well.
     *
     * @param groupId The ID of the group that contains this child.
     * @param childId The ID of the child.
     * @return The unique (and possibly stable) ID of the child across all
     * groups and children in this list.
     */
    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    /**
     * Gets an ID for a group that is unique across any item (either group or
     * child) that is in this list. Expandable lists require each item (group or
     * child) to have a unique ID among all children and groups in the list.
     * This method is responsible for returning that unique ID given a group's
     * ID. Furthermore, if {@link #hasStableIds()} is true, the returned ID must be
     * stable as well.
     *
     * @param groupId The ID of the group
     * @return The unique (and possibly stable) ID of the group across all
     * groups and children in this list.
     */
    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
