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

package com.hubsan.swifts.TestShow;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hubsan.swifts.R;
import com.hubsan.swifts.mapView.MapFragment;
import com.hubsan.swifts.fragment.OptionFragment;
import com.hubsan.swifts.fragment.RCFragment;


/**
 * Created by lizhaotailang on 2016/12/27.
 */

public class TestShowFragment extends Fragment implements TestShowContract.View {

    private TestShowContract.Presenter presenter;
    MapFragment mapFragment;
    RCFragment rcFragment;
    OptionFragment optionFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_testshow, container, false);

//        mapFragment = new MapFragment();
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.add(R.id.mapFragment, mapFragment).commit();

        rcFragment = new RCFragment();
        FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
        transaction2.add(R.id.joystickFragment, rcFragment).commit();
//
//        optionFragment = new OptionFragment();
//        FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
//        transaction2.add(R.id.optionFragment, optionFragment).commit();

        return view;
    }


    @Override
    public void setPresenter(TestShowContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }

}
