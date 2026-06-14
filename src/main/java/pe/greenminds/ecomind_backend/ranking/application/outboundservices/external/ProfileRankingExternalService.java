package pe.greenminds.ecomind_backend.ranking.application.outboundservices.external;

import org.springframework.stereotype.Service;

@Service
public class ProfileRankingExternalService {

    public Long fetchProfileIdByUserId(Long userId) {
        return userId;
    }
}
