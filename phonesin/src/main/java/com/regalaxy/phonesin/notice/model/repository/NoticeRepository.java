package com.regalaxy.phonesin.notice.model.repository;


import com.regalaxy.phonesin.notice.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByStatusEqualsAndNoticeTypeEquals(Integer status, Integer noticeType);
    List<Notice> findByStatusIsNotNullAndNoticeTypeEquals(Integer noticeType);
    List<Notice> findByStatusEqualsAndNoticeTypeIsNotNull(Integer status);
}
