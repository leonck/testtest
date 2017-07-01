//package com.hubsan.swifts.fragment.mission;
//
//import android.view.View;
//
//
//public class MissionRegionOfInterestFragment extends MissionDetailFragment
//		implements OnTextSeekBarChangedListener {
//
//	private SeekBarWithText altitudeSeekBar;
//
//	@Override
//	protected int getResource() {
//		return R.layout.hubsan_fragment_editor_detail_roi;
//	}
//
//	@Override
//	protected void setupViews(View view) {
//		super.setupViews(view);
//		//typeSpinner.setSelection(commandAdapter.getPosition(MissionItemTypes.ROI));
//
//		altitudeSeekBar = (SeekBarWithText) view.findViewById(R.id.altitudeView);
//		altitudeSeekBar.setValue(((RegionOfInterest) item).getAltitude().valueInMeters());
//		altitudeSeekBar.setOnChangedListener(this);
//	}
//
//	@Override
//	public void onSeekBarChanged() {
//		((RegionOfInterest) item).setAltitude(new Altitude(altitudeSeekBar
//				.getValue()));
//	}
//
//}
