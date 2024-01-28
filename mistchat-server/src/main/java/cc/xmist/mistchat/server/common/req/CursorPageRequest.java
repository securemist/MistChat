package cc.xmist.mistchat.server.common.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;

@Schema(description = "游标分页请求")
public class CursorPageRequest {
    private Integer size;
    private String cursor;

    public Page plusPage() {
        return new Page(1, size, false);
    }

    public Boolean isFirstPage() {
        return StringUtils.isEmpty(cursor);
    }
}
