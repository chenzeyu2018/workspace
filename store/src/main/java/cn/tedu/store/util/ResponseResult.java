package cn.tedu.store.util;

import java.io.Serializable;

/**
 * 控制层返回的json对象类
 * 
 * @author Tiberius E是泛型占位符 是 服务端向客户端响应数据的类型
 *
 *         服务端向客户端 响应结果的类型
 */

public class ResponseResult<E> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1626793180717240861L;
	private int state;
	private String message;
	private E data;

	public ResponseResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 表示操作成功
	public ResponseResult(int state) {
		super();
		// 如果在set方法中写了逻辑判断，若此处使用this那么set的逻辑判断则会跳过
		setState(state);
	}

	// 表示操作失败
	public ResponseResult(int state, String message) {
		this(state);
		setMessage(message);
	}

	// 表示操作失败
	public ResponseResult(int state, Exception e) {
		this(state, e.getMessage());
	}

	// 表示获取数据信息
	public ResponseResult(int state, E data) {
		this(state);
		setData(data);
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}

}
