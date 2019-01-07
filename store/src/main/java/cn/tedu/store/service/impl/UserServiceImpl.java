package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserservice;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.UpdaInfoExcetion;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;

/*
 * 是IUserservice 接口的实现类
 * 
 */
@Service
public class UserServiceImpl implements IUserservice {

	@Autowired
	private UserMapper userMapper;
	
	
	@Override
	/**
	 * 用户注册
	 */
	public User reg(User user) throws InsertException, DuplicateKeyException {
		//根据尝试注册的用户名查询用户数据
		User findByUsername = findByUsername(user.getUsername());
		//判断查到的数据是否为null
		if (findByUsername ==null) {
			//进行密码加密
			String srcPassword = user.getPassword();
			String salt =  UUID.randomUUID().toString().toUpperCase();
			String mad5Password = getMd5password(srcPassword, salt).toUpperCase();
			System.out.println(mad5Password);
			user.setSalt(salt);
			user.setPassword(mad5Password);
			//补充用户未提交的数据
			
			Date date = new Date();
			user.setIsDelete(0);
			user.setCreatedUser(user.getUsername());
			user.setCreatedTime(date);
			user.setModifiedUser(user.getUsername());
			user.setModifiedTime(date);
			
			//是，用户名不存在，执行注册，返回注册对象
			inserUser(user);
			return findByUsername;
		}else {
			//否，不允许注册，则抛出异常DuplicateKeyException
			throw new DuplicateKeyException("注册失败！尝试注册的用户名：'"+user.getUsername()+"'已经被占用");
		}
		
	}
	
	/**
	 * 根据UUID获取的到加密后密码
	 * @param password   用户密码
	 * @param salt       UUID生成的盐值
	 * @return   返回一个32位的字符串
	 */
	public String  getMd5password(String password,String salt) {
		String md5Password =salt+password+salt;
		for (int i = 0; i <15; i++) {
			md5Password = DigestUtils.md5DigestAsHex(md5Password.getBytes()).toUpperCase();
		}
		return md5Password;
	}
	
	

	@Override
	public User login(String username, String password) {
		//根据登录名字查询user数据
		System.out.println("收到数据"+username+password);
		User findByUsername = findByUsername(username);
		//结果为null，提示错误
		if (findByUsername == null) {
			throw new UserNotFoundException("用户名不存在");
		}
			String salt = findByUsername.getSalt();
			String md5password = getMd5password(password, salt);
			if(findByUsername.getPassword().equals(md5password)) {
				if (findByUsername.getIsDelete() ==0) {
					findByUsername.setPassword(null);
					System.out.println(findByUsername.getUsername()+"登录成功");
					return findByUsername;
				} else {
					throw new UserNotFoundException("用户已经被删除");
				}
			}else {
				throw new PasswordNotMatchException("登录失败，密码错误！");
			}
			
		
		
	}

	

	@Override
	public void updatePassword(Integer id, String oldpassword, String newPassword)
			throws UserNotFoundException, UpdateException, PasswordNotMatchException {
		User selectId = selectFormId(id);
		if (selectId == null) {
			throw new UserNotFoundException("修改密码失败！你尝试访问的用户数据不存在");
		}
		
		if (selectId.getIsDelete() == 1) {
			throw new UserNotFoundException("修改密码失败！你尝试访问的用户已注销");
		}
		
		String salt = selectId.getSalt();
		String oldmd5password=getMd5password(oldpassword, salt);
		if (selectId.getPassword().equals(oldmd5password)) {
			String newmd5password = getMd5password(newPassword, salt);
			Date modifiedTime = new Date();
			updatePassword(id, newmd5password, selectId.getUsername(), modifiedTime);
		} else {
			throw new PasswordNotMatchException("修改密码失败！原密码错误");
		}
		
	
	}

	
	@Override
	public void updaInfo(User user) throws UpdaInfoExcetion, UserNotFoundException {
		//根据user获取id
		int id = user.getId();
		
		//根据id获取该id原有数据
		User selectFormId = selectFormId(id);
		//为null，抛UserNotFoundException
		if (selectFormId == null) {
			throw new UserNotFoundException("该用户不存在");
		} 
			//不为null
			//判断is_delete是否为1
			if (selectFormId.getIsDelete() ==1) {
				//是，抛UserNotFoundException
				throw new UserNotFoundException("该用户已被注销");
			} 
				//不是，
				//封装参数modified_user and modified_time到user中
				user.setModifiedUser(selectFormId.getUsername());
				Date modifiedTime = new Date();
				user.setModifiedTime(modifiedTime);
				UpdaInfoExcetion(user);
	}
	
	
	

	@Override
	public User infoFormId(Integer id) throws UserNotFoundException {
		User selectFormId = selectFormId(id);
		if (selectFormId == null) {
			throw new UserNotFoundException("该用户不存在");
		}
		
		if (selectFormId.getIsDelete() ==1) {
			//是，抛UserNotFoundException
			throw new UserNotFoundException("该用户已被注销");
		} 
		selectFormId.setPassword(null);
		selectFormId.setSalt(null);
		selectFormId.setIsDelete(null);
		return selectFormId;
	}
	
	
	
	@Override
	public void updateAvatar(Integer id, String avatar)
			throws UserNotFoundException, UpdateException {
		//根据用户id获取用户
		User selectFromId = userMapper.selectFromId(id);
		//判断用户是否存在
		if (selectFromId == null) {
			throw new UserNotFoundException("头像修改失败，用户不存在");
		}
		//用户已经被注销
		if (selectFromId.getIsDelete() == 1) {
			throw new UserNotFoundException("头像修改失败，用户已经注销");
		}
		//获取操作用户名
		String modifiedUser  =  selectFromId.getUsername();
		//获取操作时间
		Date modifiedTime = new Date();
		//修改用户头像地址
		updateAva(id, avatar, modifiedUser, modifiedTime);
		
	}

	/**
	 * 根据用户id修改用户头像
	 * @param id   用户id
	 * @param avatar  用户头像路径
	 * @throws UserNotFoundException  用户没有找到
	 * @throws UpdateException    插入异常
	 */
	private void updateAva(Integer id, String avatar ,String modifiedUser,Date modifiedTime)
			throws UserNotFoundException, UpdateException {
		Integer updateAvatar = userMapper.updateAvatar(id, avatar, modifiedUser, modifiedTime);
		if (updateAvatar !=1) {
			throw new UpdateException("头像修改失败，发生未知错误！");
		}
	}
	

	/**
	 *根据用户名查找用户是否存在
	 * @param username
	 * @return   存在返回user用户数据，否则返回null
	 */
	private User findByUsername(String username) {
		return  userMapper.findByUsername(username);
	}
	
	//与持久层UserMapper中的方法相似，需为私有的
	/**插入用户数据
	 * @author UID-ECD
	 * @param user
	 */
	private void inserUser(User user) {
		Integer inserUser = userMapper.inserUser(user);
		if(inserUser != 1) {
			throw new InsertException("增加用户数据时出现未知错误");
		}
	}
	
	
	/**
	 * 
	 * @param id            用户id
	 * @param password      新密码
	 * @param modifiedUser  修改用户
	 * @param modifiedTime   修改时间 
	 */
	private void updatePassword(Integer id,String password,String modifiedUser,Date  modifiedTime) {
		Integer updatePassword = userMapper.updatePassword(password, modifiedUser, modifiedTime, id);
		if(updatePassword  != 1) {
			throw new UpdateException("失败，更新密码时发生未知错误");
		}
		
	}
	
	/**
	 * 根据用户id查询用户数据
	 * @param id
	 * @return
	 */
	private User selectFormId(Integer id) {	 
		return userMapper.selectFromId(id);
	}
	
	
	/**
	 * 更新资料的私有方法
	 * @param user
	 */
	private void UpdaInfoExcetion(User user) {
		Integer updataInfo = userMapper.updataInfo(user);
		if (updataInfo != 1 ) {
			throw new UpdaInfoExcetion("修改资料失败！发生未知错误!");
		} 
	}

	
	
}
