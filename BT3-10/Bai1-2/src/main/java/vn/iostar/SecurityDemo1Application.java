package vn.iostar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.iostar.entity.UserInfo;
import vn.iostar.repository.UserInfoRepository;

@SpringBootApplication
public class SecurityDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemo1Application.class, args);
    }

    // THÊM BEAN NÀY VÀO
    @Bean
    CommandLineRunner initDatabase(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Tạo user admin nếu chưa tồn tại
            if (userInfoRepository.findByName("admin").isEmpty()) {
                System.out.println(">>> Creating default ADMIN user...");
                UserInfo admin = new UserInfo();
                admin.setName("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles("ROLE_ADMIN");
                userInfoRepository.save(admin);
            }
            // Tạo user thường nếu chưa tồn tại
            if (userInfoRepository.findByName("user").isEmpty()) {
                System.out.println(">>> Creating default USER user...");
                UserInfo user = new UserInfo();
                user.setName("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRoles("ROLE_USER");
                userInfoRepository.save(user);
            }
        };
    }
}