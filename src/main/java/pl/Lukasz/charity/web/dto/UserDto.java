package pl.Lukasz.charity.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {
    @NotBlank
    String name;
    @NotBlank
    String surname;
    @Email
    String email;
    @NotBlank
    String password1;
    @NotBlank
    String password2;
}
