package controller;

import model.dto.InventoryDTO;
import model.dto.RelationshipDTO;
import model.service.CatService;
import model.service.FriendService;
import model.service.ItemService;
import model.service.WalkService;
import view.ItemView;
import view.MainView;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 아이템 인벤토리 화면 흐름을 제어하는 컨트롤러
 * 먹기 / 선물하기 처리
 */
public class ItemController {

    private Scanner scanner = new Scanner(System.in);

    private MainView mainView = new MainView();
    private ItemView itemView = new ItemView();

    private ItemService   itemService   = new ItemService();
    private CatService    catService    = new CatService();
    private FriendService friendService = new FriendService();
    private WalkService   walkService   = new WalkService();

    /**
     * 인벤토리 메뉴 전체 흐름을 실행한다
     */
    public void run(int catId, Date gameDate) throws SQLException {
        List<InventoryDTO> inventory = itemService.getInventory(catId);
        itemView.printInventory(inventory);

        if (inventory.isEmpty()) return;

        mainView.printMessage("\n사용할 아이템 번호를 선택하세요 (0 = 취소)");
        System.out.print("선택 > ");
        String input = scanner.nextLine().trim();
        if ("0".equals(input)) return;

        int idx;
        try {
            idx = Integer.parseInt(input) - 1;
            if (idx < 0 || idx >= inventory.size()) {
                mainView.printMessage("잘못된 번호다냥.");
                return;
            }
        } catch (NumberFormatException e) {
            mainView.printMessage("숫자를 입력해다냥.");
            return;
        }

        InventoryDTO selected = inventory.get(idx);
        itemView.printItemActionMenu();
        String action = scanner.nextLine().trim();

        switch (action) {
            case "1":
                // 먹기
                int recover = itemService.eatItem(catId, selected.getItemId());
                catService.applyEatItem(catId, recover);
                itemView.printEatResult(selected.getItemName(), recover);
                walkService.writeLog(catId, gameDate, "EAT",
                        selected.getItemName() + "을(를) 먹었다 (허기 +" + recover + ")");
                break;

            case "2":
                // 선물하기 → FRIEND 관계인 고양이 목록 표시
                List<RelationshipDTO> friends = friendService.getMyRelationships(catId)
                        .stream()
                        .filter(r -> "FRIEND".equals(r.getRelType()))
                        .collect(Collectors.toList());

                if (friends.isEmpty()) {
                    mainView.printMessage("선물할 친구가 없다냥...");
                    return;
                }
                itemView.printFriendListForGift(friends);
                String friendInput = scanner.nextLine().trim();
                try {
                    int fIdx = Integer.parseInt(friendInput) - 1;
                    if (fIdx < 0 || fIdx >= friends.size()) return;
                    RelationshipDTO friend = friends.get(fIdx);
                    itemService.giftItem(catId, selected.getItemId());
                    // 선물하면 라이벌도 뉴트럴로, 친구면 기분 상승 (기분 수치는 CatService에서)
                    catService.applyEatItem(catId, 0); // 기분만 +5 반영
                    itemView.printGiftResult(selected.getItemName(), friend.getNpcName());
                    walkService.writeLog(catId, gameDate, "GIFT",
                            friend.getNpcName() + "에게 " + selected.getItemName() + "을(를) 선물했다");
                } catch (NumberFormatException e) {
                    mainView.printMessage("숫자를 입력해다냥.");
                }
                break;

            default:
                mainView.printMessage("취소했다냥.");
        }
    }
}
