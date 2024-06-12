package com.osio.userservice.service;

import com.osio.userservice.dto.SaveDTO;
import com.osio.userservice.entity.User;
import com.osio.userservice.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class UserServiceImplTest  {

    @Autowired
    private UserServiceImpl userService;

    @Test
    @DisplayName("회원가입")
    @Transactional
    @Rollback
    void userSave() {
        // Given
        SaveDTO saveDTO = SaveDTO.builder()
                .email("testuser@example.com")
                .name("user")
                .phone("1234567890")
                .address("충북")
                .build();

        // When
        User savedUser = userService.saveUser(saveDTO);

        // Then
        assertNotNull(savedUser);
        assertEquals("testuser@example.com", savedUser.getEmail());
        assertEquals("user", savedUser.getName());
        assertEquals("1234567890", savedUser.getPhone());
        assertEquals("충북",savedUser.getAddress());

    }
}
