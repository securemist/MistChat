package cc.xmist.mistchat.server.chat.model.req;

import cc.xmist.mistchat.server.chat.model.enums.RoomType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoomCreateRequest {
    @Schema(description = "聊天室类型")
    private RoomType type;
    @Schema(description = "成员列表")
    @NotNull
    private List<Long> uidList;
}
