using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Web;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.Data.SqlClient;
using System.Security.Cryptography;
using System.IO;
using System.Collections.Generic;
using System.Configuration;

namespace ws
{
    /// <summary>
    /// Service1 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    //[System.Web.Script.Services.ScriptService]
    public class Service : System.Web.Services.WebService
    {
        private string strCon = ConfigurationSettings.AppSettings["SqlCon"];
        const string KEY_64 = "VavicApp";//注意了，是8个字符，64位
        const string IV_64 = "VavicApp";

        [WebMethod(Description = "<h3>getIP</h3>")]
        public string getIP()
        {
            string IpAddress = (HttpContext.Current.Request.ServerVariables["HTTP_X_FORWARDED_FOR"] != null
            && HttpContext.Current.Request.ServerVariables["HTTP_X_FORWARDED_FOR"] != String.Empty)
            ? HttpContext.Current.Request.ServerVariables["HTTP_X_FORWARDED_FOR"]
            : HttpContext.Current.Request.ServerVariables["REMOTE_ADDR"];
            return IpAddress;
        }
    }
}
