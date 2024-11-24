package com.portfolio.scott.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.scott.domains.user.model.User;
import com.portfolio.scott.repository.CreditCardRepository;
import com.portfolio.scott.repository.IncomeRepository;
import com.portfolio.scott.repository.NationalPensionRepository;
import com.portfolio.scott.domains.user.repository.UserRepository;
import com.portfolio.scott.domains.user.service.UserService;
import com.portfolio.scott.support.WithMockCustomUser;
import com.portfolio.scott.controllers.dto.LoginDTO;
import com.portfolio.scott.controllers.dto.SignupDTO;
import com.portfolio.scott.controllers.dto.TokenDTO;
import com.portfolio.scott.controllers.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class SzsControllerTest {

    private final static Logger logger = LoggerFactory.getLogger(SzsControllerTest.class);

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private NationalPensionRepository nationalPensionRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    private static boolean isInitialized = false;

    @BeforeEach
    public void setUp(){
        if (!isInitialized) {
            UserDTO userDTO = userService.signup(createUser());
            logger.info("user id {}", userDTO.getId());
            logger.info("login id {}", userDTO.getLoginId());


            isInitialized = true;
        }
    }

    private SignupDTO createUser() {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUserId("ASXX");
        signupDTO.setName("관우");
        signupDTO.setPassword("12qw##e");
        signupDTO.setRegNo("681108-1582816");
        return signupDTO;
    }

    private void createIncome() {
        Optional<User> user = userRepository.findByLoginId("ASXX");
        Income income = new Income();
        income.setYear(2023);
        income.setUser(user.get());
        income.setTotalIncome(new BigDecimal(20000000));
        income.setTaxCredit(new BigDecimal(300000));
        income.setCreatedDate(Instant.now());
        incomeRepository.save(income);

        createNationalPension(2023, 1, new BigDecimal("300000.25"), income);
        createNationalPension(2023, 2, new BigDecimal("200000"), income);
        createNationalPension(2023, 3, new BigDecimal("400000.75"), income);
        createNationalPension(2023, 4, new BigDecimal("100000.10"), income);
        createNationalPension(2023, 5, new BigDecimal("100000.10"), income);
        createNationalPension(2023, 6, new BigDecimal("300000.00"), income);
        createNationalPension(2023, 8, new BigDecimal("200000.20"), income);
        createNationalPension(2023, 9, new BigDecimal("300000.40"), income);
        createNationalPension(2023, 10, new BigDecimal("300000.70"), income);
        createNationalPension(2023, 11, new BigDecimal("0"), income);
        createNationalPension(2023, 12, new BigDecimal("0"), income);

        createCreditCard(2023, 1, new BigDecimal("100000.10"), income);
        createCreditCard(2023, 3, new BigDecimal("100000.20"), income);
        createCreditCard(2023, 5, new BigDecimal("200000.30"), income);
        createCreditCard(2023, 10, new BigDecimal("100000"), income);
        createCreditCard(2023, 12, new BigDecimal("300000.50"), income);
    }

    private void createNationalPension(int year, int month, BigDecimal deduction, Income income) {
        NationalPension pension = new NationalPension();
        pension.setYear(year);
        pension.setMonth(month);
        pension.setDeduction(deduction);
        pension.setCreatedDate(Instant.now());
        pension.setIncome(income);
        nationalPensionRepository.save(pension);
    }

    private void createCreditCard(int year, int month, BigDecimal deduction, Income income) {
        CreditCard creditCard = new CreditCard();
        creditCard.setYear(year);
        creditCard.setMonth(month);
        creditCard.setDeduction(deduction);
        creditCard.setCreatedDate(Instant.now());
        creditCard.setIncome(income);
        creditCardRepository.save(creditCard);
    }

    @Test
    @Order(1)
    void 회원가입() throws Exception {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUserId("ths201244");
        signupDTO.setPassword("thsrnjsdlek");
        signupDTO.setName("손권");
        signupDTO.setRegNo("890601-2455116");

        mockMvc.perform(post("/szs/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signupDTO)))
                .andExpect(jsonPath("$.loginId").value(signupDTO.getUserId()))
                .andExpect(jsonPath("$.name").value(signupDTO.getName()));
    }

    @Test
    void 회원가입_허용되지않은회원() throws Exception {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUserId("ths201244");
        signupDTO.setPassword("thsrnjsdlek");
        signupDTO.setName("장비");
        signupDTO.setRegNo("900601-2455116");

        mockMvc.perform(post("/szs/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signupDTO)))
                .andExpect(jsonPath("$.errorCode").value("USER_NOT_ALLOWED_SIGNUP"));
    }

    @Test
    void 회원가입_이미가입된회원() throws Exception {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUserId("ASXX");
        signupDTO.setName("관우");
        signupDTO.setPassword("12qw##e");
        signupDTO.setRegNo("681108-1582816");

        mockMvc.perform(post("/szs/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signupDTO)))
                .andExpect(jsonPath("$.errorCode").value("USER_ALREADY_SIGNUP"));
    }

    @Test
    void 회원가입_필수체크() throws Exception {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUserId("ASXX");
        signupDTO.setPassword("12qw##e");
        signupDTO.setRegNo("681108-1582816");

        mockMvc.perform(post("/szs/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signupDTO)))
                .andExpect(jsonPath("$.name").value("Name is required"));
    }

    @Test
    void 회원가입_주민번호패턴체크() throws Exception {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUserId("ASXX");
        signupDTO.setName("관우");
        signupDTO.setPassword("12qw##e");
        signupDTO.setRegNo("681108-AA82816");

        mockMvc.perform(post("/szs/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signupDTO)))
                .andExpect(jsonPath("$.regNo").value("Registration number must match the pattern 'YYMMDD-gabcdef'"));
    }

    @Test
    @Order(2)
    void 로그인() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId("ASXX");
        loginDTO.setPassword("12qw##e");

        mockMvc.perform(post("/szs/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andDo(print());
    }

    @Test
    void 로그인_없는사용자() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId("KKK222");
        loginDTO.setPassword("5412asd##e");

        mockMvc.perform(post("/szs/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDTO)))
                .andExpect(jsonPath("$.errorCode").value("USER_NOT_FOUND"));
    }

    @Test
    void 로그인_패스워드틀린경우() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId("ASXX");
        loginDTO.setPassword("32qw##e");

        mockMvc.perform(post("/szs/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDTO)))
                .andExpect(jsonPath("$.errorCode").value("USER_NOT_FOUND"));
    }

    @Test
    @Order(3)
    @WithMockCustomUser()
    void 소득정보요청() throws Exception {
        mockMvc.perform(post("/szs/scrap")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 소득정보요청_토큰없는회원() throws Exception {
        mockMvc.perform(post("/szs/scrap")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value("UNAUTHORIZED_ERROR"));
    }

    @Test
    void 소득정보요청_중복요청() throws Exception {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUserId("ee2dwe");
        signupDTO.setPassword("er34aas");
        signupDTO.setName("조조");
        signupDTO.setRegNo("810326-2715702");

        mockMvc.perform(post("/szs/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signupDTO)))
                .andExpect(jsonPath("$.loginId").value(signupDTO.getUserId()))
                .andExpect(jsonPath("$.name").value(signupDTO.getName()));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId("ee2dwe");
        loginDTO.setPassword("er34aas");
        MvcResult result = mockMvc.perform(post("/szs/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        TokenDTO response = new ObjectMapper().readValue(content, TokenDTO.class);
        mockMvc.perform(post("/szs/scrap")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + response.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(post("/szs/scrap")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + response.getAccessToken()))
                .andExpect(jsonPath("$.errorCode").value("SCRAP_REQUEST_ALREADY"));
    }

    @Test
    @Order(4)
    @WithMockCustomUser()
    void 결정세액조회() throws Exception {
        createIncome();

        String taxAmount = "989,999";
        mockMvc.perform(get("/szs/refund")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.결정세액").value(taxAmount))
                .andDo(print());
    }

    @Test
    void 결정세액조회_소득정보없는회원() throws Exception {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUserId("sadiwj1");
        signupDTO.setPassword("vcswwaaa");
        signupDTO.setName("유비");
        signupDTO.setRegNo("790411-1656116");

        mockMvc.perform(post("/szs/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signupDTO)))
                .andExpect(jsonPath("$.loginId").value(signupDTO.getUserId()))
                .andExpect(jsonPath("$.name").value(signupDTO.getName()));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId("sadiwj1");
        loginDTO.setPassword("vcswwaaa");
        MvcResult result = mockMvc.perform(post("/szs/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        TokenDTO response = new ObjectMapper().readValue(content, TokenDTO.class);
        mockMvc.perform(get("/szs/refund")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + response.getAccessToken()))
                .andExpect(jsonPath("$.errorCode").value("NOT_FOUND_USER_INCOME"));
    }
}
