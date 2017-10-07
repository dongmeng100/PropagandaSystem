using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Services;
using System.Net;
using System.IO;
using System.Xml;

namespace juheWeather
{
    /// <summary>
    /// bjWeather 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class bjWeather : System.Web.Services.WebService
    {

        [WebMethod]
        public string getWeather()
        {
            string weatherXML=GetRequestData("http://flash.weather.com.cn/wmaps/xml/beijing.xml");
            XmlDocument xml = new XmlDocument();
            xml.LoadXml(weatherXML);
            XmlNode root = xml.SelectSingleNode("beijing");
            XmlNodeList childlist = root.ChildNodes;
            string strResult = "[";
            for (int i = 0; i < childlist.Count; i++)
            {
                strResult += "{'cityname':'" + childlist[i].Attributes["cityname"].Value+"',";
                strResult += "'stateDetailed':'" + childlist[i].Attributes["stateDetailed"].Value + "',";
                strResult += "'tem1':'" + childlist[i].Attributes["tem1"].Value + "',";
                strResult += "'tem2':'" + childlist[i].Attributes["tem2"].Value + "',";
                strResult += "'temNow':'" + childlist[i].Attributes["temNow"].Value + "',";
                strResult += "'windState':'" + childlist[i].Attributes["windState"].Value + "',";
                strResult += "'windDir':'" + childlist[i].Attributes["windDir"].Value + "',";
                strResult += "'windPower':'" + childlist[i].Attributes["windPower"].Value + "',";
                strResult += "'humidity':'" + childlist[i].Attributes["humidity"].Value + "',";
                strResult += "'time':'" + childlist[i].Attributes["time"].Value + "',";
                strResult += "'url':'" + childlist[i].Attributes["url"].Value + "'},";
            }
            strResult=strResult.Substring(0, strResult.Length - 1);
            return strResult+"]";
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
