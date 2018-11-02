package com.dryseed.moduleb;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.dryseed.dscomponent.annotations.ModuleView;

@ModuleView(register = "BModuleView", desc = "Module B View")
public class BModuleView extends LinearLayout {

    public BModuleView(Context context) {
        this(context, null);
    }

    public BModuleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(final Context context) {
        inflate(context, R.layout.activity_moduleb_layout, this);
        Button btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Toast From B View", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
