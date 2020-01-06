package net.skai.chefplaneteapi.repository;

import net.skai.chefplaneteapi.domain.Pantry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PantryRepository extends MongoRepository<Pantry, String> {
}
