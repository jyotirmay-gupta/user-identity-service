package com.redashwood.useridentity.config;

import com.redashwood.useridentity.filter.VersionHeaderFilter;
import com.redashwood.useridentity.mapper.UserIdentityMapper;
import com.redashwood.useridentity.mapper.UserIdentityMapperImpl;
import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.*;
import com.redashwood.useridentity.service.impl.*;
import com.redashwood.useridentity.util.IdentityUtils;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ApplicationConfig {

    @Bean
    VersionHeaderFilter registerVersionHeaderFilter() {
        return new VersionHeaderFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserIdentityMapper configureUserIdentityMapper(){
        return new UserIdentityMapperImpl();
    }

    @Bean
    public IdentityUtils configureIdentityUtils(PasswordEncoder passwordEncoder) {
        return new IdentityUtils(passwordEncoder);
    }

    @Bean
    @Profile("!test")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    RegisterUserService configureRegisterService(UserIdentityRepository userIdentityRepository, IdentityUtils identityUtils, UserIdentityMapper userIdentityMapper) {
        return new RegisterUserServiceImpl(userIdentityRepository, identityUtils, userIdentityMapper);
    }

    @Bean
    UpdateUserService configureUpdateUserService(UserIdentityRepository userIdentityRepository, UserIdentityMapper userIdentityMapper) {
        return new UpdateUserServiceImpl(userIdentityRepository, userIdentityMapper);
    }

    @Bean
    GetUserService configureGetUserService(UserIdentityRepository userIdentityRepository, UserIdentityMapper userIdentityMapper) {
        return new GetUserServiceImpl(userIdentityRepository, userIdentityMapper);
    }

    @Bean
    DeleteUserService configureDeleteUserService(UserIdentityRepository userIdentityRepository) {
        return new DeleteUserServiceImpl(userIdentityRepository);
    }

    @Bean
    UpdateUserCredentialService configureUpdateUserCredentialService(UserIdentityRepository userIdentityRepository, IdentityUtils identityUtils) {
        return new UpdateUserCredentialServiceImpl(userIdentityRepository, identityUtils);
    }
}
