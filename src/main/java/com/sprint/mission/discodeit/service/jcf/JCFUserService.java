package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * packageName    : com.sprint.mission.discodeit.service.jcf
 * fileName       : JCFUserService
 * author         : doungukkim
 * date           : 2025. 4. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 3.        doungukkim       최초 생성
 */

public class JCFUserService implements UserService {

    private final List<User> data;
    private final ChannelService channelService;

    public JCFUserService(ChannelService channelService) {
        this.data = new ArrayList<>();
        this.channelService = channelService;
    }

    // 등록
    @Override
    public UUID registerUser(String username) {
        User newUser = new User(username);
        data.add(newUser);
        return newUser.getId();
    }

    // 단건 조회
    @Override
    public User findUserById(UUID userId) {
        for (User user : data) {
            try {
                if (user.getId().equals(userId)) {
                    return user;
                }
            } catch (Exception e) {
                System.out.println("찾는 유저 없음");
            }
        }
        return null;
    }

    // 다건 조회
    @Override
    public List<User> findAllUsers() {
        return data;
    }

    // 수정
    @Override
    public void updateUsername(UUID userId, String newName) {

        for (User user : data) {
            if (user.getId().equals(userId)) {
                user.setUsername(newName);
                user.setUpdatedAt(System.currentTimeMillis());
            }
        }
    }

//    삭제
    @Override
    public void deleteUser(UUID userId) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(userId)) {
                data.remove(i);
                break;
            }
        }
    }

    @Override
    public void addChannel(UUID userId, UUID channelId) {
        for (User user : data) {
            if (user.getId().equals(userId)) {
                if(user.getChannelIds()!=null){
                    List<UUID> channelIds = user.getChannelIds();
                    // 새 메세지 추가
                    channelIds.add(channelId);
                    user.setChannelIds(channelIds);
                }
            }
        }
    }

    @Override
    public List<UUID> findChannelIdsById(UUID userId) {
        for (User user : data) {
            if (user.getId().equals(userId)) {
                List<UUID> channelIds = user.getChannelIds();
                return channelIds;
            }
        }
        return new ArrayList<>();
    }

}
