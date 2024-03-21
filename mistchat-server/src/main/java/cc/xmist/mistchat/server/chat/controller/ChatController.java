package cc.xmist.mistchat.server.chat.controller;

import cc.xmist.mistchat.server.chat.req.MessageRequest;
import cc.xmist.mistchat.server.chat.resp.MessageResponse;
import cc.xmist.mistchat.server.chat.service.MessageService;
import cc.xmist.mistchat.server.common.constant.Constants;
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
    public R<MessageResponse.Message> sendMsg(@RequestParam Long roomId,
                                              @RequestBody MessageRequest req) {
        Long uid = RequestContext.getUid();
        MessageResponse.Message m = messageService.send(uid, roomId, req);
        return R.ok(m);
    }

    @GetMapping("/list/page")
    @Operation(summary = "消息记录")
    public R<CursorResult<MessageResponse.Message>> list(@RequestParam Long roomId,
                                                         @RequestParam(required = false) String cursor,
                                                         @RequestParam(required = false, defaultValue = Constants.CUSROR_PAGESIZE) Integer pageSize) {
        CursorResult<MessageResponse.Message> response = messageService.listMessage(roomId, cursor, pageSize);
        return R.ok(response);
    }


}

