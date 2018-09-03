package com.lee.berries.result;
/**
 * 返回结果状态码
 * @author yzw
 *
 */
public enum ResultCodeEnum {
	
	SUCCESS("成功", 0, "操作成功!"),
	FAIL("失败", -1, "操作失败!"),
	NO_LOGIN("未登录", -2, "您还没有登录!"),
	PROHIBIT("无权限", -3, "您无权限操作此模块!"),
	MAINTENANCE("系统维护", -9, "系统正在维护!");
	
	private int code;
	private String name;
	private String message;

	private ResultCodeEnum(String name, int code) {
		this(name, code, "");
	}
	
	private ResultCodeEnum(String name, int code, String message) {
		this.name = name;
		this.code = code;
		this.message = message;
	}
	
	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
