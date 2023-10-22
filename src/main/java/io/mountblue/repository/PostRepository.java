package io.mountblue.repository;

import io.mountblue.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
//    Page<Post> findByIsPublishedTrue(Pageable pageable);
//    @Query("SELECT p FROM Post p " +
//            "JOIN p.tags t " +
//            "WHERE (LOWER(p.title) LIKE %:keyword% OR " +
//            "LOWER(p.content) LIKE %:keyword% OR " +
//            "LOWER(p.author) LIKE %:keyword% OR " +
//            "LOWER(t.name) LIKE %:keyword%)"+
//            "AND p.isPublished=true")
//    List<Post> searchByKeyword(@Param("keyword") String keyword);


        @Query("SELECT DISTINCT p FROM Post p " +
            "JOIN p.tags t " +
            "WHERE (:authorList IS NULL OR LOWER(p.author) IN :authorList) "+
            "AND (:tagList IS NULL OR LOWER(t.name) IN :tagList) "+
            "AND (:keyword IS NULL OR (LOWER(t.name) LIKE %:keyword% OR " +
            "LOWER(p.content) LIKE %:keyword% OR " +
            "LOWER(p.author) LIKE %:keyword% OR " +
            "LOWER(p.title) LIKE %:keyword%)) "+
            "AND p.isPublished=true")
    Page<Post> findAllWithPagination(Pageable pageable, String keyword, List<String> authorList, List<String> tagList);
//    Page<Post> findByIsPublishedTrueAndTagsNameIn(String[] tagList, Pageable pageable);
//    Page<Post> findByIsPublishedTrueAndAuthorIn(String[] authorList, Pageable pageable);
//    Page<Post> findByIsPublishedTrueAndAuthorInAndTagsNameIn(String[] authorList, String[] tagList, Pageable pageable);


}
