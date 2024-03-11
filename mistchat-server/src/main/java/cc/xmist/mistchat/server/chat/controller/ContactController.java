package cc.xmist.mistchat.server.chat.controller;

import cc.xmist.mistchat.server.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "会话接口")
@RequestMapping("/contact")
public class ContactController {

    @GetMapping("/list")
    @Operation(summary = "回话列表")
    public R list(Long uid) {
        return null;
    }
}
