package cc.xmist.mistchat.server.user.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UidListRequest {
    @Schema(name = "用户 id 列表")
    private List<Integer> uidList;
}
