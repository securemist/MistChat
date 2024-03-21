package cc.xmist.mistchat.server.chat.controller;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.req.ChatMessageRequest;
import cc.xmist.mistchat.server.chat.service.MessageService;
import cc.xmist.mistchat.server.common.context.RequestContext;
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
    public R<Long> sendMsg(@RequestParam Long roomId,
                     @RequestBody ChatMessageRequest req) {
        Long uid = RequestContext.getUid();
        Long msgId = messageService.send(uid, roomId, req);
        return R.ok(msgId);
    }

    @GetMapping("/list/page")
    @Operation(summary = "消息记录")
    public R<CursorResult<Message>> list(@RequestParam Long roomId,
                                         @RequestParam(required = false) String cursor,
                                         @RequestParam Integer pageSize) {
        CursorResult result = messageService.lilistMessage(roomId,cursor, pageSize);
        return R.ok(result);
    }


}

