package com.tools;
public class EmployeeNumberChecker implements BaseFilter_Albert_Frank_Check {
	
public boolean doCheck(String sin) {
		
		if(!numAndWordTest.numAndWordTest(sin))
		{	
			System.out.println(getClass().getName()+"  error");
			System.out.println(sin+"    �˻����Ƿ�������������");
			return false;
		}
		// TODO Auto-generated method stub
		System.out.println(sin+"    �˻����Ϸ�");
		return true;
	}

}