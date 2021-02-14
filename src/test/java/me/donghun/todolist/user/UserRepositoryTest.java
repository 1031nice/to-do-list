package me.donghun.todolist.user;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // slicing test & h2 의존성이 있는 경우 h2를 test db로 사용
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @org.junit.jupiter.api.Test
    @DisplayName("유저 아이디로 유저 찾기")
    void test() {
        User user = new User();
        user.setUserId("donghun");
        user.setPassword("1234");
        userRepository.save(user);

        Optional<User> byUserId = userRepository.findByUserId(user.getUserId());
        assertThat(byUserId).isNotEmpty();
        assertThat(byUserId.get()).isEqualTo(user);
    }

}
