package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.userStatus.JpaUserStatusResponse;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.advanced.AdvancedUserStatusMapper;
import com.sprint.mission.discodeit.repository.jpa.JpaUserRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

/**
 * packageName    : com.sprint.mission.discodeit.service.basic fileName       :
 * BasicUserStatusService author         : doungukkim date           : 2025. 4. 28. description    :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2025. 4. 28.        doungukkim 최초 생성
 */
@Service("basicUserStatusService")
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
  private final JpaUserRepository userRepository;
  private final AdvancedUserStatusMapper userStatusMapper;

  @Transactional
  @Override
  public JpaUserStatusResponse updateByUserId(UUID userId, Instant newLastActiveAt) {
    Objects.requireNonNull(userId, "no userId in param");
    Objects.requireNonNull(newLastActiveAt, "no userId in param");

    User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("UserStatus with userId " + userId + " not found"));
    UserStatus userStatus = user.getStatus();
    userStatus.setLastActiveAt(newLastActiveAt);

    JpaUserStatusResponse response = userStatusMapper.toDto(userStatus);
    return response;
  }

}
