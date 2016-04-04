package com.qz.qizhi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.qz.qizhi.fragment.FragmentDevice;
import com.qz.qizhi.fragment.FragmentMe;
import com.qz.qizhi.fragment.FragmentSocil;

/**
 * Created by xycong on 2016/3/26.
 */
public class MainActivity  extends FragmentActivity{
    FragmentDevice fragmentDevice;
    FragmentSocil fragmentSocil;
    FragmentMe fragmentMe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        init();
        ((RadioGroup) findViewById(R.id.rg_tab)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbDevice:
                        showFragment = fragmentDevice;
                        break;
                    case R.id.rbSocial:
                        showFragment = fragmentSocil;
                        break;
                    case R.id.rbMe:
                        showFragment = fragmentMe;
                        break;
                    default:
                        break;
                }
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(curFragment);
                if (!showFragment.isAdded()) {
                    trx.add(R.id.layout_fragment, showFragment);
                }

                trx.show(showFragment).commit();
                curFragment = showFragment;
            }
        });
    }

    Fragment curFragment = null, showFragment = null;

    private void init() {
        // TODO Auto-generated method stub
        fragmentMe = new FragmentMe();
        fragmentSocil= new FragmentSocil();
        fragmentDevice = new FragmentDevice();

        getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment, fragmentDevice)
               /* .add(R.id.layout_fragment, fragmentSocil).hide(fragmentMe)*/.show(fragmentDevice)
                .commit();
        curFragment = fragmentDevice;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            fragmentDevice.update();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
