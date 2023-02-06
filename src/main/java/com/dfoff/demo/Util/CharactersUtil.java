package com.dfoff.demo.Util;

import com.dfoff.demo.Domain.JsonDtos.CharacterBuffEquipmentJsonDto;
import com.dfoff.demo.Domain.JsonDtos.CharacterSkillDetailJsonDto;
import com.dfoff.demo.Domain.JsonDtos.EquipmentDetailJsonDto;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

@Slf4j
public class CharactersUtil {

    //버퍼들의 장비 옵션을 가져와서 버프강화 수치를 나타내주는 메소드
    //신화장비 장착시에는 +2, 최신 에픽 장착시에는 +3가 됨 (30레벨 기준)
    public static List<String> getBuffPercent(CharacterBuffEquipmentJsonDto dto, List<EquipmentDetailJsonDto> equipment) throws InterruptedException {
        List<String> list = new ArrayList<>();
        int levelPlus =0;
        if(equipment.size()>0){
            for(EquipmentDetailJsonDto buff : equipment){
                //버퍼 전용 옵션이기 때문에 버퍼일때만 스킬 레벨을 올려준다.
                if(dto.getJobGrowName().equals("眞 크루세이더") || dto.getJobGrowName().equals("眞 인챈트리스") ||
                        dto.getJobGrowName().equals("헤카테")  ||dto.getJobGrowName().equals("홀리 오더") ||
                        dto.getJobGrowName().equals("세인트")){
                    //30레벨 50레벨 스킬 강화 옵션이 달려있을때
                    if(buff.getItemBuff()==null || !buff.getItemBuff().getExplain().contains("30") || !buff.getItemBuff().getExplain().contains("50")){
                        if(buff.getItemRarity().equals("신화")){
                            levelPlus += 2;
                        }
                        continue;
                    }
                    String str =  buff.getItemBuff().getExplain();
                    str = buff.getItemBuff()!=null? buff.getItemBuff().getExplain().substring(0,str.indexOf("+")+1) : "";
                    if(!str.contains("50")){
                        str = buff.getItemBuff()!=null? buff.getItemBuff().getExplain().substring( buff.getItemBuff().getExplain().indexOf("+")+1, buff.getItemBuff().getExplain().indexOf("+")+2) : "0";
                        levelPlus += Integer.parseInt(str.charAt(0)+"");
                    }
                }
            }
        }
        if(dto.getSkill()!=null){
            if(dto.getSkill().getBuff()!=null && levelPlus==0 && !dto.getSkill().getBuff().getSkillInfo().getName().equals("영광의 축복") && !dto.getSkill().getBuff().getSkillInfo().getName().equals("용맹의 축복") &&
            !dto.getSkill().getBuff().getSkillInfo().getName().equals("금단의 저주")){
                list.add(dto.getSkill().getBuff().getSkillInfo().getName());
                String desc = dto.getSkill().getBuff().getSkillInfo().getOption().getDesc();
                List<String> val = dto.getSkill().getBuff().getSkillInfo().getOption().getValues();
                StringTokenizer st = new StringTokenizer(desc,"\n");
                int i=0;
                while(st.hasMoreTokens()){
                    String token = st.nextToken();
                    if(token.contains("스킬") || token.contains("데미지")||token.contains("무기")){
                        list.add(dto.getSkill().getBuff().getSkillInfo().getOption().getLevel()+"");
                        list.add(val.get(i)+"%");
                        break;
                    }
                    i++;
                }
                return list;

            } else if(dto.getSkill().getBuff()!=null && levelPlus>0){
                list.add(dto.getSkill().getBuff().getSkillInfo().getName());
                //버퍼일때는 스킬강화 수치가 버프강화 옵션에 나오지 않기때문에 새로 스킬 정보를 파싱해와서 해당 레벨에 맞는 수치를 찾아서 계산해주어야함
                CharacterSkillDetailJsonDto skill = RestTemplateUtil.parseJsonFromUri(RestTemplateUtil.getCharacterSkillDetailUri(dto.getJobId(),dto.getSkill().getBuff().getSkillInfo().getSkillId()), CharacterSkillDetailJsonDto.class);
                String desc = skill.getLevelInfo().getOptionDesc();
                Integer level = dto.getSkill().getBuff().getSkillInfo().getOption().getLevel()+levelPlus;
                list.add(level + "");
                CharacterSkillDetailJsonDto.OptionValue val = skill.getLevelInfo().getRows().stream().filter(o-> Objects.equals(o.getLevel(), level)).findFirst().get().getOptionValue();
                StringTokenizer st = new StringTokenizer(desc,"\n");
                while(st.hasMoreTokens()){
                    String token = st.nextToken();
                    if(token.contains("스킬") || token.contains("데미지")||token.contains("무기")){
                        String num = token.substring(token.length()-3,token.length()-2);
                        switch (Integer.parseInt(num)-1) {
                            // json이 네임과 벨류로 필드가 이뤄져있기 때문에 일일히 케이스를 나눠서 값을 가져와야함
                            case 0 -> list.add(val.getValue1() + "%");
                            case 1 -> list.add(val.getValue2() + "%");
                            case 2 -> list.add(val.getValue3() + "%");
                            case 3 -> list.add(val.getValue4() + "%");
                            case 4 -> list.add(val.getValue5() + "%");
                            case 5 -> list.add(val.getValue6() + "%");
                            case 6 -> list.add(val.getValue7() + "%");
                            case 7 -> list.add(val.getValue8() + "%");
                            case 8 -> list.add(val.getValue9() + "%");
                            case 9 -> list.add(val.getValue10() + "%");
                        }
                        return list;
                    }
                }
                return list;

            }

        } return list;
    }

    //계산법만 가져와서 씀
   // 제대로된 계산법을 찾지 못했기때문에 보류
    public static String getSkillFinalPercent (CharacterSkillDetailJsonDto dto, int level) {
        List<String> percentPerLevel = new ArrayList<>();
        String optionDesc = dto.getLevelInfo().getOptionDesc();
        List<CharacterSkillDetailJsonDto.Row> optionValues = dto.getLevelInfo().getRows();
        CharacterSkillDetailJsonDto.OptionValue levelValue = optionValues.stream().filter(o->o.getLevel().equals(level)).findFirst().get().getOptionValue();
        optionDesc= optionDesc.replace("value1",levelValue.getValue1()+"");
        optionDesc= optionDesc.replace("value2",levelValue.getValue2()+"");
        optionDesc= optionDesc.replace("value3",levelValue.getValue3()+"");
        optionDesc= optionDesc.replace("value4",levelValue.getValue4()+"");
        optionDesc= optionDesc.replace("value5",levelValue.getValue5()+"");
        optionDesc= optionDesc.replace("value6",levelValue.getValue6()+"");
        optionDesc= optionDesc.replace("value7",levelValue.getValue7()+"");
        optionDesc= optionDesc.replace("value8",levelValue.getValue8()+"");
        optionDesc= optionDesc.replace("value9",levelValue.getValue9()+"");
        optionDesc= optionDesc.replace("value10",levelValue.getValue10()+"");
        return optionDesc;
    }




        public static long dPlus(LocalDateTime dayBefore) {
            return ChronoUnit.DAYS.between(dayBefore, LocalDateTime.now());
        }

        public static long dMinus(LocalDateTime dayAfter) {
            return ChronoUnit.DAYS.between(dayAfter, LocalDateTime.now());
        }

        public static String timesAgo(LocalDateTime dayBefore) {
            long gap = ChronoUnit.MINUTES.between(dayBefore, LocalDateTime.now());
            String word;
            if (gap == 0) {
                word = "방금 전";
            } else if (gap < 60) {
                word = gap + "분 전";
            } else if (gap < 60 * 24) {
                word = (gap / 60) + "시간 전";
            } else if (gap < 60 * 24 * 10) {
                word = (gap / 60 / 24) + "일 전";
            } else {
                word = dayBefore.format(DateTimeFormatter.ofPattern("MM월 dd일"));
            }
            return word;
        }

        public static String customForm(LocalDateTime date) {
            return date.format(DateTimeFormatter.ofPattern("MM월 dd일"));
        }
        //캐릭터 프로필 이미지 css 클래스이름용 변환 메소드
    public static String getStyleClassName(String jobName) {
        return switch (jobName) {
            case "격투가(남)" -> "m-fighter";
            case "격투가(여)" -> "f-fighter";
            case "마법사(여)" -> "f-mage";
            case "거너(남)" -> "m-gunner";
            case "거너(여)" -> "f-gunner";
            case "마창사" -> "m-lancer";
            case "귀검사(남)" -> "m-warrior";
            case "귀검사(여)" -> "f-warrior";
            case "총검사" -> "m-gunwarrior";
            case "프리스트(남)" -> "m-priest";
            case "프리스트(여)" -> "f-priest";
            default -> "default";
        };
    }
}
