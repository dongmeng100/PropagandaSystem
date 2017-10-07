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
		
		String result = "{}"; //������
		Video video = new Video(); //�Զ���Video���ͣ����ڴ����Ƶ��Ϣ
		
		PrintWriter finnalout = resp.getWriter();
		File f = new File(this.getServletContext().getRealPath("/") + "uploadvideo");  
        f.mkdir();  
          
        DiskFileItemFactory factory = new DiskFileItemFactory() ;  
        // ��׼ȷ��˵�Ǵ���һ���ϴ�ʱ��һ����ʱ�ļ���Ҳ���ڸ�Ŀ¼��  
        //factory.setRepository(new File(this.getServletContext().getRealPath("/") + "uploadvideo")) ;        
        ServletFileUpload upload = new ServletFileUpload(factory) ;  
        // ����һ��ֻ������ϴ�20M,���������жϣ�����Ͳ����趨  
        //upload.setFileSizeMax(20*1024*1024) ;   
          
        //������Ϣȫ���ù���������һ��List��  
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
        // �����ǽ���ȫ�����ݣ��ٷŵ�һ�����������Ա������  
        Iterator<FileItem> iter = items.iterator() ;   
        FileItem videofile = null;  //�洢��Ƶ�ļ��Ķ���
		boolean isupload = true;  //�����ж��Ƿ��ϴ���  
        long videosize = 0,itemsize = 0;  //��Ƶ�ļ���С
        String fileRoot = "";//��Ƶ�ļ�·��  
        String fend = ""; //�ϴ����ļ���׺��  
        String filename = ""; //�ϴ����ļ���  
        String VideoType = ""; //��Ƶ��ʽ
        String ftime = String.valueOf(System.currentTimeMillis());//�ļ���ʱ���  
        String VideoCaption = ""; //��Ƶ����
        String UploadTime = "";   //��Ƶ�ϴ�ʱ��
        String VideoAuthor = "";  //��Ƶ��Դ
        
        try{
        while(iter.hasNext()){  
            FileItem item = iter.next() ;  
            String fieldName = item.getFieldName() ;  // ȡ�ñ��ؼ�������  
            itemsize = item.getSize();
            
            //û����д����ʱ��������  
            if(itemsize < 1024){    //��ֹ�ļ���������ڴ����
            	if(item.getString().equals("")){  
            		result = "{error:'��Ϣ��д���������ϴ�ʧ�ܣ�'}";
            		isupload=false;  
            		break;  
            	}  
            }
            if(!item.isFormField())// ������ͨ�ı�ʱ��ʱ��Ƶʱ����Ƶ�ϴ���ָ���ļ���  
            {         
                //ȡ���ϴ�����Ƶ�Ĵ�С  
            	videosize = item.getSize();
                filename = item.getName();
                VideoType = item.getContentType();
                
                String[] FileNameChild = item.getName().split("\\.");
                fend = FileNameChild[FileNameChild.length-1].toLowerCase();
                
                if(!fend.equals("webm")&&!fend.equals("mp4")&&!fend.equals("mov")&&!fend.equals("mkv")&&!fend.equals("wmv")&&!fend.equals("3gp")&&!fend.equals("flv")&&!fend.equals("ogg")){  
                	System.out.println("�ļ�ֻ��Ϊwebm��MP4��flv��mov��wmv��3gp��mkv��asf�����͵���Ƶ�ļ����Ǵ�����ļ�����������Ƶת����ת����ϴ�");
                	result = "{error:'�ļ����Ͳ�����Ҫ���ϴ�ʧ�ܣ�'}";
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
                output.write(data) ;    // �ֿ鱣��  
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
        	result = "{error:'���ݿ�����ϴ�ʧ�ܣ�'}";
        	e.printStackTrace();
        }
        catch (IOException e) {
        	result = "{error:'������IO�����ϴ�ʧ�ܣ�'}";
		    e.printStackTrace();
		}
        catch(Exception e){
        	result = "{error:'�������쳣���ϴ�ʧ�ܣ�'}";
        	e.printStackTrace();
        }
        finnalout.print(JSON.parse(result));
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
