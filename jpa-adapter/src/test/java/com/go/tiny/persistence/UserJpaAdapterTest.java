package com.go.tiny.persistence;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.ObtainUser;
import com.go.tiny.persistence.dao.UserDao;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = GoTinyJpaTestConfiguration.class)
public class UserJpaAdapterTest {
  @Autowired private UserDao userDao;

  @Autowired
  @Qualifier("test-user-adapter")
  private ObtainUser obtainUser;

  @Test
  @DisplayName("should be able to register user with the support of database")
  public void shouldBeAbleToRegisterUserWithTheSupportOfStub() {
    // Given
    User user = constructUser();
    // When
    obtainUser.register(user);
    // Then
    assertThat(obtainUser.signIn(user));
    assertThat(obtainUser.checkForUserExistence(user.getEmailId())).isTrue();
  }

  @Test
  @DisplayName("should not register unknown user with the support of database")
  public void shouldNotRegisterUnknownUserWithTheSupportOfStub() {
    // When
    obtainUser.register(null);
    // Then
    assertThat(obtainUser.checkForUserExistence(null)).isFalse();
  }

  @Test
  @DisplayName("should not signIn unknown user with the support of database")
  public void shouldNotSignINUnknownUserWithTheSupportOfStub() {
    // When
    Boolean isValidUser = obtainUser.signIn(null);
    // Then
    assertThat(isValidUser).isFalse();
  }

  private User constructUser() {
    return User.builder().name("TINY-USER").password("TINY-PWD").emailId("TINY-EMAIL").build();
  }
}
