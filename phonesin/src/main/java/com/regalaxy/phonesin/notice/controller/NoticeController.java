package com.regalaxy.phonesin.notice.controller;

import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.notice.model.NoticeResponseDto;
import com.regalaxy.phonesin.notice.model.service.NoticeService;
import com.regalaxy.phonesin.rental.model.RentalSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Header;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Api(value = "공지 API", tags = "공지 API", description = "공지 Controller")
public class NoticeController {
    private static final String SUCCESS = "success";
    private final JwtTokenProvider jwtTokenProvider;
    private final NoticeService noticeService;

    @ApiOperation(value = "공지 이미지 조회")
    @GetMapping("/image/{posterUrl}")
    public ResponseEntity<Resource> noticeList(@PathVariable String posterUrl) throws Exception {
        ResponseEntity<Resource> result = noticeService.loadImage(posterUrl);
        return result;
    }

    @ApiOperation(value = "공지 리스트 조회")
    @GetMapping("/list")
    public ResponseEntity<?> noticeList(@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer type) throws Exception {

        List<NoticeResponseDto> noticeList = noticeService.noticeList(status, type);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("notice", noticeList);
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
