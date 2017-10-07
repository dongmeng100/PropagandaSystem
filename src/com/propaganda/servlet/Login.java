package com.propaganda.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.propaganda.bean.LoginResult;
import com.propaganda.bean.User;
import com.propaganda.exception.DataBaseException;
import com.propaganda.exception.UserNotFoundException;
import com.propaganda.service.SystemManagerImpl;
import com.propaganda.service.SystemMangager;
import com.propaganda.DButil.MD5Helper;

import java.security.MessageDigest;
import java.util.HashMap;

public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6935962592010152298L;

	/**
	 * Constructor of the object.
	 */
	public Login() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req,resp);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();//获取响应接口,获取PrintWriter流，用来在客户端输出
		
		String userName = req.getParameter("UserName");
		String passWord = req.getParameter("Password");
		
		String inputtext = "data="+passWord;
		URL url = new URL("http://localhost/ws/Service.asmx/Encode");//你要登陆网站的登陆界面的地址. 
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); //利用HttpURLConnection对象从网络中获取网页数据
		con.setDoOutput(true); // POST方式 
		con.setRequestMethod("POST"); 
		OutputStream os = con.getOutputStream(); // 输出流，写数据 
		os.write(inputtext.getBytes());//提交用户名和密码.当然你要知道这个网站用户名和密码的变量名称向里面传值 
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream())); // 读取结果 
		String line,result=""; 
		LoginResult res = new LoginResult();
		while ((line = reader.readLine()) != null) { 
			result += line;//输出返回信息,这是一个字符传 你需要分析字符串来判断是否登陆成功.
		} 
		//out.print(result);
		String s1 = result.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">", "");
		String s2 = s1.replace("</string>","");
		//System.out.println(s2);
		
		//String[] childsddr = req.getRemoteAddr().split("\\.");
		/*String addr = "";
		for(int i=0;i<childsddr.length;i++){
			addr+=childsddr[i];
		}*/
		//System.out.println(Integer.parseInt("0"+addr));
		
		
		// encode with md5 algorithm
        s2 = MD5Helper.encode(s2, "UTF-8").toUpperCase();
        
        System.out.println(s2);
		
        
        
		try {
			SystemMangager systemManager = new SystemManagerImpl();
			User user = systemManager.login(userName, s2);
			
			//把用户的信息放到session中存起来
			HttpSession session = req.getSession();//用户浏览器的会话对象
			session.setAttribute("user", user.getName()); //setAttribute：设置属性
			session.setAttribute("username", user.getApart());
			
			if(user.getName().equals("admin")){
				//如果是管理员登陆，则跳到管理员相应界面
				//response.sendRedirect("index.html");
				//System.out.println("管理员登录");
				//out.print("管理员登录");
				/*result.setMessage("管理员登录");
				result.setStatusCode(1);
				result.setNextUrl(nexturl + "index.html");
				result.setUser(user.getName());*/
			}
			else{
				//如果是普通用户则跳转到登录成功页面
				//response.sendRedirect("index.html");
				//System.out.println("普通用户登录");
				//out.print("普通用户登录");
				/*result.setMessage("普通用户登录");
				result.setStatusCode(2);
				result.setNextUrl(nexturl + "index.html");
				result.setUser(user.getName());*/
			}
			
			res.setSuccess(true);
			res.setMessage("登陆成功");
			res.setStateCode("1");
			res.setAuthority(user.getAuthority());
		}
		catch (UserNotFoundException e) {             
			if ("500".equals(e.getMessage())) {   //获取异常消息字符串
				res.setSuccess(false);
				res.setMessage("密码错误");
				res.setStateCode("3");
			} 
			else {
				res.setSuccess(false);
				res.setMessage("用户不存在");
				res.setStateCode("4");
				
				//System.out.println("用户不存在");
			}
			
			//如果出现错误，则返回到登陆页面，并将用户输入的错误信息带回
			//request.setAttribute("message", message);
			//request.setAttribute("userName", userName);
			//request.setAttribute("passWord", passWord);
			//response.sendRedirect("Login.html");
			//request.getRequestDispatcher("Login.html").forward(request, response);
		} 
		catch (DataBaseException e) {
			e.printStackTrace();   //在命令行打印异常信息在程序中出错的位置及原因
			if("ORA-1013".equals(e.getMessage())){  //数据库连接超时
				res.setSuccess(false);
				res.setMessage("数据库异常");
				res.setStateCode("5");
			}
			else{
				res.setSuccess(false);
				res.setMessage("数据库查询失败");
				res.setStateCode("5");
			}
			
			//System.out.println(e.getMessage());
			//response.sendRedirect("Login.html");
			//request.getRequestDispatcher("HomePage.html").forward(request,response);
		} 
		catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage("服务器异常");
			res.setStateCode("6");
		}
		finally{
			out.write(JSON.toJSON(res).toString());
			/*	状态码                   描述
			 *   1		登录成功
			 * 	 
			 * 	 3 		密码错误
			 * 	 4		用户不存在
			 * 	 5		数据库异常
			 * 	 6 		服务器异常
			*/
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
