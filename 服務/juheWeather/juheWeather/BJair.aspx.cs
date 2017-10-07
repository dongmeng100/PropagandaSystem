using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Net;
using System.IO;

namespace juheWeather
{
    public partial class BJair : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Response.Write(GetRequestData("http://web.juhe.cn:8080/environment/air/cityair?key=afea7ab795a03cdcc246f7ca4946fcb8&dtype=json&city=%E5%8C%97%E4%BA%AC"));
            Response.End();
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