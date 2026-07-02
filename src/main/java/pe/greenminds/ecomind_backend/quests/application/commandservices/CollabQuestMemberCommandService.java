package pe.greenminds.ecomind_backend.quests.application.commandservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestMember;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.AcceptCollabQuestMemberCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeclineCollabQuestMemberCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.InviteCollabQuestMemberCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.LeaveCollabQuestMemberCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.RemoveCollabQuestMemberCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface CollabQuestMemberCommandService {
    Result<CollabQuestMember, ApplicationError> handle(InviteCollabQuestMemberCommand command);
    Result<CollabQuestMember, ApplicationError> handle(AcceptCollabQuestMemberCommand command);
    Result<CollabQuestMember, ApplicationError> handle(DeclineCollabQuestMemberCommand command);
    Result<CollabQuestMember, ApplicationError> handle(LeaveCollabQuestMemberCommand command);
    Result<CollabQuestMember, ApplicationError> handle(RemoveCollabQuestMemberCommand command);
}
