package content.malgn.domain;

import content.malgn.domain.baseentity.BaseAuditingEntity;
import content.malgn.domain.baseentity.BaseTimeEntity;
import content.malgn.enums.DeleteStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 콘텐츠
 */
@Entity
@Table(name = "content")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //회원 아이디

    @Column(name = "title", nullable = false)
    private String title; //제목

    @Column(name = "description", columnDefinition = "TEXT" )
    private String description; //내용

    @Column(name = "view_count", nullable = false)
    private Long viewCount; //조회수

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_stauts" , nullable = false)
    private DeleteStatus deleteStatus; //삭제 상태


}
