package pe.greenminds.ecomind_backend.community.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post extends AbstractDomainAggregateRoot<Post> {
    @Getter
    @Setter
    private Long id;

    private Long communityId;
    private Long userId;
    private String content;
    private Integer points;
    private Integer likes;
    private String imageUrl;
    private LocalDateTime createdAt;

    public Post(Long id, Long communityId, Long userId, String content, Integer points, Integer likes, String imageUrl, LocalDateTime createdAt) {
        this.id = id;
        this.communityId = Objects.requireNonNull(communityId, "communityId must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.content = Objects.requireNonNull(content, "content must not be null");
        this.points = Objects.requireNonNull(points, "points must not be null");
        this.likes = Objects.requireNonNull(likes, "likes must not be null");
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public Post(Long communityId, Long userId, String content, Integer points, Integer likes, String imageUrl, LocalDateTime createdAt) {
        this(null, communityId, userId, content, points, likes, imageUrl, createdAt);
    }
    
    public Post(Long communityId, Long userId, String content, Integer points, String imageUrl) {
        this(null, communityId, userId, content, points, 0, imageUrl, null);
    }

    public Long getCommunityId() { return communityId; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
    public Integer getPoints() { return points; }
    public Integer getLikes() { return likes; }
    public String getImageUrl() { return imageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
