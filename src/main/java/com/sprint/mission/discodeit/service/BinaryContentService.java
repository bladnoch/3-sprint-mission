package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binaryContent.JpaBinaryContentResponse;

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

    JpaBinaryContentResponse find(UUID binaryContentId);

    List<JpaBinaryContentResponse> findAllByIdIn(List<UUID> binaryContentIds);
}
