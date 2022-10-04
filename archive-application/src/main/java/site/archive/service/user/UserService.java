package site.archive.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.common.exception.common.ResourceNotFoundException;
import site.archive.domain.user.UserRepository;
import site.archive.dto.v1.user.BaseUserDto;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public BaseUserDto findUserById(long userId) {
        return userRepository.findById(userId)
                             .map(BaseUserDto::from)
                             .orElseThrow(() -> new ResourceNotFoundException("아이디에 해당하는 유저가 존재하지 않습니다."));
    }

    public boolean existsEmail(String email) {
        return userRepository.findByMailAddress(email).isPresent();
    }

    @Transactional
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

}
