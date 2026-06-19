package view;

import java.util.Scanner;

/**
 * 고양이 캐릭터 생성 화면 출력을 담당하는 View
 * 게임 시작 시 이름/색깔/성격 입력 받기
 */
public class CatView {

    private Scanner scanner = new Scanner(System.in);

    /**
     * 고양이 이름을 입력받는다
     */
    public String inputName() {
        System.out.println("\n✦ 고양이 이름을 지어주세요");
        System.out.print("이름 > ");
        return scanner.nextLine().trim();
    }

    /**
     * 고양이 털 색깔을 선택받는다
     */
    public String selectColor() {
        System.out.println("\n✦ 털 색깔을 선택하세요");
        System.out.println("1. 치즈 (노랑)");
        System.out.println("2. 고등어 (회색)");
        System.out.println("3. 턱시도 (흑백)");
        System.out.println("4. 삼색이 (흰/주황/검)");
        System.out.print("선택 > ");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1": return "치즈";
            case "2": return "고등어";
            case "3": return "턱시도";
            case "4": return "삼색이";
            default:  return "치즈"; // 잘못 입력하면 기본값
        }
    }

    /**
     * 고양이 성격을 선택받는다
     */
    public String selectPersonality() {
        System.out.println("\n✦ 성격을 선택하세요");
        System.out.println("1. 도도함  - 자존심 강하고 쿨한 성격");
        System.out.println("2. 애교쟁이 - 붙임성 좋고 친화력 높음");
        System.out.println("3. 겁쟁이  - 조심성 많고 온순한 성격");
        System.out.print("선택 > ");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1": return "도도함";
            case "2": return "애교쟁이";
            case "3": return "겁쟁이";
            default:  return "도도함";
        }
    }

    /**
     * 고양이 생성 완료 메시지를 출력한다
     */
    public void printCreateSuccess(String name, String color, String personality) {
        System.out.println("\n🐱 고양이 탄생!");
        System.out.printf("  이름: %s | 색깔: %s | 성격: %s%n", name, color, personality);
        System.out.println("  산책을 시작해봐냥~!");
    }
}
