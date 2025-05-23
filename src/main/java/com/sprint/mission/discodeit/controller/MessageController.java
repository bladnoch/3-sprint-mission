package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.Dto.message.MessageCreateRequest;
import com.sprint.mission.discodeit.Dto.message.MessageUpdateRequest;
import com.sprint.mission.discodeit.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * packageName    : com.sprint.mission.discodeit.controller
 * fileName       : MessageController
 * author         : doungukkim
 * date           : 2025. 5. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 5. 11.        doungukkim       최초 생성
 */
@Tag(name = "Message 컨트롤러", description = "스프린트 미션5 메세지 컨트롤러 엔트포인트들 입니다.")
@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @Operation(summary = "채널 메세지 목록 조회", description = "채널의 메세지 목록을 전체 조회 합니다.")
    @GetMapping
    public ResponseEntity<?> findChannelMessages(@RequestParam UUID channelId) {
        return messageService.findAllByChannelId(channelId);
    }

    @Operation(summary = "메세지 생성", description = "메세지를 생성 합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> creatMessage(
            @RequestPart("messageCreateRequest") MessageCreateRequest request,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachmentFiles
    ) {
        return messageService.createMessage(request, attachmentFiles);
    }

    @Operation(summary = "메세지 삭제", description = "메세지를 삭제 합니다.")
    @DeleteMapping(path = "/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable UUID messageId) {
        return messageService.deleteMessage(messageId);
    }

    @Operation(summary = "메세지 수정", description = "메세지를 수정 합니다.")
    @PatchMapping(path = "/{messageId}")
    public ResponseEntity<?> updateMessage(
            @PathVariable UUID messageId,
            @Valid @RequestBody MessageUpdateRequest request) {
        return messageService.updateMessage(messageId, request);
    }
}
