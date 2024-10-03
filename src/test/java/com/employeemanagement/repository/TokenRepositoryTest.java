package com.employeemanagement.repository;

import com.employeemanagement.model.entity.Role;
import com.employeemanagement.model.entity.Token;
import com.employeemanagement.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("test@example.com")
                .firstname("Test")
                .lastname("User")
                .password("password")
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    @Test
    void testFindAllValidTokenByUser() {
        Token validToken1 = new Token();
        validToken1.setUser(user);
        validToken1.setToken("validToken1");
        validToken1.setExpired(false);
        validToken1.setRevoked(false);
        tokenRepository.save(validToken1);

        Token invalidToken = new Token();
        invalidToken.setUser(user);
        invalidToken.setToken("invalidToken");
        invalidToken.setExpired(true);
        invalidToken.setRevoked(true);
        tokenRepository.save(invalidToken);

        List<Token> validTokens = tokenRepository.findAllValidTokenByUser("test@example.com");

        assertThat(validTokens).hasSize(1);
        assertThat(validTokens.get(0).getToken()).isEqualTo("validToken1");
    }

    @Test
    void testFindByToken() {
        Token token = new Token();
        token.setUser(user);
        token.setToken("uniqueToken");
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);

        Optional<Token> foundToken = tokenRepository.findByToken("uniqueToken");

        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getToken()).isEqualTo("uniqueToken");
    }
}
