package com.woofwoof.mediaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.woofwoof.mediaservice.model.Media;
import com.woofwoof.mediaservice.model.MediaType;

import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findByMediaTypeAndStayId(MediaType mediaType, Long stayId);

    List<Media> findByMediaTypeAndUserId(MediaType mediaType, UUID userId);

    List<Media> findByMediaType(MediaType mediaType);
}
