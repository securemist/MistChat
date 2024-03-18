package cc.xmist.mistchat.server.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageExtra implements Serializable {
    private static final long serialVersionUID = 1L;
    // 文本内容
    private String content;
    // @的 uid
    private List<Long> atUidList;
}
