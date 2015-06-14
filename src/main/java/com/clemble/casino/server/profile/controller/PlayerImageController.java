package com.clemble.casino.server.profile.controller;

import com.clemble.casino.player.service.PlayerImageService;
import com.clemble.casino.server.ServerController;
import com.clemble.casino.server.profile.PlayerImageRedirect;
import com.clemble.casino.server.profile.repository.PlayerImageRedirectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.clemble.casino.player.PlayerWebMapping.*;

/**
 * Created by mavarazy on 7/26/14.
 */
@RestController
public class PlayerImageController implements PlayerImageService, ServerController {

    final private PlayerImageRedirectRepository imageRedirectRepository;

    public PlayerImageController(PlayerImageRedirectRepository imageRedirectRepository) {
        this.imageRedirectRepository = imageRedirectRepository;
    }

    @Override
    public byte[] myImage() {
        throw new IllegalAccessError();
    }

    @RequestMapping(value = MY_IMAGE, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void myImage(@CookieValue("player") String player,
        @RequestParam(value = "height", defaultValue = "60") int height,
        @RequestParam(value = "width", defaultValue = "60") int width,
        HttpServletResponse response) {
        getImage(player, height, width, response);
    }

    @Override
    public byte[] getImage(String player) {
        throw new IllegalAccessError();
    }

    @RequestMapping(value = PLAYER_IMAGE, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void getImage(
        @PathVariable("player") String player,
        @RequestParam(value = "height", defaultValue = "60") int height,
        @RequestParam(value = "width", defaultValue = "60") int width,
        HttpServletResponse response) {
        try {
            // Step 1. Extracting player image redirect
            PlayerImageRedirect imageRedirect = imageRedirectRepository.findOne(player);
            if(imageRedirect != null) {
                // Case 1. Player image redirect exists
                String redirect = imageRedirect.
                    getRedirect().
                    replace("{height}", Integer.toString(height)).
                    replace("{width}", Integer.toString(width));
                // Sending redirect as a Response
                response.sendRedirect(redirect);
            } else {
                // Case 2. No redirect exists returning 404 error
                response.sendError(404);
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
