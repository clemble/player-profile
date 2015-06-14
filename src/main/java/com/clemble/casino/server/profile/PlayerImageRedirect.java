package com.clemble.casino.server.profile;

import com.clemble.casino.player.PlayerAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

/**
 * Created by mavarazy on 8/16/14.
 */
public class PlayerImageRedirect implements PlayerAware {

    @Id
    final private String player;
    final private String redirect;

    @JsonCreator
    public PlayerImageRedirect(@JsonProperty(PLAYER) String player, @JsonProperty("redirect") String redirect) {
        this.player = player;
        this.redirect = redirect;
    }

    public String getPlayer() {
        return player;
    }

    public String getRedirect() {
        return redirect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerImageRedirect that = (PlayerImageRedirect) o;

        if (!player.equals(that.player)) return false;
        return redirect.equals(that.redirect);

    }

    @Override
    public int hashCode() {
        int result = player.hashCode();
        result = 31 * result + redirect.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "player:image:" + player + ':' + redirect;
    }

}
