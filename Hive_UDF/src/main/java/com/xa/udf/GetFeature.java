package com.xa.udf;

/**
 * 入院记录子集 收缩压 舒张压
 */
import org.apache.hadoop.hive.ql.exec.UDF;

public class GetFeature extends UDF {

	public String evaluate(String content, String name, int type) {
		content = content.replaceAll("，", ",").replaceAll("”", "\"").replaceAll("；", ";").replaceAll("：", ":").replaceAll("“", "\"").replaceAll("。", ".").replaceAll(" ", ".");

		String str = null;
		String result = null;
		if (content.indexOf(name) >= 0 && content.indexOf("mmHg")>=0) {
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
			String[] strs = str.split("/", -1);
			
			if (strs.length > 1) {
				result = strs[type];
			} else {
				result = str;
			}
			if(result.indexOf("mmHg") < 0) {
				result = result+"mmHg";
			}
		}
		
		return result;
	}

	public static void main(String[] args) {
		String s = "生命体征:体温36.5℃，脉搏84次/分，规则，呼吸20次/分，规则，血压:110/70 mmHg 一般情况:发育正常，营养良好，表情自如，检查合作，正力体型，步态正常，自动体位，神志清醒。皮肤、黏膜:色泽正常，无皮疹，无皮下出血点，无蜘蛛痣，无水肿。淋巴结:无浅表淋巴结肿大。头部:头颅大小正常，形态正常，头发分布正常，无突眼，眼睑无浮肿，结膜无充血，巩膜无黄染，角膜透明，瞳孔等大等圆，瞳孔对光反射正常，耳廓正常，无外耳道分泌物，无乳突压痛，无听力障碍。无鼻翼扇动，无分泌物，无副鼻窦压痛。唇红，粘膜无溃烂，舌居中，齿列正常，齿龈正常，扁桃体双侧不肿大，咽不红，声音无异常。颈部:无颈部强直，下颌距胸骨颈动脉搏动正常，无颈动脉杂音，颈静脉正常，肝颈静脉回流征阴性，气管正中。胸部:胸廓正常，无胸骨叩痛，正常呼吸运动，右侧胸壁可及一约30cm手术切口，愈合良好；语颤正常，无胸膜摩擦感无皮下捻发感；叩诊正常清音；呼吸音正常，心尖搏动正常，心尖搏动位置正常；距左锁骨中线触诊心尖搏动正常，无震颤，心相对浊音界正常；心率80次/分，心律整齐，心音正常，无杂音。周围血管征:无。腹部:外形正常，无胃形，无肠形，无腹壁静脉曲张；腹壁紧张度柔软，无压痛，无反跳痛，腹部包块:无，肝肋下未及，胆囊无压痛，Murphy征:阴性，脾肋下未及，肾区无叩痛，肝浊音界存在，肝上界距右锁骨中线5肋间移动性浊音阴性，腹水0度，双肾区无叩痛；肠鸣音正常，无气过水声，无血管杂音，直肠肛门:未查。外生殖器:未查。脊柱:正常。四肢:正常，无关节红肿，无关节强直，无杵状指趾，无肌肉萎缩。神经系统:正常。";

		GetFeature test = new GetFeature();
		test.evaluate(s, "血压", 1);
	}
}
