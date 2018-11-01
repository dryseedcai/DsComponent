package com.dryseed.dscomponent.api;

import android.content.Context;
import android.os.Bundle;

/**
 * @author caiminming
 */
interface IModuleService {
    abstract void onStartCommand(String type, Bundle inputParams, Bundle outputParams, Context context);
}
