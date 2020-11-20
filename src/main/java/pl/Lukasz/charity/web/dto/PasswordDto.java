package pl.Lukasz.charity.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class PasswordDto {
    @NotBlank
    String pass1;
    @NotBlank
    String pass2;
}
