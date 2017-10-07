package com.propaganda.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.propaganda.bean.PreviewResult;
import com.propaganda.bean.initialPreviewConfig;
import com.propaganda.dao.VideoPreviewdao;
import com.propaganda.dao.VideoPreviewdaoImpl;
import com.propaganda.exception.DataBaseException;

public class VideoPreview extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7234380125643774123L;

	public VideoPreview() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		VideoPreviewdao getvideos = new VideoPreviewdaoImpl();
		ArrayList<initialPreviewConfig> videos = new ArrayList<initialPreviewConfig>();
		ArrayList<String> initialPreview = new ArrayList<String>();
		PreviewResult result = new PreviewResult();
		
		try{
			videos = getvideos.getvideo();
			for(int i=0;i<videos.size();i++){
				String url = "";
				String[] FileNameChild = videos.get(i).getCaption().split("<br>");
				String[] FileNameChilds = FileNameChild[1].split("\\.");
				String fend = FileNameChilds[FileNameChilds.length-1].toLowerCase();
				if(fend.equals("mkv")){
					videos.get(i).setFiletype("");
				}
				url = "../uploadvideo/"+videos.get(i).getKey()+"."+fend;
				initialPreview.add(url);
			}
			result.setInitialPreviewConfig(videos);
			result.setInitialPreview(initialPreview);
			
		}catch(DataBaseException e){
        	e.printStackTrace();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
		out.print(JSON.toJSON(result));
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
