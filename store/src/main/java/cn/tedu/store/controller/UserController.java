package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserservice;
import cn.tedu.store.util.ResponseResult;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	private final static String UPLOAD_DIR_NAME = "upload";
	/*
	 * 允许上传的文件类型
	 */
private static final List<String> FILE_CONTENT_TYPES=new ArrayList<>();
static {
	FILE_CONTENT_TYPES.add("image/jpg");
	FILE_CONTENT_TYPES.add("image/png");
	FILE_CONTENT_TYPES.add("image/jpeg");
}
	@Autowired
	private IUserservice userService;
	
	@PostMapping("/reg.do")
	public ResponseResult<Void> handleReg(User user){
		userService.reg(user);
		return new ResponseResult<Void>(SUCCESS);
	}
	
	
	@PostMapping("/login.do")
	public ResponseResult<Void> handLogin(@RequestParam("username") String username,@RequestParam("password") String password
			,HttpSession session){
		System.out.println("开始处理");
		User login = userService.login(username, password);
		session.setAttribute("uid", login.getId());
		session.setAttribute("username", login.getUsername());
		System.out.println("处理结束");
		return new ResponseResult<Void>(SUCCESS);
	}
	
	@PostMapping("/updatepassword.do")
	public ResponseResult<Void> updatePassword(
			@RequestParam("oldpassword")String oldpassword,
			@RequestParam("newPassword")String newPassword,
			HttpSession session){
		//获取用户id
		Integer id =  getUidFromSession(session);
		System.out.println("修改密码处获取的id:"+id);
		userService.updatePassword(id, oldpassword, newPassword);
		
		return new ResponseResult<Void>(SUCCESS);
	}
	
	/**
	 * 修改资料返回的用户
	 * @param session
	 * @return
	 */
	@RequestMapping("/info.do")
	public ResponseResult<User> uInfo(HttpSession session){
		User user = userService.infoFormId(getUidFromSession(session));
		return new ResponseResult<User>(SUCCESS,user);
	}
	
	/**
	 * 修改资料
	 * @param user
	 * @param session
	 * @return
	 */
	@PostMapping("/chang_info.do")
	public ResponseResult<Void> updateInfo(User user,HttpSession session){
		//获取id封装到user对象中，因为user是用户提交的数据不包含id
		user.setId(getUidFromSession(session));
		userService.updaInfo(user);
		
		return new ResponseResult<Void>(SUCCESS);
	}
	
	
	@PostMapping("/upload.do")
	public ResponseResult<String> handleUpload(MultipartFile file,HttpSession session) {
		//检查是否存在上传文件
		if (file.isEmpty()) {
			return new ResponseResult<String>(404, new Exception("请上传文件"));
		}
		System.out.println(file.getSize());
		//检查文件的类型以及大小
		if (!(file.getSize()<=10000)) {
			return new ResponseResult<String>(404, new Exception("图片过大"));
			
		}
		List<String> list = new ArrayList<String>();
		list.add("image/jpeg");
		list.add("image/png");
		list.add("image/gif");
		String conType = file.getContentType();
		if (!list.contains(conType)) {
			return new ResponseResult<String>(404, new Exception("图片类型不匹配"));
			//TODO 头像类型不匹配
		}
		System.out.println("开始接受");
		System.out.println(file);
		//确定上传路径
		String pathString = session.getServletContext().getRealPath(UPLOAD_DIR_NAME);
		System.out.println("上传路径:"+pathString);
		File panretPath = new File(pathString);
		if (!panretPath.exists()) {
			panretPath.mkdirs();
		}
		
		//确定文件名
		String contu = file.getOriginalFilename();
		String name = contu.substring(contu.lastIndexOf("."));
		String  fileName = System.currentTimeMillis()+""+(new Random().nextInt(900000)+100000)+name;
		
		//确定文件
		File dest = new File(panretPath,fileName);
		
			try {
				file.transferTo(dest);
				System.err.println("头像更新完成!");
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//获取当前用户的id
			Integer uid = getUidFromSession(session);
		/*String pathw = pathString.substring(pathString.lastIndexOf("\\")+1);*/
		String avatar = "/" + UPLOAD_DIR_NAME + "/" + fileName;
		userService.updateAvatar(uid, avatar);
		System.out.println(avatar);
//		userService.updateAvatar(getUidFromSession(session), avatar);
		ResponseResult<String> rr
		= new ResponseResult<>();
	rr.setState(SUCCESS);
	rr.setData(avatar);
	return rr;
		
	}
	
	
	
}
