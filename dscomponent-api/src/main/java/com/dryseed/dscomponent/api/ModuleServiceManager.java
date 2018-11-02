package com.dryseed.dscomponent.api;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.HashMap;

/**
 * @author caiminming
 */
public class ModuleServiceManager {
    private static class ModuleServiceManagerHolder {
        private static final ModuleServiceManager sInstance = new ModuleServiceManager();
    }

    public static ModuleServiceManager getInstance() {
        return ModuleServiceManagerHolder.sInstance;
    }

    private HashMap mServiceMap = new HashMap<String, Class<?>>();

    private HashMap mViewMap = new HashMap<String, Class<Fragment>>();

    private HashMap mFragmentMap = new HashMap<String, Class<Fragment>>();

    private ModuleServiceManager() {
        try {
            Class moduleIndex = Class.forName("com.dryseed.dscomponent.api.DefaultModuleIndex");
            registerModules((IModule) moduleIndex.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerModules(IModule iModule) {
        if (iModule == null) {
            return;
        }
        mServiceMap.putAll(iModule.getModuleService());
        mViewMap.putAll(iModule.getModuleView());
        mFragmentMap.putAll(iModule.getModuleFragment());
    }

    public Object loadService(String serviceRegisterName) {
        Class className = (Class) mServiceMap.get(serviceRegisterName);
        if (null == className) {
            return null;
        }
        try {
            return className.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IModuleService loadModuleService(String serviceRegisterName) {
        Class className = (Class) mServiceMap.get(serviceRegisterName);
        if (null == className) {
            return null;
        }
        try {
            return (IModuleService) className.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class loadServiceClass(String serviceRegisterName) {
        return (Class) mServiceMap.get(serviceRegisterName);
    }

    public View loadView(Context context, String viewRegisterName) {
        Class<View> viewClass = (Class<View>) mViewMap.get(viewRegisterName);
        if (viewClass == null) {
            return null;
        }
        try {
            return viewClass.getDeclaredConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<View> loadViewClass(String serviceRegisterName) {
        return (Class<View>) mViewMap.get(serviceRegisterName);
    }

    public Fragment loadFragment(String viewRegisterName) {
        Class<Fragment> fragmentClass = (Class<Fragment>) mFragmentMap.get(viewRegisterName);
        if (fragmentClass == null) {
            return null;
        }
        try {
            return fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
