package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProfileSummaryResource(
        UserResource user,
        FamilyResource family,
        @JsonProperty("family_members") List<FamilyUserResource> familyMembers,
        @JsonProperty("received_invitations") List<FamilyInvitationResource> receivedInvitations,
        @JsonProperty("sent_invitations") List<FamilyInvitationResource> sentInvitations,
        List<FriendResource> friends,
        @JsonProperty("equipped_cosmetics") List<Object> equippedCosmetics
) {
}
