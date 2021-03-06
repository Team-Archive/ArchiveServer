package site.archive.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.domain.user.entity.BaseUser;
import site.archive.domain.user.entity.PasswordUser;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordUserRepository passwordUserRepository;

    public boolean existsEmail(String email) {
        return userRepository.findByMailAddress(email).isPresent();
    }

    @Transactional
    public void deleteUser(long userId) {
        BaseUser user = userRepository.getById(userId);
        userRepository.delete(user);
    }

    public boolean isTemporaryPasswordLogin(long userId) {
        return passwordUserRepository.findById(userId)
                                     .map(PasswordUser::isCurrentTemporaryPassword)
                                     .orElse(false);
    }

}
