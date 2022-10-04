package site.archive.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.archive.domain.user.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserProfileImageService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUserProfileImage(Long userId, String profileImageUri) {
        userRepository.updateUserProfileImage(userId, profileImageUri);
    }

}
