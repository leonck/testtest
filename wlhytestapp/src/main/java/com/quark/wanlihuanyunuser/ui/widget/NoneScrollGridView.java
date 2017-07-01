package com.quark.wanlihuanyunuser.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NoneScrollGridView extends GridView {

	public NoneScrollGridView(Context context) {
		super(context);

	}

	public NoneScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}