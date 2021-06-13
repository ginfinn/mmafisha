package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DistrictRepository extends PagingAndSortingRepository<District, Integer> {
}
