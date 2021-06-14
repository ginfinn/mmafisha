package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Member;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberRepository extends PagingAndSortingRepository<Member, Integer> {
    Boolean existsByMemberName(String name);

    Member findMemberByMemberName(String memberName);

    Member findByMemberName(String name);


}
