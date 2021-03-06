package com.example.fready.moneychoo;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by fready on 2018-02-07.
 */

public class ResultShipView extends LinearLayout {

    TextView textAgent ;
    TextView textApplyWeight;
    TextView textShppingCharge;
    TextView textRealWeight;
    TextView textVolumeWeight;

    public ResultShipView(Context context) {
        super(context);
        init(context);
    }

    public ResultShipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    //생성자에서 호출될 메시지
    private void init(Context context) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.result_shpping, this, true);
        textAgent = (TextView)findViewById(R.id.agent);
        textApplyWeight = (TextView)findViewById(R.id.applyWeight);
        textShppingCharge = (TextView)findViewById(R.id.shppingCharge);
        textRealWeight = (TextView)findViewById(R.id.realWeight);
        textVolumeWeight= (TextView)findViewById(R.id.volumeWeight);
    }

    public void setAgent(String agent){
        textAgent.setText(agent);
    }
    public void setApplyWeight(String applyWeight){
        textApplyWeight.setText(applyWeight);
    }
    public void setShppingCharge(String shppingCharge){
        textShppingCharge.setText(shppingCharge);

    }
    public void setRealWeight(String realWeight){
        textRealWeight.setText(realWeight);
    }
    public void setVolumeWeight(String volumeWeight){
        textVolumeWeight.setText(volumeWeight);
    }

}
