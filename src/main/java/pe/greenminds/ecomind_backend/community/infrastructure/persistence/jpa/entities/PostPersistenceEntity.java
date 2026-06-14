package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "community_posts")
public class PostPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "community_id", nullable = false)
    private Long communityId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "likes", nullable = false)
    private Integer likes;

    @Column(name = "image_url")
    private String imageUrl;

    public Long getCommunityId() { return  communityId; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
    public Integer getPoints() { return points; }
    public Integer getLikes() { return likes; }
    public String getImageUrl() { return imageUrl; }

    public void setCommunityId(Long communityId) { this.communityId = communityId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setContent(String content) { this.content = content; }
    public void setPoints(Integer points) { this.points = points; }
    public void setLikes(Integer likes) { this.likes = likes; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
