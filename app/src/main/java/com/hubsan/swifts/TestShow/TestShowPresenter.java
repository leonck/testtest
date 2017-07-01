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


import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lizhaotailang on 2016/12/27.
 */

public class TestShowPresenter implements TestShowContract.Presenter {
    Context context;
    TestShowContract.View view;

    public TestShowPresenter(@NonNull Context context, @NonNull TestShowContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }



    @Override
    public void requestData() {

    }
}