package com.example.cardentity.person.repository;

import com.example.cardentity.person.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person,String> {

    Person findByCode(String code);

}
