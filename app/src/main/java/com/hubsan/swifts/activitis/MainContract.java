/*
 * Copyright 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hubsan.swifts.activitis;

import android.graphics.Point;
import android.os.Message;
import android.view.Surface;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.hubsan.swifts.BasePresenter;
import com.hubsan.swifts.BaseView;
import com.hubsan.swifts.drone.variables.mission.MissionItem;

import java.util.List;


public class MainContract {

    public interface View extends BaseView<Presenter> {
        AMap getAMap();
        void updateMap(List<LatLng> newPath);
        void addMMarkerMap(double lat,double lon,int rotate,boolean status);
        void clearWaypointLine();
        void receiveInstructions(Message msg);
    }

    public interface Presenter extends BasePresenter {
        void onPathFinished(List<Point> path) ;
        void uploadWayPoin();
        void beginOrCloseWapPoin();
        void dealCalibration(double lat,double lon);
        void deleteAllWaypoint();
        void deleteWaypoint(MissionItem mItems);
        void addFocus(LatLng coord);
        void playCamera(Surface surface);
    }
}
