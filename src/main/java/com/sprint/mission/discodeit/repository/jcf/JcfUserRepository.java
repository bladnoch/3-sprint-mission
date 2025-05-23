package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * packageName    : com.sprint.mission.discodeit.repository.jcf fileName       : JcfUserRepository2
 * author         : doungukkim date           : 2025. 4. 17. description    :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2025. 4. 17.        doungukkim 최초 생성
 */

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
public class JcfUserRepository implements UserRepository {

    Map<UUID, User> data = new HashMap<>();

    public User createUserByName(String username, String email, String password) {
        User user = new User(username, email, password);
        data.put(user.getId(), user);
        return user;
    }

    public User createUserByName(String username, String email, String password, UUID profileId) {
        User user = new User(username, email, password, profileId);
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public void updateProfileIdById(UUID userId, UUID profileId) {
        if (data.get(userId) == null) {
            throw new IllegalStateException("no user in repository");
        }
        User user = data.get(userId);
        user.setProfileId(profileId);
    }

    public User findUserById(UUID userId) {
        if (data.get(userId) == null) {
            return null;
        }
        return data.get(userId);
    }

    public List<User> findAllUsers() {
        return data.values().stream().toList();
    }

    public void updateNameById(UUID userId, String name) {
        if (data.get(userId) == null) {
            throw new RuntimeException("파일 없음: JcfUserRepository.updateUserById");
        }
        data.get(userId).setUsername(name);
    }

    public void updateEmailById(UUID userId, String email) {
        if (data.get(userId) == null) {
            throw new RuntimeException("파일 없음: JcfUserRepository.updateEmailById");
        }
        data.get(userId).setEmail(email);
    }

    public void updatePasswordById(UUID userId, String password) {
        if (data.get(userId) == null) {
            throw new RuntimeException("파일 없음: JcfUserRepository.updateEmailById");
        }
        data.get(userId).setPassword(password);
    }

    public void deleteUserById(UUID userId) {
        if (!data.containsKey(userId)) {
            throw new IllegalStateException("no user to delete by given userId");
        }
        data.remove(userId);
    }

    @Override
    public boolean isUniqueUsername(String username) {
        List<User> list = data.values().stream().toList();
        if (list.isEmpty()) {
            return true;
        }
        for (User user : list) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isUniqueEmail(String email) {
        List<User> list = data.values().stream().toList();
        if (list.isEmpty()) {
            return true;
        }
        for (User user : list) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

//        userRepository.hasSameName -> 모든 유저에서 이름이 같은 계정이 있는지 확인
//        userRepository.hasSameEmail -> 모든 유저에서 이메일이 같은 계정이 있는지 확인
//        or stream 으로 둘 다 같이 확인하고 하나라도 있으면 return false

    @Override
    public boolean hasSameName(String name) {
        if (data.isEmpty()) return false;
        for (User user : data.values()) {
            if (user.getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasSameEmail(String email) {
        if (data.isEmpty()) return false;
        for (User user : data.values()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
