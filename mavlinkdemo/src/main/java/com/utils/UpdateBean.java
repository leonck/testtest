package com.utils;

/**
 * @project name: X-Hubsan
 * @class name：com.csk.hbsdrone.update
 * @class describe:
 * @anthor shengkun.cheng
 * @time 2017/4/5 16:45
 * @change
 * @chang time:
 * @class describe:
 */
public class UpdateBean {


    /**
     * code : 600
     * message : 获取app版本成功
     * appapi : 1.0
     * date : 20170405
     * data : {"appversion":"20160107","appurl":"http://www.baidu.com","description":"You have new version,please update","forcedUpgrade":"1"}
     */

    private int code;
    private String message;
    private String appapi;
    private int date;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppapi() {
        return appapi;
    }

    public void setAppapi(String appapi) {
        this.appapi = appapi;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appversion : 20160107
         * appurl : http://www.baidu.com
         * description : You have new version,please update
         * forcedUpgrade : 1
         */

        private String appversion;
        private String appurl;
        private String description;
        private String forcedUpgrade;

        public String getAppversion() {
            return appversion;
        }

        public void setAppversion(String appversion) {
            this.appversion = appversion;
        }

        public String getAppurl() {
            return appurl;
        }

        public void setAppurl(String appurl) {
            this.appurl = appurl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getForcedUpgrade() {
            return forcedUpgrade;
        }

        public void setForcedUpgrade(String forcedUpgrade) {
            this.forcedUpgrade = forcedUpgrade;
        }
    }
}
