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
    public class ReturnData
    {
        public int resultNum;
        public string resultNumType;
        public string authority;
		public string administrator;
        public string departName;
    }
    public class Service : System.Web.Services.WebService
    {
        private string strCon = ConfigurationSettings.AppSettings["SqlCon"];
        const string KEY_64 = "VavicApp";//注意了，是8个字符，64位
        const string IV_64 = "VavicApp";

        [WebMethod(Description = "<h3>登陆密码加密</h3> <br/>单个加密，参数和返回值均为字符串")]
        public string Encode(string data)
        {
            byte[] byKey = System.Text.ASCIIEncoding.ASCII.GetBytes(KEY_64);
            byte[] byIV = System.Text.ASCIIEncoding.ASCII.GetBytes(IV_64);

            DESCryptoServiceProvider cryptoProvider = new DESCryptoServiceProvider();
            int i = cryptoProvider.KeySize;
            MemoryStream ms = new MemoryStream();
            CryptoStream cst = new CryptoStream(ms, cryptoProvider.CreateEncryptor(byKey,byIV), CryptoStreamMode.Write);
            StreamWriter sw = new StreamWriter(cst);
            sw.Write(data);
            sw.Flush();
            cst.FlushFinalBlock();
            sw.Flush();
            return Convert.ToBase64String(ms.GetBuffer(), 0, (int)ms.Length);

        }
        //        [WebMethod(Description = "登陆密码解密")]
        //        public string Decode(string data)
        //        {
        //            byte[] byKey = System.Text.ASCIIEncoding.ASCII.GetBytes(KEY_64);
        //            byte[] byIV = System.Text.ASCIIEncoding.ASCII.GetBytes(IV_64);

        //            byte[] byEnc;
        //            try
        //            {
        //                byEnc = Convert.FromBase64String(data);
        //            }
        //            catch
        //            {
        //                return null;
        //            }
        //            DESCryptoServiceProvider cryptoProvider = new DESCryptoServiceProvider();
        //            MemoryStream ms = new MemoryStream(byEnc);
        //            CryptoStream cst = new CryptoStream(ms, cryptoProvider.CreateDecryptor(byKey,
        //byIV), CryptoStreamMode.Read);
        //            StreamReader sr = new StreamReader(cst);
        //            return sr.ReadToEnd();
        //        }

        [WebMethod(Description = "<h3>登陆密码解密</h3> <br/>如果解密多个密码，请用英文逗号隔开，返回值为list集合，在flex中需要用ArrayCollection接收")]
        public List<string> Decode(string data)
        {
            string[] abc = data.Split(',');
            List<string> mList = new List<string>();
            for (int i = 0; i < abc.Length; i++)
                mList.Add(fnDecode(abc[i]));
            return mList;
        }
        public string fnDecode(string data)
        {
            byte[] byKey = System.Text.ASCIIEncoding.ASCII.GetBytes(KEY_64);
            byte[] byIV = System.Text.ASCIIEncoding.ASCII.GetBytes(IV_64);

            byte[] byEnc;
            try
            {
                byEnc = Convert.FromBase64String(data);
            }
            catch
            {
                return null;
            }
            DESCryptoServiceProvider cryptoProvider = new DESCryptoServiceProvider();
            MemoryStream ms = new MemoryStream(byEnc);
            CryptoStream cst = new CryptoStream(ms, cryptoProvider.CreateDecryptor(byKey,byIV), CryptoStreamMode.Read);
            StreamReader sr = new StreamReader(cst);
            return sr.ReadToEnd();
        }

        [WebMethod(Description = "<h3>密文登陆服务 雨量</h3> <br/> 参数1：strConn，数据库eg:mete_data <br/> 参数2：userName，登录名字符串eg:xcyl <br/> 参数3：pwd，登陆密码字符串(密文) eg:R88OvSVI9yw=<br/> 返回值：int，1代表用户名、密码正确，2代表密码不正确，3代表用户名、密码都不正确 <br/>")]
        public int userLogin(string database, string userName, string userPwd)
        {
            SqlConnection conn = new SqlConnection(strCon + database);
            conn.Open();
            //userPwd = Encode(userPwd);//明文登陆，否则为密文
            string strComm = "select count(*) from tbUsers where userName='" + userName + "' and userPwd='" + Encode(userPwd) + "'";
            SqlCommand comm = new SqlCommand(strComm, conn);
            int resultNum = (int)comm.ExecuteScalar();
            if (resultNum > 0)
            {
                resultNum = 1;
            }
            else
            {
                strComm = "select count(*) from tbUsers where userName='" + userName + "'";
                comm = new SqlCommand(strComm, conn);
                resultNum = (int)comm.ExecuteScalar();
                if (resultNum > 0)
                {
                    resultNum = 2;
                }
                else
                {
                    resultNum = 3;
                }
            }
            return resultNum;
        }

         [WebMethod(Description = "<h3>密文登陆服务 物联网DBSensorMonitorWithData</h3>")]
        public int userLogin_Sensor(string database, string userName, string userPwd)
        {
            SqlConnection conn = new SqlConnection(database);
            conn.Open();
            //userPwd = Encode(userPwd);//明文登陆，否则为密文
            string strComm = "select count(*) from tbUsers where userName='" + userName + "' and userPwd='" + userPwd + "'";
            SqlCommand comm = new SqlCommand(strComm, conn);
            int resultNum = (int)comm.ExecuteScalar();
            if (resultNum > 0)
            {
                resultNum = 1;
            }
            else
            {
                strComm = "select count(*) from tbUsers where userName='" + userName + "'";
                comm = new SqlCommand(strComm, conn);
                resultNum = (int)comm.ExecuteScalar();
                if (resultNum > 0)
                {
                    resultNum = 2;
                }
                else
                {
                    resultNum = 3;
                }
            }
            return resultNum;
        }

        [WebMethod(Description = "<h3>密文登陆服务 防汛DBFloodPrev</h3>")]
        public string userLoginOld(string database, string userName, string userPwd)
        {
            SqlConnection conn = new SqlConnection(strCon + database);
            conn.Open();
            string strComm = "select userType from tbUsers where userName='" + userName + "' and userPwd='" + Encode(userPwd) + "'";
            SqlCommand comm = new SqlCommand(strComm, conn);
            object resultNum = comm.ExecuteScalar();
            if (resultNum == null)
            {
                strComm = "select userType from tbUsers where userName='" + userName + "'";
                comm = new SqlCommand(strComm, conn);
                resultNum = comm.ExecuteScalar();
                if (resultNum == null)
                {
                    resultNum = 3;//用户名不存在
                }
                else
                {
                    resultNum = 2;//用户名存在，密码错误
                }
            }
            return resultNum.ToString();
        }

        [WebMethod(Description = "<h3>密文登陆服务 防汛SZFXXXSB</h3>")]
        public string userLoginNew(string database, string userName, string userPwd)
        {
            SqlConnection conn = new SqlConnection(strCon + database);
            conn.Open();
            string strComm = "select userType from tbUsers where LonginName='" + userName + "' and LoginPsw='" + Encode(userPwd) + "'";
            SqlCommand comm = new SqlCommand(strComm, conn);
            object resultNum = comm.ExecuteScalar();
            if (resultNum == null)
            {
                strComm = "select userType from tbUsers where LonginName='" + userName + "'";
                comm = new SqlCommand(strComm, conn);
                resultNum = comm.ExecuteScalar();
                if (resultNum == null)
                {
                    resultNum = 3;//用户名不存在
                }
                else
                {
                    resultNum = 2;//用户名存在，密码错误
                }
            }
            return resultNum.ToString();
        }
		
		        [WebMethod(Description = "<h3>密文登陆服务 防汛二期DBFlood2</h3>")]
        public string userLoginFlood2(string database, string userName, string userPwd)
        {
            SqlConnection conn = new SqlConnection(strCon + database);
			ReturnData ret_data = new ReturnData();
            conn.Open();
            string strComm = "select userType,departName from tbUsers where LonginName='" + userName + "' and LoginPsw='" + Encode(userPwd) + "'";
            SqlCommand comm = new SqlCommand(strComm, conn);
            SqlDataReader dr = comm.ExecuteReader();
            int resultNum = 0;
			if (dr.Read())
            {
                ret_data.resultNumType = dr["userType"].ToString();	
                ret_data.departName = dr["departName"].ToString();				
                dr.Close();
            }
			else
            {
                dr.Close();
                strComm = "select count(*) from tbUsers where LonginName='" + userName + "'";
                comm = new SqlCommand(strComm, conn);
                resultNum = (int)comm.ExecuteScalar();

                if (resultNum>0)
                {
                    ret_data.resultNumType = "2";
                    ret_data.departName = "";	
                }
                else
                {
                    ret_data.resultNumType = "3";
                    ret_data.departName = "";	
                }
            }
            return ret_data.resultNumType.Trim() + ";" + ret_data.departName.Trim();
        }

        [WebMethod(Description = "<h3>密文登陆服务 雨雪运维</h3>")]
        public string userLogin_rainMt(string database, string userName, string userPwd)
        {
            SqlConnection conn = new SqlConnection(strCon + database);
            conn.Open();
            string strComm = "select count(*) from tbUsers where LoginName='" + userName + "' and LoginPsw='" + Encode(userPwd) + "'";
            SqlCommand comm = new SqlCommand(strComm, conn);
            object resultNum = comm.ExecuteScalar();
            if (resultNum == null)
            {
                strComm = "select count(*) from tbUsers where LoginName='" + userName + "'";
                comm = new SqlCommand(strComm, conn);
                resultNum = comm.ExecuteScalar();
                if (resultNum == null)
                {
                    resultNum = 3;//用户名不存在
                }
                else
                {
                    resultNum = 2;//用户名存在，密码错误
                }
            }
            return resultNum.ToString();
        }

        [WebMethod(Description = "<h3>密文登陆服务 物联网startPage</h3>")]
        public string userLogin_startPage(string userName, string userPwd)
        {
			string strCon = ConfigurationSettings.AppSettings["sqlConIoT"];
            ReturnData ret_data = new ReturnData();
            SqlConnection conn = new SqlConnection(strCon);
            conn.Open();
            //userPwd = Encode(userPwd);//明文登陆，否则为密文
            string strComm = "select * from tbUsers where userName='" + userName + "' and userPwd='" + userPwd + "'";
            SqlCommand comm = new SqlCommand(strComm, conn);
            SqlDataReader dr = comm.ExecuteReader();
            int resultNum = 0;
            //DataTable dt = new DataTable();
            //SqlDataAdapter sda = new SqlDataAdapter(comm);
            //sda.Fill(dt);

            if (dr.Read())
            {
                ret_data.resultNum = 1;
                ret_data.authority = dr["authority"].ToString();
				ret_data.administrator=dr["administrator"].ToString();
                dr.Close();
            }
            else
            {
                dr.Close();
                strComm = "select count(*) from tbUsers where userName='" + userName + "'";
                comm = new SqlCommand(strComm, conn);
                resultNum = (int)comm.ExecuteScalar();
                if (resultNum > 0)
                {
                    ret_data.resultNum = 2;
                    ret_data.authority = "";
                }
                else
                {
                    ret_data.resultNum = 3;
                    ret_data.authority = "";
                }
            }
            return ret_data.resultNum + ";" + ret_data.authority.Trim()+";"+ret_data.administrator ;
			//return ret_data.resultNum + ";" + ret_data.authority.Trim();
        }
    }
}
