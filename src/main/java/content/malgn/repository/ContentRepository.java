package content.malgn.repository;

import content.malgn.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 콘텐츠 레파지토리
 */
public interface ContentRepository extends JpaRepository<Content, Long> {
}
