package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Post;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.PostPersistenceEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class PostPersistenceAssembler {
    private PostPersistenceAssembler() {}

    public static Post toDomainFromPersistence(PostPersistenceEntity entity){
        LocalDateTime createdAt = entity.getCreatedAt() == null
                ? null
                : entity.getCreatedAt().toInstant()
                  .atZone(ZoneId.systemDefault())
                  .toLocalDateTime();

        var post = new Post(
                entity.getCommunityId(),
                entity.getUserId(),
                entity.getContent(),
                entity.getPoints(),
                entity.getLikes(),
                entity.getImageUrl(),
                createdAt
        );
        post.setId(entity.getId());
        return post;
    }

    public static PostPersistenceEntity toPersistenceFromDomain(Post post) {
        var entity = new PostPersistenceEntity();
        entity.setId(post.getId());
        entity.setCommunityId(post.getCommunityId());
        entity.setUserId(post.getUserId());
        entity.setContent(post.getContent());
        entity.setPoints(post.getPoints());
        entity.setLikes(post.getLikes());
        entity.setImageUrl(post.getImageUrl());
        return entity;
    }
}
