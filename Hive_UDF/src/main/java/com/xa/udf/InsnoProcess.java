package com.xa.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 将新his的住院号转为老his的住院号
 * @author Administrator
 *
 */
public class InsnoProcess extends UDF {

	public String evaluate(String new_in_sno) {
		if(new_in_sno == null || "".equals(new_in_sno)) {
			return null;
		}
		int index = 0;
		String[] nums = new_in_sno.split("", -1);
		for (int i = 0; i < nums.length; i++) {
			String number = nums[i];
			if (!"0".equals(number) && !"".equals(number)) {
				index = i - 1;
				break;
			}
		}
		String inpatient_no = new_in_sno.substring(index);
		
		return inpatient_no;
	}

}
