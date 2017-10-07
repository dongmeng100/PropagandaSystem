using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data.SqlClient;
using System.Data;

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
    public DataTable getRainValue(string strDate) {
        string[] strs = strDate.Split('$');
        String str = System.Configuration.ConfigurationSettings.AppSettings["sqlConDuke"];
        SqlConnection conn = new SqlConnection(str);
        conn.Open();//打开
        SqlCommand cmd = new SqlCommand("FldRainValue", conn);
        cmd.CommandType = CommandType.StoredProcedure;
        cmd.Parameters.Add(new SqlParameter("@strStartDatetime", SqlDbType.VarChar, 30));
        cmd.Parameters.Add(new SqlParameter("@strEndDatetime", SqlDbType.VarChar,30));
        cmd.UpdatedRowSource = UpdateRowSource.None;
        cmd.Parameters["@strStartDatetime"].Value = strs[0];
        cmd.Parameters["@strEndDatetime"].Value = strs[1]; 
        DataSet ds = new DataSet();
        SqlDataAdapter da = new SqlDataAdapter(cmd);
        da.Fill(ds, "rvDataTable");
        conn.Close();
        return ds.Tables["rvDataTable"]; 

    }
    
}