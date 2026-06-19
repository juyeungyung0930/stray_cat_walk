package view;

import model.dto.CatDTO;
import model.dto.LocationDTO;

/**
 * 메인 화면 출력을 담당하는 View
 * 메뉴 출력 및 사용자 입력 처리
 */
public class MainView {

    /**
     * 게임 타이틀을 출력한다
     */
    public void printTitle() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     🐾 Stray Cat Walk 🐾      ║");
        System.out.println("╚══════════════════════════════╝");
    }

    /**
     * 현재 고양이 상태와 위치를 출력한다
     */
    public void printStatus(CatDTO cat, LocationDTO location) {
        System.out.println("\n──────────────────────────────");
        System.out.printf("  %s | 📍 %s%n", cat.getName(), location.getName());
        System.out.printf("  🍣 허기:%d  😴 피로:%d  😸 기분:%d%n",
                cat.getHunger(), cat.getFatigue(), cat.getMood());
        System.out.println("──────────────────────────────");
    }

    /**
     * 메인 메뉴를 출력한다
     */
    public void printMainMenu() {
        System.out.println("\n[ 메인 메뉴 ]");
        System.out.println("1. 장소 이동");
        System.out.println("2. 아이템 인벤토리");
        System.out.println("3. 친구/라이벌 목록");
        System.out.println("4. 오늘의 일지 보기");
        System.out.println("5. 잠자기 (다음 날로)");
        System.out.println("0. 게임 종료");
        System.out.print("선택 > ");
    }

    /**
     * 일반 메시지를 출력한다
     */
    public void printMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * 에러 메시지를 출력한다
     */
    public void printError(String msg) {
        System.out.println("[오류] " + msg);
    }
}
