package zhou.app.jfbs.util;

import com.zhou.appinterface.util.LogKit;

import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;

/**
 * Created by zhou on 15/9/8.
 */
public class MarkDownKit {

    public static final Markdown4jProcessor processor=new Markdown4jProcessor();

    public static String conver(String markdown){
        try {
            return processor.process(markdown);
        } catch (IOException e) {
            LogKit.d("conver","MarkDownKit",e);
        }
        return null;
    }
}
