package com.xa.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

public class SplitByCommas extends UDF {

	public String evaluate(String data, int number) {
		String str = null;
		if (data == null || "".equals(data)) {
			return str;
		}

		String[] datas = data.split(",", -1);
		if (datas.length >= number) {
			str = datas[number-1];
		}
		
		return str;
	}
}
