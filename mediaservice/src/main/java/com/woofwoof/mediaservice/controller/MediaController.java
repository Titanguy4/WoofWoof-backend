package com.woofwoof.mediaservice.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.woofwoof.mediaservice.model.Media;
import com.woofwoof.mediaservice.model.MediaType;
import com.woofwoof.mediaservice.repository.MediaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medias")
public class MediaController {

    @Autowired
    private MediaRepository mediaRepository;

    // Endpoint pour récupérer tous les médias
    @GetMapping
    public List<Media> getAllMedias() {
        return mediaRepository.findAll();
    }

    // function to get stayId from WoofShare photo to link with stay service
    @GetMapping("/woofshare/{mediaId}")
    public Long getStayIdFromWoofSharePhoto(@PathVariable Long mediaId) {

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));

        if (media.getMediaType() != MediaType.WOOFSHARE_PHOTO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This media is not a WoofShare photo");
        }

        if (media.getStayId() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "WoofShare photo has no stayId");
        }

        return media.getStayId();
    }

    // function to get profile photo for profile page
    @GetMapping("/profile/{userId}")
    public Media getProfilePhotoByUserId(@PathVariable UUID userId) {
        return mediaRepository.findByMediaTypeAndUserId(MediaType.PROFILE_PHOTO, userId)
                .stream()
                .findFirst() // prend la première (et normalement unique) photo
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Profile photo not found for userId: " + userId));
    }

    // function to get stay photo for stay page
    @GetMapping("/stay/{stayId}")
    public List<Media> getStayPhotoByStayId(@PathVariable Long stayId) {
        return mediaRepository.findByMediaTypeAndStayId(MediaType.STAY_PHOTO, stayId);
    }

    // Endpoint pour récupérer toutes les WoofShare photos
    @GetMapping("/woofshare")
    public List<Media> getAllWoofSharePhotos() {
        return mediaRepository.findByMediaType(MediaType.WOOFSHARE_PHOTO);
    }

    @PostMapping
    public Media createMedia(@RequestBody Media media) {

        if (media.getMediaType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media type is required");
        }

        switch (media.getMediaType()) {

            case PROFILE_PHOTO:
                if (media.getUserId() == null) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "userId is required for PROFILE_PHOTO"
                    );
                }
                break;

            case STAY_PHOTO:
            case WOOFSHARE_PHOTO:
                if (media.getStayId() == null) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "stayId is required for this media type"
                    );
                }
                break;

            default:
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, 
                    "Unknown media type"
                );
        }

        // auto date
        if (media.getPostDate() == null) {
            media.setPostDate(LocalDate.now());
        }

        return mediaRepository.save(media);
    }
}
