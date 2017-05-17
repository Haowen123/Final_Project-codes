package instantmessaging.android.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jxmpp.jid.Jid;
import org.jxmpp.util.XmppStringUtils;

import java.util.Collection;

import instantmessaging.android.myapplication.Adapter.FriendAdapter;
import instantmessaging.android.myapplication.model.ConnectionManager;
import instantmessaging.android.myapplication.model.FriendInfo;
import instantmessaging.android.myapplication.model.RosterManager;

public class HomeActivity extends AppCompatActivity implements RosterListener {
    private AbstractXMPPConnection connection=ConnectionManager.getConnection();
    private static final int Message_REQUEST=1;
    private static final int Message_FEFRESH=2;
    private RosterManager rosterManager;
    private FriendAdapter adapter;
    private Roster roster;

    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Message_REQUEST:
                    alertInvestDialog(msg.obj.toString());
                    break;
                case Message_FEFRESH:
                    adapter.setData(roster.getGroups());
                    adapter.notifyDataSetChanged();
                    break;

                    default:
                        break;
                }
            }

        };

    protected void alertInvestDialog(String string) {
        final String Jidname=string;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Request");
        builder.setMessage(Jidname+"sent to request,would you want to add new friend?");
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                String nickname=XmppStringUtils.parseLocalpart(Jidname);
                rosterManager.addEntry(Jidname,nickname,null);
                SubscribeManager.subscribed(connection);
            }
        });
        builder.setNegativeButton("Refuse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SubscribeManager.unsubscribed(connection);

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }





    //  private List<User> userItem=new ArrayList<User>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,AddFriendActivity.class);
                startActivity(intent);
            }
        });

//        TextView user_name=(TextView)findViewById(R.id.user_name);
        if(connection.isConnected()){
            Log.d("connection","server");
        }

        roster=Roster.getInstanceFor(connection);
        roster.addRosterListener(this);

        ExpandableListView eiv=(ExpandableListView)findViewById(R.id.User_Item);

         roster.getGroups();

         adapter=new FriendAdapter(this,roster.getGroups());
         eiv.setAdapter(adapter);

         eiv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                FriendInfo info=(FriendInfo)adapter.getChild(groupPosition,childPosition);
                String chartToJid=info.getJid();
                Intent intent=new Intent(HomeActivity.this,ChatActivity.class);
                intent.putExtra("chartToJid",chartToJid);
                startActivity(intent);
                return true;
            }
        });

        rosterManager=RosterManager.getInstance(connection);


        //listening the messages sent by server
        connection.addAsyncStanzaListener(new StanzaListener() {
            @Override
            public void processPacket(Stanza packet) throws SmackException.NotConnectedException, InterruptedException {
                if (packet instanceof Presence) {
                    Presence presence = (Presence) packet;
                    Jid inverstorJid = packet.getFrom();
                    if (presence.getType() == Presence.Type.subscribe) {
                        RosterEntry entry = roster.getEntry(inverstorJid);
                        if(entry==null){
                            Message message=handler.obtainMessage(Message_REQUEST,inverstorJid );
                            handler.sendMessage(message);
                        }

                    }
                }
            }
        }, new StanzaFilter() {
            @Override
            public boolean accept(Stanza stanza) {
                return true;
            }
        });
//        initView();
//        UserListView();
//        CometoConversation();
    }

    /**
     * Called when roster entries are added.
     *
     * @param addresses the XMPP addresses of the contacts that have been added to the roster.
     *///which people been add as new Friend
    @Override
    public void entriesAdded(Collection<Jid> addresses) {
        handler.sendEmptyMessage(Message_FEFRESH);
    }
    @Override
    public void entriesUpdated(Collection<Jid> addresses) {
        handler.sendEmptyMessage(Message_FEFRESH);
    }
    @Override
    public void entriesDeleted(Collection<Jid> addresses) {
        handler.sendEmptyMessage(Message_FEFRESH);

    }
    @Override
    public void presenceChanged(Presence presence) {
        handler.sendEmptyMessage(Message_FEFRESH);

    }

//    private void CometoConversation() {
//        ListView list=(ListView)findViewById(R.id.User_Item);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View viewClick, int position, long id) {
//                Intent intent=new Intent(HomeActivity.this, ChatActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
//    private void UserListView() {
//        ArrayAdapter<User> adapter=new MyListAdapter();
//        ListView list=(ListView)findViewById(R.id.User_Item);
//        list.setAdapter(adapter);
//
//    }
//
//    private void initView() {
//        userItem.add(new User("001@servername",6,"See You!"));
//        userItem.add(new User("002@servername",5,"hello"));
//        userItem.add(new User("003@servername",4,"need you"));
//        userItem.add(new User("004@servername",11,"See"));
//
//    }
//
//    private class MyListAdapter extends ArrayAdapter<User> {
//        public MyListAdapter() {
//            super(HomeActivity.this,R.layout.item_home,userItem);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View itemView=convertView;
//            if (itemView==null){
//                itemView=getLayoutInflater().inflate(R.layout.item_home,parent,false);
//            }
//            User currentUser=userItem.get(position);
//
//            TextView userId=(TextView)itemView.findViewById(R.id.name);
//            userId.setText(currentUser.getUserid());
//
//            TextView lastMessage=(TextView)itemView.findViewById(R.id.last_message);
//            lastMessage.setText(currentUser.getLastmessage());
//
//            TextView message_time=(TextView)itemView.findViewById(R.id.message_time);
//            message_time.setText(getTime());
//
//            TextView unread_Num=(TextView)itemView.findViewById(R.id.unread_num);
//            unread_Num.setText(""+currentUser.getMessages());
//
//            return itemView;
//        }
//
//        private String getTime() {
//            Calendar c = Calendar.getInstance();
//            String year = String.valueOf(c.get(Calendar.YEAR));
//            String month = String.valueOf(c.get(Calendar.MONTH));
//            String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
//            String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
//            String mins = String.valueOf(c.get(Calendar.MINUTE));
//            StringBuffer sbBuffer = new StringBuffer();
//            sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins);
//            return sbBuffer.toString();
//        }
//    }
}
