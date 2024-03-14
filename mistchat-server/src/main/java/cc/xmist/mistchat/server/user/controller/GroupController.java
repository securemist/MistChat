package cc.xmist.mistchat.server.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/group")
@Tag(name = "群组接口")
public class GroupController {


    @PostMapping("/add")
    @Operation(summary = "创建群组")
    public void create() {

    }
}
