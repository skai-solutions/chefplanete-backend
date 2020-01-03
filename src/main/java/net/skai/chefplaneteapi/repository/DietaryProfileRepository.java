package net.skai.chefplaneteapi.repository;

import net.skai.chefplaneteapi.domain.DietaryProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietaryProfileRepository extends MongoRepository<DietaryProfile, String> {
}
