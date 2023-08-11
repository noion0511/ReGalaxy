package com.regalaxy.phonesin.module.model.service;

import com.regalaxy.phonesin.module.model.ClimateHumidityDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class LocationService {
    public final String address(ClimateHumidityDto climateHumidityDto) {

        String addr = "https://dapi.kakao.com/v2/local/geo/coord2address.json?";
        addr= addr+"x=" + climateHumidityDto.getLongitude() + "&y=" + climateHumidityDto.getLatitude();

        //위의 주소를 가지고 URL 객체를 생성
        try {
            //위의 주소를 가지고 URL 객체를 생성
            URL url = new URL(addr);
            //URL 객체를 가지고 HttpURLConnection 객체 만들기
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            //인증부분은 받아야 하면 api에 작성되어있습니다.
            //인증받기
            con.setRequestProperty("Authorization", "KakaoAK 2b66806c25f3d073cd1c15335cc6e3d1");
            //옵션 설정
            con.setConnectTimeout(20000);
            con.setUseCaches(false);
            //줄단위 데이터 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder sb = new StringBuilder();
            while(true) {
                String line =br.readLine();
                if(line ==null) {
                    break;
                }
                //읽은 데이터가 있으면 sb에추가
                sb.append(line);
            }
            br.close();
            con.disconnect();

            System.out.println(sb);
            JSONObject obj = new JSONObject(sb.toString());
            System.out.println(obj);
            JSONArray imsi = obj.getJSONArray("documents");
            System.out.println(imsi);
            JSONObject o = imsi.getJSONObject(0);
            System.out.println(o);
            JSONObject c = o.getJSONObject("address");

            String address= c.getString("address_name");

            return address;
        }catch (Exception e) {
            System.out.println("지도보이기" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
