package com.sanerzone.smscenter.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.bytecode.Descriptor.Iterator;

import com.sanerzone.common.support.storedMap.BDBStoredMapFactoryImpl;

public class BdbTest {
	public static void main(String[] args) {
		
		
		Map<String,Serializable> phoneInfo = BDBStoredMapFactoryImpl.INS.buildMap("phoneInfo", "phoneInfo");
		phoneInfo.clear();
		System.out.println(phoneInfo.size());
		try {
			
			if(true) {
				FileReader reader = new FileReader("/Users/mac/dumps/号段-新.csv");
				BufferedReader br = new BufferedReader(reader);
	
				String phone = null;
				while ((phone = br.readLine()) != null) {
					String[] items = phone.replace("\"", "").split(",");
					if(items.length < 2) {
						System.out.println("异常号码："+phone);
					}
					phoneInfo.put(items[0], items[1]);
				}
	
				br.close();
				reader.close();
			}
			
			System.out.println(phoneInfo.size());
			
//			java.util.Iterator<String> it = phoneInfo.keySet().iterator();
//			while(it.hasNext()) {
//				System.out.println(it.next());
//			}
			
			
			FileReader reader = new FileReader("/Users/mac/Downloads/20171016短信黑.txt");
			BufferedReader br = new BufferedReader(reader);

			int i=0;
			String phone = null;
			while ((phone = br.readLine()) != null) {
				if(phone.length() > 7 ) {
					String info = (String)phoneInfo.get(phone.substring(0, 7));
					if(StringUtils.equals(info, "YD")) {
						FileUtils.write(new File("/Users/mac/Desktop/移动号码.txt"), phone +"\r\n", true);
					}
					
					if(StringUtils.equals(info, "LT")) {
						FileUtils.write(new File("/Users/mac/Desktop/联通号码.txt"), phone +"\r\n", true);
					}
					
					if(StringUtils.equals(info, "DX")) {
						FileUtils.write(new File("/Users/mac/Desktop/电信号码.txt"), phone +"\r\n", true);
					}
				} else {
					System.out.println("异常号码:" + phone);
				}
			}

			br.close();
			reader.close();
			
			// write string to file
//			FileWriter writer = new FileWriter("/Users/mac/Desktop/山西移动号码.txt");
//			BufferedWriter bw = new BufferedWriter(writer);
//			bw.write(sb.toString());
//
//			bw.close();
//			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		List<SmsGatewayGroupRel> list = Lists.newArrayList();
//		
//		SmsGatewayGroupRel smsGatewayGroupRel = new SmsGatewayGroupRel();
//		
//		smsGatewayGroupRel.setGroupId("");
//		smsGatewayGroupRel.setPhoneType("");
//		smsGatewayGroupRel.setProvinceId("");
//		smsGatewayGroupRel.setGwCode("");
//		smsGatewayGroupRel.setLevel(1);
//		
//		
//		GatewayRelHelper.init(list);
		
	}
}
