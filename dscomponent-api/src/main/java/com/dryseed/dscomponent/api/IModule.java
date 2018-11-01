package com.dryseed.dscomponent.api;

/**
 * @author caiminming
 */

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.HashMap;

interface IModule {
    abstract HashMap<String, View> getModuleView();

    abstract HashMap<String, Class> getModuleService();

    abstract HashMap<String, Fragment> getModuleFragment();
}
