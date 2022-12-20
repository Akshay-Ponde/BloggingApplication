package com.blogging.payloads;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CategoryDto implements Serializable {


    private int categoryId;

    @NotEmpty
    @Size(min = 4,max = 15)
    @Pattern(regexp = "^[a-zA-Z ]*$")
    private String categoryTitle;

    @NotEmpty
    private String categoryDescription;
}
