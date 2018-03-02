package com.example.fready.moneychoo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fready on 2018-02-05.
 * 배송비 결과 Fragment
 */

public class ResultFragment extends Fragment {
    ResultAdapter adapter;
    ViewGroup rootView ;
    ListView listView;
    TextView resultPoundView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.result_frag, container, false);
        listView=(ListView)rootView.findViewById(R.id.listView);
        resultPoundView=(TextView)rootView.findViewById(R.id.result);
        adapter = new ResultAdapter();
        //앞에서 화면에서 입력한 정보를 받아옮.

        //배송비 계산 함수 호출
        ArrayList<CompShppingAgentVO> voList = new ArrayList<CompShppingAgentVO>();
        //몰테일 호출
        voList.add( calDeliveryPrice("malltail"));
        //뉴욕걸즈 호출
        voList.add( calDeliveryPrice("nygirlz"));
        //아이포터 호출
        voList.add( calDeliveryPrice("iporter"));
        //요걸루 호출
        voList.add( calDeliveryPrice("yogirloo"));

        //배송비가 가장 적은 것부터서 순서대로 정렬한다.
        CompShppingAgentVO tempVo ;
        BigDecimal ship1;
        BigDecimal ship2;
        for (int i=0; i<voList.size(); i++){
            for(int j=0; j<voList.size(); j++) {
                ship1 =  new BigDecimal(voList.get(i).getShppingCharge());
                ship2 = new BigDecimal(voList.get(j).getShppingCharge());
                if (ship1.compareTo(ship2) < 0) {
                    tempVo = voList.get(j);
                    voList.set(j,voList.get(i) );
                    voList.set(i, tempVo);
                }
            }
        }
        //////
        //리스트 화면에 붙혀줌.
        calShippingPrice(voList);
        resultPoundView.setText("실무게:"+voList.get(0).getRealWeight()
                +"lbs  부피무게"+voList.get(0).getVolumeWeight()
                +"lbs  적용무게:"+voList.get(0).getApplyWeight()
                +"lbs" );
        return rootView;
    }
    //데이터를 관리하는 어뎁터
    ////
    class ResultAdapter extends BaseAdapter{
        ArrayList<CompShppingAgentVO> items = new ArrayList<CompShppingAgentVO>();
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
        //부분화면을 보내준다.
        @Override
        public View getView(int i, View converView, ViewGroup viewGroup) {
            ResultShipView view= null;
            if (converView==null) {
                view = new ResultShipView(viewGroup.getContext());
            }else{
                view = (ResultShipView)converView;
            }
            CompShppingAgentVO item = items.get(i);
            view.setAgent(item.getAgent());
            if(item.getShppingCharge().equals("0") ){
                view.setShppingCharge("요금표 범위 초과!");
            }else{
                view.setShppingCharge(item.getShppingCharge()+"$");
            }

            view.setApplyWeight(item.getApplyWeight()+"lbs");
            view.setRealWeight(item.getRealWeight()+"lbs");
            view.setVolumeWeight(item.getVolumeWeight()+"lbs");
            return view;
        }
        //데이터 넣기
        public void addIem(CompShppingAgentVO item){

            items.add(item);
        }
    }
    //계산된 배송비 정보를 아답터를 이용해 화면에 붙혀줌.
    private void calShippingPrice(ArrayList<CompShppingAgentVO> list) {
        for (int i = 0; i< list.size(); i++){
            adapter.addIem(list.get(i));
        }

        listView.setAdapter(adapter);
    }
    //json파일 공통으로 불러오는 메서드
    private Map<String,String> getMapWeightPrice(String shippingGubun) {
        // myJson.json
        JSONParser parser = new JSONParser();
        InputStream inputStream = null;
        if(shippingGubun.equals("malltail")){
            inputStream =  getResources().openRawResource(R.raw.malltail);
        }else if(shippingGubun.equals("nygirlz")){
            inputStream =  getResources().openRawResource(R.raw.nygirlz);
        }else if(shippingGubun.equals("iporter")){
            inputStream =  getResources().openRawResource(R.raw.iporter);
        }else if(shippingGubun.equals("yogirloo")){
            inputStream =  getResources().openRawResource(R.raw.yogirloo);
        }
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader buffer = new BufferedReader(reader);
        StringBuffer sb = new StringBuffer();
        String line = null;
        Map<String, String> mp = new HashMap<String, String>();
        try {
            /*while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            System.out.println("------------------------------------------");
            JSONObject jsonObject = new JSONObject(sb.toString());



            System.out.println(jsonObject);
            System.out.println(jsonObject.get("lbs"));
            JSONObject lbsJson = (JSONObject) jsonObject.get("lbs");
            System.out.println(lbsJson.toString());

            System.out.println(lbsJson.names());
            System.out.println(lbsJson.keys());
            */

            Object obj = parser.parse(reader);
            JSONArray jsonArray =(JSONArray) obj;

            System.out.println("------------------------------------------");
            //System.out.println(jsonArray);
            System.out.println(jsonArray.get(0));
            JSONObject jObj =null;
            Collection<String> values = null;
            int beforePound =0;
            int currPound = 0;
            String currPrice="";
            for (int i =0; i<jsonArray.size(); i++){
                jObj = (JSONObject)jsonArray.get(i);
                values = jObj.values();

                currPound = ( new BigDecimal(values.toArray()[0].toString()))
                                .setScale(0, BigDecimal.ROUND_FLOOR)
                                .intValue();

                currPrice = values.toArray()[1].toString().replace("$", "").replace(")", "").trim();
                mp.put(currPound+"",currPrice );
                System.out.println(jObj.values());

                System.out.println(values.toArray()[0]);
                //만약 요걸루의 경우 범위값으로 되어있으므로 빈범위분은 계산하여 임의로 채워줌.
                if(shippingGubun.equals("yogirloo")){

                    for(int j=beforePound+1; j<currPound ; j++){
                        mp.put(j+"",currPrice );
                    }
                    beforePound =Integer.parseInt( values.toArray()[0].toString());
                }
            }

            System.out.println(mp.toString());
            System.out.println(mp.size());
        }catch (Exception e){

        }
        return mp;
    }
    //공통 입력받은 값으로 배송비 계산
    private CompShppingAgentVO calDeliveryPrice(String shippingGubun){
        CompShppingAgentVO vo = new CompShppingAgentVO();
        try{
            Map <String , String> infoMap = getMapWeightPrice(shippingGubun);

            //부피무게 적용여부 확인
			/*항공화물의 무게비용 당 허용되는 부피를 초과하는 화물의 경우, 중량무게 대신 부피환산무게(이하 부피무게)를 항공화물운임단가로 적용합니다.
			 *  따라서 저희 몰테일도 해당 화물에 대해서는 부피무게로 요금을 청구하게 됩니다.
			 *  부피무게 적용대상은 가로X세로X높이/166 (인치기준)를 계산하였을 때 중량보다 큰 화물에 해당하며 저희 몰테일에서는 고객님의 부담을 덜어드리고자, 타사와는 달리 해당 부피무게의 50%만 적용하여 청구하고 있습니다.
			 *  예를 들어 가로 18인치, 세로 12인치, 높이 10인치에 중량 5lbs인 화물의 경우 부피무게로 14lbs에 해당되나 7lbs의 요금만 청구하고 있습니다.
            */
            //부피무게를 계산하여 부피무게가 더 클경우 부피무게로 산정함.
            String goodPrice = getArguments().getString("goodPrice");//가격
            String goodWidth = getArguments().getString("goodWidth"); //가로
            String goodHeight = getArguments().getString("goodHeight"); //높이
            String goodVertical = getArguments().getString("goodVertical"); //세로
            String goodWeight = getArguments().getString("goodWeight"); //중량
            System.out.println("width=>"+goodWidth);
            System.out.println("width=>"+goodWidth+"  height==>"+goodHeight+"  vertical==>"+goodVertical);
            //가로, 세로 , 높이 규격이 모두 있을때 부피무게 계산을 해줌.
            BigDecimal volumeWeight = BigDecimal.ZERO;
            if(goodWidth!=null && goodHeight!=null && goodVertical!=null ){
                BigDecimal width = new BigDecimal(goodWidth) ;  //가로
                BigDecimal vertical = new BigDecimal(goodVertical) ;  //세로
                BigDecimal height = new BigDecimal(goodHeight)  ;  //높이
                System.out.println("width=>"+width+"  height==>"+height+"  vertical==>"+vertical);
                volumeWeight =( width.multiply(height.multiply(vertical)) ).divide(new BigDecimal(166),  BigDecimal.ROUND_UP);
                //입력받은 무게의 값을 조정하여 가격을 산정함.
                System.out.println("부피무게---->"+volumeWeight);

            }

            BigDecimal weight = new BigDecimal(goodWeight); //입력받은 무게
            System.out.println("입력받은값----->"+weight);
            BigDecimal finalWeight= weight.setScale(0, BigDecimal.ROUND_UP)  ; //소스점 자리 반올림하여 무게 산정함.
            System.out.println("최종무게----->"+finalWeight);
            System.out.println("소수점자리 버림----->"+finalWeight.setScale(0, BigDecimal.ROUND_FLOOR));
            //부피무게와 실무게를 비교하여 실무게와 부피무게 50%중 더 무게가 많은것으러로 책정 몰테일 /뉴욕걸즈 더 많은 것을 택함
            System.out.println("비교전 volumeWeight=="+volumeWeight+"    finalWeight=="+finalWeight);
            volumeWeight = volumeWeight.multiply(new BigDecimal("0.5")).setScale(0, BigDecimal.ROUND_UP); //부피 50%
            if(volumeWeight.compareTo(finalWeight) > 0){

                finalWeight = volumeWeight; //부피무게가 더 크면 값을 치환해줌
                System.out.println("비교후 volumeWeight=="+volumeWeight+"    finalWeight=="+finalWeight);
            }
            // System.out.println("lbsJson--"+lbsJson);

            //배대지별 배송요율표 max설정
            BigDecimal shipPrice = null;
            int maxLbs =0;
            if(shippingGubun.equals("malltail")){
                maxLbs = 60; //60파운드 이상은 1:1 게시판 문의
            }else if(shippingGubun.equals("nygirlz")){
                maxLbs=30;
            }else if(shippingGubun.equals("iporter")){
                maxLbs=2000;
            }else if(shippingGubun.equals("yogirloo")){
                maxLbs=1837;
            }
            if(finalWeight.intValue() <= maxLbs ){
                shipPrice = new BigDecimal(	infoMap.get(finalWeight.toString()).toString());  //파운드별 배송비
            }else{
                shipPrice = BigDecimal.ZERO;
            }

            System.out.println("배송비 ==>"+shipPrice);
            System.out.println("입력받은 무게==>"+weight);
            System.out.println("계산된 무게==>"+finalWeight);

            if(shippingGubun.equals("malltail")){
                vo.setAgent("몰테일");
                vo.setGubun("항공");
            }else if(shippingGubun.equals("nygirlz")){
                vo.setAgent("뉴욕걸즈");
                vo.setGubun("항공");
            }else if(shippingGubun.equals("iporter")){
                vo.setAgent("아이포터");
                vo.setGubun("항공");
            }else if(shippingGubun.equals("yogirloo")){
                vo.setAgent("요걸루");
                vo.setGubun("해상");
            }

            vo.setRealWeight(goodWeight);
            vo.setVolumeWeight(volumeWeight.toString());
            vo.setApplyWeight(finalWeight.toString());
            vo.setShppingCharge(shipPrice.toString());

        }catch(Exception e){
            e.printStackTrace();
        }

        return  vo;
    }

}
