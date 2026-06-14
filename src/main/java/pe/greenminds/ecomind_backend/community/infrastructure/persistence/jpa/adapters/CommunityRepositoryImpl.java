package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Community;
import pe.greenminds.ecomind_backend.community.domain.repositories.CommunityRepository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers.CommunityPersistenceAssembler;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories.CommunityPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommunityRepositoryImpl implements CommunityRepository {

    private final CommunityPersistenceRepository communityPersistenceRepository;

    public CommunityRepositoryImpl(CommunityPersistenceRepository communityPersistenceRepository) {
        this.communityPersistenceRepository = communityPersistenceRepository;
    }
    @Override
    public Optional<Community> findById(Long id) {
        return communityPersistenceRepository.findById(id).map(CommunityPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Community> search(String name, String location){
        return communityPersistenceRepository
                .search(name, location)
                .stream()
                .map(CommunityPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Community save(Community community) {
        var savedEntity = communityPersistenceRepository.save(
                CommunityPersistenceAssembler.toPersistenceFromDomain(community)
        );
        return CommunityPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }
}
