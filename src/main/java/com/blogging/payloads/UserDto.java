package com.blogging.payloads;
import com.blogging.models.Role;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDto implements Serializable {


    private int id;

    @NotEmpty
    @Size(min = 4 , message = "Username must be of minimum 4 characters.")
    private String name;

    @Email(message = "Email is not valid !!!")
    private String email;

    @NotEmpty
    @Size(min = 4 ,max = 12, message = "Password length must of minimum 4 to maximum 12")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain one digit."),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain one lowercase letter."),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain one upper letter."),
            @Pattern(regexp = "(?=.*[!@#$%^&*+=?]).+", message ="Password must contain one special character."),
            @Pattern(regexp = "(?=\\S+$).+", message = "Password must contain no whitespace.")
    })
    private String password;

    @NotEmpty
    private String about;

    private Set<RoleDto> roles = new HashSet<>();

}
