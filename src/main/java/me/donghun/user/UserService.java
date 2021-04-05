package me.donghun.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public User processSignUp(SignUpForm signUpForm) {
        User user = modelMapper.map(signUpForm, User.class);
        return userRepository.save(user);
    }

}
