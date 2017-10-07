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

/// <summary>
///STService 的摘要说明
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
//若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。 
// [System.Web.Script.Services.ScriptService]
public class STService : System.Web.Services.WebService {

    public STService () {

        //如果使用设计的组件，请取消注释以下行 
        //InitializeComponent(); 
    }
    private string strCon = ConfigurationSettings.AppSettings["SqlCon"];
    public static bool IsCanConnectioned = false;
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

    [WebMethod(Description = "获取传感器的状态，参数INTsensorType:查询类型（1:雨量传感器；2:积水传感器；3:雪量传感器；4:大型牌匾传感器；5:沿街牌匾传感器）.返回值类型:XML.")]
    public XmlDocument UploadSensorType(int INTsensorType)
    {
        DateTime currentTime=new DateTime();
        currentTime = System.DateTime.Now;
        DataSet dsUser = new DataSet();

        XmlDocument doc = new XmlDocument();
        XmlDeclaration dec = doc.CreateXmlDeclaration("1.0", "GB2312", null);
        doc.AppendChild(dec);
        //创建一个根节点（一级）  
        XmlElement root = doc.CreateElement("root");
        doc.AppendChild(root);
        //创建节点（二级）  
        XmlNode items = doc.CreateElement("items");
        root.AppendChild(items);

        switch (INTsensorType)
        {
            case 1:
                {
                    //从usermessage表中获取站点号
                    string sqlUser = "select 2,StationNo,DataTime,Voltage,DeviceState from tbRWater_Realtime";
                    SqlDataAdapter sdaUser = new SqlDataAdapter(sqlUser, strCon);

                    sdaUser.Fill(dsUser);
                }
                break;
            case 2:
                {
                    //从usermessage表中获取站点号
                    string sqlUser = "select 2,a.StationNo,a.DataTime,a.Voltage,a.DeviceState,b.StationAddress,b.StationName from tbRWater_Realtime a,tbRWStationInfo b where a.StationNo=b.StationNo";
                    SqlDataAdapter sdaUser = new SqlDataAdapter(sqlUser, strCon);

                    sdaUser.Fill(dsUser);

                    for (int m = 0; m < dsUser.Tables[0].Rows.Count; m++)
                    {
                        DateTime RdataTime = new DateTime();//最近一次接受数据的时间
                        int VOl = Convert.ToInt32(dsUser.Tables[0].Rows[m][3].ToString());
                        //创建节点（三级）  
                        XmlNode item = doc.CreateElement("item");

                        //创建四级节点
                        XmlNode sensorType = doc.CreateElement("sensorType");
                        sensorType.InnerText = dsUser.Tables[0].Rows[m][0].ToString();
                        item.AppendChild(sensorType);
                        XmlNode sensorNum = doc.CreateElement("sensorNum");
                        sensorNum.InnerText = dsUser.Tables[0].Rows[m][1].ToString();
                        item.AppendChild(sensorNum);
                        XmlNode sensorState = doc.CreateElement("sensorState");
                        sensorState.InnerText = "1";
                        RdataTime = Convert.ToDateTime(dsUser.Tables[0].Rows[m][2].ToString());
                        TimeSpan tp = currentTime.Subtract(RdataTime);
                        int tpDays = tp.Days ;
                        int tpHours = tp.Hours + 1;
                        //string test = dsUser.Tables[0].Rows[m][3].ToString();

                        if (dsUser.Tables[0].Rows[m][4].ToString() != "-1")
                        {
                            if (tpDays > 0 || tpHours > 5 || VOl < 25)
                            {
                                sensorState.InnerText = "2";//接收数据的的时间间隔不正常
                            }
                            if (tpDays > 4)
                            {
                                sensorState.InnerText = "3";//失效
                            }
                        }
                        else
                        {
                            sensorState.InnerText = "4";//处在维修状态之下（禁用）
                        }
                        item.AppendChild(sensorState);
                        XmlNode probType = doc.CreateElement("probType");
                        probType.InnerText = "0";
                        
                        if (dsUser.Tables[0].Rows[m][4].ToString() != "-1")
                        {
                            if (VOl < 25)
                            {
                                probType.InnerText = "1";
                            }
                            if (tpDays > 0 || tpHours > 5)
                            {
                                probType.InnerText = "5";//接收数据的的时间间隔不正常
                            }
                            if (tpDays > 4)
                            {
                                probType.InnerText = "3，4";//传感器损坏
                            }
                        }
                        else
                        {
                            probType.InnerText = "6";//处在维修状态之下
                        }
                        item.AppendChild(probType);
                        XmlNode stateTime = doc.CreateElement("stateTime");
                        stateTime.InnerText = System.DateTime.Now.ToString();
                        item.AppendChild(stateTime);

						XmlNode sensorAdress = doc.CreateElement("sensorAdress");
                        sensorAdress.InnerText = dsUser.Tables[0].Rows[m][5].ToString();
                        item.AppendChild(sensorAdress);

						XmlNode sensorName = doc.CreateElement("sensorName");
                        sensorName.InnerText = dsUser.Tables[0].Rows[m][6].ToString();
                        item.AppendChild(sensorName);


                        items.AppendChild(item);
                    }
                }
                break;
            case 3:
                {
                    //从usermessage表中获取站点号
                    string sqlUser = "select 2,StationNo,DataTime,Voltage,DeviceState from tbRWater_Realtime";
                    SqlDataAdapter sdaUser = new SqlDataAdapter(sqlUser, strCon);

                    sdaUser.Fill(dsUser);
                }
                break;
            case 4:
                {
                    //从usermessage表中获取站点号
                    string sqlUser = "select 2,StationNo,DataTime,Voltage,DeviceState from tbRWater_Realtime";
                    SqlDataAdapter sdaUser = new SqlDataAdapter(sqlUser, strCon);

                    sdaUser.Fill(dsUser);
                }
                break;

//积水器历史状态
			case 6:
                {
                    //从usermessage表中获取站点号
                    string sqlUser = "select 2,a.StationNo,a.DataTime,a.Voltage,b.StationAddress,b.StationName from tbRWater_Record a,tbRWStationInfo b where a.StationNo=b.StationNo";
                    SqlDataAdapter sdaUser = new SqlDataAdapter(sqlUser, strCon);

                    sdaUser.Fill(dsUser);

                    for (int m = 0; m < dsUser.Tables[0].Rows.Count; m++)
                    {
                        DateTime RdataTime = new DateTime();//最近一次接受数据的时间
                        int VOl = Convert.ToInt32(dsUser.Tables[0].Rows[m][3].ToString());
                        //创建节点（三级）  
                        XmlNode item = doc.CreateElement("item");

                        //创建四级节点
                        XmlNode sensorType = doc.CreateElement("sensorType");
                        sensorType.InnerText = dsUser.Tables[0].Rows[m][0].ToString();
                        item.AppendChild(sensorType);
                        XmlNode sensorNum = doc.CreateElement("sensorNum");
                        sensorNum.InnerText = dsUser.Tables[0].Rows[m][1].ToString();
                        item.AppendChild(sensorNum);
                        XmlNode sensorState = doc.CreateElement("sensorState");
                        sensorState.InnerText = "1";
                        RdataTime = Convert.ToDateTime(dsUser.Tables[0].Rows[m][2].ToString());
                        TimeSpan tp = currentTime.Subtract(RdataTime);
                        int tpDays = tp.Days ;
                        int tpHours = tp.Hours + 1;
                        //string test = dsUser.Tables[0].Rows[m][3].ToString();

                        if (dsUser.Tables[0].Rows[m][4].ToString() != "-1")
                        {
                            if (tpDays > 0 || tpHours > 5 || VOl < 25)
                            {
                                sensorState.InnerText = "2";//接收数据的的时间间隔不正常
                            }
                            if (tpDays > 4)
                            {
                                sensorState.InnerText = "3";//失效
                            }
                        }
                        else
                        {
                            sensorState.InnerText = "4";//处在维修状态之下（禁用）
                        }
                        item.AppendChild(sensorState);
                        
						XmlNode sensorDate = doc.CreateElement("sensorDate");
                        sensorDate.InnerText = dsUser.Tables[0].Rows[m][2].ToString();
                        item.AppendChild(sensorDate);


                        XmlNode stateTime = doc.CreateElement("stateTime");
                        stateTime.InnerText = System.DateTime.Now.ToString();
                        item.AppendChild(stateTime);

						XmlNode sensorAdress = doc.CreateElement("sensorAdress");
                        sensorAdress.InnerText = dsUser.Tables[0].Rows[m][4].ToString();
                        item.AppendChild(sensorAdress);

						XmlNode sensorName = doc.CreateElement("sensorName");
                        sensorName.InnerText = dsUser.Tables[0].Rows[m][5].ToString();
                        item.AppendChild(sensorName);

                        items.AppendChild(item);
                    }
                }
                break;
        }

        

        

        
        
        return doc;
        
    }
    
}
