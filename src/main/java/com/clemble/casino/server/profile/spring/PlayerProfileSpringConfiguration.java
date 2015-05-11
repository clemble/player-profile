package com.clemble.casino.server.profile.spring;

import com.clemble.casino.server.player.notification.ServerNotificationService;
import com.clemble.casino.server.player.notification.SystemNotificationService;
import com.clemble.casino.server.profile.listener.*;
import com.clemble.casino.server.profile.repository.MongoPlayerProfileRepository;
import com.clemble.casino.server.profile.repository.PlayerImageRedirectRepository;
import com.clemble.casino.server.profile.repository.PlayerProfileRepository;
import com.clemble.casino.server.spring.common.MongoSpringConfiguration;
import com.clemble.casino.server.spring.common.CommonSpringConfiguration;
import com.clemble.casino.server.spring.common.SpringConfiguration;
import com.clemble.casino.server.profile.controller.PlayerImageController;
import com.clemble.casino.server.profile.controller.PlayerProfileController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

/**
 * Created by mavarazy on 7/4/14.
 */
@Configuration
@Import({CommonSpringConfiguration.class, MongoSpringConfiguration.class})
public class PlayerProfileSpringConfiguration implements SpringConfiguration {

    @Bean
    public PlayerProfileCreationEventListener playerProfileRegistrationEventListener(
            PlayerProfileRepository playerRepository,
            SystemNotificationService notificationService
    ) {
        PlayerProfileCreationEventListener profileCreationService = new PlayerProfileCreationEventListener(playerRepository, notificationService);
        return profileCreationService;
    }

    @Bean
    public PlayerImageChangedEventListener playerImageChangedEventListener(
        PlayerImageRedirectRepository imageRedirectRepository
    ) {
        PlayerImageChangedEventListener imageChangedEventListener = new PlayerImageChangedEventListener(imageRedirectRepository);
        return imageChangedEventListener;
    }

    @Bean
    public PlayerProfileSocialAddedEventListener playerProfileSocialAddedEventListener(
        PlayerProfileRepository profileRepository,
        @Qualifier("playerNotificationService")  ServerNotificationService notificationService
    ) {
        PlayerProfileSocialAddedEventListener socialAddedEventListener = new PlayerProfileSocialAddedEventListener(profileRepository, notificationService);
        return socialAddedEventListener;
    }

    @Bean
    public PlayerProfilePhoneVerifiedEventListener playerProfilePhoneVerifiedEventListener(
        PlayerProfileRepository profileRepository,
        @Qualifier("playerNotificationService")  ServerNotificationService notificationService
    ) {
        PlayerProfilePhoneVerifiedEventListener phoneVerifiedEventListener = new PlayerProfilePhoneVerifiedEventListener(profileRepository, notificationService);
        return phoneVerifiedEventListener;
    }

    @Bean
    public PlayerProfileEmailVerifiedEventListener playerProfileEmailVerifiedEventListener(
        PlayerProfileRepository profileRepository,
        @Qualifier("playerNotificationService")  ServerNotificationService notificationService
    ) {
        PlayerProfileEmailVerifiedEventListener emailVerifiedEventListener = new PlayerProfileEmailVerifiedEventListener(profileRepository, notificationService);
        return emailVerifiedEventListener;
    }

    @Bean
    public PlayerProfileRepository playerProfileRepository(MongoRepositoryFactory mongoRepositoryFactory) {
        return mongoRepositoryFactory.getRepository(MongoPlayerProfileRepository.class);
    }

    @Bean
    public PlayerImageRedirectRepository playerImageRedirectRepository(MongoRepositoryFactory mongoRepositoryFactory) {
        return mongoRepositoryFactory.getRepository(PlayerImageRedirectRepository.class);
    }

    @Bean
    public PlayerProfileController playerProfileController(PlayerProfileRepository playerProfileRepository) {
        return new PlayerProfileController(playerProfileRepository);
    }

    @Bean
    public PlayerImageController playerImageController(PlayerImageRedirectRepository imageRedirectRepository) {
        return new PlayerImageController(imageRedirectRepository);
    }

}
