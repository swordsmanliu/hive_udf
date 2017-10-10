package com.xa.udf;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDF;

public class GetValueByName extends UDF {

	public String evaluate(String line, String name) {
		if(line == null || "".equals(line.trim())) {
			return line;
		}
		
		
		line = line.replaceAll("：", ":").replaceAll("，", ",").replaceAll("；", ";");
		String[] lines = line.split(" ", -1);
		
		Map<String,String> cache = new HashMap<String, String>();
		
		String propertyname = null;
		StringBuffer propertyvalue = null;
		for(int i=0; i<lines.length; i++) {
			String record = lines[i];
			String[] records = record.split(":", -1);
			
			if(records.length > 1) {
				propertyvalue = new StringBuffer();
				propertyname = records[0];
				propertyvalue.append(records[1]);
			} else {
				propertyvalue.append(records[0]);
			}
			if(i<(lines.length-1) && lines[i+1].split(":", -1).length > 1) {
				cache.put(propertyname, propertyvalue.toString());
			}
		}
		
		for(String key : cache.keySet()) {
			System.out.println(key+"===="+cache.get(key));
		}
		return cache.get(name);
	}
	
	
	public static void main(String[] args) {
		String s = "姓名：曾天喜 性别：男 年龄：67 岁 手术日期：2014 年1　　月 3 日 术中诊断：左侧颈内动脉起始段轻度狭窄 麻醉方式：局麻 手术方式：经皮穿刺选择性动脉造影术+经股动脉插管全脑血管+双肾动脉造影术 手术简要经过：患者平卧位，常规消毒铺巾，1％利多卡因局部麻醉。采用Seldinger技术经右侧股动脉穿刺，放置5F动脉鞘，静脉注射肝素2000U。选择5F-PIG和VER造影导管与0.035\"超滑导丝组合，在导丝的导引下，分别造影导管小心送至主动脉弓、腹主动脉、双侧锁骨下动脉、双侧颈总动脉及左侧椎动脉造影。造影结束，拔管拔鞘，穿刺点压迫止血器包扎。 术后处理措施： 1. 术后给予24小时心电、血压、呼吸、指脉氧饱和度监测； 2. 术后给予补液处理，促进造影剂排除； 3. 术后右下肢严格制动伸直，局部压迫止血器压迫止血； 术后观察事项： 1. 术后观察生命体征变化：心电、血压、呼吸、指脉氧饱和度监测； 2. 术后观察神经系统症状及体征变化：神志、瞳孔、肢体活动、病理征、脑膜刺激征等； 3. 术后观察穿刺局部有无渗血、渗液、瘀斑，局部有无血管杂音等局部出血征象； 4. 术后观察双下肢足背动脉搏动、皮温及皮肤色泽；";
		GetValueByName model = new GetValueByName();
		String value = model.evaluate(s,"姓名");
		System.out.println(value);
	}

}
