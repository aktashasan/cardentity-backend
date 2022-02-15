package com.example.cardentity.user.authentication;


import com.example.cardentity.user.model.User;
import com.example.cardentity.user.model.UserDTO;
import com.example.cardentity.user.model.UserMapperImpl;
import com.example.cardentity.user.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/app")
public class AuthenticationResource {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationResource.class);

    private final UserService userService;

    private final UserMapperImpl userMapper;

    @GetMapping(path = "/login")
    public ResponseEntity<UserDTO> login(){
        User user = userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

        log.info(String.valueOf(user));
        return ResponseEntity.ok(userMapper.toDTO(user));
    }
}
