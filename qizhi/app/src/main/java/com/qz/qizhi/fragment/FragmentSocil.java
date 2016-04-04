package com.qz.qizhi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qz.qizhi.R;

/**
 * Created by xycong on 2016/3/27.
 */
public class FragmentSocil extends Fragment {
    View mLayout;
    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mLayout == null) {
            mLayout = inflater.inflate(R.layout.fragment_socil,null);
            mContext = getActivity();
            init();
        } else {
            ViewGroup parent = (ViewGroup) mLayout.getParent();
            if (parent != null) {
                parent.removeView(mLayout);
            }
        }
        return mLayout;
    }

    private void init() {
        mLayout.findViewById(R.id.ibLeft).setVisibility(View.GONE);
    }
}
