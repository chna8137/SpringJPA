package kopo.poly.service.impl;

import kopo.poly.dto.MelonDTO;
import kopo.poly.persistance.mongodb.IMelonMapper;
import kopo.poly.service.IMelonService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MelonService implements IMelonService {

    private final IMelonMapper melonMapper; // MongoDB에 저장할 Mapper


    @Override
    public List<MelonDTO> doCollect() throws Exception {

        log.info(this.getClass().getName() + "doCollect Start!");

        List<MelonDTO> pList = new LinkedList<>();

        // 멜론 Top100 중 50위까지 정보 가져오는 페이지
        String url = "https://www.melon.com/chart/index.htm";

        // JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML소스 저장할 변수
        Document doc = Jsoup.connect(url).get();

        // <div class="service_list_song"> 이 태그 내에서 있는 HTML소스만 element에 저장됨
        Elements element = doc.select("div.service_list_song");

        // Iterator을 사용하여 멜론차트 정보를 가져오기
        for (Element songInfo : element.select("div.wrap_song_info")) {

            //크롤링을 통해 데이터 저장하기
            String song = CmmUtil.nvl(songInfo.select("div.ellipsis.rank01 a").text()); // 노래
            String singer = CmmUtil.nvl(songInfo.select("div.ellipsis.rank02 a").eq(0).text()); // 가수

            log.info("song : " + song);
            log.info("singer : " + singer);

            // 가수와 노래 정보가 모두 수집되었다면, 저장됨
            if ((!song.isEmpty()) && (!singer.isEmpty())) {

                MelonDTO pDTO = MelonDTO.builder().collectTime(DateUtil.getDateTime("yyyyMMddhhmmss"))
                        .song(song).singer(singer).build();

                // 한 번에 여러개의 데이터를 MongoDB에 저장할 List 형태의 데이터 저장하기
                pList.add(pDTO);
            }
        }

        log.info(this.getClass().getName() + ".doCollect End!");

        return pList;
    }

    @Override
    public int collectMelonSong() throws Exception {

        int res = 0;

        // 생성할 컬렉션명
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // private 함수로 선언된 doCollect 함수를 호출하여 결과를 받기
        List<MelonDTO> rList = this.doCollect();

        // MongoDB에 데이터 저장하기
        res = melonMapper.insertSong(rList, colNm);

        log.info(this.getClass().getName() + ".collectMelonSong End!");

        return res;
    }

    @Override
    public List<MelonDTO> getSongList() throws Exception {

        log.info(this.getClass().getName() + ".getSongList Start!");

        // MongoDB에 저장된 컬렉션 이름
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        List<MelonDTO> rList = melonMapper.getSongList(colNm); // MongoDB에서 데이터 가져오기

        log.info(this.getClass().getName() + ".getSongList End!");

        return rList;
    }
}
