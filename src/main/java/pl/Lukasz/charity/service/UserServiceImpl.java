package pl.Lukasz.charity.service;

import pl.Lukasz.charity.entity.Role;
import pl.Lukasz.charity.entity.User;
import pl.Lukasz.charity.repository.UserRepository;
import pl.Lukasz.charity.web.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleService roleService;
    BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveEditUser(User user) {
        User userInBase = userRepository.findById(user.getId()).orElse(null);
        userInBase.setName(user.getName());
        userInBase.setSurname(user.getSurname());
        userInBase.setEmail(user.getEmail());
        if(!user.getPassword().equals("")) userInBase.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userInBase);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword1()));
        user.setIsBlocked(false);
        List<Role> roles = new ArrayList<>();
//        roles.add(roleService.findById(1L));
        roles.add(roleService.findById(2L));
        user.setRole(roles);
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveAdmin(User user) {
        user.setIsBlocked(false);
        List<Role> roles = new ArrayList<>();
        Role role1 = roleService.findById(1L);
        Role role2 = roleService.findById(2L);
        roles.add(role1);
        roles.add(role2);
        user.setRole(roles);
        return userRepository.save(user);
    }

    @Override
    public User saveDemoteAdmin(User user) {
        List<Role> roles = new ArrayList<>();
        Role role = roleService.findById(2L);
        roles.add(role);
        user.setRole(roles);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole())).collect(Collectors.toList());
    }
}