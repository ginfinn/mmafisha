package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Sphere;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SphereRepository extends PagingAndSortingRepository<Sphere, Integer> {
    List<Sphere> findAll();


}
