package com.clonecoding.myboxclone.controllerTest;

import com.clonecoding.myboxclone.controller.AuthController;
import com.clonecoding.myboxclone.dto.MemberReqDTO;
import com.clonecoding.myboxclone.entity.MemberRepository;
import com.clonecoding.myboxclone.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;
    @Mock
    private MemberReqDTO memberReqDTO;
    @Mock
    private AuthService authService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private MockMvc mockMvc;
    AutoCloseable openMocks;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("회원 가입 테스트")
    @Order(1)
    public void signupTest() throws Exception {
        memberReqDTO = MemberReqDTO.builder()
                .email("abc@mail.com")
                .name("testUser")
                .password("1234")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/signup")
                .content(objectMapper.writeValueAsString(memberReqDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
