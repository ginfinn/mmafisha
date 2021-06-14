package com.realityflex.mmafisha.repository;

import com.realityflex.mmafisha.entity.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    Post findByPostId(Integer postId);
    List<Post> findAllByName(String name);
}
