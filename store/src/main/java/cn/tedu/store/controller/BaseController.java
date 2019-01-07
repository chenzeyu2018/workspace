package cn.tedu.store.controller;
/**
 * 控制层
 * 当前项目中所有的控制器的基类
 * 统一处理异常，提供某些公共资源
 * @author Tiberius
 *
 */

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.FileEmptyException;
import cn.tedu.store.service.exception.FileSizeOutOfLimitException;
import cn.tedu.store.service.exception.FileTypeNotSupportException;
import cn.tedu.store.service.exception.FileUploadException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.SeviceException;
import cn.tedu.store.service.exception.UpdaInfoExcetion;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;
import cn.tedu.store.util.ResponseResult;

public abstract class BaseController {
	/**
	 * SUCCESS 正确的响应码
	 */
	public static final Integer SUCCESS = 200;

	// 对已知异常的处理
	@ExceptionHandler(SeviceException.class)
	@ResponseBody
	// Void表示 占位用的 不能进行初始化，不能被继承
	public ResponseResult<Void> hanleException(Exception e) {
//		String message = e.getMessage();
		int state =200;
		if (e instanceof DuplicateKeyException) {
			// 400-违反了Unique约束异常
			state=400;
		} else if (e instanceof InsertException) {
			// 500-插入数据异常
			state=500;
		} else if (e instanceof UserNotFoundException) {
			// 401-用户名错误
			state=401;
		} else if (e instanceof PasswordNotMatchException) {
			// 402-用户密码错误
			state=402;
		} else if (e instanceof UpdateException) {
			state=501;
		}else if (e instanceof UpdaInfoExcetion) {
			state = 405;
		}else if(e instanceof FileEmptyException) {
			state=600;
		}else if(e instanceof FileSizeOutOfLimitException) {
			state=601;
		}else if(e instanceof FileTypeNotSupportException) {
			state=602;
		}else if(e instanceof FileUploadException) {
			state=603;
		}
		return new ResponseResult<Void>(state,e);

	}
	
	
	
	protected Integer getUidFromSession(HttpSession session) {
	return Integer.valueOf(session.getAttribute("uid").toString());	
	}
}
