package com.dryseed.modulea;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import com.dryseed.dscomponent.annotations.ModuleService;
import com.dryseed.dscomponent.api.IModuleService;

@ModuleService(register = "AModuleCalculateService2")
public class AModuleCalculateService2 implements IModuleService {

    @Override
    public void onStartCommand(String type, Bundle inputParams, Bundle outputParams, Context context) {
        switch (type) {
            case "showMsg":
                String msg = inputParams.getString("MSG");
                Toast.makeText(context, "Toast From A Module:$msg", Toast.LENGTH_SHORT).show();
                break;
            case "calculate":
                int input = inputParams.getInt("INPUT");
                outputParams.putInt("OUTPUT", input * 60 / 100);
        }
    }
}