package com.regalaxy.phonesin.notice.controller;

import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.notice.model.NoticeRequestDto;
import com.regalaxy.phonesin.notice.model.NoticeResponseDto;
import com.regalaxy.phonesin.notice.model.service.NoticeService;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.RentalSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
@Api(value = "관리자 공지 API", tags = "관리자 공지 API", description = "관리자 공지 Controller")
public class AdminNoticeController {
    private static final String SUCCESS = "success";
    private final JwtTokenProvider jwtTokenProvider;
    private final NoticeService noticeService;

    @ApiOperation(value = "공지 리스트 조회")
    @GetMapping("/list")
    public ModelAndView noticeList(@RequestParam(required = false) Integer status, @RequestParam(required = false) Integer type) throws Exception {

        List<NoticeResponseDto> noticeList = noticeService.noticeList(status, type);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("notice", noticeList);
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
//        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

        RentalSearchDto rentalSearchDto = new RentalSearchDto();
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", noticeList);
        mav.addObject("title", "공지");
        mav.addObject("back", false);
        mav.setViewName("/list");//어디로 이동할지 ex) rental/list
        return mav;
    }

    @ApiOperation(value = "공지 신청")
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> noticeApply(@RequestParam String title,@RequestParam Integer noticeType, @RequestParam("file") MultipartFile file, @ApiIgnore @RequestHeader String authorization) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        NoticeRequestDto noticeRequestDto = new NoticeRequestDto(title, noticeType);
        Long memberId = jwtTokenProvider.getMemberId(authorization.replace("Bearer ", ""));
        noticeService.noticeApply(noticeRequestDto, memberId, file);
        resultMap.put("status", 201);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
    }


    @ApiOperation(value = "공지 진행중으로 수정")
    @PutMapping("/toprocess/{noticeId}")
    public ResponseEntity<Map<String, Object>> noticeToProcess(@PathVariable Long noticeId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        noticeService.noticeUpdate(noticeId, 2);
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "공지 종료로 수정")
    @PutMapping("/toend/{noticeId}")
    public ResponseEntity<Map<String, Object>> noticeToEnd(@PathVariable Long noticeId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        noticeService.noticeUpdate(noticeId, 3);
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
