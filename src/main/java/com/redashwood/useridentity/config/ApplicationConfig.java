package com.redashwood.useridentity.config;

import com.redashwood.useridentity.repository.UserIdentityRepository;
import com.redashwood.useridentity.service.*;
import com.redashwood.useridentity.service.impl.*;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ApplicationConfig {

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    RegisterUserService configureRegisterService(UserIdentityRepository userIdentityRepository){
        return new RegisterUserServiceImpl(userIdentityRepository);
    }

    @Bean
    UpdateUserService configureUpdateUserService(UserIdentityRepository userIdentityRepository){
        return new UpdateUserServiceImpl(userIdentityRepository);
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
