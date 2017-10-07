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

import com.alibaba.fastjson.JSON;
import com.propaganda.bean.Video;
import com.propaganda.dao.VideoUploaddao;
import com.propaganda.dao.VideoUploaddaoImpl;
import com.propaganda.exception.DataBaseException;

public class VideoUpload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2638226127186997729L;

	public VideoUpload() {
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
		
		String result = "{}"; //输出结果
		Video video = new Video(); //自定义Video类型，用于存放视频信息
		
		PrintWriter finnalout = resp.getWriter();
		File f = new File(this.getServletContext().getRealPath("/") + "uploadvideo");  
        f.mkdir();  
          
        DiskFileItemFactory factory = new DiskFileItemFactory() ;  
        // 更准确的说是创建一个上传时的一个临时文件，也放在该目录下  
        //factory.setRepository(new File(this.getServletContext().getRealPath("/") + "uploadvideo")) ;        
        ServletFileUpload upload = new ServletFileUpload(factory) ;  
        // 设置一次只能最大上传20M,但下面有判断，这里就不再设定  
        //upload.setFileSizeMax(20*1024*1024) ;   
          
        //将表单信息全部拿过来，放在一个List中  
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
        // 以上是接收全部内容，再放到一个迭代器中以便于输出  
        Iterator<FileItem> iter = items.iterator() ;   
        FileItem videofile = null;  //存储视频文件的对象
		boolean isupload = true;  //参数判断是否上传了  
        long videosize = 0,itemsize = 0;  //视频文件大小
        String fileRoot = "";//视频文件路径  
        String fend = ""; //上传的文件后缀名  
        String filename = ""; //上传的文件名  
        String VideoType = ""; //视频格式
        String ftime = String.valueOf(System.currentTimeMillis());//文件的时间戳  
        String VideoCaption = ""; //视频标题
        String UploadTime = "";   //视频上传时间
        String VideoAuthor = "";  //视频来源
        
        try{
        while(iter.hasNext()){  
            FileItem item = iter.next() ;  
            String fieldName = item.getFieldName() ;  // 取得表单控件的名称  
            itemsize = item.getSize();
            
            //没有填写完整时弹出警告  
            if(itemsize < 1024){    //防止文件过大造成内存溢出
            	if(item.getString().equals("")){  
            		result = "{error:'信息填写不完整，上传失败！'}";
            		isupload=false;  
            		break;  
            	}  
            }
            if(!item.isFormField())// 不是普通文本时，时视频时将视频上传到指定文件夹  
            {         
                //取得上传的视频的大小  
            	videosize = item.getSize();
                filename = item.getName();
                VideoType = item.getContentType();
                
                String[] FileNameChild = item.getName().split("\\.");
                fend = FileNameChild[FileNameChild.length-1].toLowerCase();
                
                if(!fend.equals("webm")&&!fend.equals("mp4")&&!fend.equals("mov")&&!fend.equals("mkv")&&!fend.equals("wmv")&&!fend.equals("3gp")&&!fend.equals("flv")&&!fend.equals("ogg")){  
                	System.out.println("文件只能为webm、MP4、flv、mov、wmv、3gp、mkv、asf等类型的视频文件，非此类别文件可以下载视频转码器转码后上传");
                	result = "{error:'文件类型不符合要求，上传失败！'}";
                    isupload=false;  
                    break;  
                }  
                else{  
                	videofile = item;    
                }  
            }   
            else   
            {  
            	
                 String value= new String(item.get(),"utf-8");; 
                 switch(fieldName){
                 	case "title": VideoCaption = value;break;
                 	case "time": UploadTime = value;break;
                 	case "author": VideoAuthor = value;break;
                 	
                 }
            }  
        }   
        if(isupload){
        	InputStream input = null ;  
            OutputStream output = null; 
            VideoUploaddao pushtoDB = new VideoUploaddaoImpl();
            
            input = videofile.getInputStream() ;  
             
            fileRoot=this.getServletContext().getRealPath("/")+"uploadvideo"+File.separator+ftime+"."+fend;  
           
            output = new FileOutputStream(new File(fileRoot));  
            @SuppressWarnings("unused")
			int temp = 0 ;  
            byte data[] = new byte[512] ;  
            while((temp=input.read(data,0,512))!=-1){  
                output.write(data) ;    // 分块保存  
            }  
            input.close() ;  
            output.close() ;
            
            video.setVideoKey(ftime);
            video.setVideoType(VideoType);
            video.setVideoCaption(VideoCaption);
            video.setUploadTime(UploadTime);
            video.setVideoAuthor(VideoAuthor);
            video.setVideoName(filename);
            video.setVideoSize(videosize);
            
            pushtoDB.upload(video);
        }
        
        }
        catch(DataBaseException e){
        	result = "{error:'数据库错误，上传失败！'}";
        	e.printStackTrace();
        }
        catch (IOException e) {
        	result = "{error:'服务器IO错误，上传失败！'}";
		    e.printStackTrace();
		}
        catch(Exception e){
        	result = "{error:'服务器异常，上传失败！'}";
        	e.printStackTrace();
        }
        finnalout.print(JSON.parse(result));
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
