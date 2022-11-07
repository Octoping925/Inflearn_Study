package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item);
        }
        else {
            em.merge(item);
        }
    }

    @Transactional
    public void updateItem(Item itemParam) { // itemParam = 파라미터로 넘어온 준영속 엔티티
        Item findItem = em.find(Item.class, itemParam.getId()); // 같은 엔티티를 조회
        findItem.setName(itemParam.getName());
        findItem.setPrice(itemParam.getPrice()); // 데이터를 수정
        findItem.setCategories(itemParam.getCategories());
        findItem.setStockQuantity(itemParam.getStockQuantity());
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class)
                .getResultList();
    }
}