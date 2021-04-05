package me.donghun.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public User processSignUp(SignUpForm signUpForm) {
        Optional<User> byUsername = userRepository.findByUsername(signUpForm.getUsername());
        if(byUsername.isPresent())
            return null;
        User user = modelMapper.map(signUpForm, User.class);
        return userRepository.save(user);
    }

}
