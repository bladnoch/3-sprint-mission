package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * packageName    : com.sprint.mission.discodeit.repository.jcf
 * fileName       : JcfMessageRepository
 * author         : doungukkim
 * date           : 2025. 4. 17.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 17.        doungukkim       최초 생성
 */

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
public class JcfMessageRepository implements MessageRepository {
    public final Map<UUID, Message> data = new HashMap<>();

    // working on it
    @Override
    public Message createMessageWithAttachments(UUID userId, UUID channelId, List<UUID> attachments, String content) {
        Message message = new Message(userId, channelId, attachments, content);
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public Message createMessageWithContent(UUID senderId, UUID channelId, String content) {
        Message message = new Message(senderId, channelId, content);
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public List<Message> findMessagesByChannelId(UUID channelId) {
        return data.values().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public Message findMessageById(UUID messageId) {
        return data.get(messageId);
    }

    @Override
    public List<Message> findAllMessages() {
        return data.values().stream().toList();
    }

    @Override
    public void updateMessageById(UUID messageId, String content) {
        if (data.get(messageId) == null) {
            throw new RuntimeException("no file: JcfMessageRepository.updateMessageById");
        }
        data.get(messageId).setContent(content);
    }

    @Override
    public void deleteMessageById(UUID messageId) {
        if (!data.containsKey(messageId)) {
            throw new IllegalStateException("no message to delete");
        }
        data.remove(messageId);
    }
}
