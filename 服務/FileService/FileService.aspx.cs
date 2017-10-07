using System;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;

public partial class _Default : System.Web.UI.Page 
{
    string uploadFolder = "upload"; // 上传文件夹
    protected void Page_Load(object sender, EventArgs e)
    {
        HttpFileCollection files = Request.Files;

        if (files.Count == 0)
        {
            Response.Write("请勿直接访问本文件");
            Response.End();
        }

        string path = Server.MapPath(uploadFolder);

        // 只取第 1 个文件
        HttpPostedFile file = files[0];

        if (file != null && file.ContentLength > 0)
        {
            // flash 会自动发送文件名到 Request.Form["fileName"]
            string savePath = path + "/" + Request.Form["fileName"];
            file.SaveAs(savePath);
        }
    }
}
