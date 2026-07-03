package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.GemPackageResource;

public class GemPackageResourceFromEntityAssembler {

    private GemPackageResourceFromEntityAssembler() {}

    public static GemPackageResource toResourceFromEntity(GemPackage gemPackage) {
        return new GemPackageResource(
                gemPackage.getId(),
                gemPackage.getName(),
                gemPackage.getGemAmount(),
                gemPackage.getRealPrice(),
                gemPackage.getCurrency()
        );
    }
}
