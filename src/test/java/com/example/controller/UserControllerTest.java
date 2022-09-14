package com.example.controller;

import static org.mockito.ArgumentMatchers.any;

import com.example.dto.request.UserRequestDto;
import com.example.model.User;
import com.example.service.UserService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private final static String BASE_PATH = "/users";
    private final static int REQUEST_OK = HttpStatus.OK.value();
    private final static int REQUEST_BAD = HttpStatus.BAD_REQUEST.value();
    @Value("${local.variable.age.restriction:18}")
    private int ageRestriction;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void call_findByBirthday_withoutParameters_ok() {
        List<User> mockUser = List.of(
                new User(1L, "test@example.org", "first", "last",
                        LocalDate.of(2000, 10, 1), null, null),
                new User(2L, "test@example.net", "first", "last",
                        LocalDate.of(1990, 10, 1), null, null));
        Mockito.when(userService.findByBirthday(null, null)).thenReturn(mockUser);
        RestAssuredMockMvc.when()
                .get(BASE_PATH + "/by-birthday")
                .then()
                .statusCode(REQUEST_OK)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].email", Matchers.equalTo("test@example.org"))
                .body("[0].birthday", Matchers.equalTo("01.10.2000"));
    }

    @Test
    void call_findByBirthday_withFromTo_ok() {
        String from = "01.01.1990";
        String to = "01.01.2020";
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2020, 1, 1);
        List<User> mockUser = List.of(
                new User(1L, "test@example.org", "first", "last",
                        LocalDate.of(2000, 10, 1), null, null));
        Mockito.when(userService.findByBirthday(fromDate, toDate)).thenReturn(mockUser);
        RestAssuredMockMvc.given()
                .queryParam("from", from)
                .queryParam("to", to)
                .when()
                .get(BASE_PATH + "/by-birthday")
                .then()
                .statusCode(REQUEST_OK)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].email", Matchers.equalTo("test@example.org"))
                .body("[0].birthday", Matchers.equalTo("01.10.2000"));
    }

    @Test
    void call_findByBirthday_withFromInFuture_notOk() {
        String from = "01.01.3020";
        RestAssuredMockMvc.given()
                .queryParam("from", from)
                .when()
                .get(BASE_PATH + "/by-birthday")
                .then()
                .statusCode(REQUEST_BAD);
    }

    @Test
    void call_findByBirthday_withToInFuture_notOk() {
        String from = "01.01.3020";
        RestAssuredMockMvc.given()
                .queryParam("from", from)
                .when()
                .get(BASE_PATH + "/by-birthday")
                .then()
                .statusCode(REQUEST_BAD);
    }

    @Test
    void call_findByBirthday_withFromOlderThanTo_notOk() {
        String from = "01.01.2020";
        String to = "01.01.1990";
        RestAssuredMockMvc.given()
                .queryParam("from", from)
                .queryParam("to", to)
                .when()
                .get(BASE_PATH + "/by-birthday")
                .then()
                .statusCode(REQUEST_BAD);
    }

    @Test
    void call_createUser_ok() {
        User user = new User(10L, "test@example.org", "first", "last",
                LocalDate.of(2000, 10, 1), null, null);
        Mockito.when(userService.add(any(User.class))).thenReturn(user);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(getUserRequestDto(user))
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(REQUEST_OK)
                .body("id", Matchers.equalTo(10))
                .body("email", Matchers.equalTo("test@example.org"));
    }

    @Test
    void call_createUser_emailValidation_notOk() {
        User user = new User("test", "first", "last",
                LocalDate.of(2000, 10, 1), null, null);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(getUserRequestDto(user))
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(REQUEST_BAD);
    }

    @Test
    void call_createUser_firstNameNull_notOk() {
        User user = new User("test", null, "last",
                LocalDate.of(2000, 10, 1), null, null);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(getUserRequestDto(user))
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(REQUEST_BAD);
    }

    @Test
    void call_createUser_lastNameBlank_notOk() {
        User user = new User("test", "first", " ",
                LocalDate.of(2000, 10, 1), null, null);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(getUserRequestDto(user))
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(REQUEST_BAD);
    }

    @Test
    void call_createUser_AgeValidation_notOk() {
        User user = new User("test", "first", "last",
                LocalDate.now().minusYears(ageRestriction - 1),
                null, null);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(getUserRequestDto(user))
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(REQUEST_BAD);
    }

    @Test
    void call_patchUser_ok() {
        long userId = 20;
        User user = new User(userId, "test@example.org", "first", "last",
                LocalDate.of(2000, 10, 1), null, null);
        User patchUser = new User();
        patchUser.setEmail("test@example.org.com");
        Mockito.when(userService.findById(userId)).thenReturn(user);
        Mockito.when(userService.save(any(User.class))).thenReturn(user);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(getUserRequestDto(patchUser))
                .when()
                .patch(BASE_PATH + "/" + userId)
                .then()
                .statusCode(REQUEST_OK)
                .body("id", Matchers.equalTo(20))
                .body("email", Matchers.equalTo("test@example.org.com"));
    }

    @Test
    void call_updateUser_ok() {
        long userId = 20;
        User existedUser = new User(userId, "test@example.org", "first", "last",
                LocalDate.of(2000, 10, 1), null, null);
        User updatedUser = new User(userId, "test@example.org.com",
                "first name", "last name",
                LocalDate.of(1990, 10, 1), "address", "911");
        Mockito.when(userService.findById(userId)).thenReturn(existedUser);
        Mockito.when(userService.save(any(User.class))).thenReturn(updatedUser);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(getUserRequestDto(updatedUser))
                .when()
                .put(BASE_PATH + "/" + userId)
                .then()
                .statusCode(REQUEST_OK)
                .body("id", Matchers.equalTo(20))
                .body("email", Matchers.equalTo("test@example.org.com"));
    }

    @Test
    void call_deleteUser_ok() {
        long userId = 20;
        User user = new User(userId, "test@example.org", "first", "last",
                LocalDate.of(2000, 10, 1), null, null);
        Mockito.when(userService.findById(userId)).thenReturn(user);
        RestAssuredMockMvc.given()
                .when()
                .delete(BASE_PATH + "/" + userId)
                .then()
                .statusCode(REQUEST_OK);
    }

    private UserRequestDto getUserRequestDto(User user) {
        return new UserRequestDto(user.getEmail(), user.getFirstName(), user.getLastName(),
                user.getBirthday(), user.getAddress(), user.getPhone());
    }
}