
package com.hubsan.swifts.mapUtils.marks;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * Description 
 * 		标记的管理器
 * @author ShengKun.Cheng
 * @date  2015年7月15日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class MarkerManager {
    
    public AMap aMap;
    
    public HashMap<Marker, MarkerSource> hashMap = new HashMap<Marker, MarkerSource>();
    
    public interface MarkerSource {
        
        MarkerOptions build(Context context);
        
        void update(Marker marker, Context context);
    }
    
    public MarkerManager(AMap map) {
        this.aMap = map;
    }
    
    /**
     * 清理
     * Description 
     * @see [class/class#field/class#method]
     */
    public void clean() {
        List<MarkerSource> emptyList = new ArrayList<MarkerSource>();
        removeOldMarkers(emptyList);
    }
    
    /**
     * 更新标记
     * Description 
     * @param list
     * @param draggable
     * @param context
     * @see [class/class#field/class#method]
     */
    public <T extends MarkerSource> void updateMarkers(List<T> list, boolean draggable, Context context) {
        for (T object : list) {
            updateMarker(object, draggable, context);
        }
    }
    
    /**
     * 
     * Description  更新标记
     * @param source
     * @param draggable
     * @param context
     * @see [class/class#field/class#method]
     */
    public void updateMarker(MarkerSource source, boolean draggable, Context context) {
        if (hashMap.containsValue(source)) {
            Marker marker = getMarkerFromSource(source);
            source.update(marker, context);
            marker.setDraggable(draggable);
        } else {
            addMarker(source, draggable, context);
        }
    }
    
    /**
     *  清除旧的标记
     * Description 
     * @param list
     * @see [class/class#field/class#method]
     */
    private <T> void removeOldMarkers(List<T> list) {
    
        List<MarkerSource> toRemove = new ArrayList<MarkerSource>();
        for (Marker marker : hashMap.keySet()) {
            MarkerSource object = getSourceFromMarker(marker);
            if (!list.contains(object)) {
                toRemove.add(object);
            }
        }
        for (MarkerSource markerSource : toRemove) {
            removeMarker(markerSource);
        }
        
    }
    
    /**
     * 移除标记
     * Description 
     * @param object
     * @return
     * @see [class/class#field/class#method]
     */
    private boolean removeMarker(MarkerSource object) {
    
        if (hashMap.containsValue(object)) {
            Marker marker = getMarkerFromSource(object);
            hashMap.remove(marker);
            marker.remove();
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 添加标记
     * Description 
     * @param object
     * @param draggable
     * @param context
     * @see [class/class#field/class#method]
     */
    private void addMarker(MarkerSource object, boolean draggable, Context context) {
        Marker marker = aMap.addMarker(object.build(context));
        marker.setDraggable(draggable);
        hashMap.put(marker, object);
    }
    
    /**
     * 遍历得到标记
     * Description 
     * @param object
     * @return
     * @see [class/class#field/class#method]
     */
    public Marker getMarkerFromSource(MarkerSource object) {
    
        for (Marker marker : hashMap.keySet()) {
            if (getSourceFromMarker(marker) == object) {
                return marker;
            }
        }
        return null;
    }
    
    public MarkerSource getSourceFromMarker(Marker marker) {
    
        return hashMap.get(marker);
    }
    
}