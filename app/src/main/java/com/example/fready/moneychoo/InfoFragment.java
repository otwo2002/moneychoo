package com.example.fready.moneychoo;

import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by fready on 2018-02-05.
 */

public class InfoFragment extends Fragment{
    String[] items ={"CA-켈리포니아","DW-델라웨이","NJ-뉴저지", "OR-오레곤"};  //물류센터 목록
    MainActivity mainActivity;
    GoodInfoVO goodInfoVO;
    ViewGroup rootView ;
    String goodWeight;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity=(MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(rootView.getContext(), "onActivityCreated", Toast.LENGTH_LONG).show();

        Spinner spinner = (Spinner)rootView.findViewById(R.id.shippingCenter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                rootView.getContext(),android.R.layout.simple_spinner_item, items
        );
        spinner.setAdapter(adapter);
        goodWeight= ((TextView) (rootView.findViewById(R.id.goodWeight))).getText().toString();
        if(goodWeight!=null && !goodWeight.trim().equals("") ){
            callResult();
        }
        //계산하기 버튼클릭시 배송비 계산하기
        Button calButton = (Button)rootView.findViewById(R.id.button);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodWeight= ((TextView) (rootView.findViewById(R.id.goodWeight))).getText().toString();
                //입력값 체크
                if(goodWeight ==null || goodWeight.trim().equals("") || goodWeight.trim().equals("0")){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoFragment.this.getActivity());
                    dialog = builder.setMessage("상품무게를 입력해주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }else{
                    callResult();
                }
            }
        });
    }

    private void callResult(){
        goodInfoVO = new GoodInfoVO(
                ((TextView) (rootView.findViewById(R.id.goodPrice))).getText().toString(),
                ((TextView) (rootView.findViewById(R.id.tax))).getText().toString(),
                ((TextView) (rootView.findViewById(R.id.localShipCharge))).getText().toString(),

                ((TextView) (rootView.findViewById(R.id.goodWidth))).getText().toString(),
                ((TextView) (rootView.findViewById(R.id.goodHeight))).getText().toString(),
                ((TextView) (rootView.findViewById(R.id.goodVertical))).getText().toString(),
                ((TextView) (rootView.findViewById(R.id.goodWeight))).getText().toString()
        );
        mainActivity.callResult(goodInfoVO);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.info_frag, container, false);
        Toast.makeText(rootView.getContext(), "onCreateView", Toast.LENGTH_LONG).show();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(rootView.getContext(), "onStart", Toast.LENGTH_LONG).show();



    }

}
