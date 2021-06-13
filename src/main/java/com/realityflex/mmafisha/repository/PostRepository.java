package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Member;
import com.realityflex.mmafisha.entity.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    Post findByPostId(Integer postId);
}
