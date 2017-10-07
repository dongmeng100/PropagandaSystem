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
		
		PrintWriter out = resp.getWriter();//��ȡ��Ӧ�ӿ�,��ȡPrintWriter���������ڿͻ������
		
		String userName = req.getParameter("UserName");
		String passWord = req.getParameter("Password");
		
		String inputtext = "data="+passWord;
		URL url = new URL("http://localhost/ws/Service.asmx/Encode");//��Ҫ��½��վ�ĵ�½����ĵ�ַ. 
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); //����HttpURLConnection����������л�ȡ��ҳ����
		con.setDoOutput(true); // POST��ʽ 
		con.setRequestMethod("POST"); 
		OutputStream os = con.getOutputStream(); // �������д���� 
		os.write(inputtext.getBytes());//�ύ�û���������.��Ȼ��Ҫ֪�������վ�û���������ı������������洫ֵ 
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream())); // ��ȡ��� 
		String line,result=""; 
		LoginResult res = new LoginResult();
		while ((line = reader.readLine()) != null) { 
			result += line;//���������Ϣ,����һ���ַ��� ����Ҫ�����ַ������ж��Ƿ��½�ɹ�.
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
			
			//���û�����Ϣ�ŵ�session�д�����
			HttpSession session = req.getSession();//�û�������ĻỰ����
			session.setAttribute("user", user.getName()); //setAttribute����������
			session.setAttribute("username", user.getApart());
			
			if(user.getName().equals("admin")){
				//����ǹ���Ա��½������������Ա��Ӧ����
				//response.sendRedirect("index.html");
				//System.out.println("����Ա��¼");
				//out.print("����Ա��¼");
				/*result.setMessage("����Ա��¼");
				result.setStatusCode(1);
				result.setNextUrl(nexturl + "index.html");
				result.setUser(user.getName());*/
			}
			else{
				//�������ͨ�û�����ת����¼�ɹ�ҳ��
				//response.sendRedirect("index.html");
				//System.out.println("��ͨ�û���¼");
				//out.print("��ͨ�û���¼");
				/*result.setMessage("��ͨ�û���¼");
				result.setStatusCode(2);
				result.setNextUrl(nexturl + "index.html");
				result.setUser(user.getName());*/
			}
			
			res.setSuccess(true);
			res.setMessage("��½�ɹ�");
			res.setStateCode("1");
			res.setAuthority(user.getAuthority());
		}
		catch (UserNotFoundException e) {             
			if ("500".equals(e.getMessage())) {   //��ȡ�쳣��Ϣ�ַ���
				res.setSuccess(false);
				res.setMessage("�������");
				res.setStateCode("3");
			} 
			else {
				res.setSuccess(false);
				res.setMessage("�û�������");
				res.setStateCode("4");
				
				//System.out.println("�û�������");
			}
			
			//������ִ����򷵻ص���½ҳ�棬�����û�����Ĵ�����Ϣ����
			//request.setAttribute("message", message);
			//request.setAttribute("userName", userName);
			//request.setAttribute("passWord", passWord);
			//response.sendRedirect("Login.html");
			//request.getRequestDispatcher("Login.html").forward(request, response);
		} 
		catch (DataBaseException e) {
			e.printStackTrace();   //�������д�ӡ�쳣��Ϣ�ڳ����г����λ�ü�ԭ��
			if("ORA-1013".equals(e.getMessage())){  //���ݿ����ӳ�ʱ
				res.setSuccess(false);
				res.setMessage("���ݿ��쳣");
				res.setStateCode("5");
			}
			else{
				res.setSuccess(false);
				res.setMessage("���ݿ��ѯʧ��");
				res.setStateCode("5");
			}
			
			//System.out.println(e.getMessage());
			//response.sendRedirect("Login.html");
			//request.getRequestDispatcher("HomePage.html").forward(request,response);
		} 
		catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage("�������쳣");
			res.setStateCode("6");
		}
		finally{
			out.write(JSON.toJSON(res).toString());
			/*	״̬��                   ����
			 *   1		��¼�ɹ�
			 * 	 
			 * 	 3 		�������
			 * 	 4		�û�������
			 * 	 5		���ݿ��쳣
			 * 	 6 		�������쳣
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
