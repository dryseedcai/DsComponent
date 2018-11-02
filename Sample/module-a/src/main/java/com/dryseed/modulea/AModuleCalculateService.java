package com.dryseed.modulea;

import android.content.Context;
import android.widget.Toast;
import com.dryseed.common.IAModuleCalculateService;
import com.dryseed.dscomponent.annotations.ModuleService;

@ModuleService(register = "AModuleCalculateService", desc = "Module A Calculate Service")
public class AModuleCalculateService implements IAModuleCalculateService {
    @Override
    public void showMsg(Context context) {
        Toast.makeText(context, "Toast From A Module", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int calculate(int input) {
        return input * 70 / 100;
    }

}