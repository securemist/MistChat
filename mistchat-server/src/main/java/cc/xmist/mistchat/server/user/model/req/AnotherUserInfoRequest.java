package cc.xmist.mistchat.server.user.model.req;

import lombok.Data;

import java.util.List;

@Data
public class AnotherUserInfoRequest {
    private List<Long> uidList;
}
