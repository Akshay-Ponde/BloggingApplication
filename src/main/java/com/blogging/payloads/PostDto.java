package com.blogging.payloads;

import com.blogging.models.Category;
import com.blogging.models.Comment;
import com.blogging.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PostDto{

    private int postId;

    @NotEmpty
    @Size(min=4, max = 15)
    @Pattern(regexp = "^[a-zA-Z ]*$")
    private String title;

    @NotEmpty
    private String content;

    private String imageName;

    private Date addDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();


}
