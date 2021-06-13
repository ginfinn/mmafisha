package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Member;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<Member, Integer>{
    Boolean existsByMemberName(String name);
    Member findByMemberName(String name);
}
