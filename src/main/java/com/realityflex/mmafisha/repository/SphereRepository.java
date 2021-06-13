package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Sphere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;
import java.util.Set;

public interface SphereRepository extends PagingAndSortingRepository<Sphere, Integer> {
    List<Sphere> findAll();

}
