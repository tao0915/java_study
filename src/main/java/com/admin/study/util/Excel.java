package com.admin.study.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Excel {

	public void write(String fileName, List<Map<String, Object>> listAdminInfo){
		
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
		
		
			HSSFWorkbook workbook = new HSSFWorkbook(); 
			
	
			int cellCnt = 0;
			
			HSSFSheet worksheet = workbook.createSheet("sheetName");
			HSSFRow row = worksheet.createRow(cellCnt++); 
	
			row.createCell(0).setCellValue("번호"); 
			row.createCell(1).setCellValue("이름"); 
			row.createCell(2).setCellValue("아이디"); 
			row.createCell(3).setCellValue("전화번호"); 
			row.createCell(4).setCellValue("핸드폰번호"); 
			row.createCell(5).setCellValue("이메일");
			row.createCell(6).setCellValue("사용가능");
			row.createCell(7).setCellValue("가입일");
			row.createCell(8).setCellValue("수정일");
			
			for (Map<String, Object> adminInfo : listAdminInfo) {
				row = worksheet.createRow(cellCnt++);
				row.createCell(1).setCellValue((String)adminInfo.get("ADMIN_NM"));
				row.createCell(2).setCellValue((String)adminInfo.get("ADMIN_ID"));
				row.createCell(3).setCellValue((String)adminInfo.get("TEL_NO"));
				row.createCell(4).setCellValue((String)adminInfo.get("MOBILE_NO"));
				row.createCell(5).setCellValue((String)adminInfo.get("EMAIL"));
				row.createCell(6).setCellValue((String)adminInfo.get("USE_YN"));
				
				System.out.println(adminInfo.get("CRE_DT"));
				
				row.createCell(7).setCellValue((Timestamp)adminInfo.get("CRE_DT"));
				row.createCell(8).setCellValue((Timestamp)adminInfo.get("MOD_DT"));
			}
			
			workbook.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
