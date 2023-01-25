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

    public static List<String> getBuffPercent(CharacterBuffEquipmentJsonDto dto, List<EquipmentDetailJsonDto> equipment){
        List<String> list = new ArrayList<>();
        int levelPlus =0;
        if(equipment.size()>0){
            for(EquipmentDetailJsonDto buff : equipment){
                if(dto.getJobGrowName().equals("眞 크루세이더") || dto.getJobGrowName().equals("眞 인챈트리스") ||
                        dto.getJobGrowName().equals("헤카테")  ||dto.getJobGrowName().equals("홀리 오더") ||
                        dto.getJobGrowName().equals("세인트")){
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
                        log.info("str : {}",str);
                        log.info("levelPlus : {}",levelPlus);
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
                CharacterSkillDetailJsonDto skill = RestTemplateUtil.parseJsonFromUri(RestTemplateUtil.getCharacterSkillDetailUri(dto.getJobId(),dto.getSkill().getBuff().getSkillInfo().getSkillId()), CharacterSkillDetailJsonDto.class);
                String desc = skill.getLevelInfo().getOptionDesc();
                Integer level = dto.getSkill().getBuff().getSkillInfo().getOption().getLevel()+levelPlus;
                list.add(level + "");
                CharacterSkillDetailJsonDto.OptionValue val = skill.getLevelInfo().getRows().stream().filter(o-> Objects.equals(o.getLevel(), level)).findFirst().get().getOptionValue();
                StringTokenizer st = new StringTokenizer(desc,"\n");
                while(st.hasMoreTokens()){
                    String token = st.nextToken();
                    if(token.contains("스킬") || token.contains("데미지")||token.contains("무기")){
                        log.info("token : {}",token);
                        String num = token.substring(token.length()-3,token.length()-2);
                        switch (Integer.parseInt(num)-1) {
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
                        log.info("스킬버프 : "+list);
                        return list;
                    }
                }
                return list;

            }

        } return list;
    }

    //계산법만 가져와서 씀
    public static String getSkillFinalPercent (CharacterSkillDetailJsonDto dto, int level) {
        List<String> percentPerLevel = new ArrayList<>();
        String optionDesc = dto.getLevelInfo().getOptionDesc();
        if(optionDesc==null||optionDesc.contains("증가")){
            return "0";
        }
        List<CharacterSkillDetailJsonDto.Row> optionValues = dto.getLevelInfo().getRows();
        StringTokenizer st = new StringTokenizer(optionDesc, "\n");
        String[] optionDescs = new String[st.countTokens()];
        while(st.hasMoreTokens()){
            for(int i=0; i<optionDescs.length; i++){
                optionDescs[i] = st.nextToken();
            }
        }
        for (CharacterSkillDetailJsonDto.Row row : optionValues) {
            List<Double> optionValuesInt = new ArrayList<>();
            if (row.getOptionValue().getValue1() != null) {
                optionValuesInt.add(!row.getOptionValue().getValue1().equals("-") ? Double.parseDouble(row.getOptionValue().getValue1()) : 0);
            }
            if (row.getOptionValue().getValue2() != null) {
                optionValuesInt.add(!row.getOptionValue().getValue2().equals("-") ? Double.parseDouble(row.getOptionValue().getValue2()) : 0);
            }
            if (row.getOptionValue().getValue3() != null) {
                optionValuesInt.add(!row.getOptionValue().getValue3().equals("-") ? Double.parseDouble(row.getOptionValue().getValue3()) : 0);
            }
            if (row.getOptionValue().getValue4() != null) {
                optionValuesInt.add(!row.getOptionValue().getValue4().equals("-") ? Double.parseDouble(row.getOptionValue().getValue4()) : 0);
            }
            if (row.getOptionValue().getValue5() != null) {
                optionValuesInt.add(!row.getOptionValue().getValue5().equals("-") ? Double.parseDouble(row.getOptionValue().getValue5()) : 0);
            }
            if (row.getOptionValue().getValue6() != null) {
                optionValuesInt.add(!row.getOptionValue().getValue6().equals("-") ? Double.parseDouble(row.getOptionValue().getValue6()) : 0);
            }
            if (row.getOptionValue().getValue7() != null) {
                optionValuesInt.add(!row.getOptionValue().getValue7().equals("-") ? Double.parseDouble(row.getOptionValue().getValue7()) : 0);
            }
            if (row.getOptionValue().getValue8() != null) {
                optionValuesInt.add(!row.getOptionValue().getValue8().equals("-") ? Double.parseDouble(row.getOptionValue().getValue8()) : 0);
            }
            if (row.getOptionValue().getValue9() != null) {
                optionValuesInt.add(!row.getOptionValue().getValue9().equals("-") ? Double.parseDouble(row.getOptionValue().getValue9()) : 0);
            }
            double[] dp = new double[optionDescs.length - 1];
            int k = 0;
            if (optionDescs[0].contains("공격력") && !optionDescs[0].contains("px") && optionDescs[0].contains("x")) {
                dp[0] = optionValuesInt.get(k) * optionValuesInt.get(k + 1);
                k += 2;
            } else if (optionDescs[0].contains("공격력") && !optionDescs[0].contains("px")) {
                dp[0] = optionValuesInt.get(k);
                k += 1;
            } else if (optionDescs[0].contains("x") && !optionDescs[0].contains("px")) {
                k += 2;
            } else {
                k += 1;
            }

            for (int j = 1; j < optionDescs.length - 1; j++) {
                if (optionDescs[j].contains("공격력") && !optionDescs[j].contains("px") && optionDescs[j].contains("x")) {
                    dp[j] = dp[j - 1] + (optionValuesInt.get(k) * optionValuesInt.get(k + 1));
                    k += 2;
                } else if (optionDescs[j].contains("공격력") && !optionDescs[j].contains("px")) {
                    dp[j] = dp[j - 1] + optionValuesInt.get(k);
                    k += 1;
                } else if (optionDescs[j].contains("x") && !optionDescs[j].contains("px")) {
                    k += 2;
                } else {
                    k += 1;
                }
            }
            percentPerLevel.add(dp[optionDescs.length - 2] + "");
        }
        log.info("percentPerLevel : {}",percentPerLevel);
        return percentPerLevel.get(level-1);
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
}
