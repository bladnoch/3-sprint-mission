package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.Dto.binaryContent.BinaryContentCreateResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * packageName    : com.sprint.mission.discodeit.service.jcf
 * fileName       : BinaryContentService
 * author         : doungukkim
 * date           : 2025. 4. 28.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 28.        doungukkim       최초 생성
 */
public interface BinaryContentService {

    BinaryContentCreateResponse create(String fileName, Long size, String contentType, byte[] bytes, String extension);

    ResponseEntity<?> find(UUID binaryContentId);

    ResponseEntity<?> findAllByIdIn(List<UUID> binaryContentIds);

    void delete(UUID attachmentId);
}
