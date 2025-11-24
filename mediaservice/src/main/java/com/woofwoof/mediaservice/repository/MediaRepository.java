package com.woofwoof.mediaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.woofwoof.mediaservice.model.Media;
import com.woofwoof.mediaservice.model.MediaType;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findByMediaTypeAndStayId(MediaType mediaType, Long stayId);

    List<Media> findByMediaTypeAndUsername(MediaType mediaType, String username);
}
