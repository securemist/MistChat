package cc.xmist.mistchat.server.chat.controller;

import cc.xmist.mistchat.server.chat.model.req.RoomCreateRequest;
import cc.xmist.mistchat.server.chat.service.RoomService;
import cc.xmist.mistchat.server.common.context.RequestContext;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Resource
    private RoomService roomService;

    @PostMapping("/add")
    @Operation(summary = "发起聊天")
    public void addFriendRoom(@RequestBody @Valid RoomCreateRequest request) {
    }
}
