package cn.tedu.store.service;


import cn.tedu.store.entity.User;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.UpdaInfoExcetion;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;

/**
 * 为注册、登录等功能提供方法
 * @author Tiberius
 *
 */
public interface IUserservice {

	
	//注册业务
	/**
	 * 用户注册    user用户注册信息
	 * @return   返回成功注册的用户数据
	 * @throws InsertException      插入数据异常
	 * @throws DuplicateKeyException    违反了
	 */
	public  User reg(User user) throws InsertException,DuplicateKeyException;
	
	
	
	//登录业务
	public User login(String username,String password) throws UserNotFoundException,PasswordNotMatchException;
	
	//根据id查询用户
//	public User selectId(Integer id) throws UserNotFoundException;
	
	
	/**
	 * //密码修改
	 * @param id       用户id
	 * @param username  用户名
	 * @param oldpassword   老密码
	 * @param newPassword  新密码
	 * @return
	 * @throws UserNotFoundException   
	 * @throws UpdateException
	 * @throws PasswordNotMatchException
	 */
	public void updatePassword(Integer id,String oldpassword,String newPassword) throws UserNotFoundException,
	UpdateException,PasswordNotMatchException;
	
	/**
	 * 用户资料修改
	 * @param user
	 * @param session
	 * @throws UpdaInfoExcetion
	 * @throws UserNotFoundException
	 * @throws UpdateException
	 */
	public void updaInfo(User user) throws UpdaInfoExcetion,UserNotFoundException,UpdateException;
	
	
	/**
	 * 根据id获取用户数据
	 * @param id
	 * @return
	 */
	public User infoFormId(Integer id) throws UserNotFoundException;


/**
 * 头像修改
 * @param id
 * @param avatar
 * @throws UserNotFoundException
 * @throws UpdateException
 */
	public void updateAvatar(Integer id,String avatar) throws UserNotFoundException,
	UpdateException;
}
