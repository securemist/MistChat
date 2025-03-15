package cc.xmist.mistchat.server.friend.resp;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import cc.xmist.mistchat.server.common.enums.ApplyStatus;
import cc.xmist.mistchat.server.friend.entity.FriendApply;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class FriendApplyResponse {
    private List<Apply> list;

    @Getter
    @AllArgsConstructor
    public static enum Type implements BaseEnum {
        FORWARD(1),
        RECEIVED(2);

        @JsonValue
        private Integer code;
    }

    @Data
    @Builder
    public static class Apply {
        private Integer applyId;
        private Integer applyUserId;
        private ApplyStatus status;
        private String applyMsg;
        private String replyMsg;
        private LocalDateTime createdTime;
    }

    public static FriendApplyResponse build(Integer uid, List<FriendApply> applyList) {
        List<FriendApplyResponse.Apply> list = applyList.stream().map(a -> {
            FriendApplyResponse.Type type = uid == a.getUid()
                    ? FriendApplyResponse.Type.FORWARD
                    : FriendApplyResponse.Type.RECEIVED;

            return FriendApplyResponse.Apply.builder()
                    .applyId(a.getId())
                    .applyMsg(a.getApplyMsg())
                    .replyMsg(a.getReplyMsg())
                    .applyUserId(type == FriendApplyResponse.Type.FORWARD ? a.getTargetUid() : a.getUid())
                    .createdTime(a.getCreateTime())
                    .status(a.getStatus())
                    .build();
        }).collect(Collectors.toList());

        return new FriendApplyResponse(list);
    }
}
