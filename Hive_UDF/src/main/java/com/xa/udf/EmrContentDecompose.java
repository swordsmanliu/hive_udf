package com.xa.udf;

/**
 * 6.13.1首次病程记录子集
 */
import org.apache.hadoop.hive.ql.exec.UDF;

public class EmrContentDecompose extends UDF {
	public String evaluate(String content, String name) {
		if (content == null || "".equals(content.trim())) {
			return "";
		}

		content = content.replaceAll("，", ",").replaceAll("”", "\"").replaceAll("；", ".").replaceAll("：", ":").replaceAll("“", "\"").replaceAll("。", ".").replaceAll(" ", ".");
		String str = null;
		int index = content.indexOf(name);
		if (index >= 0 && (index+name.length()) < (content.length()-1)) {
			str = content.substring(content.indexOf(name) + name.length() + 1);
			if(str.indexOf(":")>=0) {
				str = str.substring(0, str.indexOf(":"));
			}			

			int period = str.lastIndexOf(".");
			int comma = str.lastIndexOf(",");

			if (period == -1 && comma == -1) {
				return str;
			}

			if (period > comma) {
				if (comma > 0) {
					str = str.substring(0, comma);
				} else {
					str = str.substring(0, period);
				}
			} else {
				if (period > 0) {
					str = str.substring(0, period);
				} else {
					str = str.substring(0, comma);
				}
			}
		}

		return str;
	}

	public static void main(String[] args) {
//		String s = "患者张晓丽，女，55岁； 因“左侧腰部胀痛不适2天”入院； 患者2天前无明显诱因出现左侧腰部胀痛不适，间歇发作，疼痛无放射，伴间歇肉眼血尿，不伴尿频、尿急、尿痛、排尿困难、畏寒、发热等。到外院就诊，行B超示左肾结石，长径0.8cm。来我院诊治收住我科。发病以来，患者精神、饮食、睡眠尚可，大小便如常，体力、体重无明显改变。 既往：无特殊病史，否认高血压、糖尿病、乙肝、肺结核病史，否认药物过敏史； 体格检查 生命体征：体温36.5℃，脉搏70次/分，规则，呼吸16次/分，规则，血压：120/80mmHg。一般情况：发育正常，营养良好，表情自如，检查合作，正力体型，步态正常，自动体位，神志清醒。皮肤、黏膜：色泽正常，无皮疹，无皮下出血点，无蜘蛛痣，无水肿。淋巴结：无浅表淋巴结肿大。头部：头颅大小正常，形态正常，头发分布正常，无突眼，眼睑无浮肿，结膜无充血，巩膜无黄染，角膜透明，瞳孔等大等圆，瞳孔对光反射正常，耳廓正常，无外耳道分泌物，无乳突压痛，无听力障碍。无鼻翼扇动，无分泌物，无副鼻窦压痛。唇红，粘膜无溃烂，舌居中，齿列正常，齿龈正常，扁桃体双侧不肿大，咽不红，声音无异常。颈部：无颈部强直，下颌距胸骨颈动脉搏动正常，无颈动脉杂音，颈静脉正常，肝颈静脉回流征阴性，气管正中，甲状腺正常，无血管杂音。胸部：胸廓正常，乳房正常，无胸骨叩痛，正常呼吸运动；语颤正常，无胸膜摩擦感无皮下捻发感；叩诊正常清音；呼吸音正常，心尖搏动正常，心尖搏动位置正常；距左锁骨中线触诊心尖搏动正常，无震颤，心相对浊音界正常；心率70次/分，心律整齐，心音正常，无杂音。周围血管征：无。腹部：外形正常，无胃形，无肠形，无腹壁静脉曲张；腹壁紧张度柔软，无压痛，无反跳痛，腹部包块：无，肝肋下未及，胆囊无压痛，Murphy征：阴性，脾肋下未及，肾区无叩痛，肝浊音界存在，肝上界距右锁骨中线5肋间移动性浊音阴性，腹水0度，双肾区无叩痛；肠鸣音正常，无气过水声，无血管杂音，直肠肛门：未查。外生殖器：未查。脊柱：正常。四肢：正常，无关节红肿，无关节强直，无杵状指趾，无肌肉萎缩。神经系统：正常。 专科情况（体检）：双侧上、中输尿管点无压痛，右肾区无叩痛，左肾区叩击痛，膀胱区无压痛，尿道外口无异常分泌物 门诊及院外重要辅助检查(包括日期、医疗机构、检查项目、结果) ：外院B超示左肾结石，长径0.8cm。 诊疗计划： 1、行泌尿系CT，了解结石位置及体积、上尿路是否通畅。 2、完善其他相关检查（血尿常规、凝血常规、肝肾功能、心电图）。 3、根据结石状况选择经皮肾镜、体外碎石或开放手术。";		EmrContentDecompose test = new EmrContentDecompose();
		String s = "患者一般情况可，未诉特殊不适。查体，Bp 135/80mmHg,神清，双瞳等大等圆，颈部肿块大小同前，无压痛。双肺呼吸音存，未闻及干湿性罗音。心脏听诊未及异常。腹平软，未及压痛反跳痛。 拟于在全麻下行甲状腺次全切除术。向患者及家属交代手术风险，注意事项";
		//		test.evaluate(s, "主诉");
//		test.evaluate(s, "体格检查");
//		test.evaluate(s, "诊断依据");
//		test.evaluate(s, "鉴别诊断");
		EmrContentDecompose test = new EmrContentDecompose();
		test.evaluate(s, "注意事项");
	}
}
