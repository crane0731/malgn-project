package content.malgn.config.audit;

import content.malgn.domain.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * 현재 로그인 회원 정보의 PK값 반환(String 타입)
     * @return Optional<String>
     */
    @Override
    public Optional<String> getCurrentAuditor() {

        log.info("getCurrentAuditor() 호출됨!");

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();


        //인증 정보가 없다면 "system" 반환
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("system");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails userDetail) {
            return Optional.of(userDetail.getId().toString());
        }

        return Optional.empty();
    }
}
