package instantmessaging.android.myapplication;

import android.app.Application;

import org.jivesoftware.smack.AbstractXMPPConnection;

import de.measite.minidns.record.A;
import instantmessaging.android.myapplication.model.ConnectionManager;

import static instantmessaging.android.myapplication.R.string.username;

/**
 * Created by dell on 2017/4/22.
 */

public class MyApplication extends Application{
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */

//    private XMPPTCPConnection connection;
//    private String username;
//    private String password;
////
//    public MyApplication(String username, String password) {
//        this.username = username;
//        this.password = password;
//}
 //   private static AbstractXMPPConnection connection= ConnectionManager.getCountion();

    @Override
    public void onCreate() {
        super.onCreate();
   // new MyApplication(username,password);
    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
}
