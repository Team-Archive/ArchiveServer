package site.archive.dto.v1.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEmailRequestDtoV1 {

    @Email
    @NotNull
    @NotBlank
    private String email;

}
