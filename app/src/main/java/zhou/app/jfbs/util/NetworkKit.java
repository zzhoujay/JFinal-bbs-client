package zhou.app.jfbs.util;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.net.NetworkManager;

import java.util.List;

import zhou.app.jfbs.App;
import zhou.app.jfbs.model.Daily;
import zhou.app.jfbs.model.NotificationResult;
import zhou.app.jfbs.model.ReplyResult;
import zhou.app.jfbs.model.Result;
import zhou.app.jfbs.model.Section;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.model.TopicPage;
import zhou.app.jfbs.model.TopicWithReply;
import zhou.app.jfbs.model.User;
import zhou.app.jfbs.model.UserResult;

/**
 * Created by zzhoujay on 2015/8/11 0011.
 * 网络请求工具类
 */
public class NetworkKit {

    /**
     * 获取模块列表
     *
     * @param loadCallback 回调
     */
    public static void sections(@NonNull LoadCallback<Result<List<Section>>> loadCallback) {
        Request request = new Request.Builder()
                .get().url(App.SECTIONS_URL).build();
        NetworkManager.getInstance().request(request, loadCallback, new TypeToken<Result<List<Section>>>() {
        }.getType());
    }

    /**
     * 主题首页
     *
     * @param p            页数，默认1
     * @param tab          主题分类
     * @param size         每一页的主题数量
     * @param loadCallback 回调
     */
    public static void index(int p, String tab, int size, @NonNull LoadCallback<Result<TopicPage>> loadCallback) {
        Request request = new Request.Builder()
                .url(App.INDEX_URL + "?p=" + p + "&tab=" + tab + "&size=" + size).get().build();
        NetworkManager.getInstance().request(request, loadCallback, new TypeToken<Result<TopicPage>>() {
        }.getType());
    }

    /**
     * 主题详情
     *
     * @param id           主题id
     * @param loadCallback 回调
     */
    public static void topic(String id, @NonNull LoadCallback<Result<TopicWithReply>> loadCallback) {
        Request request = new Request.Builder()
                .url(App.TOPIC_URL + "/" + id).get().build();
        NetworkManager.getInstance().request(request, loadCallback, new TypeToken<Result<TopicWithReply>>() {
        }.getType());
    }

    /**
     * 新建主题
     *
     * @param token        用户令牌
     * @param title        标题
     * @param sid          模块iid
     * @param content      主题内容
     * @param originalUrl  原文链接，原创可不填
     * @param loadCallback 回调
     */
    public static void createTopic(String token, String title, String sid, String content, String originalUrl
            , @NonNull LoadCallback<Result<Topic>> loadCallback) {
        FormEncodingBuilder builder = new FormEncodingBuilder()
                .add("token", token).add("title", title).add("sid", sid).add("content", content);
        if (originalUrl != null) {
            builder.add("original_url", originalUrl);
        }
        Request request = new Request.Builder()
                .url(App.CREATE_TOPIC_URL)
                .post(builder.build())
                .build();
        NetworkManager.getInstance().request(request, loadCallback, new TypeToken<Result<Topic>>() {
        }.getType());
    }

    /**
     * 收藏主题
     *
     * @param token        用户令牌
     * @param tid          被收藏的主题id
     * @param loadCallback 回调
     */
    public static void collect(String token, String tid, @NonNull LoadCallback<Result> loadCallback) {
        Request request = new Request.Builder()
                .url(App.COLLECT_URL + "?token=" + token + "&tid=" + tid)
                .get()
                .build();
        NetworkManager.getInstance().request(request, loadCallback, Result.class);
    }

    /**
     * 取消收藏
     *
     * @param token        用户令牌
     * @param tid          被取消收藏的主题id
     * @param loadCallback 回调
     */
    public static void deleteCollect(String token, String tid, @NonNull LoadCallback<Result> loadCallback) {
        Request request = new Request.Builder()
                .url(App.COLLECT_DELETE_URL + "?token=" + token + "&tid=" + tid)
                .get()
                .build();
        NetworkManager.getInstance().request(request, loadCallback, Result.class);
    }

    /**
     * 创建新评论
     *
     * @param token        用户令牌
     * @param tid          评论话题的id
     * @param content      评论的内容
     * @param quote        引用：对另一个评论回复时，另一个评论的id
     * @param loadCallback 回调
     */
    public static void createReply(String token, String tid, String content, String quote, @NonNull LoadCallback<Result<ReplyResult>> loadCallback) {
        FormEncodingBuilder builder = new FormEncodingBuilder()
                .add("token", token).add("tid", tid).add("content", content);
        if (quote != null) {
            builder.add("quote", quote);
        }
        Request request = new Request.Builder()
                .url(App.REPLY_CREATE_URL)
                .post(builder.build())
                .build();
        NetworkManager.getInstance().request(request, loadCallback, new TypeToken<Result<ReplyResult>>() {
        }.getType());
    }

    /**
     * 用户详情
     *
     * @param token        用户令牌
     * @param loadCallback 回调
     */
    public static void userInfo(String token, @NonNull LoadCallback<Result<UserResult>> loadCallback) {
        RequestBody body = new FormEncodingBuilder().add("token", token).build();
        Request request = new Request.Builder().url(App.USER_URL).post(body).build();
        NetworkManager.getInstance().request(request, loadCallback, new TypeToken<Result<UserResult>>() {
        }.getType());
    }

    /**
     * 每日签到
     *
     * @param token        用户令牌
     * @param loadCallback 回调
     */
    public static void daily(String token, @NonNull LoadCallback<Result<Daily>> loadCallback) {
        Request request = new Request.Builder().url(App.MISSION_DAILY_URL + "?token=" + token).get().build();
        NetworkManager.getInstance().request(request, loadCallback, new TypeToken<Result<Daily>>() {
        }.getType());
    }

    /**
     * 未读消息数
     *
     * @param token        用户令牌
     * @param loadCallback 回调
     */
    public static void countnotread(String token, @NonNull LoadCallback<Result<Integer>> loadCallback) {
        Request request = new Request.Builder().url(App.NOTIFICATION_COUNT_URL + "?token=" + token).get().build();
        NetworkManager.getInstance().request(request, loadCallback, new TypeToken<Result<User>>() {
        }.getType());
    }

    /**
     * 已读和未读消息
     *
     * @param token        用户令牌
     * @param loadCallback 回调
     */
    public static void notifications(String token, @NonNull LoadCallback<Result<NotificationResult>> loadCallback) {
        Request request = new Request.Builder().url(App.NOTIFICATION_URL + "?token=" + token).get().build();
        NetworkManager.getInstance().request(request, loadCallback, new TypeToken<Result<NotificationResult>>() {
        }.getType());
    }

}
