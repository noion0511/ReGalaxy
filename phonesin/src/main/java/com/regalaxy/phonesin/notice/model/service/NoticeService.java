package com.regalaxy.phonesin.notice.model.service;

import com.regalaxy.phonesin.donation.model.DonationRequestDto;
import com.regalaxy.phonesin.donation.model.DonationResponseDto;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.donation.model.repository.DonationRepository;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import com.regalaxy.phonesin.module.model.YtwokDto;
import com.regalaxy.phonesin.module.model.entity.Ytwok;
import com.regalaxy.phonesin.notice.model.NoticeRequestDto;
import com.regalaxy.phonesin.notice.model.NoticeResponseDto;
import com.regalaxy.phonesin.notice.model.entity.Notice;
import com.regalaxy.phonesin.notice.model.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void noticeApply(NoticeRequestDto noticeRequestDto, Long memberId, MultipartFile file) throws Exception {

        String contentType = file.getContentType();
        String extension;

        //파일의 Content Type 이 있을 경우 Content Type 기준으로 확장자 확인
        if (!StringUtils.hasText(contentType)){throw new Exception("유효한 확장자를 가진 파일이(가) 아닙니다.");}

        if (contentType.equals("image/jpg")) {
            extension = "image/jpg";
        } else if (contentType.equals("image/png")) {
            extension = "image/png";
        } else if (contentType.equals("image/jpeg")) {
            extension = "image/jpeg";
        } else if (contentType.equals("image/gif")) {
            extension = "image/gif";
        } else if (contentType.equals("image/*")) {
            extension = "image/*";
        }
        else{
            // contentType 존재하지 않는 경우 처리
            throw new Exception("사진 파일이 아닙니다.");
        }

        // 파일이 비었는지 검증
        if (file.isEmpty()) {throw new Exception("파일이(가) 비어있습니다.");}

        Member member = memberRepository.findById(memberId).get();
        String title = noticeRequestDto.getTitle();
        Integer noticeType = noticeRequestDto.getNoticeType();
        String noticeTypeName;
        if (noticeType == 0) noticeTypeName = "banner";
        else noticeTypeName = "bottom";

        // 파일 경로 지정
        String uploadPath = new File("").getAbsolutePath() + "\\" + "images/" + noticeTypeName;

        String saveFolder = uploadPath + File.separator;

        File folder = new File(saveFolder);

        // 폴더가 존재하는지 검증
        if (!folder.exists())
            folder.mkdirs();

        String originalFileName = file.getOriginalFilename();
        String saveFileName = originalFileName;

        // file name 중복경우 예외처리
        if (!originalFileName.isEmpty()) {
            saveFileName = UUID.randomUUID().toString()
                    + originalFileName.substring(originalFileName.lastIndexOf('.'));
            file.transferTo(new File(folder, saveFileName));
        }

        Notice notice = new Notice(member, title, saveFileName, 1, noticeType);
        noticeRepository.save(notice);

    }

    public List<NoticeResponseDto> noticeList(Integer status, Integer type) throws Exception {
        List<Notice> noticeList;
        if (status == null) noticeList = noticeRepository.findByStatusIsNotNullAndNoticeTypeEquals(type);
        else noticeList = noticeRepository.findByStatusEqualsAndNoticeTypeEquals(status, type);
        List<NoticeResponseDto> result = noticeList
            .stream()
            .map(notice -> new NoticeResponseDto(notice))
            .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public void noticeUpdate(Long noticeId, int status) throws Exception {
        Notice notice = noticeRepository.findById(noticeId).get();
        if(notice.getStatus() != status) notice.setStatus(status);
    }
}
