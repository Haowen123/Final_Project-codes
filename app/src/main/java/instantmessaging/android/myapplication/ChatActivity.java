package instantmessaging.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.JidWithLocalpart;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jxmpp.util.XmppStringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import instantmessaging.android.myapplication.Adapter.ChatMsgViewAdapter;
import instantmessaging.android.myapplication.model.ChatMsgEntity;
import instantmessaging.android.myapplication.model.ConnectionManager;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, ChatMessageListener, ChatManagerListener {
    private Button BtnSend;
    private EditText TextContent;
    private ImageButton back_home;
    private ImageButton ME;
    //Adatper
    private ChatMsgViewAdapter chatMsgViewAdapter;

    private ListView listView;
    //chat content
    private List<ChatMsgEntity> mDataArrays=new ArrayList<ChatMsgEntity>();
    private AbstractXMPPConnection connection= ConnectionManager.getConnection();
    private ChatManager chatManager;

    private String ChatToJid;
    private Chat chat;
    private String log_User;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initDate();

        Intent intent=getIntent();
        ChatToJid=intent.getStringExtra("chartToJid");
        TextView userId=(TextView)findViewById(R.id.Chat_username);
        userId.setText(ChatToJid);

        chatManager=ChatManager.getInstanceFor(connection);
        chatManager.addChatListener(this);



        log_User= XmppStringUtils.parseBareJid(String.valueOf(connection.getUser()));

        if(connection.isConnected()){
            Log.i("connected","server");
        }


    }

    private   String[] dataArray=new String[]{
           "2017-04-10 15:00","2017-04-10 16:00"
    };
    private String[] msgArray=new String[]{
            "Hello","Nice to meet u!"
    };
    private void initDate() {
        for (int i=0;i<dataArray.length;i++){
            ChatMsgEntity entity=new ChatMsgEntity();
            entity.setDate(dataArray[i]);
            if (i % 2 == 0)
            {
                entity.setName("001");
                entity.setMsgType(true);
            }else{
                entity.setName("002");
                entity.setMsgType(false);
            }
            entity.setText(msgArray[i]);
            mDataArrays.add(entity);
        }
        chatMsgViewAdapter=new ChatMsgViewAdapter(this,mDataArrays);
        listView.setAdapter(chatMsgViewAdapter);
    }

    private void initView() {
        listView=(ListView) findViewById(R.id.listview);
        back_home=(ImageButton)findViewById(R.id.BacktoHomePage);
        back_home.setOnClickListener(this);
        ME=(ImageButton)findViewById(R.id.Me);
        ME.setOnClickListener(this);
        BtnSend=(Button)findViewById(R.id.btn_send);
        BtnSend.setOnClickListener(this);
        TextContent=(EditText)findViewById(R.id.et_sendmessage);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:{
                send();
  //              SendtoServer();
//                Receiver();
                break;
            }case R.id.BacktoHomePage:{
                Intent intent=new Intent(ChatActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.Me:{
                Intent intent=new Intent(ChatActivity.this, GoogleME.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

//    private void Receiver() {
//        String messages=app.setMessage();
//        ChatMsgEntity mEntity = new ChatMsgEntity();
//        mEntity.setDate(getDate());
//        mEntity.setName("");
//        mEntity.setMsgType(true);
//        mEntity.setText(messages);
//        mDataArrays.add(mEntity);
//        chatMsgViewAdapter.notifyDataSetChanged();
//        chatMsgViewAdapter = new ChatMsgViewAdapter(this, mDataArrays);
//        listView.setAdapter(chatMsgViewAdapter);
//        listView.setSelection(listView.getCount() - 1);
//
//
//    }

//    private void SendtoServer() {
//        String textContent=TextContent.getText().toString();
//        Jid jid;
//        try {
//            chatManager.addChatListener((ChatManagerListener) this);
//            jid= JidCreate.from(ChatToJid);
//            Chat mChat=chatManager.createChat((JidWithLocalpart) jid,this);
//            mChat.sendMessage(textContent);
//
//
//        } catch (XmppStringprepException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (SmackException.NotConnectedException e) {
//            e.printStackTrace();
//      }
//        TextContent.setText("");
//
//
//    }


    private void send() {
        String contString=TextContent.getText().toString();
        if(contString.length()>0) {

            ChatMsgEntity mEntity = new ChatMsgEntity();
            mEntity.setDate(getDate());
            mEntity.setName("");
            mEntity.setMsgType(false);
            mEntity.setText(contString);
            mDataArrays.add(mEntity);
            chatMsgViewAdapter.notifyDataSetChanged();
            chatMsgViewAdapter = new ChatMsgViewAdapter(this, mDataArrays);
            listView.setAdapter(chatMsgViewAdapter);
            TextContent.setText("");
            listView.setSelection(listView.getCount() - 1);


//            ChatMsgEntity uEntity=new ChatMsgEntity();
//            uEntity.setDate(getDate());
//            uEntity.setDate(getDate());
//            uEntity.setName("");
//            uEntity.setMsgType(true);
//            uEntity.setText(contString);
           Jid chatID;
            try {
                chatID= JidCreate.from(ChatToJid);
                chat=chatManager.createChat((JidWithLocalpart)chatID);
                chat.sendMessage(contString);
            } catch (XmppStringprepException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }


        }}



    private String getDate() {
        Calendar c=Calendar.getInstance();
        StringBuilder mBuilder=new StringBuilder();
        mBuilder.append(Integer.toString(c.get(Calendar.DATE))+"/");
        mBuilder.append(Integer.toString(c.get(Calendar.MONTH))+"   ");

        mBuilder.append(Integer.toString(c.get(Calendar.HOUR_OF_DAY))+":");
        mBuilder.append(Integer.toString(c.get(Calendar.MINUTE)));
        return mBuilder.toString();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        back();
        return true;
    }

    private void back() {
        finish();
    }


    private android.os.Handler handler=new android.os.Handler(){

        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(android.os.Message msg) {
            ChatMsgEntity mEntity = new ChatMsgEntity();
            mEntity.setDate(getDate());
            mEntity.setName("");
            mEntity.setMsgType(true);
            mEntity.setText(msg.obj+"");
            mDataArrays.add(mEntity);
            chatMsgViewAdapter.notifyDataSetChanged();
            chatMsgViewAdapter = new ChatMsgViewAdapter(ChatActivity.this, mDataArrays);
            listView.setAdapter(chatMsgViewAdapter);
            listView.setSelection(listView.getCount() - 1);
            super.handleMessage(msg);
        }
    };

    @Override
    public void processMessage(Chat chat, Message message) {
        if(message!=null){
            android.os.Message mes= android.os.Message.obtain();
                    mes.what=1;
            mes.obj=message.getBody();
            handler.sendMessage(mes);
        }

    }

    /**
     * Event fired when a new chat is created.
     *
     * @param chat           the chat that was created.
     * @param createdLocally true if the chat was created by the local user and false if it wasn't.
     */
    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        //add
        chat.addMessageListener(this);


    }
}

