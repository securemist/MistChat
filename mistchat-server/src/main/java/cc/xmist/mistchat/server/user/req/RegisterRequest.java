package cc.xmist.mistchat.server.user.model.req;

import cc.xmist.mistchat.server.common.enums.Gender;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private Gender gender;
}
