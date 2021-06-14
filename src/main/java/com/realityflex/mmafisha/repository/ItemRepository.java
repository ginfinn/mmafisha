package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {

    List<Item> findAllByAgeIsNotNull(Pageable pageable);
    List<Item> findAllByDateFrom(String Data,Pageable pageable);
    List<Item> findAllItemById(int id);


    Item findItemById(int id);
}
