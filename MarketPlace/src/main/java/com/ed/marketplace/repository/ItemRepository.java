package com.ed.marketplace.repository;

import com.ed.marketplace.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

//    Optional<Item> findByTitle(String title);
//
//    @Query(value = "select i.amount_on_warehouse from Item i where i.title = :title", nativeQuery = true)
//    int amountOnWarehouse(@Param("title") String title);
//
//    @Query(value = "", nativeQuery = true)
//    List<Item> findItemsByTitles(@Param("titles")List<String> titles);

}
