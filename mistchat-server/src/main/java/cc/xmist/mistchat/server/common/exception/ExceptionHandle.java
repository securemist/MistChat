package cc.xmist.mistchat.server.common.exception;

import cc.xmist.mistchat.server.common.util.ErrorType;
import cc.xmist.mistchat.server.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandle {
    @ExceptionHandler(value = BusinessException.class)
    public R businessException(BusinessException e) {
        if (e instanceof BlackException) {
            return R.error(ErrorType.BLACK);
        }

        if (e instanceof NotLoginException) {
            return R.error(ErrorType.NOT_LOGIN);
        }

        return R.commonError(e.getMessage());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R parameterException(MethodArgumentNotValidException e) {
        return R.paramError(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e) {
        log.error("{}", e);
        return R.commonError();
    }

    @ExceptionHandler(value = ParamException.class)
    public R paramException(ParamException e) {
        return R.paramError(e.getMessage());
    }
}
