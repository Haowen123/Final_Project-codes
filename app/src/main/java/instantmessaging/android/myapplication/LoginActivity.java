package instantmessaging.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import java.io.IOException;

import instantmessaging.android.myapplication.model.ConnectionManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button btn_login;
    private AbstractXMPPConnection connection = ConnectionManager.getConnection();
    private Presence presence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
        ImageButton back = (ImageButton) findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initview() {
        username = (EditText) findViewById(R.id.Login_username);
        password = (EditText) findViewById(R.id.Login_password);
        btn_login = (Button) findViewById(R.id.btn_Login);
        btn_login.setOnClickListener(this);


    }
//    private XMPPTCPConnection getConnection() {
//        String server = "172.16.60.10";
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
//
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        final String Username = username.getText().toString();
        final String Password = password.getText().toString();
        if (Username.length() == 0 || Password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Not Allow be empty!", Toast.LENGTH_LONG).show();
        } else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(connection.isConnected()){
                            connection.disconnect();
                        }
                        connection.setPacketReplyTimeout(50000);
                        connection.connect();

                        connection.login(Username, Password);


                        presence = new Presence(Presence.Type.available);
                        presence.setStatus("Online");
                        connection.sendStanza(presence);
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    } catch (SmackException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (presence.getStatus().equals("Online")) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
            thread.start();
        }
    }
}
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    if(connection.isConnected()){
//                        connection.disconnect();
//                    }else {
//                        try {
//                            connection.connect();
//                            connection.login(Username,Password);
//                            presence=new Presence(Presence.Type.available);
//                            presence.setStatus("Online");
//                            connection.sendStanza(presence);
//                            if(presence.getStatus().equals("Online")){
//                                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
//                                startActivity(intent);
//                            }
//                        } catch (SmackException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (XMPPException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
////                        try {
////
////                            connection.login(Username,Password);
////                            presence=new Presence(Presence.Type.available);
////                            presence.setStatus("Online");
////                            connection.sendStanza(presence);
////                            if(presence.getStatus().equals("Online")){
////                                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
////                                startActivity(intent);
////                            }
////                        } catch (SmackException e) {
////                            e.printStackTrace();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        } catch (XMPPException e) {
////                            e.printStackTrace();
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                    }
//
//            });
//            thread.start();
//            }
//
//        }
//
//

