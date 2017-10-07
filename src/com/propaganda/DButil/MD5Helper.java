package com.propaganda.DButil;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MD5Helper {

    //ͨ����ȡ�������������µ�MD5У���� Ȼ��� ��ȡ����MD5У���� �Ա� �Դ���֤�������������Ƿ��޸�
    public static boolean validatePassword(String origin, String inputString, String charsetname){    
        if(origin.equals(encode(inputString,charsetname).toUpperCase())){    
            return true;    
        } else{    
            return false;    
        }    
    } 

    //ͨ����ȡ����������MD5��֤�� charsetnameΪ"UTF-8"����ȽϺ�
    public static String encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestString = md.digest(resultString.getBytes(charsetname));
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(digestString);
            else
                resultString = byteArrayToHexString(digestString);
        } catch (Exception exception) {
        }
        return resultString;
    }

    //����MD5���е��߼�
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        String byteToHex = null;
        for (int i = 0; i < b.length; i++){
            byteToHex = byteToHexString(b[i]);
            resultSb.append(byteToHex);
        }
        return resultSb.toString();
    }

    //�߼���ÿһλ���� �������Ӧת���������ַ�
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    //ת���ַ��ļ��� ������Ը����Լ�ϲ�ø�һ��˳�� �ﵽ�ı����ɵ�MD5�Ľ�������� ������ ���ƿ��ܿ������˳���
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
