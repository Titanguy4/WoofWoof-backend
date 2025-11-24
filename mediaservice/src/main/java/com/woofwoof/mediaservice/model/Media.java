package com.woofwoof.mediaservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private LocalDate postDate;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    // Champs dépendants du type de média
    private Long stayId;      // Pour WOOFSHARE_PHOTO et STAY_PHOTO
    private String username;  // Pour PROFILE_PHOTO

    // --- Getters / Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public LocalDate getPostDate() { return postDate; }
    public void setPostDate(LocalDate postDate) { this.postDate = postDate; }

    public MediaType getMediaType() { return mediaType; }
    public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }

    public Long getStayId() { return stayId; }
    public void setStayId(Long stayId) { this.stayId = stayId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
