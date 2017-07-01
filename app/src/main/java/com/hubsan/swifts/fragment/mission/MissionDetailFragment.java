//
//package com.hubsan.swifts.fragment.mission;
//
//import android.app.Activity;
//import android.app.DialogFragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.TextView;
//
//import com.hubsan.swifts.R;
//import com.hubsan.swifts.SwiftsApplication;
//import com.hubsan.swifts.drone.variables.mission.MissionItem;
//import com.hubsansdk.application.HubsanApplication;
//
//public abstract class MissionDetailFragment extends DialogFragment implements OnItemSelectedListener {
//
//    public interface OnWayPointTypeChangeListener {
//
//        public void onWaypointTypeChanged(MissionItem newItem, MissionItem oldItem);
//    }
//
//    protected abstract int getResource();
//
//    protected SpinnerSelfSelect typeSpinner;
//
//    protected AdapterMissionItens commandAdapter;
//
//    protected Mission mission;
//
//    private OnWayPointTypeChangeListener mListner;
//
//    protected MissionItem item;
//    private SwiftsApplication app;
//
//    public void onActivityCreated(Bundle savedInstanceState) {
//
//        super.onActivityCreated(savedInstanceState);
//
//        final Hubsan501AActivity parentActivity = (Hubsan501AActivity)getActivity();
//        if (parentActivity.getItemDetailFragment() != this) {
//            dismiss();
//            // parentActivity.switchItemDetail(item);
//        }
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
//        setRetainInstance(true);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View view = inflater.inflate(getResource(), null);
//        setupViews(view);
//        applyProfie(view);
//        return view;
//    }
//
//    protected void setupViews(View view) {
//        app = (SwiftsApplication) HubsanApplication.getApplication();
//        mission = app.mission;
//        mListner = (OnWayPointTypeChangeListener)getActivity();
//        typeSpinner = (SpinnerSelfSelect)view.findViewById(R.id.spinnerWaypointType);
//        commandAdapter = new AdapterMissionItens(this.getActivity(), android.R.layout.simple_list_item_1, MissionItemTypes.values());
//        typeSpinner.setAdapter(commandAdapter);
//        typeSpinner.setOnItemSelectedListener(this);
//        final TextView waypointIndex = (TextView)view.findViewById(R.id.WaypointIndex);
//        Integer temp = mission.getNumber(item);
//        waypointIndex.setText(temp.toString());
//        final TextView distanceView = (TextView)view.findViewById(R.id.DistanceValue);
//        final TextView distanceLabelView = (TextView)view.findViewById(R.id.DistanceLabel);
//        try {
//            distanceLabelView.setVisibility(View.VISIBLE);
//            distanceView.setText(mission.getDistanceFromLastWaypoint((SpatialCoordItem)item).toString());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//
//        super.onAttach(activity);
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> arg0, View v, int position, long id) {
//
//        MissionItemTypes selected = commandAdapter.getItem(position);
//        try {
//            MissionItem newItem = selected.getNewItem(getItem());
//            if (!newItem.getClass().equals(getItem().getClass())) {
//                mListner.onWaypointTypeChanged(newItem, getItem());
//            }
//        } catch (InvalidItemException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//
//    }
//
//    public MissionItem getItem() {
//
//        return item;
//    }
//
//    public void setItem(MissionItem item) {
//        this.item = item;
//    }
//
//    private void applyProfie(View view) {
//
////        app.drone.profile.applyMissionViewProfile(view, getResource());
//    }
//
//}