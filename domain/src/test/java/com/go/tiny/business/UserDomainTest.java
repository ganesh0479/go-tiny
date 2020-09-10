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

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.USER_RIGHT_SIDE_PORT_UNAVAILABLE;
import static java.lang.Boolean.TRUE;
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
    User user = constructUser();
    lenient().doNothing().when(obtainUser).register(user);
    UserDomain userDomain = new UserDomain(obtainUser);
    userDomain.register(user);
    verify(obtainUser).register(user);
  }

  @Test
  @DisplayName("should be able to signIn user with the support of stub")
  void shouldBeAbleToLoginUserWithTheSupportOfStub() {
    User user = constructUser();
    lenient().when(obtainUser.signIn(user)).thenReturn(TRUE);
    UserDomain userDomain = new UserDomain(obtainUser);
    Boolean signedIn = userDomain.signIn(user);
    assertTrue(signedIn);
    verify(obtainUser).signIn(user);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available when registering user with the support of stub")
  void
      shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhenRegisteringUserWithTheSupportOfStub() {
    User user = constructUser();
    UserDomain userDomain = new UserDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> userDomain.register(user),
        USER_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while user sign In with the support of stub")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileUserSignInWithTheSupportOfStub() {
    User user = constructUser();
    UserDomain userDomain = new UserDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> userDomain.signIn(user),
        USER_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  private User constructUser() {
    return new User("TINY USER", "user@tiny.com", "tinypwd@1", LocalDate.of(2020, 10, 9));
  }
}
