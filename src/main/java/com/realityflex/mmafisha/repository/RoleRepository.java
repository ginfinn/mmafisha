package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {
    Role findByName(String name);
}
