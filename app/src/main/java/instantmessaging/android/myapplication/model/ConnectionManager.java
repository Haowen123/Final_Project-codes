package instantmessaging.android.myapplication.model;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

/**
 * Created by dell on 2017/4/23.
 */

public class ConnectionManager {
    private static AbstractXMPPConnection connection;
    private static String server = "172.16.20.92";
    private static int port = 5222;
    public static AbstractXMPPConnection getConnection(){
        if (connection==null){
            openConnection();
        }
        return connection;
    }
    private static void openConnection() {
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

            connection=new XMPPTCPConnection(builder.build());
            connection.connect();
        } catch (XmppStringprepException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (SmackException e1) {
            e1.printStackTrace();
        } catch (XMPPException e1) {
            e1.printStackTrace();
        }

    }
    public static void release(){
        if(connection.isConnected()){
            connection.disconnect();
        }
    }

}
