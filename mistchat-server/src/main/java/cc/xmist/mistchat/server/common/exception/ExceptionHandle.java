package cc.xmist.mistchat.server.common.exception;

import cc.xmist.mistchat.server.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public R businessException(BusinessException e) {
        return R.commonError(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R handleException(Exception e) {
        log.error(e.getMessage());
        return R.commonError(e.getMessage());
    }

}
