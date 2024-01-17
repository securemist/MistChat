package cc.xmist.mistchat.server.common.exception;

import cc.xmist.mistchat.server.common.util.ErrorType;
import cc.xmist.mistchat.server.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandle {
    @ExceptionHandler(value = BusinessException.class)
    public R businessException(BusinessException e) {
        return R.commonError(e.getMessage());
    }


    @ExceptionHandler(value = NotLoginException.class)
    public R notLogin(NotLoginException e) {
        return R.error(ErrorType.NOT_LOGIN);
    }

    @ResponseBody
    public R parameterException(MethodArgumentNotValidException e) {
        return R.paramError(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage());
        return R.commonError(e.getMessage());
    }
}
