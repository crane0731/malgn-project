package content.malgn.repository;

import content.malgn.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 콘텐츠 레파지토리
 */
public interface ContentRepository extends JpaRepository<Content, Long>, ContentRepositoryCustom {

    @Query("SELECT c FROM Content c WHERE c.id=:id and c.deleteStatus='UNDELETED'")
    Optional<Content> findByActive(Long id);


    @Modifying
    @Query("""
       UPDATE Content c
       SET c.viewCount = c.viewCount + :count
       WHERE c.id = :contentId
    """)
    void increaseViewCount(@Param("contentId")Long contentId, @Param("count")Long count);

}
