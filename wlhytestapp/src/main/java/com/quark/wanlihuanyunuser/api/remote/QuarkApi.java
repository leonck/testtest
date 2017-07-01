package com.quark.wanlihuanyunuser.api.remote;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.ui.chooseImage.FileItem;
import com.quark.wanlihuanyunuser.util.Utils;
import com.quark.wanlihuanyunuser.util.ValidateHelper;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuarkApi {

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @param handler
     */
    public static void login(String username, String password,
                             AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("pwd", password);
        params.put("keep_login", 1);
        String loginurl = "action/api/login_validate";
        ApiHttpClient.post(loginurl, params, handler);
    }

    public static void HttpRequest(Object javaBean, AsyncHttpResponseHandler handler) {
        if (ValidateHelper.isNetworkAvailable(AppContext.instance)) {
            boolean needsign = false;
            List<MD5Item> dlist = new ArrayList<MD5Item>();
            String urlStr = "";
            RequestParams params = new RequestParams();
            Method[] methods = javaBean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                try {
                    if (method.getName().startsWith("get")) {
                        String field = method.getName();
                        field = field.substring(field.indexOf("get") + 3);
                        field = field.toLowerCase().charAt(0) + field.substring(1);
                        Object value = method.invoke(javaBean, (Object[]) null);
                        if (field.equals("url")) {
                            urlStr = value.toString();
                        } else if (field.equals("invoke")) {
                            params.put(field, "app");
                        } else {
                            if ((value != null) && (!value.toString().equals(""))) {
                                params.put(field, null == value ? "" : value.toString());
                                if (!field.equals("method")) {
                                    MD5Item md = new MD5Item(field, value.toString());
                                    dlist.add(md);  //save md5 need
                                }
                            }
                            if (field.equals("app_sign")) {
                                needsign = true;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            params.put("token", new AppParam().getToken(new AppContext().instance));
            MD5Item md = new MD5Item("token", new AppParam().getToken(new AppContext().instance));
            dlist.add(md);  //save md5 need

            if (needsign) {
                params.put("app_sign", sign(dlist));
            }

            ApiHttpClient.get(urlStr, params, handler);
        } else {
            Toast.makeText(AppContext.instance, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
    }

    //md5 sign
    public static String sign(List<MD5Item> dlist) {
        Collections.sort(dlist, new Comparator<MD5Item>() {
            @Override
            public int compare(MD5Item b1, MD5Item b2) {
                return b1.getKey().compareTo(b2.getKey());
            }
        });
        String signStr = "";
        for (int i = 0; i < dlist.size(); i++) {
            signStr += dlist.get(i).getKey() + "=" + dlist.get(i).getValue() + "&";
        }
        signStr += "key=" + new AppParam().app_quark_key;
        String sign = com.jfinal.kit.EncryptionKit.md5Encrypt(signStr).toUpperCase();
        return sign;
    }

    public static void HttpRequestpost(Object javaBean, AsyncHttpResponseHandler handler) {
        if (ValidateHelper.isNetworkAvailable(AppContext.instance)) {
            boolean needsign = false;
            List<MD5Item> dlist = new ArrayList<MD5Item>();

            String urlStr = "";
            RequestParams params = new RequestParams();
            Method[] methods = javaBean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                try {
                    if (method.getName().startsWith("get")) {
                        String field = method.getName();
                        field = field.substring(field.indexOf("get") + 3);
                        field = field.toLowerCase().charAt(0) + field.substring(1);
                        Object value = method.invoke(javaBean, (Object[]) null);
                        if (field.equals("url")) {
                            urlStr = value.toString();
                        } else if (field.equals("invoke")) {
                            params.put(field, "app");
                        } else {
                            if ((value != null) && (!value.toString().equals(""))) {
                                params.put(field, null == value ? "" : value.toString());
                                if (!field.equals("method")) {
                                    MD5Item md = new MD5Item(field, value.toString());
                                    dlist.add(md);  //save md5 need
                                }
                            }
                            if (field.equals("app_sign")) {
                                needsign = true;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            params.put("token", new AppParam().getToken(new AppContext().instance));

            MD5Item md = new MD5Item("token", new AppParam().getToken(new AppContext().instance));
            dlist.add(md);  //save md5 need

            if (needsign) {
//            Collections.sort(dlist, new Comparator<MD5Item>() {
//                @Override
//                public int compare(MD5Item b1, MD5Item b2) {
//                    return b1.getKey().compareTo(b2.getKey());
//                }
//            });
//            MD5Item key = new MD5Item("key",new AppParam().app_quark_key);
//            dlist.add(key);  //save md5 need
//            String sign = com.jfinal.kit.EncryptionKit.md5Encrypt(dlist.toString()).toUpperCase();
                params.put("app_sign", sign(dlist));
            }
            ApiHttpClient.post(urlStr, params, handler);
        } else {
            Toast.makeText(AppContext.instance, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
    }

    //不需要token 但是要签名
    public static void HttpRequestNewList(Object javaBean, AsyncHttpResponseHandler handler) {
        if (ValidateHelper.isNetworkAvailable(AppContext.instance)) {
            boolean needsign = false;
            List<MD5Item> dlist = new ArrayList<MD5Item>();
            String urlStr = "";
            RequestParams params = new RequestParams();
            Method[] methods = javaBean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                try {
                    if (method.getName().startsWith("get")) {
                        String field = method.getName();
                        field = field.substring(field.indexOf("get") + 3);
                        field = field.toLowerCase().charAt(0) + field.substring(1);
                        Object value = method.invoke(javaBean, (Object[]) null);
                        if (field.equals("url")) {
                            urlStr = value.toString();
                        } else if (field.equals("invoke")) {
                            params.put(field, "app");
                        } else {
                            if ((value != null) && (!value.toString().equals(""))) {
                                params.put(field, null == value ? "" : value.toString());
                                if (!field.equals("method")) {
                                    MD5Item md = new MD5Item(field, value.toString());
                                    dlist.add(md);  //save md5 need
                                }
                            }
                            if (field.equals("app_sign")) {
                                needsign = true;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (needsign) {
                params.put("app_sign", sign(dlist));
            }

            ApiHttpClient.get(urlStr, params, handler);
        } else {
            Toast.makeText(AppContext.instance, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openIdLogin(String s) {

    }

    /**
     * 获取新闻列表
     *
     * @param catalog 类别 （1，2，3）
     * @param page    第几页
     * @param handler
     */
    public static void getNewsList(int catalog, int page,
                                   AsyncHttpResponseHandler handler) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        if (catalog == NewsList.CATALOG_WEEK) {
//            params.put("show", "week");
//        } else if (catalog == NewsList.CATALOG_MONTH) {
//            params.put("show", "month");
//        }
//        ApiHttpClient.get("action/api/news_list", params, handlerbase);
    }
//
//    public static void getBlogList(String type, int pageIndex,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("type", type);
//        params.put("pageIndex", pageIndex);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/blog_list", params, handlerbase);
//    }
//
//    public static void getPostList(int catalog, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/post_list", params, handlerbase);
//    }
//
//    public static void getPostListByTag(String tag, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("tag", tag);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/post_list", params, handlerbase);
//    }
//
//    public static void getTweetList(int uid, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/tweet_list", params, handlerbase);
//    }
//
//    public static void getTweetTopicList(int page, String topic,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("pageIndex", page);
//        params.put("title", topic);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/tweet_topic_list", params, handlerbase);
//    }
//
//    public static void getTweetLikeList(AsyncHttpResponseHandler handlerbase) {
//        ApiHttpClient.get("action/api/my_tweet_like_list", handlerbase);
//    }
//
//    public static void pubLikeTweet(int tweetId, int authorId,
//            AsyncHttpResponseHandler handlerbase) {
//
//        RequestParams params = new RequestParams();
//        params.put("tweetid", tweetId);
//        params.put("uid", AppContext.getInstance().getLoginUid());
//        params.put("ownerOfTweet", authorId);
//        ApiHttpClient.post("action/api/tweet_like", params, handlerbase);
//    }
//
//    public static void pubUnLikeTweet(int tweetId, int authorId,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("tweetid", tweetId);
//        params.put("uid", AppContext.getInstance().getLoginUid());
//        params.put("ownerOfTweet", authorId);
//        ApiHttpClient.post("action/api/tweet_unlike", params, handlerbase);
//    }
//
//    public static void getTweetLikeList(int tweetId, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("tweetid", tweetId);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/tweet_like_list", params, handlerbase);
//
//    }
//
//    public static void getActiveList(int uid, int catalog, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("catalog", catalog);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/active_list", params, handlerbase);
//    }
//
//    public static void getFriendList(int uid, int relation, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("relation", relation);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/friends_list", params, handlerbase);
//    }
//
//    /**
//     * 获取所有关注好友列表
//     *
//     * @param uid
//     *            指定用户UID
//     * @param handlerbase
//     * */
//    public static void getAllFriendsList(int uid, int relation, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("relation", relation);
//        params.put("all", 1);
//        ApiHttpClient.get("action/api/friends_list", params, handlerbase);
//    }
//
//    /**
//     * 获取用户收藏
//     *
//     * @param uid
//     *            指定用户UID
//     * @param type
//     *            收藏类型: 0:全部收藏　1:软件　2:话题　3:博客　4:新闻　5:代码
//     * @param page
//     * @param handlerbase
//     */
//    public static void getFavoriteList(int uid, int type, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("type", type);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/favorite_list", params, handlerbase);
//    }
//
//    /**
//     * 分类列表
//     *
//     * @param tag
//     *            第一级:0
//     * @param handlerbase
//     */
//    public static void getSoftwareCatalogList(int tag,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams("tag", tag);
//        ApiHttpClient.get("action/api/softwarecatalog_list", params, handlerbase);
//    }
//
//    public static void getSoftwareTagList(int searchTag, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("searchTag", searchTag);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/softwaretag_list", params, handlerbase);
//    }
//
//    /**
//     * @param searchTag
//     *            　　软件分类　　推荐:recommend 最新:time 热门:view 国产:list_cn
//     * @param page
//     * @param handlerbase
//     */
//    public static void getSoftwareList(String searchTag, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("searchTag", searchTag);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/software_list", params, handlerbase);
//    }
//
//    /**
//     * 获取评论列表
//     *
//     * @PARAM ID
//     * @PARAM CATALOG
//     *            1新闻 2帖子 3动弹 4动态
//     * @PARAM PAGE
//     * @PARAM HANDLER
//     */
//    public static void getCommentList(int id, int catalog, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("id", id);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        params.put("clientType", "android");
//        ApiHttpClient.get("action/api/comment_list", params, handlerbase);
//    }
//
//    public static void getBlogCommentList(int id, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("id", id);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/blogcomment_list", params, handlerbase);
//    }
//
//    public static void getChatMessageList(int friendId, int page, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("id", friendId);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/message_detail", params, handlerbase);
//    }
//
//    public static void getUserInformation(int uid, int hisuid, String hisname,
//            int pageIndex, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("hisuid", hisuid);
//        params.put("hisname", hisname);
//        params.put("pageIndex", pageIndex);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/user_information", params, handlerbase);
//    }
//
//    @SuppressWarnings("deprecation")
//    public static void getUserBlogList(int authoruid, final String authorname,
//            final int uid, final int pageIndex, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("authoruid", authoruid);
//        params.put("authorname", URLEncoder.encode(authorname));
//        params.put("uid", uid);
//        params.put("pageIndex", pageIndex);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/userblog_list", params, handlerbase);
//    }
//
//    public static void updateRelation(int uid, int hisuid, int newrelation,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("hisuid", hisuid);
//        params.put("newrelation", newrelation);
//        ApiHttpClient.post("action/api/user_updaterelation", params, handlerbase);
//    }
//
//    public static void getMyInformation(int uid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        ApiHttpClient.get("action/api/my_information", params, handlerbase);
//    }
//
//    /**
//     * 获取新闻明细
//     *
//     * @param id 新闻的id
//     * @param handlerbase
//     */
//    public static void getNewsDetail(int id, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams("id", id);
//        ApiHttpClient.get("action/api/news_detail", params, handlerbase);
//    }
//
//    public static void getBlogDetail(int id, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams("id", id);
//        ApiHttpClient.get("action/api/blog_detail", params, handlerbase);
//    }
//
//    /**
//     * 获取软件详情
//     *
//     * @param ident
//     * @param handlerbase
//     */
//    public static void getSoftwareDetail(String ident,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams("ident",
//                ident);
//        ApiHttpClient.get("action/api/software_detail", params, handlerbase);
//    }
//
//    /***
//     * 通过id获取软件详情
//     * @param id
//     * @param handlerbase
//     */
//    public static void getSoftwareDetail(int id, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams("id",
//                id);
//        ApiHttpClient.get("action/api/software_detail", params, handlerbase);
//    }
//
//    public static void getPostDetail(int id, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams("id", id);
//        ApiHttpClient.get("action/api/post_detail", params, handlerbase);
//    }
//
//    public static void getTweetDetail(int id, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams("id", id);
//        ApiHttpClient.get("action/api/tweet_detail", params, handlerbase);
//    }
//
//    /**
//     * 用户针对某个新闻，帖子，动弹，消息发表评论的接口，参数使用POST方式提交
//     *
//     * @param catalog
//     *            　　 1新闻　　2 帖子　　３　动弹　　４消息中心
//     * @param id
//     *            被评论的某条新闻，帖子，动弹或者某条消息的id
//     * @param uid
//     *            当天登陆用户的UID
//     * @param content
//     *            发表的评论内容
//     * @param isPostToMyZone
//     *            是否转发到我的空间，０不转发　　１转发到我的空间（注意该功能之对某条动弹进行评论是有效，其他情况下服务器借口可以忽略该参数）
//     * @param handlerbase
//     */
//    public static void publicComment(int catalog, int id, int uid,
//            String content, int isPostToMyZone, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("id", id);
//        params.put("uid", uid);
//        params.put("content", content);
//        params.put("isPostToMyZone", isPostToMyZone);
//        ApiHttpClient.post("action/api/comment_pub", params, handlerbase);
//    }
//
//    public static void replyComment(int id, int catalog, int replyid,
//            int authorid, int uid, String content,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("id", id);
//        params.put("uid", uid);
//        params.put("content", content);
//        params.put("replyid", replyid);
//        params.put("authorid", authorid);
//        ApiHttpClient.post("action/api/comment_reply", params, handlerbase);
//    }
//
//    public static void publicBlogComment(int blog, int uid, String content,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("blog", blog);
//        params.put("uid", uid);
//        params.put("content", content);
//        ApiHttpClient.post("action/api/blogcomment_pub", params, handlerbase);
//    }
//
//    public static void replyBlogComment(int blog, int uid, String content,
//            int reply_id, int objuid, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("blog", blog);
//        params.put("uid", uid);
//        params.put("content", content);
//        params.put("reply_id", reply_id);
//        params.put("objuid", objuid);
//        ApiHttpClient.post("action/api/blogcomment_pub", params, handlerbase);
//    }
//
//    public static void pubTweet(Tweet tweet, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", tweet.getAuthorid());
//        params.put("msg", tweet.getBody());
//
//        // Map<String, File> files = new HashMap<String, File>();
//        if (!TextUtils.isEmpty(tweet.getImageFilePath())) {
//            try {
//                params.put("img", new File(tweet.getImageFilePath()));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        if (!TextUtils.isEmpty(tweet.getAudioPath())) {
//            try {
//                params.put("amr", new File(tweet.getAudioPath()));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        ApiHttpClient.post("action/api/tweet_pub", params, handlerbase);
//    }
//
//    public static void pubSoftWareTweet(Tweet tweet, int softid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", tweet.getAuthorid());
//        params.put("msg", tweet.getBody());
//        params.put("project", softid);
//        ApiHttpClient.post("action/api/software_tweet_pub", params, handlerbase);
//    }
//
//    public static void deleteTweet(int uid, int tweetid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("tweetid", tweetid);
//        ApiHttpClient.post("action/api/tweet_delete", params, handlerbase);
//    }
//
//    public static void deleteComment(int id, int catalog, int replyid,
//            int authorid, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("id", id);
//        params.put("catalog", catalog);
//        params.put("replyid", replyid);
//        params.put("authorid", authorid);
//        ApiHttpClient.post("action/api/comment_delete", params, handlerbase);
//    }
//
//    public static void deleteBlog(int uid, int authoruid, int id,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("authoruid", authoruid);
//        params.put("id", id);
//        ApiHttpClient.post("action/api/userblog_delete", params, handlerbase);
//    }
//
//    public static void deleteBlogComment(int uid, int blogid, int replyid,
//            int authorid, int owneruid, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("blogid", blogid);
//        params.put("replyid", replyid);
//        params.put("authorid", authorid);
//        params.put("owneruid", owneruid);
//        ApiHttpClient.post("action/api/blogcomment_delete", params, handlerbase);
//    }
//
//    /**
//     * 用户添加收藏
//     *
//     * @param uid
//     *            用户UID
//     * @param objid
//     *            比如是新闻ID 或者问答ID 或者动弹ID
//     * @param type
//     *            1:软件 2:话题 3:博客 4:新闻 5:代码
//     */
//    public static void addFavorite(int uid, int objid, int type,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("objid", objid);
//        params.put("type", type);
//        ApiHttpClient.post("action/api/favorite_add", params, handlerbase);
//    }
//
//    public static void delFavorite(int uid, int objid, int type,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("objid", objid);
//        params.put("type", type);
//        ApiHttpClient.post("action/api/favorite_delete", params, handlerbase);
//    }
//
//    public static void getSearchList(String catalog, String content,
//            int pageIndex, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("content", content);
//        params.put("pageIndex", pageIndex);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/search_list", params, handlerbase);
//    }
//
//    public static void publicMessage(int uid, int receiver, String content,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("receiver", receiver);
//        params.put("content", content);
//
//        ApiHttpClient.post("action/api/message_pub", params, handlerbase);
//    }
//
//    public static void deleteMessage(int uid, int friendid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("friendid", friendid);
//        ApiHttpClient.post("action/api/message_delete", params, handlerbase);
//    }
//
//    public static void forwardMessage(int uid, String receiverName,
//            String content, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("receiverName", receiverName);
//        params.put("content", content);
//        ApiHttpClient.post("action/api/message_pub", params, handlerbase);
//    }
//
//    public static void getMessageList(int uid, int pageIndex,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("pageIndex", pageIndex);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/message_list", params, handlerbase);
//    }
//
//    public static void updatePortrait(int uid, File portrait,
//            AsyncHttpResponseHandler handlerbase) throws FileNotFoundException {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("portrait", portrait);
//        ApiHttpClient.post("action/api/portrait_update", params, handlerbase);
//    }
//
//    public static void getNotices(AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", AppContext.getInstance().getLoginUid());
//        ApiHttpClient.get("action/api/user_notice", params, handlerbase);
//    }
//
//    /**
//     * 清空通知消息
//     *
//     * @param uid
//     * @param type
//     *            1:@我的信息 2:未读消息 3:评论个数 4:新粉丝个数
//     * @return
//     * @throws AppException
//     */
//    public static void clearNotice(int uid, int type,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("type", type);
//        ApiHttpClient.post("action/api/notice_clear", params, handlerbase);
//    }
//
//    public static void singnIn(String url, AsyncHttpResponseHandler handlerbase) {
//        ApiHttpClient.getDirect(url, handlerbase);
//    }
//
//    /**
//     * 获取软件的动态列表
//     *
//     * @param softid
//     * @param handlerbase
//     */
//    public static void getSoftTweetList(int softid, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("project", softid);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/software_tweet_list", params, handlerbase);
//    }
//
//    public static void checkUpdate(AsyncHttpResponseHandler handlerbase) {
//        ApiHttpClient.get("MobileAppVersion.xml", handlerbase);
//    }
//
//    /**
//     * 查找用户
//     *
//     * @param username
//     * @param handlerbase
//     */
//    public static void findUser(String username,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("name", username);
//        ApiHttpClient.get("action/api/find_user", params, handlerbase);
//    }
//
//    /**
//     * 获取活动列表
//     *
//     * @param pageIndex
//     * @param uid
//     *            <= 0 近期活动 实际的用户ID 则获取用户参与的活动列表，需要已登陆的用户
//     * @param handlerbase
//     */
//    public static void getEventList(int pageIndex, int uid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("pageIndex", pageIndex);
//        params.put("uid", uid);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/event_list", params, handlerbase);
//    }
//
//    /**
//     * 获取某活动已出席的人员列表
//     *
//     * @param eventId
//     * @param pageIndex
//     * @param handlerbase
//     */
//    public static void getEventApplies(int eventId, int pageIndex,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("pageIndex", pageIndex);
//        params.put("event_id", eventId);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        ApiHttpClient.get("action/api/event_attend_user", params, handlerbase);
//    }
//
//    /**
//     * 举报
//     *
//     * @param report
//     * @param handlerbase
//     */
//    public static void report(Report report, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("obj_id", report.getObjId());
//        params.put("url", report.getUrl());
//        params.put("obj_type", report.getObjType());
//        params.put("reason", report.getReason());
//        if (report.getOtherReason() != null
//                && !StringUtils.isEmpty(report.getOtherReason())) {
//            params.put("memo", report.getOtherReason());
//        }
//        ApiHttpClient.post("action/communityManage/report", params, handlerbase);
//    }
//
//    /**
//     * 摇一摇，随机数据
//     *
//     * @param handlerbase
//     */
//    public static void shake(AsyncHttpResponseHandler handlerbase) {
//        shake(-1, handlerbase);
//    }
//
//    /**
//     * 摇一摇指定请求类型
//     */
//    public static void shake(int type, AsyncHttpResponseHandler handlerbase) {
//        String inter = "action/api/rock_rock";
//        if (type > 0) {
//            inter = (inter + "/?type=" + type);
//        }
//        ApiHttpClient.get(inter, handlerbase);
//    }
//
//    /**
//     * 活动报名
//     *
//     * @param data
//     * @param handlerbase
//     */
//    public static void eventApply(EventApplyData data,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("event", data.getEvent());
//        params.put("user", data.getUser());
//        params.put("name", data.getName());
//        params.put("gender", data.getGender());
//        params.put("mobile", data.getPhone());
//        params.put("company", data.getCompany());
//        params.put("job", data.getJob());
//        if (!StringUtils.isEmpty(data.getRemark())) {
//            params.put("misc_info", data.getRemark());
//        }
//        ApiHttpClient.post("action/api/event_apply", params, handlerbase);
//    }
//

    private static void uploadLog(String data, String report,
                                  AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("app", "1");
        params.put("report", report);
        params.put("msg", data);
        ApiHttpClient.post("action/api/user_report_to_admin", params, handler);
    }

    //
//    /**
//     * BUG上报
//     *
//     * @param data
//     * @param handlerbase
//     */
    public static void uploadLog(String data, AsyncHttpResponseHandler handler) {
        uploadLog(data, "1", handler);
    }
//
//    /**
//     * 反馈意见
//     *
//     * @param data
//     * @param handlerbase
//     */
//    public static void feedback(String data, AsyncHttpResponseHandler handlerbase) {
//        uploadLog(data, "2", handlerbase);
//    }
//
//    /**
//     * team动态
//     *
//     * @param team
//     * @param page
//     * @param handlerbase
//     */
//    public static void teamDynamic(Team team, int page,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        // int uid = AppContext.getInstance().getLoginUid();
//        // params.put("uid", uid);
//        params.put("teamid", team.getId());
//        params.put("pageIndex", page);
//        params.put("pageSize", 20);
//        params.put("type", "all");
//        ApiHttpClient.get("action/api/team_active_list", params, handlerbase);
//    }
//
//    /**
//     * 获取team列表
//     *
//     * @param handlerbase
//     */
//    public static void teamList(AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", AppContext.getInstance().getLoginUid());
//        ApiHttpClient.get("action/api/team_list", params, handlerbase);
//    }
//
//    /**
//     * 获取team成员列表
//     *
//     * @param handlerbase
//     */
//    public static void getTeamMemberList(int teamid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("teamid", teamid);
//        ApiHttpClient.get("action/api/team_member_list", params, handlerbase);
//    }
//
//    /**
//     * 获取team成员个人信息
//     *
//     * @param handlerbase
//     */
//    public static void getTeamUserInfo(String teamid, String uid,
//            int pageIndex, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("teamid", teamid);
//        params.put("uid", uid);
//        params.put("pageIndex", pageIndex);
//        params.put("pageSize", 20);
//        ApiHttpClient.get("action/api/team_user_information", params, handlerbase);
//    }
//
//    /**
//     * 获取我的任务中进行中、未完成、已完成等状态的数量
//     */
//    public static void getMyIssueState(String teamid, String uid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("teamid", teamid);
//        params.put("uid", uid);
//        ApiHttpClient.get("action/api/team_user_issue_information", params,
//                handlerbase);
//    }
//
//    /**
//     * 获取指定用户的动态
//     */
//    public static void getUserDynamic(int teamid, String uid, int pageIndex,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("teamid", teamid);
//        params.put("pageIndex", pageIndex);
//        params.put("pageSize", 20);
//        params.put("type", "git");
//        params.put("uid", uid);
//        ApiHttpClient.get("action/api/team_active_list", params, handlerbase);
//    }
//
//    /**
//     * 动态详情
//     *
//     * @param activeid
//     * @param teamid
//     * @param uid
//     * @param handlerbase
//     */
//    public static void getDynamicDetail(int activeid, int teamid, int uid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("teamid", teamid);
//        params.put("uid", uid);
//        params.put("activeid", activeid);
//        ApiHttpClient.get("action/api/team_active_detail", params, handlerbase);
//    }
//
//    /**
//     * 获取指定用户的任务
//     */
//    public static void getMyIssue(String teamid, String uid, int pageIndex,
//            String type, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("teamid", teamid);
//        params.put("uid", uid);
//        params.put("pageIndex", pageIndex);
//        params.put("pageSize", 20);
//        params.put("state", type);
//        params.put("projectid", "-1");
//        ApiHttpClient.get("action/api/team_issue_list", params, handlerbase);
//    }
//
//    /**
//     * 获取指定周周报
//     *
//     * @param teamid
//     * @param year
//     * @param week
//     * @param handlerbase
//     */
//    public static void getDiaryFromWhichWeek(int teamid, int year, int week,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("teamid", teamid);
//        params.put("year", year);
//        params.put("week", week);
//        ApiHttpClient.get("action/api/team_diary_list", params, handlerbase);
//    }
//
//    /**
//     * 删除一个便签
//     *
//     * @param id
//     *            便签id
//     * @param uid
//     *            用户id
//     */
//    public static void deleteNoteBook(int id, int uid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("id", id); // 便签id
//        ApiHttpClient
//                .get("action/api/team_stickynote_recycle", params, handlerbase);
//    }
//
//    public static void getNoteBook(int uid, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        ApiHttpClient.get("action/api/team_sticky_list", params, handlerbase);
//    }
//
//    /**
//     * 获取指定周报的详细信息
//     *
//     * @param teamid
//     * @param diaryid
//     * @param handlerbase
//     */
//    public static void getDiaryDetail(int teamid, int diaryid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("teamid", teamid);
//        params.put("diaryid", diaryid);
//        ApiHttpClient.get("action/api/team_diary_detail", params, handlerbase);
//    }
//
//    /**
//     * diary评论列表
//     *
//     * @param teamid
//     * @param diaryid
//     * @param handlerbase
//     */
//    public static void getDiaryComment(int teamid, int diaryid,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("teamid", teamid);
//        params.put("id", diaryid);
//        params.put("type", "diary");
//        params.put("pageIndex", 0);
//        params.put("pageSize", "20");
//        KJLoger.debug(teamid + "==getDiaryComment接口=" + diaryid);
//        ApiHttpClient
//                .get("action/api/team_reply_list_by_type", params, handlerbase);
//    }
//
//    /**
//     * 周报评论（以后可改为全局评论）
//     *
//     * @param uid
//     * @param teamid
//     * @param diaryId
//     * @param content
//     * @param handlerbase
//     */
//    public static void sendComment(int uid, int teamid, int diaryId,
//            String content, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("uid", uid);
//        params.put("teamid", teamid);
//        params.put("type", "118");
//        params.put("tweetid", diaryId);
//        params.put("content", content);
//        ApiHttpClient.post("action/api/team_tweet_reply", params, handlerbase);
//    }
//
//    /***
//     * 客户端扫描二维码登陆
//     *
//     * @author 火蚁 2015-3-13 上午11:45:47
//     *
//     * @return void
//     * @param url
//     * @param handlerbase
//     */
//    public static void scanQrCodeLogin(String url,
//            AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        String uuid = url.substring(url.lastIndexOf("=") + 1);
//        params.put("uuid", uuid);
//        ApiHttpClient.getDirect(url, handlerbase);
//    }
//
//    /***
//     * 使用第三方登陆
//     * @param catalog 类别
//     * @param openIdInfo 第三方的info
//     * @param handlerbase handlerbase
//     */
//    public static void open_login(String catalog, String openIdInfo, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("openid_info", openIdInfo);
//        ApiHttpClient.post("action/api/openid_login", params, handlerbase);
//    }
//
//    /***
//     * 第三方登陆账号绑定
//     * @param catalog 类别（QQ、wechat）
//     * @param openIdInfo 第三方info
//     * @param userName 用户名
//     * @param pwd 密码
//     * @param handlerbase handlerbase
//     */
//    public static void bind_openid(String catalog, String openIdInfo, String userName, String pwd, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("openid_info", openIdInfo);
//        params.put("username", userName);
//        params.put("pwd", pwd);
//        ApiHttpClient.post("action/api/openid_bind", params, handlerbase);
//    }
//
//    /***
//     * 使用第三方账号注册
//     * @param catalog 类别（qq、wechat）
//     * @param openIdInfo 第三方info
//     * @param handlerbase handlerbase
//     */
//    public static void openid_reg(String catalog, String openIdInfo, AsyncHttpResponseHandler handlerbase) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("openid_info", openIdInfo);
//        ApiHttpClient.post("action/api/openid_reg", params, handlerbase);
//    }


    //上傳文件
    public static void HttpuploadFile(Object javaBean,List<FileItem> fileItems, HttpCallBack httpCallBack) {
        if (ValidateHelper.isNetworkAvailable(AppContext.instance)) {
            String urlStr = "";
            HttpParams params = new HttpParams();
            Method[] methods = javaBean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                try {
                    if (method.getName().startsWith("get")) {
                        String field = method.getName();
                        field = field.substring(field.indexOf("get") + 3);
                        field = field.toLowerCase().charAt(0) + field.substring(1);
                        Object value = method.invoke(javaBean, (Object[]) null);
                        if (field.equals("url")) {
                            urlStr = value.toString();
                        } else if (field.equals("invoke")) {
                            params.put(field, "app");
                        } else {
                            if ((value != null) && (!value.toString().equals(""))) {
                                params.put(field, null == value ? "" : value.toString());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String token = new AppParam().getToken(new AppContext().instance);
            if (!Utils.isEmpty(token)) {
                params.put("token", token);
            }
            if (fileItems != null && fileItems.size() > 0) {
                for (int i = 0; i < fileItems.size(); i++) {
                    params.put(fileItems.get(i).getName(), fileItems.get(i).getFile());
                }
            }
            KJHttp kjh = new KJHttp();
            kjh.post(ApiHttpClient.HOSTURL + urlStr, params,false, httpCallBack);
        } else {
            Toast.makeText(AppContext.instance, "當前網絡不可用", Toast.LENGTH_SHORT).show();
        }
    }


    //上傳文件  不需要token请求
    public static void HttpuploadFileNewList(Object javaBean,List<FileItem> fileItems, HttpCallBack httpCallBack) {
        if (ValidateHelper.isNetworkAvailable(AppContext.instance)) {
            String urlStr = "";
            HttpParams params = new HttpParams();
            Method[] methods = javaBean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                try {
                    if (method.getName().startsWith("get")) {
                        String field = method.getName();
                        field = field.substring(field.indexOf("get") + 3);
                        field = field.toLowerCase().charAt(0) + field.substring(1);
                        Object value = method.invoke(javaBean, (Object[]) null);
                        if (field.equals("url")) {
                            urlStr = value.toString();
                        } else if (field.equals("invoke")) {
                            params.put(field, "app");
                        } else {
                            if ((value != null) && (!value.toString().equals(""))) {
                                params.put(field, null == value ? "" : value.toString());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            String token = new AppParam().getToken(new AppContext().instance);
//            if (!Utils.isEmpty(token)) {
//                params.put("token", token);
//            }
            if (fileItems != null && fileItems.size() > 0) {
                for (int i = 0; i < fileItems.size(); i++) {
                    params.put(fileItems.get(i).getName(), fileItems.get(i).getFile());
                }
            }
            KJHttp kjh = new KJHttp();
            kjh.post(ApiHttpClient.HOSTURL + urlStr, params, false,httpCallBack);
        } else {
            Toast.makeText(AppContext.instance, "當前網絡不可用", Toast.LENGTH_SHORT).show();
        }
    }
}
