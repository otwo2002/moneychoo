package com.example.fready.moneychoo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    InfoFragment infoFragment;      //제품 입력정보
    ResultFragment resultFragment;   //계산결과
    EventFragment eventFragment;     //이벤트
    WeightFragment weightFragment;  //중량별배송비
    //메뉴에 이미지 넣고싶을때
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoFragment = new InfoFragment();
        resultFragment = new ResultFragment();
        eventFragment = new EventFragment();
        weightFragment = new WeightFragment();
        //화면에 프레그먼트 화면 붙혀줌.
        getSupportFragmentManager().beginTransaction().replace(R.id.container, infoFragment).commit();

        //툴바 add
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //tab에 메뉴정보 입력
        TabLayout tab = (TabLayout)findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("배송비계산"),0,true);
        tab.addTab(tab.newTab().setText("중량별배송비"),1,false);
        tab.addTab(tab.newTab().setText("이벤트"),2,false);
        //탭클릭시 fragment변경하기 위해서 호출
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if ( position==0 ) {
                    selected = infoFragment;
                }else if(position==1){
                    selected = weightFragment;
                }else if (position==2){
                    selected = eventFragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if ( position==0 ) {
                    selected = infoFragment;
                }else if(position==1){
                    selected = weightFragment;
                }else if (position==2){
                    selected = eventFragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();

            }


        });

    }

    public void callResult(GoodInfoVO goodInfoVO){
        Bundle bundle = new Bundle();
        bundle.putString("goodPrice", goodInfoVO.getGoodPrice());
        bundle.putString("tax", goodInfoVO.getTax());
        bundle.putString("localShpping", goodInfoVO.getLocalShipCharge());
        bundle.putString("goodWidth", goodInfoVO.getGoodWidth());
        bundle.putString("goodHeight", goodInfoVO.getGoodHeight());
        bundle.putString("goodVertical", goodInfoVO.getGoodVertical());
        bundle.putString("goodWeight", goodInfoVO.getGoodWeight());
        //Toast.makeText(this, "++++goodWeight++"+ goodInfoVO.getGoodWeight(), Toast.LENGTH_LONG).show();
        resultFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.calResult, resultFragment).commit();

    }
}
