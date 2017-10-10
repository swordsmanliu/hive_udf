package com.xa.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

public class GetFeatureTwo extends UDF {
	
	public String evaluate(String content, String name) {
		content = content.replaceAll("，", ",").replaceAll("”", "\"").replaceAll("；", ";").replaceAll("：", ":").replaceAll("“", "\"").replaceAll("。", ".").replaceAll(" ", ".");
		String result = null;
		int index = content.indexOf(name);
		if (index >= 0 && (index+name.length())<(content.length()-2)) {
			result = content.substring(index+name.length(),index+name.length()+1);
			if(":".equals(result)) {
				result = content.substring(index+name.length()+1,index+name.length()+2);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		String content = "平素健康状况1: 1.良好 2.一般 3. 较差 疾病史：（系统回顾:如有症状在下面空行内填写发病时间及目前状况） 呼吸系统症状1: 1.无 2.有 循环系统症状1: 1.无 2.有 消化系统症状1: 1.无 2.有 泌尿系统症状1: 1.无 2.有 血液系统症状1: 1.无 2.有 内分泌代谢症状1: 1.无 2.有 神经精神症状1:1.无 2.有 生殖系统症状1:1.无 2.有 运动系统症状1:1.无 2.有 传染病史1:1.无 2.有 其他  预防接种史:1: 1.无 2.有 3.不详 预防接种药物";
//		String content = "平素健康状况：一般。无呼吸系统症状；循环系统症状：1997年患冠心病；无消化系统症状；无泌尿系统症状；无血液系统症状；无内分泌代谢症状；神经精神症状：1993年患脑梗；无生殖系统症状；无运动系统症状。传染病史：无。预防接种史：无。手术外伤史：无。药物过敏史：无。";
//		String content = "“高血压”1年余，口服药物，具体不详。否认“糖尿病”、“心脏病”等特殊病史，否认“肝炎”、“结核”等传染病史";
		GetFeatureTwo model = new GetFeatureTwo();
		model.evaluate(content, "传染病史");
	}
}
