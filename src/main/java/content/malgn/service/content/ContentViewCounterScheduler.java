package content.malgn.service.content;

import content.malgn.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 일정 시간마다 누적된 조회수를 증가시키는 스케쥴러
 */
@Service
@RequiredArgsConstructor
public class ContentViewCounterScheduler {


    private final ContentViewCounterBuffer buffer; //콘텐츠 조회수 버퍼
    private final ContentRepository contentRepository;//콘텐츠 레파지토리

    /**
     * [스케쥴러]
     */
    @Scheduled(fixedDelay = 5000) // 5초
    @Transactional
    public void flush(){

        //버퍼에서 조회수 가져오기
        Map<Long, Long> viewCounts = buffer.drain();

        //db에 반영
        for (Map.Entry<Long, Long> entry : viewCounts.entrySet()) {
            contentRepository.increaseViewCount(
                    entry.getKey(),
                    entry.getValue()
            );
        }

    }

}
