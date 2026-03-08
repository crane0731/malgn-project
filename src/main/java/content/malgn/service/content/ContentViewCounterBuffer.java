package content.malgn.service.content;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 게시글 조회수를 메모리 버퍼에 담아 주기적으로 update
 * @트래픽 증가시 redis 도입 고려
 */
@Service
public class ContentViewCounterBuffer {

    private final ConcurrentHashMap<Long, AtomicLong> buffer = new ConcurrentHashMap<>();



    /**
     * 조회시 호출
     * 조회수 증가
     * @param contentId 콘텐츠 아이디
     */
    public void increase(Long contentId){
        buffer.computeIfAbsent(contentId, k -> new AtomicLong(0)).incrementAndGet();
    }


    /**
     * DB 반영시 사용
     * 버퍼에서 누적된 조회수 가져오기 + 버퍼 초기화
     * @return Map<Long, Long>
     */
    public Map<Long, Long> drain(){
        Map<Long, Long> snapshot = new HashMap<>();

        buffer.forEach((contentId,counter)->{
            long delta = counter.getAndSet(0);
            if(delta>0){
                snapshot.put(contentId, delta);
            }
        });

        return snapshot;
    }



}
