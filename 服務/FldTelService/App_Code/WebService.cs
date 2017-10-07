using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Web;
using System.Web.Services;
using System.Xml;

/// <summary>
///WebService 的摘要说明
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
//若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。 
// [System.Web.Script.Services.ScriptService]
public class WebService : System.Web.Services.WebService {

    public WebService () {

        //如果使用设计的组件，请取消注释以下行 
        //InitializeComponent(); 
    }

    //添加用户
    [WebMethod]
    public int insertContactInfo(string ConnectionString, string strSql)
    {
        return SQLOperate(ConnectionString, strSql);
    }

    //删除用户
    [WebMethod]
    public int alterContactInfo(string ConnectionString, string strSql)
    {
        return SQLOperate(ConnectionString, strSql);
    }

    //修改用户
    [WebMethod]
    public int deleteContactInfo(string ConnectionString, string strSql)
    {
        return SQLOperate(ConnectionString, strSql);
    }
    /// <summary>
    /// 数据库操作
    /// </summary>
    /// <param name="ConnectionString"></param>
    /// <param name="strSql"></param>
    /// <returns></returns>
    public int SQLOperate(string ConnectionString, string strSql)
    {
        SqlConnection conn = new SqlConnection();
        conn.ConnectionString = ConnectionString;
        conn.Open();
        SqlCommand strCmd = new SqlCommand(strSql, conn);
        int result = strCmd.ExecuteNonQuery();
        conn.Close();
        strCmd.Dispose();
        return result;
    }
    //查询用户
    [WebMethod]
    public XmlDocument GetList(string ConnectionString, string strSql)
    {
        try
        {
            DataSet ds = SelectName(ConnectionString, strSql);
            XmlDocument xml = new XmlDocument();
            xml.LoadXml(ds.GetXml());
            return xml;
        }
        catch (Exception ex)
        {
            XmlDocument xml = new XmlDocument();
            xml.LoadXml("<Error>" + ex.Message + "</Error>");
            return xml;
        }
    }
    [WebMethod]
    public DataSet SelectName(string ConnectionString, string strSql)
    {
        SqlConnection conn = new SqlConnection();
        conn.ConnectionString = ConnectionString;
        conn.Open();
        SqlDataAdapter da = new SqlDataAdapter(strSql, conn);
        DataSet dt = new DataSet();
        da.Fill(dt);
        return dt;
    }
    
}
