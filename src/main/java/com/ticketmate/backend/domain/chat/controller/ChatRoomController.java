package com.ticketmate.backend.domain.chat.controller;

import com.ticketmate.backend.domain.applicationform.domain.dto.response.ApplicationFormFilteredResponse;
import com.ticketmate.backend.domain.chat.domain.dto.request.ChatMessageFilteredRequest;
import com.ticketmate.backend.domain.chat.domain.dto.request.ChatRoomFilteredRequest;
import com.ticketmate.backend.domain.chat.domain.dto.response.ChatMessageResponse;
import com.ticketmate.backend.domain.chat.domain.dto.response.ChatRoomListResponse;
import com.ticketmate.backend.domain.chat.service.ChatRoomService;
import com.ticketmate.backend.domain.auth.domain.dto.CustomOAuth2User;
import com.ticketmate.backend.global.aop.log.LogMonitoringInvocation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-room")
@Tag(
    name = "채팅방 관련 API",
    description = "1:1 채팅방 관련 API 제공"
)
public class ChatRoomController implements ChatRoomControllerDocs {

  private final ChatRoomService chatRoomService;

  @Override
  @GetMapping("")
  @LogMonitoringInvocation
  public ResponseEntity<Page<ChatRoomListResponse>> getChatRoomList(
      @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
      @ParameterObject @Valid ChatRoomFilteredRequest request) {
    return ResponseEntity.ok(chatRoomService.getChatRoomList(customOAuth2User.getMember(), request));
  }

  @Override
  @GetMapping("/{chat-room-id}")
  @LogMonitoringInvocation
  public ResponseEntity<Slice<ChatMessageResponse>> enterChatRoom(
      @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
      @PathVariable("chat-room-id") String chatRoomId,
      @ParameterObject @Valid ChatMessageFilteredRequest request) {
    return ResponseEntity.ok(chatRoomService.getChatMessage(customOAuth2User.getMember(), chatRoomId, request));
  }

  @Override
  @GetMapping("/{chat-room-id}/application-form")
  @LogMonitoringInvocation
  public ResponseEntity<ApplicationFormFilteredResponse> chatRoomApplicationFormInfo(
      @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
      @PathVariable("chat-room-id") String chatRoomId) {
    return ResponseEntity.ok(chatRoomService.getChatRoomApplicationFormInfo(customOAuth2User.getMember(), chatRoomId));
  }
}
