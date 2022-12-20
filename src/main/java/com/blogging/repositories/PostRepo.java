package com.blogging.repositories;

import com.blogging.models.Category;
import com.blogging.models.Post;
import com.blogging.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post ,Integer> {
    List<Post> findAllByUser(User user);
    List<Post> findAllByCategory(Category category);

    @Query("select p from Post p where p.title like :key")
    List<Post> findByTitle(@Param("key") String title);
}
