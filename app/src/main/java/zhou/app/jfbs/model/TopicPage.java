package zhou.app.jfbs.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzhoujay on 2015/8/11 0011.
 */
public class TopicPage implements Serializable{

    public boolean firstPage;
    public boolean lastPage;
    public int pageSize;
    public int pageNumber;
    public int totalRow;
    public int totalPage;
    public List<Topic> list;
}
