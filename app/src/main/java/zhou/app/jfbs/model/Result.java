package zhou.app.jfbs.model;

import com.zhou.appinterface.net.InterfaceResult;

/**
 * Created by zzhoujay on 2015/8/11 0011.
 */
public class Result<T> extends InterfaceResult {



    public int code;
    public String description;
    public T detail;

    @Override
    public boolean isSuccessful() {
        return code==200;
    }

}
