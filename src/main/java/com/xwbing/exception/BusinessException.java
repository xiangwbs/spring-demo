package com.xwbing.exception;
/**
 * 说明:自定义错误异常
 * 项目名称: spring-demo
 * 创建日期: 2016年9月27日 下午1:25:47
 * 作者: xiangwb
 */
public class BusinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7493711492820795133L;

	public BusinessException(Throwable cause) {
	    super(cause);
	}

	public BusinessException(String message) {
	    super(message);
	}

	public BusinessException(String message, Throwable cause) {
	    super(message , cause);
	}


}
