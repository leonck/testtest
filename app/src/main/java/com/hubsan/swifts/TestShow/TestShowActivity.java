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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hubsan.swifts.R;
import com.hubsan.swifts.fragment.RCFragment;

public class TestShowActivity extends AppCompatActivity {
    private TestShowFragment fragment;
    RCFragment rcFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        if (savedInstanceState != null) {
            fragment = (TestShowFragment) getFragmentManager().getFragment(savedInstanceState,"testShowFragment");
        } else {
            fragment = new TestShowFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }

        TestShowPresenter d = new TestShowPresenter(TestShowActivity.this, fragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getFragmentManager().putFragment(outState, "testShowFragment", fragment);
        }
    }


}
