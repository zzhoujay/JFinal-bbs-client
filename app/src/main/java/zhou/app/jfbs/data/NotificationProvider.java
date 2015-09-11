package zhou.app.jfbs.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.data.DataProvider;
import com.zhou.appinterface.net.NetworkManager;
import com.zhou.appinterface.util.LogKit;
import com.zhou.appinterface.util.Notifier;

import java.io.File;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.model.NotificationResult;
import zhou.app.jfbs.util.FileKit;
import zhou.app.jfbs.util.HashKit;
import zhou.app.jfbs.util.NetworkKit;

/**
 * Created by zhou on 15/9/11.
 */
public class NotificationProvider implements DataProvider<NotificationResult> {

    private NotificationResult notificationResult;
    private String key,token;
    private File file;

    public NotificationProvider(String token) {
        this.token = token;
        key= HashKit.md5(token+"notification.cache");
        file=new File(App.cacheFile(),key);
    }

    @Override
    public void persistence() {
        if(hasLoad()){
            new Thread(){
                @Override
                public void run() {
                    try {
                        FileKit.writeObject(file,notificationResult);
                    }catch (Exception e){
                        LogKit.d("persistence","notification",e);
                    }
                }
            }.start();
        }
    }

    @Nullable
    @Override
    public NotificationResult get() {
        return notificationResult;
    }

    @Override
    public void set(@Nullable NotificationResult notificationResult, boolean more) {
        this.notificationResult=notificationResult;
    }

    @Override
    public void loadByCache(@NonNull LoadCallback<NotificationResult> loadCallback) {
        NotificationResult nr=null;
        if(file.exists()){
            try {
                nr= (NotificationResult) FileKit.readObject(file);
            }catch (Exception e){
                LogKit.d("loadByCache","notification",e);
            }
        }
        loadCallback.loadComplete(nr);
    }

    @Override
    public void load(@NonNull LoadCallback<NotificationResult> loadCallback, boolean more) {
        if(NetworkManager.getInstance().isNetworkConnected()){
            NetworkKit.notifications(token,result->{
                NotificationResult nr=null;
                if(result.isSuccessful()){
                    nr=result.detail;
                }else {
                    App.toast(result.description);
                    LogKit.d("load",String.format("notification,failure,code:%d,msg:%s",result.code,result.description));
                }
                loadCallback.loadComplete(nr);
            });
        }else {
            App.toast(R.string.error_network);
            loadCallback.loadComplete(null);
        }
    }

    @Override
    public boolean hasLoad() {
        return notificationResult!=null;
    }

    @Override
    public boolean needCache() {
        return true;
    }

    @Override
    public boolean clearCache() {
        return file.exists()&&file.delete();
    }

    @NonNull
    @Override
    public String key() {
        return key;
    }

    @Override
    public void addNotifier(Notifier notifier) {

    }

    @Override
    public void removeNotifier(Notifier notifier) {

    }

    @Override
    public void removeAllNotifier() {

    }
}
