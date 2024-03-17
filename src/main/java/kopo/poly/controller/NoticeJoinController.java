package kopo.poly.controller;

import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.INoticeJoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/notice")
@RequiredArgsConstructor
@Controller
public class NoticeJoinController {

    private final INoticeJoinService noticeJoinService;

    /**
     * 게시판 리스트 보여주기
     * <p>
     * GetMapping(Value = "notice/noticeListUsingJoinColumn") =>
     * Get방식을 통해 접속되는 URL이 notice/noticeLsit 경우 아래 함수를 실행함
     */
    @GetMapping(value = "noticeListUsingJoinColumn")
    public String noticeListUsingJoinColumn(HttpSession session, ModelMap model) throws Exception{

        log.info(this.getClass().getName() + ".noticeList Start!!");

        session.setAttribute("SESSION_USER_ID", "USER01");

        // 공지사항 리스트 조회하기
        // Java 8부터 제공되는 Optional 활용하여 NPE(Null Pointer Exception) 처리
        List<NoticeDTO> rList = Optional.ofNullable(noticeJoinService.getNoticeListUsingJoinColumn()).orElseGet(ArrayList::new);

        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".noticeList End!!");

        return "notice/noticeListJoin";
    }
    /**
     * 게시판 리스트 보여주기
     */
    @GetMapping(value = "noticeListUsingNativeQuery")
    public String noticeListUsingNativeQuery(HttpSession session, ModelMap model) throws Exception{

        log.info(this.getClass().getName() + ".noticeListUsingNativeQuery Start!!");

        session.setAttribute("SESSION_USER_ID", "USER01");

        // 공지사항 리스트 조회하기
        List<NoticeDTO> rList = Optional.ofNullable(noticeJoinService.getNoticeListUsingNativeQuery()).orElseGet(ArrayList::new);

        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".noticeListUsingNativeQuery End!!");

        return "notice/noticeListJoin";
    }

    @GetMapping(value = "noticeListUsingJPQL")
    public String noticeListUsingJPQL(HttpSession session, ModelMap model) throws Exception{

        log.info(this.getClass().getName() + ".noticeListUsingJPQL Start!!");

        session.setAttribute("SESSION_USER_ID", "USER01");

        // 공지사항 리스트 조회하기
        List<NoticeDTO> rList = Optional.ofNullable(noticeJoinService.getNoticeListUsingJPQL()).orElseGet(ArrayList::new);

        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".noticeListUsingJPQL End!!");

        return "notice/noticeListJoin";
    }

    @GetMapping(value = "noticeListUsingQueryDSL")
    public String noticeListUsingQueryDSL(HttpSession session, ModelMap model) throws Exception{

        log.info(this.getClass().getName() + ".noticeListUsingQueryDSL Start!!");

        session.setAttribute("SESSION_USER_ID", "USER01");

        // 공지사항 리스트 조회하기
        List<NoticeDTO> rList = Optional.ofNullable(noticeJoinService.getNoticeListForQueryDSL()).orElseGet(ArrayList::new);

        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".noticeListUsingQueryDSL End!!");

        return "notice/noticeListJoin";
    }
}
