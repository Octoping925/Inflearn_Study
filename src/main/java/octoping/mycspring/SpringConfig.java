package octoping.mycspring;

import octoping.mycspring.repository.MemberRepository;
import octoping.mycspring.repository.MemoryMemberRepository;
import octoping.mycspring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
