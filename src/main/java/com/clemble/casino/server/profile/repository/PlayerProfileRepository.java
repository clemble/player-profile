package com.clemble.casino.server.profile.repository;

import java.util.List;

import com.clemble.casino.player.PlayerProfile;

public interface PlayerProfileRepository {

    PlayerProfile findOne(String player);

    List<PlayerProfile> findAll(Iterable<String> player);

    PlayerProfile save(PlayerProfile playerProfile);

    void deleteAll();

    long count();

}
