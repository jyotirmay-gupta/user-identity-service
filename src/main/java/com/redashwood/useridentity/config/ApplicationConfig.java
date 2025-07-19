package com.redashwood.useridentity.config;

import com.redashwood.useridentity.mapper.UserIdentityMapper;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.*;
import com.redashwood.useridentity.service.impl.*;
import com.redashwood.useridentity.util.IdentityUtils;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IdentityUtils configureIdentityUtils(PasswordEncoder passwordEncoder){
        return new IdentityUtils(passwordEncoder);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    RegisterUserService configureRegisterService(UserIdentityRepository userIdentityRepository, IdentityUtils identityUtils,
                                                 UserIdentityMapper userIdentityMapper){
        return new RegisterUserServiceImpl(userIdentityRepository, identityUtils, userIdentityMapper);
    }

    @Bean
    UpdateUserService configureUpdateUserService(UserIdentityRepository userIdentityRepository, UserIdentityMapper userIdentityMapper){
        return new UpdateUserServiceImpl(userIdentityRepository, userIdentityMapper);
    }

    @Bean
    GetUserService configureGetUserService(UserIdentityRepository userIdentityRepository){
        return new GetUserServiceImpl(userIdentityRepository);
    }

    @Bean
    DeleteUserService configureDeleteUserService(UserIdentityRepository userIdentityRepository){
        return new DeleteUserServiceImpl(userIdentityRepository);
    }

    @Bean
    UpdateUserCredentialService configureUpdateUserCredentialService(UserIdentityRepository userIdentityRepository){
        return new UpdateUserCredentialServiceImpl(userIdentityRepository);
    }
}
