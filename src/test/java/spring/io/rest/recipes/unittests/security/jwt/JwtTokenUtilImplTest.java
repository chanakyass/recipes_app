package spring.io.rest.recipes.unittests.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import spring.io.rest.recipes.config.SecurityProperties;
import spring.io.rest.recipes.security.jwt.AlgorithmStrategy;
import spring.io.rest.recipes.security.jwt.JwtTokenUtil;

class JwtTokenUtilImplTest {
    @Mock
    private AlgorithmStrategy algorithmStrategy;
    @Mock
    private SecurityProperties securityProperties;

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {

    }

    @Test
    void validate() {

    }

    @Test
    void generateToken() {
    }

    @Test
    void getSubjectFromToken() {
    }

    @Test
    void extractTokenAndGetSubject() {
    }

    @Test
    void extractToken() {
    }
}