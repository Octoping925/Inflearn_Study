package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    /**
     * 직접 JPQL 동적으로 조립 (개 구림)
     */
     public List<Order> findAllByString(OrderSearch orderSearch) {
         String jpql = "SELECT o FROM Order o JOIN o.member m WHERE 1=1";
         if (orderSearch.getOrderStatus() != null) {
             jpql += " AND o.status = :status";
         }
         if (orderSearch.getMemberName() != null) {
             jpql += " AND m.username LIKE :name";
         }
         TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);
         if (orderSearch.getOrderStatus() != null) {
             query = query.setParameter("status", orderSearch.getOrderStatus());
         }
         if (StringUtils.hasText(orderSearch.getMemberName())) {
             query = query.setParameter("name", orderSearch.getMemberName());
         }

         return query.getResultList();
     }

    /**
     * JPA Criteria
     * (JPA 제공 표준, 실무에서 안 씀)
     */
     public List<Order> findAllByCriteria(OrderSearch orderSearch) {
         CriteriaBuilder cb = em.getCriteriaBuilder();
         CriteriaQuery<Order> cq = cb.createQuery(Order.class);
         Root<Order> o = cq.from(Order.class);
         Join<Object, Object> m = o.join("member", JoinType.INNER);
         List<Predicate> criteria = new ArrayList<>();

         if(orderSearch.getOrderStatus() != null) {
             Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
             criteria.add(status);
         }
         if(StringUtils.hasText(orderSearch.getMemberName())) {
             Predicate name = cb.equal(o.get("name"), "%" + orderSearch.getMemberName() + "%");
             criteria.add(name);
         }

         cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
         TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
         return query.getResultList();
//        return em.createQuery("SELECT o FROM Order o JOIN o.member m" +
//                " WHERE o.status = :status" +
//                " AND m.username LIKE :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setMaxResults(1000) // 최대 1000건
//                .getResultList();
     }

    /**
     * QueryDSL
     */
//    public List<Order> findAll(OrderSearch orderSearch) {
//    }
}