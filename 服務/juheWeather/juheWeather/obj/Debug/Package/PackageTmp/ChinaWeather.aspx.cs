﻿using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml;
using System.Net;
using System.IO;

namespace juheWeather
{
    public partial class ChinaWeather : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            //string callback = Request.QueryString["callback"];
            Response.Write(getWeather());
            Response.End();
        }

        public string getWeather()
        {
            string weatherXML = GetRequestData("http://flash.weather.com.cn/wmaps/xml/china.xml");
            XmlDocument xml = new XmlDocument();
            xml.LoadXml(weatherXML);
            XmlNode root = xml.SelectSingleNode("china");
            XmlNodeList childlist = root.ChildNodes;
            string strResult = "[";
            for (int i = 0; i < childlist.Count; i++)
            {
                strResult += "{'cityname':'" + childlist[i].Attributes["cityname"].Value + "',";
                strResult += "'quName':'" + childlist[i].Attributes["quName"].Value + "',";
                strResult += "'state1':'" + childlist[i].Attributes["state1"].Value + "',";
                strResult += "'state2':'" + childlist[i].Attributes["state2"].Value + "',";
                strResult += "'stateDetailed':'" + childlist[i].Attributes["stateDetailed"].Value + "',";
                strResult += "'tem1':'" + childlist[i].Attributes["tem1"].Value + "',";
                strResult += "'tem2':'" + childlist[i].Attributes["tem2"].Value + "',";
                strResult += "'windState':'" + childlist[i].Attributes["windState"].Value + "'},";
            }
            strResult = strResult.Substring(0, strResult.Length - 1);
            return strResult + "]";
        }
        public static string GetRequestData(string sUrl)
        {
            //使用HttpWebRequest类的Create方法创建一个请求到uri的对象。
            HttpWebRequest request = (HttpWebRequest)HttpWebRequest.Create(sUrl);
            //指定请求的方式为Get方式
            request.Method = WebRequestMethods.Http.Get;
            //获取该请求所响应回来的资源，并强转为HttpWebResponse响应对象
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            //获取该响应对象的可读流
            StreamReader reader = new StreamReader(response.GetResponseStream());
            //将流文本读取完成并赋值给str
            string str = reader.ReadToEnd();
            //关闭响应
            response.Close();
            return str;
        }
    }
}