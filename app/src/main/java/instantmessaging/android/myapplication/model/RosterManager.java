package instantmessaging.android.myapplication.model;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 * Created by dell on 2017/4/23.
 */

public class RosterManager {
    private static RosterManager instance;
    private Roster roster;
    private RosterManager(){

    }
    public static RosterManager getInstance(AbstractXMPPConnection connection){
        instance=new RosterManager();
        instance.roster=Roster.getInstanceFor(connection);
        return instance;

    }
    public void addEntry(String string,String nikename,String group){
            try{
                DomainBareJid jid= JidCreate.domainBareFrom(string);

                roster.createEntry(jid,nikename,new String[]{group});

            }catch (XMPPException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SmackException.NotLoggedInException e) {
                e.printStackTrace();
            } catch (XmppStringprepException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            } catch (SmackException.NoResponseException e) {
                e.printStackTrace();
            }
    }
    }
