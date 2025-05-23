package com.sprint.mission.discodeit.Dto.readStatus;

import java.time.Instant;
import java.util.UUID;

/**
 * packageName    : com.sprint.mission.discodeit.Dto.readStatus
 * fileName       : ReadStatusCreateResponse
 * author         : doungukkim
 * date           : 2025. 4. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 29.        doungukkim       최초 생성
 */
public record ReadStatusCreateResponse(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        UUID userId,
        UUID channelId,
        Instant lastReadAt

) {
}
