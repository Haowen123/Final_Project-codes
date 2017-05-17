package instantmessaging.android.myapplication;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Presence;

/**
 * Created by dell on 2017/4/23.
 */

public class SubscribeManager {
    public static void subscribed(AbstractXMPPConnection connection){
        try {
            Presence p=new Presence(Presence.Type.subscribe);
            connection.sendStanza(p);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static void unsubscribed(AbstractXMPPConnection connection){
        try {
            Presence p=new Presence(Presence.Type.unsubscribe);
            connection.sendStanza(p);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
