
package com.hubsan.swifts.drone.variables.mission;

import com.MAVLink.common.msg_mission_item;
import com.MAVLink.enums.MAV_CMD;
import com.amap.api.maps.model.LatLng;
import com.hubsan.swifts.SwiftsApplication;
import com.hubsan.swifts.drone.variables.AirLine;
import com.hubsan.swifts.drone.variables.mission.waypoints.SpatialCoordItem;
import com.hubsan.swifts.drone.variables.mission.waypoints.Waypoint;
import com.hubsan.swifts.helpers.geoTools.GeoTools;
import com.hubsan.swifts.helpers.units.Altitude;
import com.hubsan.swifts.helpers.units.Length;
import com.hubsan.swifts.helpers.units.Speed;
import com.hubsan.swifts.mapUtils.MapPath;
import com.hubsan.swifts.mapUtils.marks.MarkerManager;
import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneInterfaces.DroneEventsType;
import com.hubsansdk.drone.HubsanDroneVariable;
import com.utils.Constants;
import com.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class Mission extends HubsanDroneVariable implements MapPath.PathSource {

    private List<MissionItem> items = new ArrayList<MissionItem>();
    private List<MissionItem> tempList = new ArrayList<>();//保存范围内的数据
    private List<MissionItem> selection = new ArrayList<MissionItem>();
    private Length missionLength = new Length(0);
    private Altitude defaultAlt;
    private AirLine airline;
    private Speed defultSpeed;
    private Length distanceHome2firstMission = new Length(0);
    private int sendflag = 0;
    public MissionState missionState = MissionState.EDIT;
    private Speed speed;
    private SwiftsApplication app;
    public enum MissionState {
        EDIT, UPLOAD, TIMEOUT;
    }

    public MissionState getMissionState() {

        return missionState;
    }

    public void setMissionState(MissionState missionState) {

        this.missionState = missionState;
    }

    public Mission(HubsanDrone myDrone) {
        super(myDrone);

    }

    public int getSendflag() {
        return sendflag;
    }

    public void setSendflag(int sendflag) {
        this.sendflag = sendflag;
    }

    public boolean isEmpty() {
        return items.size() < 1;
    }

    public void edit() {
        missionState = MissionState.EDIT;
    }

    /**
     * 初始化默认高度
     *
     * @param defHeight
     */
    public void init(double defHeight) {
        app = (SwiftsApplication) HubsanApplication.getApplication();
        int deful = PreferenceUtils.getPrefInt(HubsanApplication.getApplication(), Constants.SETTING_FLIGHTDATA_DEFAULT_HEIGHT, 30);
        int defulH = PreferenceUtils.getPrefInt(HubsanApplication.getApplication(), "defulHeight", 0);
        if (deful != defulH) {
            PreferenceUtils.setPrefInt(HubsanApplication.getApplication(), "defulHeight", deful);
            defaultAlt = new Altitude(deful);
            setDefaultAlt(defaultAlt);
            initSpeed(5);
        } else {
            defaultAlt = new Altitude(deful);
            setDefaultAlt(defaultAlt);
            initSpeed(5);
        }
    }

    /**
     * 获取默认高度
     *
     * @return
     */
    public Altitude getDefaultAlt() {
        return defaultAlt;
    }

    /**
     * 设置默认高度
     *
     * @param newAltitude
     */
    public void setDefaultAlt(Altitude newAltitude) {
        defaultAlt = newAltitude;
    }

    public void initSpeed(float defultSpeeds) {
        defultSpeed = new Speed(defultSpeeds);
    }

    public Speed getDefultSpeed() {
        return defultSpeed;
    }

    public void setDefultSpeed(Speed defultSpeed) {
        this.defultSpeed = defultSpeed;
    }

    public Length getMissonLength(boolean fromhome) {

        return this.getMissonLength(items, fromhome);
    }

    public void setMissionHome(MissionItem item) {

        items.add(0, item);
    }

    private Length getMissonLength(List<MissionItem> list, boolean fromhome) {
        double l1 = 0, l2, sum = 0;
        MissionItem m1, m2;
        if (list.size() == 1)
            return new Length(0);
        for (int i = 1; i < list.size(); i++) {
            m1 = list.get(i - 1);
            m2 = list.get(i);
            if ((m1 instanceof SpatialCoordItem) && (m2 instanceof SpatialCoordItem)) {
                l1 = GeoTools.getDistance(((SpatialCoordItem) m1).getCoordinate(), ((SpatialCoordItem) m2).getCoordinate()).valueInMeters();
            }
            sum += l1;
        }
        if (fromhome)
            return new Length(sum + this.distanceHome2firstMission.valueInMeters());
        else
            return new Length(sum);
    }

    /*
     * delete the mission item that beyond the lenght limit 
     * 删除任务项以外的长度限制
     */
    public void delIllegalMission(double lengthLimit) {
        double l1 = 0, l2, sum = 0;
        MissionItem m1, m2;
        List<MissionItem> titems = new ArrayList<MissionItem>();
        if (items.size() == 1)
            return;
        int i = 0, j = 0;
        for (i = 1; i < items.size(); i++) {
            m1 = items.get(i - 1);
            m2 = items.get(i);
            if ((m1 instanceof SpatialCoordItem) && (m2 instanceof SpatialCoordItem)) {
                l1 = GeoTools.getDistance(((SpatialCoordItem) m1).getCoordinate(), ((SpatialCoordItem) m2).getCoordinate()).valueInMeters();
            }
            sum += l1;
            if (sum > lengthLimit) {
                break;
            }
        }
        for (j = i; j < items.size(); j++) {
            titems.add(items.get(j));
        }
        if (titems.size() > 0)
            removeWaypoints(titems);
    }

    public void imporMission(List<Waypoint> listItem) {
        items.clear();
        selection.clear();
        items.addAll(listItem);
        notifiyMissionUpdate();
    }

    public void resetMission() {
        items.clear();
        selection.clear();
        notifiyMissionUpdate();
    }

    public List<MissionItem> exportMission() {
        return items;
    }

    /**
     * 移除单个航点
     */
    public void removeWaypoint(MissionItem item) {
        items.remove(item);
        selection.remove(item);
        notifiyMissionUpdate();
    }

    /**
     * 删除地图上所有航点
     */
    public void removeWaypoints(List<MissionItem> toRemove) {
        items.removeAll(toRemove);
        selection.removeAll(toRemove);
        notifiyMissionUpdate();
    }

    /**
     * 删除地图上所有航点
     */
    public void removeBeyondWaypoints(List<MissionItem> toRemove) {
        items.removeAll(toRemove);
        selection.removeAll(toRemove);
        notifiyMissionUpdate();
    }

    /**
     * 清除
     */
    public void clear() {
        items.clear();
        selection.clear();
        notifiyMissionUpdate();
    }

    /**
     * 添加一组点
     */
    public void addWaypoints(List<LatLng> points) {
        for (LatLng point : points) {
            items.add(new Waypoint(this, point, getAltitude(), getSpeed()));
        }
        notifiyMissionUpdate();
    }

    /**
     * 添加一个点
     */
    public void addWaypoint(LatLng point) {
        items.add(new Waypoint(this, point, getAltitude(), getSpeed()));
        notifiyMissionUpdate();
    }

    public void notifiyMissionUpdate() {
        myDrone.events.notifyDroneEvent(DroneEventsType.HUBSAN_MISSION_UPDATE);
    }

    private Altitude getLastAltitude() {
        Altitude alt;
        try {
            SpatialCoordItem lastItem = (SpatialCoordItem) items.get(items.size() - 1);
            alt = lastItem.getAltitude();
        } catch (Exception e) {
            alt = defaultAlt;
        }
        return alt;
    }

    private Speed getLastSpeed() {
        Speed speed;
        try {
            SpatialCoordItem lastItem = (SpatialCoordItem) items.get(items.size() - 1);
            speed = lastItem.getSpeed();
        } catch (Exception e) {
            speed = defultSpeed;
        }
        return speed;
    }
    private Altitude getAltitude() {
        int deful = PreferenceUtils.getPrefInt(HubsanApplication.getApplication(), Constants.SETTING_FLIGHTDATA_DEFAULT_HEIGHT, 30);
        int defulH = PreferenceUtils.getPrefInt(HubsanApplication.getApplication(), "defulHeight", 0);
        return new Altitude(deful);
    }
    private Speed getSpeed() {
        return new Speed(5);
    }


    public void replace(MissionItem oldItem, MissionItem newItem) {

        int index = items.indexOf(oldItem);
        if (selectionContains(oldItem)) {
            removeItemFromSelection(oldItem);
            addToSelection(newItem);
        }
        items.remove(index);
        items.add(index, newItem);
        notifiyMissionUpdate();
    }

    public void reverse() {
        Collections.reverse(items);
        notifiyMissionUpdate();
    }


    @Override
    public List<LatLng> getPathPoints() {

        List<LatLng> newPath = new ArrayList<LatLng>();
        for (MissionItem item : items) {
            try {
                newPath.addAll(item.getPath());
            } catch (Exception e) {
                e.printStackTrace();
                // Exception when no path for the item
            }
        }
        return newPath;
    }

    public List<MissionItem> getItems() {

        return items;
    }

    public List<MarkerManager.MarkerSource> getMarkers() {

        List<MarkerManager.MarkerSource> markers = new ArrayList<MarkerManager.MarkerSource>();
        for (MissionItem item : items) {
            try {
                markers.addAll(item.getMarkers());
            } catch (Exception e) {
                e.printStackTrace();
                // Exception when no markers for the item
            }
        }
        return markers;
    }

    public Integer getNumber(MissionItem waypoint) {

        return items.indexOf(waypoint) + 1; // plus one to account for the fact
        // that this is an index
    }

    public Length getAltitudeDiffFromPreviousItem(SpatialCoordItem waypoint) throws Exception {

        int i = items.indexOf(waypoint);
        if (i > 0) {
            MissionItem previus = items.get(i - 1);
            if (previus instanceof SpatialCoordItem) {
                return waypoint.getAltitude().subtract(((SpatialCoordItem) previus).getAltitude());
            }
        }
        throw new Exception("Last waypoint doesn't have an altitude");
    }

    public Length getDistanceFromLastWaypoint(SpatialCoordItem waypoint) throws Exception {

        int i = items.indexOf(waypoint);
        if (i > 0) {
            MissionItem previus = items.get(i - 1);
            if (previus instanceof SpatialCoordItem) {
                return GeoTools.getDistance(waypoint.getCoordinate(), ((SpatialCoordItem) previus).getCoordinate());
            }
        }
        throw new Exception("Last waypoint doesn't have a coordinate");
    }

    public boolean hasItem(MissionItem item) {
        return items.contains(item);
    }

    public void clearSelection() {
        selection.clear();
    }

    public boolean selectionContains(MissionItem item) {
        return selection.contains(item);
    }

    public void addToSelection(List<MissionItem> items) {
        selection.addAll(items);
    }

    public void addToSelection(MissionItem item) {
        selection.add(item);
    }

    public void setSelectionTo(MissionItem item) {
        selection.clear();
        selection.add(item);
    }

    public void removeItemFromSelection(MissionItem item) {

        selection.remove(item);
    }

    public List<MissionItem> getSelected() {

        return selection;
    }

    public void onMissionReceived(List<msg_mission_item> msgs) {

        if (msgs != null) {
            msgs.remove(0); // Remove Home waypoint
            selection.clear();
            items.clear();
            items.addAll(processMavLinkMessages(msgs));
            notifiyMissionUpdate();
        }
    }

    private List<MissionItem> processMavLinkMessages(List<msg_mission_item> msgs) {

        List<MissionItem> received = new ArrayList<MissionItem>();

        for (msg_mission_item msg : msgs) {
            switch (msg.command) {
                case MAV_CMD.MAV_CMD_NAV_WAYPOINT:
                    received.add(new Waypoint(msg, this));
                    break;
                default:
                    break;
            }
        }
        return received;
    }

    /**
     * Description 发送航点给飞机
     * @param drone
     * @return
     */
    public boolean sendMissionToAPM(HubsanDrone drone) {
        missionState = MissionState.UPLOAD;
        return myDrone.mavLinkSendMessage.sendWaypointsContinue(getMsgMissionItems(), drone);
    }

    /**
     * 获取航点数据
     * Description
     *
     * @return
     * @see [class/class#field/class#method]
     */
    public List<msg_mission_item> getMsgMissionItems() {
        List<msg_mission_item> data = new ArrayList<msg_mission_item>();
        for (MissionItem item : items) {
            data.addAll(item.packMissionItem());
        }
        return data;
    }

    /**
     * 发送成功数据后保存航点
     *
     * @param drone
     */
//    public void saveWaypointData(HubsanDrone drone) {
//        app.dao.saveMission(getItems());
//    }

}
