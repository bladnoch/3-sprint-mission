package com.sprint.mission.discodeit.v1.repository.file;

import com.sprint.mission.discodeit.v1.entity.User1;
import com.sprint.mission.discodeit.v1.repository.UserRepository1;
import com.sprint.mission.discodeit.util.FilePathUtil;
import com.sprint.mission.discodeit.util.FileSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * packageName    : com.sprint.mission.discodeit.repository.file
 * fileName       : FileUserRepository
 * author         : doungukkim
 * date           : 2025. 4. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 15.        doungukkim       최초 생성
 */
public class FileUserRepository1 implements UserRepository1 {
    FilePathUtil filePathUtil;
    FileSerializer fileSerializer;

    public FileUserRepository1(FilePathUtil filePathUtil, FileSerializer fileSerializer) {
        this.filePathUtil = filePathUtil;
        this.fileSerializer = fileSerializer;
    }

    @Override
    public UUID saveUser(String username) {
        User1 user = new User1(username);
        try (
                FileOutputStream fos = new FileOutputStream(filePathUtil.getUserFilePath(user.getId()).toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return user.getId();
    }
    @Override
    public User1 findUserById(UUID userId) {
        Path path = filePathUtil.getUserFilePath(userId);

        if (Files.exists(path)) {
            return fileSerializer.readFile(path, User1.class);
        }
        return null;
    }
    @Override
    public List<User1> findAllUsers() {
        Path userDirectory = filePathUtil.getUserDirectory();
        if (Files.exists(userDirectory)) {
            try {
                List<User1> list = Files.list(userDirectory)
                        .filter(path -> path.toString().endsWith(".ser"))
                        .map(path -> {
                            try (
                                    FileInputStream fis = new FileInputStream(path.toFile());
                                    ObjectInputStream ois = new ObjectInputStream(fis)
                            ) {
                                Object data = ois.readObject();
                                return (User1) data;
                            } catch (IOException | ClassNotFoundException exception) {
                                throw new RuntimeException(exception);
                            }
                        })
                        .toList();

                return list;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }
    @Override
    public void updateUsernameByIdAndName(UUID userId, String newName) {
        Path path = filePathUtil.getUserFilePath(userId);


        // 파일 확인
        if (Files.exists(path)) {
            // 역직렬화
            User1 user = fileSerializer.readFile(path, User1.class);

            // 수정
            user.setUsername(newName);

            // 직렬화
            fileSerializer.writeFile(path, user);

        }
    }
    @Override
    public void deleteUserById(UUID userId) {
        Path path = filePathUtil.getUserFilePath(userId);
        // 파일 확인
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addChannelInUserByIdAndChannelId(UUID userId, UUID channelId) {
        Path path = filePathUtil.getUserFilePath(userId);

        if (Files.exists(path)) {
            User1 user = fileSerializer.readFile(path, User1.class);

            List<UUID> channelIds = user.getChannelIds();
            channelIds.add(channelId);
            user.setChannelIds(channelIds);

            fileSerializer.writeFile(path, user);
        }
    }
    @Override
    public List<UUID> findChannelIdsInId(UUID userId) {
        Path path = filePathUtil.getUserFilePath(userId);

        if (!Files.exists(path)) {
            return null;
        }
        return fileSerializer.readFile(path, User1.class).getChannelIds();

    }
    @Override
    public void deleteChannelIdInUser(UUID channelId, UUID userId) {
        Path path = filePathUtil.getUserFilePath(userId);

        User1 user = fileSerializer.readFile(path, User1.class);
        user.getChannelIds().removeIf(id -> id.equals(channelId));
        fileSerializer.writeFile(path, user);
    }
}
