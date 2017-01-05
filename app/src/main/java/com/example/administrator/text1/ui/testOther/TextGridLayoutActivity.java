package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

/**
 * Created by Administrator on 2016/5/12.
 */
public class TextGridLayoutActivity extends Activity implements View.OnClickListener {

    private LinearLayout llTrrecord;
    private LinearLayout llTrcoin;
    private LinearLayout llTrflow;
    private LinearLayout llTrcard;
    private LinearLayout llTaie;
    private LinearLayout llTrcalculator;
    private LinearLayout llTrmessage;
    private LinearLayout llTrfriends;
    private LinearLayout llTrlist_sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_gridlayout);

        initAllAndSetListener();
    }

    private void initAllAndSetListener() {
        llTrrecord = (LinearLayout) this.findViewById(R.id.trrecord);
        llTrcoin = (LinearLayout) this.findViewById(R.id.trcoin);
        llTrflow = (LinearLayout) this.findViewById(R.id.trflow);
        llTrcard = (LinearLayout) this.findViewById(R.id.trcard);
        llTaie = (LinearLayout) this.findViewById(R.id.taie);
        llTrcalculator = (LinearLayout) this.findViewById(R.id.trcalculator);
        llTrmessage = (LinearLayout) this.findViewById(R.id.trmessage);
        llTrfriends = (LinearLayout) this.findViewById(R.id.trfriends);
        llTrlist_sel = (LinearLayout) this.findViewById(R.id.trlist_sel);

        llTrrecord.setOnClickListener(this);
        llTrcoin.setOnClickListener(this);
        llTrflow.setOnClickListener(this);
        llTrcard.setOnClickListener(this);
        llTaie.setOnClickListener(this);
        llTrcalculator.setOnClickListener(this);
        llTrmessage.setOnClickListener(this);
        llTrfriends.setOnClickListener(this);
        llTrlist_sel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.trrecord:
                Toast toast = new Toast();
                toast.showToast(this,"1");
                break;
            case R.id.trcoin:
                Toast.showToast(this,"2");
                break;
            case R.id.trflow:
                Toast.showToast(this,"3");
                break;
            case R.id.trcard:
                Toast.showToast(this,"4");
                break;
            case R.id.taie:
                Toast.showToast(this,"5");
                break;
            case R.id.trcalculator:
                Toast.showToast(this,"6");
                break;
            case R.id.trmessage:
                Toast.showToast(this,"7");
                break;
            case R.id.trfriends:
                Toast.showToast(this,"8");
                break;
            case R.id.trlist_sel:
                Toast.showToast(this,"9");
                break;
        }
    }
}
