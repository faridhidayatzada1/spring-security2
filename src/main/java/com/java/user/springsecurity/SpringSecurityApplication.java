package com.java.user.springsecurity;

import com.java.user.springsecurity.model.UserAuthority;
import com.java.user.springsecurity.model.UserEntity;
import com.java.user.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringSecurityApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        UserEntity user = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        // 2. Rolu yaradın və mütləq istifadəçini bura mənimsədin (Kritik Addım!)
        UserAuthority userAuthority = UserAuthority.builder()
                .authority("ROLE_ADMIN")
                .user(user) // Bazada user_id sütununun dolması üçün bu mütləqdir!
                .build();

        // 3. İndi isə rolu istifadəçinin siyahısına əlavə edin
        user.setAuthorities(List.of(userAuthority));



        Optional<UserEntity> byUsername = userRepository.findByUsername(user.getUsername());
        System.out.println("byUsername: " + byUsername);
        if (byUsername.isEmpty()) {
            System.out.println("user not found"+user.getUsername());
            userRepository.save(user);
        }
    }
}
