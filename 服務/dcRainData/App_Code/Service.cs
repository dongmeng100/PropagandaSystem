using System;
//using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Services.Protocols;
//using System.Xml.Linq;
using System.Xml;
using System.Data.SqlClient;
using System.Data;
using System.Configuration;

[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
// [System.Web.Script.Services.ScriptService]
public class Service : System.Web.Services.WebService
{
    private string strCon = ConfigurationSettings.AppSettings["SqlCon"];
    public static bool IsCanConnectioned = false;
    public Service()
    {
        //如果使用设计的组件，请取消注释以下行 
        //InitializeComponent(); 
    }

    public bool connectDB()
    {

        //创建连接对象       
        SqlConnection mySqlConnection = new SqlConnection(strCon);
        try
        {
            mySqlConnection.Open();
            IsCanConnectioned = true;
        }
        catch
        {
            IsCanConnectioned = false;
        }
        finally
        {
            mySqlConnection.Close();
        }

        if (mySqlConnection.State == ConnectionState.Closed || mySqlConnection.State == ConnectionState.Broken)
        {
            return IsCanConnectioned;
        }
        else
        {
            return IsCanConnectioned;
        }
    }
    public void excuteSQLcmd(string cmd)
    {
            string cmdUpdate = cmd;
            SqlConnection cnn = new SqlConnection(strCon);
            SqlCommand com = new SqlCommand(cmdUpdate, cnn);
            cnn.Open();
            com.ExecuteNonQuery();
            cnn.Close();
    }
    [WebMethod(Description = "从雨量数据库中返回各区过去雨量，返回XmlDocument类型数据列表")]
    public XmlDocument getXmlData(string hour)
    {
        connectDB();
        if (IsCanConnectioned == true)
        {
            //从usermessage表中获取站点号
            //string sqlUser = "SELECT StationNo FROM USERMESSAGE";
            //SqlDataAdapter sdaUser = new SqlDataAdapter(sqlUser, strCon);
            //DataSet dsUser = new DataSet();
            //sdaUser.Fill(dsUser);

            //从HNZZ里面获取1小时前的雨量值并更新到表duiJIE中

            string sqlcmdreal = string.Format("USE [dcRain_Data] DECLARE @return_value int EXEC @return_value = [dbo].[GetWarnRainBetweenTwoTime] @strTimeInternal ="+hour+" SELECT 'Return Value' = '-1'");

            
            
            //从表DuiJie中查询出所有值，以XML形式返回SELECT temp as marker,StationNo as StationNo, StationName as StationName,convert(varchar,DataTime,120) as DataTime,RainValue as RainValue,StationAddress as Address,subdistrict as StreetName FROM DuiJie
            //string sqlcmdreal = string.Format("select a.StationNo,b.StationName,b.Subdistrict,b.StationAddress,a.RWaterValue,a.DataTime,a.Voltage,a.DeviceState,a.PongingLevel from tbRWater_Realtime a ,[tbRWStationInfo] b where a.StationNo=b.StationNo");
            SqlDataAdapter sda = new SqlDataAdapter(sqlcmdreal, strCon);
            DataSet dsReal = new DataSet("items");
            sda.Fill(dsReal);

            XmlDocument doc = new XmlDocument();
            XmlDeclaration dec = doc.CreateXmlDeclaration("1.0", "GB2312", null);
            doc.AppendChild(dec);
            //创建一个根节点（一级）  
            XmlElement root = doc.CreateElement("root");
            doc.AppendChild(root);
            //创建节点（二级）  
            XmlNode items = doc.CreateElement("items");
            root.AppendChild(items);

            for (int m = 0; m < dsReal.Tables[0].Rows.Count; m++)
            {
                //创建节点（三级）  
                XmlNode item = doc.CreateElement("item");

                //创建四级节点
                XmlNode RainValue = doc.CreateElement("RainValue");
                RainValue.InnerText = dsReal.Tables[0].Rows[m][0].ToString();
                item.AppendChild(RainValue);
                XmlNode subdistrict = doc.CreateElement("subdistrict");
                subdistrict.InnerText = dsReal.Tables[0].Rows[m][1].ToString();
                item.AppendChild(subdistrict);

                items.AppendChild(item);
            }
            return doc;
        }
        else
        {
            return null;
        }

    }
}
