package cc.xmist.mistchat.server.chat.controller;

import cc.xmist.mistchat.server.chat.service.ContactService;
import cc.xmist.mistchat.server.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "会话接口")
@RequestMapping("/contact")
public class ContactController {
    @Resource
    private ContactService contactService;

    @GetMapping("/list")
    @Operation(summary = "会话列表")
    public R<cc.xmist.mistchat.server.chat.model.resp.ContactListResp> list(Long uid) {
        cc.xmist.mistchat.server.chat.model.resp.ContactListResp res = contactService.getContactList(uid);
        return R.ok(res);
    }
}
