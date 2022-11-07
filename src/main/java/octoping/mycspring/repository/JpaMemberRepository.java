package octoping.mycspring.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import octoping.mycspring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager entityManager;

    @Autowired
    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Member.class, id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return entityManager.createQuery("SELECT m FROM Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList()
            .stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        /*
        일반 SQL의 경우 id, name 등의 속성 값을 다 받아와서 Member에 매핑해야 했으나,
        JPA는 그냥 한방에 객체에 매핑이 된다.
         */
        return entityManager.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }
}
