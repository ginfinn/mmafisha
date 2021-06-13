package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Subscriptions;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SubscriptionsRepository extends PagingAndSortingRepository<Subscriptions, Integer> {
    Boolean existsBySphereAndMemberId(String sphere,Integer memberId);

    void deleteBySphere(String sphere);
    List<Subscriptions> findAllByMemberId( Integer id);
}
