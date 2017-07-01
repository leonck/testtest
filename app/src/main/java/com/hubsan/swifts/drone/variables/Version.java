//
//package com.hubsan.swifts.drone.variables;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.preference.PreferenceManager;
//import android.util.Log;
//
//import com.hubsansdk.application.HubsanApplication;
//import com.hubsansdk.drone.HubsanDrone;
//import com.hubsansdk.drone.HubsanDroneVariable;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class Version extends HubsanDroneVariable {
//
//    public static final String GETVERSION = "get.version";
//
//    public static final String LATESTVERSION = "version.latest";
//
//    public static final String LATESTVERSIONURL = "version.url";
//
//    public static final String LATESTVERSIONDISC = "version.disc";
//
//    public static final String LATESTVERSIONLEVEL = "version.level";
//
//    public static Version mVersion;
//
//    private String curVersion;
//
//    private String latestVersion;
//
//    private String versionLatestDisc;
//
//    private String versionLatestUrl;
//
//    private int versionLatestLevel;
//
//    private boolean ignore;
//
//    private String versionIgnore;
//
//    private static Context context;
//
//    private SharedPreferences prefs;
//
//    private static final String pref_versionIgnore = "pref_versionIgnore";
//
//    private static final String TAG = "Version";
//
//    private boolean updating;
//
//    private static final int VERLEN = 6;
//
//    public Version( HubsanDrone myDrone) {
//        super(myDrone);
//        Init(HubsanApplication.getApplication());
//    }
//
//    private boolean Init(Context context) {
//        try {
//            curVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
//            prefs = PreferenceManager.getDefaultSharedPreferences(context);
//            versionIgnore = prefs.getString(pref_versionIgnore, curVersion);
//            //    getUpdate();
//            return true;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    /**
//     *
//     * Description 获取版本号
//     * @param mContext
//     * @return
//     * @see [class/class#field/class#method]
//     */
//    public static int getVersionCode(Context mContext) {
//        try {
//            PackageManager pm = mContext.getPackageManager();
//            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
//            return info.versionCode;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//    public static String getVersionNamse(Context mContext) {
//        try {
//            PackageManager pm = mContext.getPackageManager();
//            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
//            return info.versionName;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//    public boolean isUpdating() {
//        return updating;
//    }
//
//    public void setUpdating(boolean updating) {
//
//        this.updating = updating;
//    }
//
//    /*
//     * {"version:url":"15012301","version:disc":"15012301","version:latest":"15012301","version:level":0}
//     */
//    public boolean fromJson(JSONObject Jversion) {
//
//        if (Jversion.has(LATESTVERSION)) {
//            try {
//                setLatestVersion(Jversion.getString(LATESTVERSION));
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return false;
//            }
//        } else {
//            Log.e(TAG, "invalid LATESTVERSION info");
//        }
//
//        if (Jversion.has(LATESTVERSIONURL)) {
//            try {
//                setVersionLatestUrl(Jversion.getString(LATESTVERSIONURL));
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return false;
//            }
//        } else {
//            Log.e(TAG, "invalid LATESTVERSION info");
//        }
//
//        if (Jversion.has(LATESTVERSIONDISC)) {
//
//            try {
//                setVersionLatestDisc(Jversion.getString(LATESTVERSIONDISC));
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return false;
//            }
//        } else {
//            Log.e(TAG, "invalid LATESTVERSION info");
//        }
//
//        setVersionLatestLevel(Jversion.optInt(LATESTVERSIONLEVEL));
//        Log.d(TAG, this.toString());
////        myDrone.events.notifyDroneEvent(DroneEventsType.VERSION);
//        return true;
//
//    }
//
//
//
//    public boolean needUpdate() {
//
//        if (getVersionLatestLevel() >= 1) {
//            return true;
//        } else {
//
//            try {
//                int cur = Integer.valueOf(getCurVersion().substring(0, VERLEN));
//                int latest = Integer.valueOf(getLatestVersion().substring(0, VERLEN));
//                int ignore = Integer.valueOf(getVersionIgnore().substring(0, VERLEN));
//                if (latest > cur) {
//                    if (latest == ignore) {
//                        return false;
//                    } else {
//                        return true;
//                    }
//                } else {
//                    return false;
//                }
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//    }
//
//    public void forceUpdate() {
//
//    }
//
//    public void ignoreLatest() {
//
//        versionIgnore = this.getLatestVersion();
//        prefs.edit().putString(pref_versionIgnore, versionIgnore);
//        this.updating = false;
//
//    }
//
//    public void later() {
//
//        this.updating = false;
//    }
//
//    public String getCurVersion() {
//
//        return curVersion;
//    }
//
//    public void setCurVersion(String curVersion) {
//
//        this.curVersion = curVersion;
//    }
//
//    public String getLatestVersion() {
//
//        return latestVersion;
//    }
//
//    public void setLatestVersion(String latestVersion) {
//
//        this.latestVersion = latestVersion;
//    }
//
//    public String getVersionLatestDisc() {
//
//        return versionLatestDisc;
//    }
//
//    public void setVersionLatestDisc(String versionLatestDisc) {
//
//        this.versionLatestDisc = versionLatestDisc;
//    }
//
//    public String getVersionLatestUrl() {
//
//        return versionLatestUrl;
//    }
//
//    public void setVersionLatestUrl(String versionLatestUrl) {
//
//        this.versionLatestUrl = versionLatestUrl;
//    }
//
//    public int getVersionLatestLevel() {
//
//        return versionLatestLevel;
//    }
//
//    public void setVersionLatestLevel(int versionLatestLevel) {
//
//        this.versionLatestLevel = versionLatestLevel;
//    }
//
//    public String getVersionIgnore() {
//
//        return versionIgnore;
//    }
//
//    public void setVersionIgnore(String versionIgnore) {
//
//        this.versionIgnore = versionIgnore;
//    }
//
//    @Override
//    public String toString() {
//
//        return "Version [curVersion=" + curVersion + ", latestVersion=" + latestVersion + ", versionLatestDisc=" + versionLatestDisc + ", versionLatestUrl=" + versionLatestUrl + ", versionLatestLevel=" + versionLatestLevel + ", ignore=" + ignore + ", versionIgnore=" + versionIgnore + ", context=" + context + ", updating=" + updating + "]";
//    }
//
//}
