package com.hubsan.swifts.mapUtils;

import android.content.res.Resources;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.List;

/**
 * Description 画地图路线
 *
 * @author ShengKun.Cheng
 * @date 2015年7月15日
 * @see [class/class#method]
 * @since [product/model]
 */

//=============tag leon =============
public class MapPath {
    /**
     * 设置地图上航线的宽度
     */
    private static final int DEFAULT_WITDH = 8;
    private Polyline missionPath;
    private AMap aMap;
    private float width;
    private int color;
    private PolylineOptions flightPath;

    public interface PathSource {
        public List<LatLng> getPathPoints();
    }


    public MapPath(AMap aMap, int color, Resources resources) {
        this(aMap, color, DEFAULT_WITDH, resources);
    }

    private MapPath(AMap aMap, int color, float width, Resources resources) {
        this.aMap = aMap;
        this.color = color;
        this.width = scaleDpToPixels(width, resources);
    }

    private int scaleDpToPixels(double value, Resources res) {
        final float scale = res.getDisplayMetrics().density;
        return (int) Math.round(value * scale);
    }

    /**
     * 更新航线
     *
     * @param pathSource
     */
    public void update(PathSource pathSource) {
        if (pathSource == null)
            return;
        List<LatLng> newPath = pathSource.getPathPoints();
        if (newPath == null || newPath.size() == 0) {
            return;
        }
        if (flightPath == null) {
            flightPath = new PolylineOptions();
            flightPath.color(color).width(width).setUseTexture(true);
            flightPath.addAll(newPath);
            missionPath = aMap.addPolyline(flightPath);
        }
        missionPath.setPoints(newPath);
        missionPath.setVisible(true);
    }

    /**
     * 通过隐藏和显示了控制地图上删除不干净的问题
     */
    public void cancelPolyline() {
        missionPath.setVisible(false);
    }
}