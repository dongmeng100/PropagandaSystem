package com.propaganda.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.propaganda.dao.CarouselUpdatedao;
import com.propaganda.dao.CarouselUpdatedaoImpl;
import com.propaganda.exception.DataBaseException;

public class Carousel extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5494495182509056412L;

	public Carousel() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter finnalout = resp.getWriter();
		File f = new File(this.getServletContext().getRealPath("/") + "Image");  
        f.mkdir(); 
        
        DiskFileItemFactory factory = new DiskFileItemFactory() ;
        ServletFileUpload upload = new ServletFileUpload(factory) ; 
        List<FileItem> items = null;  
        
        try {  
            items = upload.parseRequest(req);
        } 
        catch (FileUploadException e) {  
            e.printStackTrace();  
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        Iterator<FileItem> iter = items.iterator();
        String title="",content="",id="";
        FileItem imagefile = null;
        boolean isupload = true;
        String fend="";
        String result = "";
        
        try{
        	while(iter.hasNext()){
        		FileItem item = iter.next();
        		String fieldName = item.getFieldName();
                long itemsize = item.getSize();
                
                if(itemsize < 1024){    //防止文件过大造成内存溢出
                	if(item.getString().equals("")){  
                		result = "{\"success\":false,\"message\":\"信息填写不完整，上传失败！\"}";
                		isupload=false;  
                		break;  
                	}  
                }
                
                if(!item.isFormField()){
                	String[] FileNameChild = item.getName().split("\\.");
                    fend = FileNameChild[FileNameChild.length-1].toLowerCase();
                    if(!fend.equals("jpg")&&!fend.equals("jpeg")&&!fend.equals("png")&&!fend.equals("gif")&&!fend.equals("bmp")&&!fend.equals("tiff")){
                    	result = "{\"success\":false,\"message\":\"图片类型错误，只接受jpg、jpeg、png、gif、bmp、tiff等格式图片，上传失败！\"}";
                    	isupload=false;
                    	break; 
                    }
                    else{
                    	imagefile = item;
                    }
                }
                
                else   
                {  
                     String value= new String(item.get(),"utf-8");; 
                     switch(fieldName){
                     	case "title": title = value;break;
                     	case "content": content = value;break;
                     	case "id": id = value;break;
                     	
                     }
                }      
        	}
        	
        	if(isupload){
        		InputStream input = null ;  
                OutputStream output = null;
                
                input = imagefile.getInputStream();
                String filefoot = this.getServletContext().getRealPath("/")+"Image"+File.separator+id+"."+fend;
                output = new FileOutputStream(new File(filefoot));  
                @SuppressWarnings("unused")
    			int temp = 0 ;  
                byte data[] = new byte[512] ;  
                while((temp=input.read(data,0,512))!=-1){  
                    output.write(data) ;    // 分块保存  
                }  
                input.close();  
                output.close();
                
                CarouselUpdatedao obj = new CarouselUpdatedaoImpl();
                obj.update(id, title, content, (id+"."+ fend));
                result = "{\"success\":true,\"message\":\"上传成功！\"}";
        	}
        }
        catch(DataBaseException e){
        	result = "{\"success\":false,\"message\":\"数据库错误，上传失败！\"}";
        	e.printStackTrace();
        }
        catch (IOException e) {
        	result = "{\"success\":false,\"message\":\"服务器IO错误，上传失败！\"}";
		    e.printStackTrace();
		}
        catch(Exception e){
        	result = "{\"success\":false,\"message\":\"服务器异常，上传失败！\"}";
        	e.printStackTrace();
        }
        
        finnalout.print(result);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
