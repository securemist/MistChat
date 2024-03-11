package cc.xmist.mistchat.server.user.model.req;

import lombok.Data;

import java.util.List;

@Data
public class AnotherUserInfoReq {
    private List<Long> uidList;
}
