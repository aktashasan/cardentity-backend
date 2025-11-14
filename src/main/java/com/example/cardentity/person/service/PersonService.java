package com.example.cardentity.person.service;

import com.example.cardentity.person.model.Person;
import com.example.cardentity.person.model.PersonDTO;
import com.example.cardentity.person.model.PersonMapperImpl;
import com.example.cardentity.person.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.cardentity.cache.CacheNames.PERSON_BY_CODE;
import static com.example.cardentity.cache.CacheNames.PERSON_BY_ID;
import static com.example.cardentity.cache.CacheNames.PERSON_LIST;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final int MAX_PAGE_SIZE = 200;

    @Caching(
            put = {
                    @CachePut(cacheNames = PERSON_BY_ID, key = "#result.id", condition = "#result != null"),
                    @CachePut(cacheNames = PERSON_BY_CODE, key = "#result.code", condition = "#result != null && #result.code != null")
            },
            evict = {
                    @CacheEvict(cacheNames = PERSON_LIST, allEntries = true)
            }
    )
    public PersonDTO addPerson(PersonDTO personDTO){
        Person person = personRepository.save(PersonMapperImpl.toEntity(personDTO));
        return PersonMapperImpl.toDTO(person);
    }

    @Cacheable(cacheNames = PERSON_BY_ID, key = "#id", unless = "#result == null")
    public PersonDTO findPersonById(String id){
        Optional<Person> optional = personRepository.findById(id);

        if (optional.isPresent()){
            Person person = optional.get();
            return PersonMapperImpl.toDTO(person);
        }
        return null;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = PERSON_BY_ID, key = "#id"),
            @CacheEvict(cacheNames = PERSON_BY_CODE, allEntries = true),
            @CacheEvict(cacheNames = PERSON_LIST, allEntries = true)
    })
    public Boolean deletePersonById(String id){
        if (!personRepository.existsById(id)) {
            return Boolean.FALSE;
        }
        personRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public List<PersonDTO> findAllPersons() {
        return findAllPersons(0, DEFAULT_PAGE_SIZE);
    }

    @Cacheable(cacheNames = PERSON_LIST, key = "'page:' + #page + ':size:' + #size", unless = "#result == null || #result.isEmpty()")
    public List<PersonDTO> findAllPersons(int page, int size){
        Pageable pageable = buildPageRequest(page, size);
        return personRepository.findAll(pageable)
                .map(PersonMapperImpl::toDTO)
                .getContent();
    }

    @Cacheable(cacheNames = PERSON_BY_CODE, key = "#code", unless = "#result == null")
    public PersonDTO findPersonByCode(String code){
        Person person = personRepository.findByCode(code);
        return PersonMapperImpl.toDTO(person);
    }

    private Pageable buildPageRequest(int page, int size) {
        int safePage = Math.max(0, page);
        int safeSize = Math.max(1, Math.min(size, MAX_PAGE_SIZE));
        return PageRequest.of(safePage, safeSize);
    }

}
