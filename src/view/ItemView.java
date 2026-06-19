package view;

import model.dto.InventoryDTO;
import model.dto.ItemDTO;
import model.dto.RelationshipDTO;

import java.util.List;
import java.util.Scanner;

/**
 * 아이템 관련 화면 출력을 담당하는 View
 */
public class ItemView {

    private Scanner scanner = new Scanner(System.in);

    /**
     * 아이템을 발견했을 때 출력한다
     */
    public void printFoundItem(ItemDTO item) {
        System.out.printf("%n  ✨ 아이템 발견! [%s] %s%n", item.getRarity(), item.getName());
    }

    /**
     * 현재 인벤토리 목록을 출력한다
     */
    public void printInventory(List<InventoryDTO> list) {
        if (list.isEmpty()) {
            System.out.println("  인벤토리가 비었다냥.");
            return;
        }
        System.out.println("\n[ 인벤토리 ]");
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, list.get(i));
        }
    }

    /**
     * 아이템 사용 선택 메뉴를 출력한다
     */
    public void printItemActionMenu() {
        System.out.println("\n  이 아이템을 어떻게 할까냥?");
        System.out.println("  1. 먹기");
        System.out.println("  2. 친구에게 선물하기");
        System.out.println("  0. 취소");
        System.out.print("  선택 > ");
    }

    /**
     * 선물할 친구 목록을 출력한다
     */
    public void printFriendListForGift(List<RelationshipDTO> friends) {
        System.out.println("\n  누구에게 선물할까냥?");
        for (int i = 0; i < friends.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, friends.get(i).getNpcName());
        }
        System.out.print("  선택 > ");
    }

    /**
     * 아이템 먹기 결과를 출력한다
     */
    public void printEatResult(String itemName, int recover) {
        System.out.printf("  😋 %s 냠냠! 허기 +%d 회복!%n", itemName, recover);
    }

    /**
     * 선물 결과를 출력한다
     */
    public void printGiftResult(String itemName, String friendName) {
        System.out.printf("  🎁 %s에게 %s을(를) 선물했다! 관계가 좋아졌다냥!%n", friendName, itemName);
    }
}
