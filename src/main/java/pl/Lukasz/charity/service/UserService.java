package pl.Lukasz.charity.service;

import pl.Lukasz.charity.entity.User;
import pl.Lukasz.charity.web.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User saveEditUser(User user);
    User save(User user);
    User save(UserDto userDto);
    User findUserByEmail(String email);
    void deleteById(Long id);
    User findById(Long id);
    User saveAdmin(User user);
    User saveDemoteAdmin(User user);
}
