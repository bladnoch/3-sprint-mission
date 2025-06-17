package com.sprint.mission.discodeit.mapper.advanced;

import com.sprint.mission.discodeit.dto.message.response.JpaMessageResponse;
import com.sprint.mission.discodeit.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PackageName  : com.sprint.mission.discodeit.mapper.advanced
 * FileName     : AdvancedMessageMapper
 * Author       : dounguk
 * Date         : 2025. 6. 3.
 */

@Mapper(componentModel = "spring", uses = {AdvancedUserMapper.class})
public interface AdvancedMessageMapper {

    @Mapping(source = "channel.id", target = "channelId")
    JpaMessageResponse toDto(Message message);
}
