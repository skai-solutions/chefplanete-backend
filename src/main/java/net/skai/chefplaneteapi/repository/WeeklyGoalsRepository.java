package net.skai.chefplaneteapi.repository;

import net.skai.chefplaneteapi.domain.WeeklyGoals;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyGoalsRepository extends MongoRepository<WeeklyGoals, String> {
}
