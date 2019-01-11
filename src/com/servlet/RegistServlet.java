package com.servlet;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.services.Login;
import com.services.Regist;
import com.tools.EmployeeNumberChecker;
import com.tools.FilterManage;
import com.tools.SearchZHandToken;
import com.tools.tomd5;
import com.tools.tounicode;

import net.sf.json.JSONObject;

public class RegistServlet extends HttpServlet {
	String isError;//isError �жϳ�����Ϣ
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain;charset=utf-8");
        
        System.out.println("���ӳɹ�����");// �����Ƿ�ɹ�����
        StringBuffer json2 = new StringBuffer();// �ַ���
        String line = null;
        BufferedReader reader = request.getReader();// ��ȡ��
        while ((line = reader.readLine()) != null) {
            json2.append(line);// ���ܵ���JSON��ʽ
        }

        System.out.println(json2);//�õ�����JSON��ʽ
        // �ѵõ����ַ�����װΪJSON���ٻ�ȡ����Ĵ������ļ�ֵ��
       
        JSONObject jsonObject = JSONObject.fromObject(json2.toString());
        
        String EmployeeNumber = jsonObject.getString("zhanghu");//��ȡJson��ֵ��
        String Password = tomd5.tomd5(jsonObject.getString("mima"));//����MD5����   MD5����
        String Name = tounicode.decodeUnicode(jsonObject.getString("Name"));  //Unicode����
        String Sex = jsonObject.getString("Sex");
        String PhoneNumber = jsonObject.getString("PhoneNumber");
        String Email = jsonObject.getString("Email");
        String Minstry =tounicode.decodeUnicode( jsonObject.getString("Ministry"));
     
        FilterManage check1 = new FilterManage();
        check1.addChecker(new EmployeeNumberChecker());
		if((check1.doChain_check(EmployeeNumber)))
		{					       
      
        String Position = Regist.selectPosition(EmployeeNumber);//ͨ��Ա���Ų�ְλ      
        System.out.println(Position);
        
        if(Position.trim().toString().equals("���޴���"))//allinfo����û������ˣ������޷�ע���� "status":"0"
        {
        	response.setContentType("text/html;charset=utf-8");
        	PrintWriter out = response.getWriter();       
        	String  info_json  = "{\"status\":\"-3\"}";//
        	System.out.println(info_json);
        
        	out.write(info_json);
        	out.flush();
        	out.close();
        }
        else if(Position.trim().toString().equals("ִ��SQL��������"))
        {
        	response.setContentType("text/html;charset=utf-8");
        	PrintWriter out = response.getWriter(); 
            String  info_json  = "{\"status\":\"-1\"}";//��װjson��ʽ���ַ���������
            System.out.println(info_json);
            out.write(info_json);
            out.flush();
            out.close();     
        }
        else {
        	//����ע��  "status":"0" ���ظ��ͻ��˽���
        //	isError �жϳ�����Ϣ			
				isError = Regist.Regist(EmployeeNumber,Password,Name,Sex,PhoneNumber,Email,Minstry,Position);
		
			//����Regist�෽��  ע��        	
            if(isError.trim().toString().equals("ע��ɹ�")) 
            {
        	System.out.println("д��ɹ�");
        	
        	PrintWriter out = response.getWriter();     
            String  info_json  = "{\"status\":\"0\"}";
            System.out.println(info_json);
            out.write(info_json);
            out.flush();
            out.close();
        	 }
          
            
        	 else 
//        		 if(isError.trim().toString().equals("��Ա������ע�ᣡ"))
             {
             	response.setContentType("text/html;charset=utf-8");
             	PrintWriter out = response.getWriter();
//               ��װ��JSON��ʽ���ͻؿͻ���       
                 String  info_json  = "{\"status\":\"-4\"}";//��Ա������ע�ᣡ
                 System.out.println(info_json);
                 out.write(info_json);
                 out.flush();
                 out.close();     
             }
        
        }
        	 
            
		}       
		else {
			response.setContentType("text/html;charset=utf-8");
	    	   
	        PrintWriter out = response.getWriter();     
	        String  info_json  = "{\"status\":\"-2\"}";//�˻����Ƿ�
	        System.out.println(info_json);
	        out.write(info_json);
	        out.flush();
	        out.close();
		}   
     }
    

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
 

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.close();
    }
}