package vn.iostar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder; // Thêm import
import vn.iostar.entity.Role;
import vn.iostar.entity.Users; // Thêm import
import vn.iostar.repository.RoleRepository;
import vn.iostar.repository.UserRepository; // Thêm import

@SpringBootApplication
//THÊM DÒNG NÀY ĐỂ CHỈ THẲNG FILE CẤU HÌNH

public class SecurityDemo3Application {

 public static void main(String[] args) {
     SpringApplication.run(SecurityDemo3Application.class, args);
 }
 
 // Bean này sẽ chạy khi ứng dụng khởi động
 @Bean
 CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
     return args -> {

         Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
             Role newAdminRole = new Role();
             newAdminRole.setName("ADMIN");
             return roleRepository.save(newAdminRole);
         });

         Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
             Role newUserRole = new Role();
             newUserRole.setName("USER");
             return roleRepository.save(newUserRole);
         });


         userRepository.findByUsername("admin").ifPresentOrElse(
         
             user -> System.out.println("Admin user already exists."),
   
             () -> {
                 System.out.println(">>> Creating default admin user...");
                 Users adminUser = new Users();
                 adminUser.setUsername("admin");
                 adminUser.setName("Administrator");
                 adminUser.setEmail("admin@iostar.vn");
                 adminUser.setEnabled(true);
                 adminUser.setPassword(passwordEncoder.encode("admin123"));

                 Set<Role> roles = new HashSet<>();
                 roles.add(adminRole); 
                 roles.add(userRole); 
                 adminUser.setRoles(roles);

                 userRepository.save(adminUser);
                 System.out.println(">>> Default admin user created successfully!");
             }
         );
     };
 }
}