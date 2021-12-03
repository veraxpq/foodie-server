package foodie.wrapper;

import foodie.exceptions.PermissionDenyException;
import foodie.util.ExceptionUtil;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerErrorException;

import javax.validation.ConstraintViolationException;
import java.util.IllegalFormatException;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LogManager.getLogger(ControllerExceptionHandler.class.getName());

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException iae) {
        logger.debug(ExceptionUtil.getStackTrace(iae));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(iae.getMessage()), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleIllegalStateException(IllegalStateException ise) {
        logger.debug(ExceptionUtil.getStackTrace(ise));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(ise.getMessage()), headers, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = UnrecognizedPropertyException.class)
    @ResponseBody()
    public ResponseEntity<ErrorMessage> handleUnrecognizedPropertyException(UnrecognizedPropertyException upe) {
        logger.debug(ExceptionUtil.getStackTrace(upe));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("参数未知"), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalFormatException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleIIllegalFormatException(IllegalFormatException ife) {
        logger.debug(ExceptionUtil.getStackTrace(ife));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("参数格式错误"), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException nre) {
        logger.debug(ExceptionUtil.getStackTrace(nre));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("参数格式错误"), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException cve) {
        logger.debug(ExceptionUtil.getStackTrace(cve));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("参数值非法"), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PermissionDenyException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlePermissionDenyException(PermissionDenyException ex) {
        logger.debug(ExceptionUtil.getStackTrace(ex));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(ex.getMessage()), headers, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = ServerErrorException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleServerErrorException(ServerErrorException see) {
        logger.error(ExceptionUtil.getStackTrace(see));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(see.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException rte) {
        logger.error(ExceptionUtil.getStackTrace(rte));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("服务器内部错误，请重试或联系管理员"), headers,  HttpStatus.INTERNAL_SERVER_ERROR);
    }
}