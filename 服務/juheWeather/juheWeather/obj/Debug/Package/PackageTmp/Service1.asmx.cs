using System;
using System.Collections.Generic;
using System.Web.Services;
using System.Net;
using System.IO;
using System.Text;

namespace juheWeather
{
    /// <summary>
    /// Service1 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class Service1 : System.Web.Services.WebService
    {

        [WebMethod]
        public string airPM(String cityName)
        {
            if (cityName == null || cityName == "")
                cityName = "北京";
            return GetRequestData("http://web.juhe.cn:8080/environment/air/cityair?key=afea7ab795a03cdcc246f7ca4946fcb8&city=" + cityName);
        }
        [WebMethod]
        public string getWeather(String cityName)
        {
            if (cityName == null || cityName == "")
                cityName = "北京";
            return GetRequestData("http://v.juhe.cn/weather/index?key=33446d33cec7a268bdf222fb367df718&dtype=json&cityname=" + cityName);
        }
        [WebMethod]
        public string getWeather1()
        {
            return GetRequestData("http://www.weather.com.cn/data/cityinfo/101010100.html");
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

        [WebMethod(Description = "test WebClient")]
        public string test0(String url)
        {
            return GetRequestData(url);
        }
        [WebMethod(Description = "test WebClient")]
        public string test1(String url)
        {

            Stream stream = new WebClient().OpenRead(url);
            StreamReader sreader = new StreamReader(stream);
            string str = sreader.ReadToEnd();
            return str;
        }
        [WebMethod(Description = "test WebClient")]
        public string test2(String url)
        {
            WebRequest webRequest = WebRequest.Create(url);
            webRequest.Credentials = CredentialCache.DefaultCredentials;
            WebResponse webResponse = webRequest.GetResponse();
            Stream stream = webResponse.GetResponseStream();
            StreamReader sreader = new StreamReader(stream);
            string str = sreader.ReadToEnd();
            return str;
        }
        
    }
}