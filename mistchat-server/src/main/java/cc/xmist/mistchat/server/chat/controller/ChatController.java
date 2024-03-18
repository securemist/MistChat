package cc.xmist.mistchat.server.chat.controller;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.service.MessageService;
import cc.xmist.mistchat.server.chat.model.req.ChatMessageReq;
import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cc.xmist.mistchat.server.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
    private MessageService messageService;

    @PostMapping("/send")
    @Operation(summary = "发送消息")
    public R sendMsg(@RequestBody ChatMessageReq req) {
        Long uid = RequestContext.getUid();
        messageService.send(uid, req);
        return R.ok();
    }

    @GetMapping("/list/page")
    @Operation(summary = "消息记录")
    public R<CursorResult<Message>> list(@RequestParam("chatId") Long chatId,
                                         @RequestParam ChatType type,
                                         @RequestParam(required = false) String cursor,
                                         @RequestParam Long pageSize) {
        CursorResult result = messageService.list(chatId, type, cursor, pageSize);
        return R.ok(result);
    }
}

