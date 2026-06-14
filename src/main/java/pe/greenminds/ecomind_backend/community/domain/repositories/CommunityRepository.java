package pe.greenminds.ecomind_backend.community.domain.repositories;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Community;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository {
    Optional<Community> findById(Long id);

    List<Community> search(
            String name,
            String location
    );

    Community save(Community community);
}
