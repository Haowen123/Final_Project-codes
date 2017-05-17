package instantmessaging.android.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jxmpp.util.XmppStringUtils;

import java.util.ArrayList;
import java.util.List;

import instantmessaging.android.myapplication.model.ConnectionManager;
import instantmessaging.android.myapplication.model.RosterManager;

import static org.jivesoftware.smackx.xdata.packet.DataForm.Type.result;

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText friendID;
    private Button cancel;
    private Button btn_addFriend;
    private ListView addfriend;
    private MyApplication app = (MyApplication) getApplication();
    private AbstractXMPPConnection connection = ConnectionManager.getConnection();
    private ArrayAdapter<String> adapter;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initview();
     //   CometoConversation();

    }


    private void initview() {
        friendID = (EditText) findViewById(R.id.addfriend);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        btn_addFriend = (Button) findViewById(R.id.btn_addfriend);
        btn_addFriend.setOnClickListener(this);
        addfriend=(ListView)findViewById(R.id.list_addfriend);
        addfriend.setOnItemClickListener(this);
    }
//    private void CometoConversation() {
//        ListView list=(ListView)findViewById(R.id.User_Item);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View viewClick, int position, long id) {
//                String nikeName=adapter.getItem(position);
//                final String addToJid=XmppStringUtils.completeJidFrom(nikeName,connection.getServiceName());
//
//                AlertDialog.Builder builder=new AlertDialog.Builder(AddFriendActivity.this);
//                builder.setTitle("AddFriend Request");
//                builder.setMessage("Add "+nikeName+"as Friend ?");
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        RosterManager rosterManager = RosterManager.getInstance(connection);
//                        rosterManager.addEntry(addToJid,"","TestGroup");
//                    }
//                });
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                AlertDialog dialog=builder.create();
//                dialog.show();
//
//            }
//        });
//    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel: {
                Intent intent = new Intent(AddFriendActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_addfriend: {
                final String FriendID = friendID.getText().toString();
                if (FriendID.equals("")) {
                    Toast.makeText(getApplicationContext(), "Input you friend ID", Toast.LENGTH_LONG).show();
                }else{
                   Toast.makeText(getApplicationContext(),"Searching......",Toast.LENGTH_LONG).show();
                   new SearchTask().execute();

                }
            }
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */

    //搜索后点击列表
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final String nikeName=adapter.getItem(position);
        final String addToJid= XmppStringUtils.completeJidFrom(nikeName,connection.getServiceName());
        //弹出提示对话框
        AlertDialog.Builder builder=new AlertDialog.Builder(AddFriendActivity.this);
        builder.setTitle("AddFriend Request");
        builder.setMessage("Add "+ nikeName+" as Friend ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                RosterManager rosterManager = RosterManager.getInstance(connection);
             //   String nickname=XmppStringUtils.parseLocalpart(addToJid);
                rosterManager.addEntry(addToJid,addToJid,"Friends");
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
        });
        dialog=builder.create();
        dialog.show();

    }

    private class SearchTask extends AsyncTask{
        String FriendID = friendID.getText().toString();
        @Override
        protected Object doInBackground(Object[] params) {
            UserSearchManager userSearchManager = new UserSearchManager(connection);
            String searchService = "search." + connection.getServiceName();

            DomainBareJid SearchService = null;
            try {
                SearchService = JidCreate.domainBareFrom(searchService);

                Form searchFrom = userSearchManager.getSearchForm(SearchService);
                //setting search criteria
                Form answerForm = searchFrom.createAnswerForm();
                //search through the username
                answerForm.setAnswer("Username", true);
                answerForm.setAnswer("search", FriendID.trim());
                //get search result
                ReportedData reportedData = userSearchManager.getSearchResults(answerForm, SearchService);
                List<ReportedData.Row> rows = reportedData.getRows();
                List<String> list = new ArrayList<String>();
                for (ReportedData.Row row : rows) {
                    String username = row.getValues("Username").get(0);
                    list.add(username);
                }
                adapter = new ArrayAdapter<String>(AddFriendActivity.this,R.layout.search_user,R.id.search_name,list);
            } catch (XmppStringprepException e) {

                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SmackException.NoResponseException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            } catch (XMPPException.XMPPErrorException e) {
                e.printStackTrace();
            }

            return true;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param o The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(result !=null){
                addfriend.setAdapter(adapter);
            }else {
                Toast.makeText(getApplicationContext(),"The User doesn't exist!",Toast.LENGTH_SHORT).show();
            }

        }

    }
}

                //using the downloaded plugins

//                Thread thread=new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            connection = getConnection();
//                            if(connection.isConnected()){
//                                connection.disconnect();
//                            }else {
//                                connection.connect();
//
//                                connection.login("jessie","1234567");
//
//                                Roster roster = Roster.getInstanceFor(connection);
//
//                                try {
//
//                                    DomainBareJid fid = JidCreate.domainBareFrom(FriendID);
//                                    roster.createEntry(fid, FriendID, null);
//                                    Intent intent=new Intent(AddFriendActivity.this,HomeActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                } catch (XmppStringprepException e) {
//                                    e.printStackTrace();
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                } catch (SmackException.NotLoggedInException e) {
//                                    e.printStackTrace();
//                                } catch (XMPPException.XMPPErrorException e) {
//                                    e.printStackTrace();
//                                } catch (SmackException.NotConnectedException e) {
//                                    e.printStackTrace();
//                                } catch (SmackException.NoResponseException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (SmackException e) {
//                            e.printStackTrace();
//                        } catch (XMPPException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    });
//                thread.start();
//            }
//
//        }
//
//    }
//    private XMPPTCPConnection getConnection() {
//        String server = "172.16.80.11";
//        int port = 5222;
//        Jid servername;
//        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
//        try {
//            servername = JidCreate.from("dellpc");
//            builder.setHost(server);
//            builder.setPort(port);
//            builder.setServiceName((DomainBareJid) servername);
//            builder.setCompressionEnabled(false);
//            builder.setDebuggerEnabled(true);
//            builder.setSendPresence(true);
//            SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
//            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
//
//            connection = new XMPPTCPConnection(builder.build());
//
//        } catch (XmppStringprepException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }

