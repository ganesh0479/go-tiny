package com.go.tiny.business;

import com.go.tiny.business.domain.UserDomain;
import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.User;
import com.go.tiny.business.port.ObtainUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.INVALID_USER;
import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.USER_RIGHT_SIDE_PORT_UNAVAILABLE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserDomainTest {

  @Mock private ObtainUser obtainUser;

  @Test
  @DisplayName("should be able to register user with the support of stub")
  void shouldBeAbleToRegisterUserWithTheSupportOfStub() {
    // Given
    User user = constructUser();
    lenient().when(obtainUser.register(user)).thenReturn(true);
    UserDomain userDomain = new UserDomain(obtainUser);
    // When
    Boolean signUpStatus = userDomain.register(user);
    // Then
    assertThat(signUpStatus).isTrue();
    verify(obtainUser).register(user);
  }

  @Test
  @DisplayName("should be able to signIn user with the support of stub")
  void shouldBeAbleToLoginUserWithTheSupportOfStub() {
    // Given
    User user = constructUser();
    lenient().when(obtainUser.signIn(user)).thenReturn(TRUE);
    UserDomain userDomain = new UserDomain(obtainUser);
    // When
    Boolean signedIn = userDomain.signIn(user);
    // Then
    assertTrue(signedIn);
    verify(obtainUser).signIn(user);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available when registering user with the support of stub")
  void
      shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhenRegisteringUserWithTheSupportOfStub() {
    // Given
    User user = constructUser();
    UserDomain userDomain = new UserDomain(null);
    // When and Then
    assertThrows(
        GoTinyDomainException.class,
        () -> userDomain.register(user),
        USER_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while user sign In with the support of stub")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileUserSignInWithTheSupportOfStub() {
    // Given
    User user = constructUser();
    UserDomain userDomain = new UserDomain(null);
    // When and Then
    assertThrows(
        GoTinyDomainException.class,
        () -> userDomain.signIn(user),
        USER_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName(
      "should throw an exception if user not available when registering user with the support of stub")
  void shouldThrowAnExceptionIfUserNotAvailableWhenRegisteringUserWithTheSupportOfStub() {
    // Given
    User user = constructUser();
    UserDomain userDomain = new UserDomain(obtainUser);
    // When
    lenient().when(obtainUser.checkForUserExistence(user.getEmailId())).thenReturn(TRUE);
    // Then
    assertThrows(GoTinyDomainException.class, () -> userDomain.register(user), INVALID_USER);
  }

  private User constructUser() {
    return User.builder()
        .name("TINY USER")
        .emailId("user@tiny.com")
        .password("tinypwd@1")
        .createdOn(LocalDate.of(2020, 10, 9))
        .build();
  }
}
