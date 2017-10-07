using System;
using System.Collections.Generic;

using System.Web;
using System.Web.Services;
using System.IO;

[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。 
// [System.Web.Script.Services.ScriptService]

public class Service : System.Web.Services.WebService
{
    public Service () {

        //如果使用设计的组件，请取消注释以下行 
        //InitializeComponent(); 
    }

    [WebMethod]
    public string FileService(string photo,string name)
    {
        string message;
        try
        {
           byte[] bytes = Convert.FromBase64String(photo);
           //  FileStream fs = new FileStream(Server.MapPath("./")+"imgs/"+photoname, FileMode.Create, FileAccess.Write);
           FileStream fs = new FileStream(@"c:\imgs\"+name, FileMode.Create, FileAccess.Write);
            fs.Write(bytes, 0, bytes.Length);
            fs.Flush();
            fs.Close();
            message = "上传成功！";
        }
        catch (Exception ex)
        {
            message = ex.ToString();
        }
        return message.ToString();
    }
    
}