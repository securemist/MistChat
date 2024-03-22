package cc.xmist.mistchat.server.chat.entity;

import cc.xmist.mistchat.server.chat.message.extra.SystemMsgExtra;
import cc.xmist.mistchat.server.chat.message.extra.TextMsgExtra;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageExtra implements Serializable {
    private static final long serialVersionUID = 1L;
    // @的 uid
    private List<Integer> atUidList;

    private TextMsgExtra textMsgExtra;

    private SystemMsgExtra sysMsgExtra;
}
