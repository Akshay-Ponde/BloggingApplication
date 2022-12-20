package com.blogging.repositories;

import com.blogging.models.Comment;
import com.blogging.models.Post;
import com.blogging.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment , Integer> {

    List<Comment> findAllByPost(Post post);
}
