package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Member;
import com.realityflex.mmafisha.entity.Subscriptions;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MemberRepository extends PagingAndSortingRepository<Member, Integer>{
    Boolean existsByMemberName(String name);
    Member findMemberByMemberName(String memberName);
    Member findByMemberName(String name);
    List<Subscriptions> findSubscriptionsByMemberName(String name);
}
