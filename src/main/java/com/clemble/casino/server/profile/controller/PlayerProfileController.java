package com.clemble.casino.server.profile.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;

import com.clemble.casino.player.service.PlayerProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.clemble.casino.error.ClembleErrorCode;
import com.clemble.casino.error.ClembleException;
import com.clemble.casino.player.PlayerProfile;
import com.clemble.casino.server.ServerController;
import com.clemble.casino.server.profile.repository.PlayerProfileRepository;
import static com.clemble.casino.player.PlayerWebMapping.*;

@RestController
public class PlayerProfileController implements PlayerProfileService, ServerController {

    // TODO need a listener, that adds ConnectionKey to PlayerProfile when connection added

    final private PlayerProfileRepository profileRepository;

    public PlayerProfileController(PlayerProfileRepository playerProfileRepository) {
        this.profileRepository = checkNotNull(playerProfileRepository);
    }

    @Override
    public PlayerProfile myProfile() {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(method = RequestMethod.GET, value = MY_PROFILE, produces = PRODUCES)
    @ResponseStatus(HttpStatus.OK)
    public PlayerProfile myProfile(@CookieValue("player") String player) {
        return getProfile(player);
    }

    @Override
    public PlayerProfile updateProfile(PlayerProfile playerProfile) {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(method = RequestMethod.POST, value = MY_PROFILE, produces = PRODUCES)
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerProfile updateProfile(@CookieValue("player") String player, @RequestBody PlayerProfile playerProfile) {
        // Step 1. Sanity check
        if (playerProfile == null)
            throw ClembleException.fromError(ClembleErrorCode.PlayerProfileInvalid);
        if (!playerProfile.getPlayer().equals(player))
            throw ClembleException.fromError(ClembleErrorCode.PlayerNotProfileOwner);
        playerProfile.setPlayer(player);
        // Step 1.1. Checking player does not try to add additional Social Connections
        PlayerProfile existingProfile = profileRepository.findOne(player);
        if (!existingProfile.getSocialConnections().equals(playerProfile.getSocialConnections()))
            throw ClembleException.fromError(ClembleErrorCode.ProfileSocialCantBeEdited);
        // Step 2. Updating Profile
        return profileRepository.save(playerProfile);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = PLAYER_PROFILE, produces = PRODUCES)
    @ResponseStatus(HttpStatus.OK)
    public PlayerProfile getProfile(@PathVariable("player") String player) {
        // Step 1. Fetching playerProfile
        PlayerProfile playerProfile = profileRepository.findOne(player);
        // Step 2. Checking profile
        if (playerProfile == null)
            throw ClembleException.fromError(ClembleErrorCode.PlayerProfileDoesNotExists);
        // Step 3. Returning profile
        return playerProfile;
    }

    @Override
    public List<PlayerProfile> getProfiles(String... players) {
        throw new UnsupportedOperationException();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = PLAYER_PROFILES, produces = PRODUCES)
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerProfile> getProfiles(@RequestParam("player") Collection<String> players) {
        return profileRepository.findAll(players);
    }

}
