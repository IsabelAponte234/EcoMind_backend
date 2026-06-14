package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Post;
import pe.greenminds.ecomind_backend.community.domain.repositories.PostRepository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers.PostPersistenceAssembler;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories.PostPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final PostPersistenceRepository postPersistenceRepository;

    public PostRepositoryImpl(PostPersistenceRepository postPersistenceRepository) {
        this.postPersistenceRepository = postPersistenceRepository;
    }

    @Override
    public Optional<Post> findById(Long id){
        return postPersistenceRepository.findById(id).map(PostPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Post> search(Long communityId, Long userId) {
        return postPersistenceRepository
                .search(communityId, userId)
                .stream()
                .map(PostPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Post save(Post post) {
        var savedEntity = postPersistenceRepository.save(PostPersistenceAssembler.toPersistenceFromDomain(post));
        return PostPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public void deleteById(Long id) {postPersistenceRepository.deleteById(id);}

    @Override
    public boolean existsById(Long id) {return postPersistenceRepository.existsById(id);}

}
