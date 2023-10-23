package io.mountblue.repository;

import io.mountblue.entity.Post;
import io.mountblue.entity.Tag;
import io.mountblue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByNameAndRole(String name,String role);
    @Query("SELECT u.name FROM User u "+
            "WHERE u.role='USER'")
    List<String> findUserNames();

    @Query("SELECT p FROM User u "+
           "JOIN u.posts p "+
            "WHERE u.id=:id")
    List<Post> findUserPosts(Long id);
}
