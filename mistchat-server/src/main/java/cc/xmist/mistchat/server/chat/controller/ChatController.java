package cc.xmist.mistchat.server.chat.controller;

import cc.xmist.mistchat.server.chat.model.req.ChatMessageReq;
import cc.xmist.mistchat.server.chat.service.ChatService;
import cc.xmist.mistchat.server.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author securemist
 * @since 2024-01-26
 */
@RestController
@Tag(name = "聊天接口")
@RequestMapping("/message")
public class ChatController {
    @Resource
    private ChatService chatService;

    @PostMapping("/send")
    @Operation(summary = "发送消息")
    public R sendMsg(@RequestBody ChatMessageReq req, Long uid) {
        chatService.sendMsg(uid, req);
        return R.ok();
    }
}

