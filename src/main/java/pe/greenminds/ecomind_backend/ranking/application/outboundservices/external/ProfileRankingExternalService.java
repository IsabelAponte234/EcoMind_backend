package pe.greenminds.ecomind_backend.ranking.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;

import java.util.List;

@Service
public class ProfileRankingExternalService {

    private final FamilyRepository familyRepository;
    private final FamilyUserRepository familyUserRepository;
    private final UserRepository userRepository;

    public ProfileRankingExternalService(
            FamilyRepository familyRepository,
            FamilyUserRepository familyUserRepository,
            UserRepository userRepository
    ) {
        this.familyRepository = familyRepository;
        this.familyUserRepository = familyUserRepository;
        this.userRepository = userRepository;
    }

    public Long fetchProfileIdByUserId(Long userId) {
        return userId;
    }

    public List<Family> fetchAllFamilies() {
        return familyRepository.findAll();
    }

    public List<Long> fetchFamilyMemberUserIds(Long familyId) {
        return familyUserRepository.findByFamilyId(familyId).stream()
                .map(familyUser -> familyUser.getUserId())
                .toList();
    }

    public Integer fetchFamilyEcopointsTotal(Long familyId) {
        return fetchFamilyMemberUserIds(familyId).stream()
                .map(userRepository::findById)
                .flatMap(java.util.Optional::stream)
                .mapToInt(user -> user.getEcopoints() == null ? 0 : user.getEcopoints())
                .sum();
    }
}
