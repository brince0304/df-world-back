package com.dfoff.demo.Util;

import com.dfoff.demo.Domain.ForCharacter.CharacterBuffEquipmentJsonDto;
import com.dfoff.demo.Domain.ForCharacter.CharacterSkillDetailJsonDto;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Slf4j
public class SearchPageUtil {

    public static List<String> getBuffPercent(CharacterBuffEquipmentJsonDto dto){
        List<String> list = new ArrayList<>();
        if(dto.getSkill()!=null){
            if(dto.getSkill().getBuff()!=null){
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
        for(int i=0; i<optionValues.size();i++){
            CharacterSkillDetailJsonDto.Row row = optionValues.get(i);
            List<Double> optionValuesInt = new ArrayList<>();
            if(row.getOptionValue().getValue1()!=null ){
                optionValuesInt.add(!row.getOptionValue().getValue1().equals("-") ? Double.parseDouble(row.getOptionValue().getValue1()):0);
            }
            if(row.getOptionValue().getValue2()!=null){
                optionValuesInt.add(!row.getOptionValue().getValue2().equals("-") ? Double.parseDouble(row.getOptionValue().getValue2()):0);
            }
            if(row.getOptionValue().getValue3()!=null){
                optionValuesInt.add(!row.getOptionValue().getValue3().equals("-") ? Double.parseDouble(row.getOptionValue().getValue3()):0);
            }
            if(row.getOptionValue().getValue4()!=null){
                optionValuesInt.add(!row.getOptionValue().getValue4().equals("-") ? Double.parseDouble(row.getOptionValue().getValue4()):0);
            }
            if(row.getOptionValue().getValue5()!=null){
                optionValuesInt.add(!row.getOptionValue().getValue5().equals("-") ? Double.parseDouble(row.getOptionValue().getValue5()):0);
            }
            if(row.getOptionValue().getValue6()!=null){
                optionValuesInt.add(!row.getOptionValue().getValue6().equals("-") ? Double.parseDouble(row.getOptionValue().getValue6()):0);
            }
            if(row.getOptionValue().getValue7()!=null){
                optionValuesInt.add(!row.getOptionValue().getValue7().equals("-") ? Double.parseDouble(row.getOptionValue().getValue7()):0);
            }
            if(row.getOptionValue().getValue8()!=null){
                optionValuesInt.add(!row.getOptionValue().getValue8().equals("-") ? Double.parseDouble(row.getOptionValue().getValue8()):0);
            }
            if(row.getOptionValue().getValue9()!=null){
                optionValuesInt.add(!row.getOptionValue().getValue9().equals("-") ? Double.parseDouble(row.getOptionValue().getValue9()):0);
            }
            double[]dp = new double[optionDescs.length-1];
            int k=0;
            if(optionDescs[0].contains("공격력")&&!optionDescs[0].contains("px") && optionDescs[0].contains("x")){
                dp[0] = optionValuesInt.get(k)*optionValuesInt.get(k+1);
                k+=2;
            }else if(optionDescs[0].contains("공격력") &&!optionDescs[0].contains("px")){
                dp[0] = optionValuesInt.get(k);
                k+=1;
            } else if(optionDescs[0].contains("x") && !optionDescs[0].contains("px")){
                k+=2;
            }else{
                k+=1;
            }

            for(int j=1; j<optionDescs.length-1;j++) {
                if (optionDescs[j].contains("공격력") && !optionDescs[j].contains("px") && optionDescs[j].contains("x")) {
                    dp[j] = dp[j - 1] + (optionValuesInt.get(k) * optionValuesInt.get(k + 1));
                    k += 2;
                } else if (optionDescs[j].contains("공격력") && !optionDescs[j].contains("px")) {
                    dp[j] = dp[j - 1] + optionValuesInt.get(k);
                    k += 1;
                } else if(optionDescs[j].contains("x") && !optionDescs[j].contains("px")){
                    k+=2;
                }else{
                    k+=1;
                }
            }
            percentPerLevel.add(dp[optionDescs.length-2]+"");
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
