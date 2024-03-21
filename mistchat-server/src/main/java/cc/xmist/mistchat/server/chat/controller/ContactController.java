package cc.xmist.mistchat.server.chat.controller;

import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.service.ContactService;
import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cc.xmist.mistchat.server.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "会话接口")
@RequestMapping("/contact")
public class ContactController {
    @Resource
    private ContactService contactService;

    @GetMapping("/list")
    @Operation(summary = "会话列表")
    public R<CursorResult<Contact> > list(String cursor, Integer pageSize) {
        Long uid = RequestContext.getUid();
        CursorResult<Contact> res = contactService.getContactList(uid,cursor,pageSize);
        return R.ok(res);
    }

    @GetMapping("/read")
    @Operation(summary = "消息已读")
    public R read(@RequestParam Long roomId, @RequestParam Long msgId) {
        Long uid = RequestContext.getUid();
        contactService.readMsg(uid, roomId, msgId);
        return R.ok();
    }
}
