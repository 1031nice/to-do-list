package me.donghun.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(SignUpForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) o;
        Optional<User> byUsername = userService.findByUsername(signUpForm.getUsername());
        if (byUsername.isPresent())
            errors.rejectValue("username", "invalid.username", new Object[]{signUpForm.getUsername()}, "이미 사용중인 아이디입니다.");
    }
}
