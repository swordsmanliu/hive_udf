package com.xa.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

public class IfCharExist extends UDF {
		public boolean evaluate(String content, String name) {
			boolean flag = false;
			if(content.indexOf(name) >= 0) {
				flag = true;
			}
			
			return flag;
		}
}
