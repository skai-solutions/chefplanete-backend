package net.skai.chefplaneteapi.repository;

import net.skai.chefplaneteapi.domain.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Nullable
    User findUserByUserId(@NotNull String userId);

    void removeUserByUserId(@NotNull String userId);
}
