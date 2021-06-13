package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;

public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {
    @Query(value="SELECT * FROM item LIMIT 10", nativeQuery=true)
    List<Item> getFuckingTop();
    Item findAllById(int id);
    List<Item> findAllByAgeIsNotNull(Pageable pageable);
    List<Item> findAllByDateFrom(String Data,Pageable pageable);
    Item findItemById(int id);

}
