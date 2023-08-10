package com.regalaxy.phonesin.module.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.regalaxy.phonesin.address.model.LocationDto;
import com.regalaxy.phonesin.address.model.entity.Address;
import com.regalaxy.phonesin.member.model.MemberIsChaDto;
import com.regalaxy.phonesin.module.model.ClimateHumidityDto;
import com.regalaxy.phonesin.module.model.service.LocationService;
import com.regalaxy.phonesin.module.model.service.YtwokService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Api(value = "온습도계 API", description = "Climate Humidity Controller")
public class ClimateHumidityController {

    private final LocationService locationService;

    @ApiOperation(value = "온습도 확인")
    @PostMapping("/climatehumidity")
    public ResponseEntity<Map<String, Object>> ClimateHumidity(@RequestBody ClimateHumidityDto climateHumidityDto) {
        Map<String, Object> resultMap = new HashMap<>();
        StringBuffer response = new StringBuffer();
        try {
            // URL 설정
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?" + "lat=" + climateHumidityDto.getLatitude() + "&lon=" + climateHumidityDto.getLongitude() + "&appid=" + "94a62c4ca32360a4a91433f88869ba96");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            Map<String, Object> requestMap = new HashMap<>();

            // Body 입력
            String requestBody;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                requestBody = objectMapper.writeValueAsString(requestMap);
            } catch (Exception e) {
                e.printStackTrace();
                requestBody = null;
            }

            BufferedWriter in = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            in.write(requestBody);
            in.flush();
            in.close();

            // response 출력
            Charset charset = Charset.forName("UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println("Response: " + response.toString());

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response.toString(), new TypeReference<Map<String, Object>>(){});
            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) responseMap.get("weather");
            Map<String, Object> mainData = (Map<String, Object>) responseMap.get("main");
            resultMap.put("weather", weatherList.get(0).get("main"));
            resultMap.put("climate", (Math.round(((Double) mainData.get("temp") - 273.15) * 100.0) / 100.0));
            resultMap.put("humidity", mainData.get("humidity"));
            resultMap.put("address", locationService.address(climateHumidityDto));

            resultMap.put("message", "성공적으로 조회하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("message", "위도 또는 경도가 누락되었습니다.");
            resultMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
