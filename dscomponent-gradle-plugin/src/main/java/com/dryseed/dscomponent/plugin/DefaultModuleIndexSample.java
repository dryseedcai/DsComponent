//package com.dryseed.dscomponent.api;
//
//import android.support.v4.app.Fragment;
//import android.view.View;
//import com.dryseed.modulea.AModuleCalculateService;
//import com.dryseed.modulea.AModuleCalculateService2;
//import java.util.HashMap;
//import org.jetbrains.annotations.NotNull;
//
//public class DefaultModuleIndex implements IModule {
//    private static final HashMap<String, Class<? extends Fragment>> fragmentMap = new HashMap();
//    private static final HashMap<String, Class<?>> serviceMap = new HashMap();
//    private static final HashMap<String, Class<? extends View>> viewMap = new HashMap();
//
//    private static /* synthetic */ void $$$reportNull$$$0(int i) {
//        String str = "@NotNull method %s.%s must not return null";
//        Object[] objArr = new Object[2];
//        objArr[0] = "com/dryseed/dscomponent/api/DefaultModuleIndex";
//        switch (i) {
//            case 1:
//                objArr[1] = "getModuleFragment";
//                break;
//            default:
//                objArr[1] = "getModuleView";
//                objArr[1] = "getModuleService";
//                break;
//        }
//        throw new IllegalStateException(String.format(str, objArr));
//    }
//
//    static {
//        serviceMap.put("AModuleCalculateService", AModuleCalculateService.class);
//        serviceMap.put("AModuleCalculateService2", AModuleCalculateService2.class);
//    }
//
//    @NotNull
//    public HashMap<String, Class<? extends Fragment>> getModuleFragment() {
//        HashMap<String, Class<? extends Fragment>> hashMap = fragmentMap;
//        if (hashMap == null) {
//            $$$reportNull$$$0(0);
//        }
//        return hashMap;
//    }
//
//    @NotNull
//    public HashMap<String, Class<? extends View>> getModuleView() {
//        HashMap<String, Class<? extends View>> hashMap = viewMap;
//        if (hashMap == null) {
//            $$$reportNull$$$0(0);
//        }
//        return hashMap;
//    }
//
//    @NotNull
//    public HashMap<String, Class<?>> getModuleService() {
//        HashMap<String, Class<?>> hashMap = serviceMap;
//        if (hashMap == null) {
//            $$$reportNull$$$0(1);
//        }
//        return hashMap;
//    }
//}