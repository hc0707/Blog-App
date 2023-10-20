package io.mountblue.repository;

import io.mountblue.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findByIsPublishedTrue(Pageable pageable);
    long countByIsPublishedTrue();
    @Query("SELECT p FROM Post p " +
            "JOIN p.tags t " +
            "WHERE (LOWER(p.title) LIKE :keyword OR " +
            "LOWER(p.content) LIKE %:contentKeyword% OR " +
            "LOWER(p.author) LIKE :keyword OR " +
            "LOWER(t.name) LIKE :keyword)"+
            "AND p.isPublished=true")
    List<Post> searchByKeyword(@Param("keyword") String keyword,@Param("contentKeyword") String contentKeyword);

}
