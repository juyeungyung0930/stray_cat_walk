package view;

import model.dto.WalkLogDTO;

import java.sql.Date;
import java.util.List;

/**
 * 산책 일지 화면 출력을 담당하는 View
 */
public class LogView {

    /**
     * 오늘의 산책 일지 전체를 출력한다
     */
    public void printDailyLog(List<WalkLogDTO> logs, Date gameDate) {
        System.out.println("\n[ 오늘의 일지 - " + gameDate + " ]");
        if (logs.isEmpty()) {
            System.out.println("  오늘은 아무것도 안 했다냥...");
            return;
        }
        for (WalkLogDTO log : logs) {
            // actionType에 따라 이모지 설정
            String emoji = getEmoji(log.getActionType());
            System.out.printf("  %s %s%n", emoji, log.getDescription());
        }
    }

    /**
     * 잠자기(다음 날로 넘어가기) 메시지를 출력한다
     */
    public void printSleepMessage(Date newDate) {
        System.out.println("\n  💤 냥냥... 잘 자고 일어났다!");
        System.out.println("  📅 " + newDate + " 아침이다!");
    }

    /**
     * 행동 타입별 이모지를 반환한다
     */
    private String getEmoji(String actionType) {
        switch (actionType) {
            case "MOVE":  return "🐾";
            case "EAT":   return "🍣";
            case "MARK":  return "📌";
            case "MEET":  return "🐱";
            case "GIFT":  return "🎁";
            default:      return "•";
        }
    }
}
