package com.sdl.primer.ui.activity;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sdl.primer.R;
import com.sdl.primer.util.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

public class CheckPermissionsActivity extends AppCompatActivity {

    private RxPermissions mRxPermissions ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_permissions);

        mRxPermissions = new RxPermissions(this);

        //小米4闪退
        mRxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if(granted){
                        ToastUtil.showMessageShort(getApplicationContext(),getString(R.string.success));
                    } else {
                        ToastUtil.showMessageShort(getApplicationContext(),getString(R.string.failure));
                    }
                });
    }

    public void onClick(View view){

    }
}
