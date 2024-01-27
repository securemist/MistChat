package cc.xmist.mistchat.server.user.model.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.awt.print.Book;

@Data
public class ApplyHandleRequest {
    private Long applyId;
    @NotNull
    private Boolean pass;
    private String msg;
}
