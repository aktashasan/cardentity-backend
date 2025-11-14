package com.example.cardentity.user.service;

import com.example.cardentity.user.model.User;
import com.example.cardentity.user.model.UserDTO;
import com.example.cardentity.user.model.UserMapperImpl;
import com.example.cardentity.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.cardentity.cache.CacheNames.USER_BY_ID;
import static com.example.cardentity.cache.CacheNames.USER_BY_USERNAME;
import static com.example.cardentity.cache.CacheNames.USER_LIST;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final int MAX_PAGE_SIZE = 200;

    @Caching(
            put = {
                    @CachePut(cacheNames = USER_BY_ID, key = "#result.id", condition = "#result != null"),
                    @CachePut(cacheNames = USER_BY_USERNAME, key = "#result.username", condition = "#result != null && #result.username != null")
            },
            evict = {
                    @CacheEvict(cacheNames = USER_LIST, allEntries = true)
            }
    )
    public UserDTO addUser(UserDTO userDTO){
        User user = userRepository.save(UserMapperImpl.toEntity(userDTO));
        return UserMapperImpl.toDTO(user);
    }

    @Cacheable(cacheNames = USER_BY_ID, key = "#id", unless = "#result == null")
    public UserDTO findUserById(String id){
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            return UserMapperImpl.toDTO(user);
        }
        return null;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = USER_BY_ID, key = "#id"),
            @CacheEvict(cacheNames = USER_BY_USERNAME, allEntries = true),
            @CacheEvict(cacheNames = USER_LIST, allEntries = true)
    })
    public Boolean deleteUserById(String id){
        if (!userRepository.existsById(id)) {
            return Boolean.FALSE;
        }
        userRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public UserDTO findUserByUsernameAndPassword(String username, String password){
        User user = userRepository.findByUsernameAndPassword(username,password);
        log.info(String.valueOf(user));
        return UserMapperImpl.toDTO(user);
    }

    @Cacheable(cacheNames = USER_BY_USERNAME, key = "#username", unless = "#result == null")
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserDTO> findAllUsers(){
        return findAllUsers(0, DEFAULT_PAGE_SIZE);
    }

    @Cacheable(cacheNames = USER_LIST, key = "'page:' + #page + ':size:' + #size", unless = "#result == null || #result.isEmpty()")
    public List<UserDTO> findAllUsers(int page, int size){
        Pageable pageable = buildPageRequest(page, size);
        return userRepository.findAll(pageable)
                .map(UserMapperImpl::toDTO)
                .getContent();
    }

    private Pageable buildPageRequest(int page, int size) {
        int safePage = Math.max(0, page);
        int safeSize = Math.max(1, Math.min(size, MAX_PAGE_SIZE));
        return PageRequest.of(safePage, safeSize);
    }

}
