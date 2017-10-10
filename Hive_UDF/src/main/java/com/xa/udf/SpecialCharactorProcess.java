package com.xa.udf;

import java.io.UnsupportedEncodingException;

import org.apache.hadoop.hive.ql.exec.UDF;

public class SpecialCharactorProcess extends UDF {

	public String evaluate(String phonenumber) throws UnsupportedEncodingException {

		if (phonenumber == null) {
			return null;
		}

		String result = new String(gbk2utf8(phonenumber), "UTF-8");

		return result;
	}

	public static byte[] gbk2utf8(String chenese) {
		// Step 1: 得到GBK编码下的字符数组，一个中文字符对应这里的一个c[i]
		char c[] = chenese.toCharArray();
		// Step 2: UTF-8使用3个字节存放一个中文字符，所以长度必须为字符的3倍
		byte[] fullByte = new byte[3 * c.length];
		// Step 3: 循环将字符的GBK编码转换成UTF-8编码
		for (int i = 0; i < c.length; i++) {
			// Step 3-1：将字符的ASCII编码转换成2进制值
			int m = (int) c[i];
			String word = Integer.toBinaryString(m);
			// Step 3-2：将2进制值补足16位(2个字节的长度)
			StringBuffer sb = new StringBuffer();
			int len = 16 - word.length();
			for (int j = 0; j < len; j++) {
				sb.append("0");
			}
			// Step 3-3：得到该字符最终的2进制GBK编码
			// 形似：1000 0010 0111 1010
			sb.append(word);
			// Step 3-4：最关键的步骤，根据UTF-8的汉字编码规则，首字节
			// 以1110开头，次字节以10开头，第3字节以10开头。在原始的2进制
			// 字符串中插入标志位。最终的长度从16--->16+3+2+2=24。
			sb.insert(0, "1110");
			sb.insert(8, "10");
			sb.insert(16, "10");
			// Step 3-5：将新的字符串进行分段截取，截为3个字节
			String s1 = sb.substring(0, 8);
			String s2 = sb.substring(8, 16);
			String s3 = sb.substring(16);

			// Step 3-6：最后的步骤，把代表3个字节的字符串按2进制的方式
			byte b0 = Integer.valueOf(s1, 2).byteValue();
			byte b1 = Integer.valueOf(s2, 2).byteValue();
			byte b2 = Integer.valueOf(s3, 2).byteValue();

			// Step 3-7：把转换后的3个字节按顺序存放到字节数组的对应位置
			byte[] bf = new byte[3];
			bf[0] = b0;
			bf[1] = b1;
			bf[2] = b2;

			fullByte[i * 3] = bf[0];
			fullByte[i * 3 + 1] = bf[1];
			fullByte[i * 3 + 2] = bf[2];
		}
		return fullByte;
	}
}
