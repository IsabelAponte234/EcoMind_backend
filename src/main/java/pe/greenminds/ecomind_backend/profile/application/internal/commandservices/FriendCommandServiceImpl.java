package pe.greenminds.ecomind_backend.profile.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.commandservices.FriendCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.*;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FriendRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class FriendCommandServiceImpl implements FriendCommandService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendCommandServiceImpl(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public Result<Friend, ApplicationError> handle(CreateFriendCommand command) {
        if (command.userId().equals(command.friendId())) {
            return Result.failure(ApplicationError.businessRuleViolation("no-self-friend-request", "A user cannot send a friend request to themselves"));
        }
        if (!userRepository.existsById(command.userId())) return Result.failure(ApplicationError.notFound("User", command.userId().toString()));
        if (!userRepository.existsById(command.friendId())) return Result.failure(ApplicationError.notFound("User", command.friendId().toString()));
        if (friendRepository.existsRelationship(command.userId(), command.friendId())) {
            return Result.failure(ApplicationError.conflict("Friend", "Friend relationship already exists"));
        }
        return Result.success(friendRepository.save(new Friend(null, command.userId(), command.friendId(), command.status())));
    }

    public Result<Friend, ApplicationError> handle(UpdateFriendCommand command) {
        var friend = friendRepository.findById(command.id());
        if (friend.isEmpty()) return Result.failure(ApplicationError.notFound("Friend", command.id().toString()));
        if (command.userId().equals(command.friendId())) {
            return Result.failure(ApplicationError.businessRuleViolation("no-self-friend-request", "A user cannot be friends with themselves"));
        }
        friend.get().update(command.userId(), command.friendId(), command.status());
        return Result.success(friendRepository.save(friend.get()));
    }

    public Result<Friend, ApplicationError> handle(AcceptFriendCommand command) {
        var friend = friendRepository.findById(command.friendRelationId());
        if (friend.isEmpty()) return Result.failure(ApplicationError.notFound("Friend", command.friendRelationId().toString()));
        friend.get().accept();
        return Result.success(friendRepository.save(friend.get()));
    }

    public Result<Friend, ApplicationError> handle(RejectFriendCommand command) {
        var friend = friendRepository.findById(command.friendRelationId());
        if (friend.isEmpty()) return Result.failure(ApplicationError.notFound("Friend", command.friendRelationId().toString()));
        friend.get().reject();
        return Result.success(friendRepository.save(friend.get()));
    }

    public Result<Friend, ApplicationError> handle(DeleteFriendCommand command) {
        var friend = friendRepository.findById(command.friendRelationId());
        if (friend.isEmpty()) return Result.failure(ApplicationError.notFound("Friend", command.friendRelationId().toString()));
        friendRepository.deleteById(command.friendRelationId());
        return Result.success(friend.get());
    }
}
