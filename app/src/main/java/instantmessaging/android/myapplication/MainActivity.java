package instantmessaging.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private Button btn_register;
    private XMPPTCPConnection connection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        findViewById(R.id.gotologin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        username=(EditText)findViewById(R.id.Register_Name);
        password=(EditText)findViewById(R.id.Register_password);
        btn_register=(Button)findViewById(R.id.btn_Register);
        btn_register.setOnClickListener(this);
    }
    private XMPPTCPConnection getConnection() {
        String server = "172.16.20.92";
        int port = 5222;
        Jid servername;
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        try {
            servername = JidCreate.from("dellpc");
            builder.setHost(server);
            builder.setPort(port);
            builder.setServiceName((DomainBareJid) servername);
            builder.setCompressionEnabled(false);
            builder.setDebuggerEnabled(true);
            builder.setSendPresence(true);
            SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

            connection = new XMPPTCPConnection(builder.build());

        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        return connection;
    }


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
            return;
        } else {
            if(Password.length()<6){
                Toast.makeText(getApplicationContext(),"Your password is too short!",Toast.LENGTH_LONG).show();
            }else {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            connection = getConnection();
                            if (connection.isConnected()) {
                                connection.disconnect();
                            }
                            connection.connect();
                            AccountManager accountManage = AccountManager.getInstance(connection);
                            if (accountManage.supportsAccountCreation()) {
                                Map<String, String> map = new HashMap<>();
                                map.put("emil", "admin@demo.com");
                                //map.put("emil", email.getText().toString);
                                //map.put("name", name.getText().toString);
                                accountManage.createAccount(Username, Password, map);

                            }


                        } catch (SmackException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XMPPException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
            }

            }




    }

