package controller;

import model.dto.FriendCatDTO;
import model.dto.ItemDTO;
import model.dto.LocationDTO;
import model.dto.TerritoryDTO;
import model.service.CatService;
import model.service.FriendService;
import model.service.ItemService;
import model.service.TerritoryService;
import model.service.WalkService;
import view.FriendView;
import view.ItemView;
import view.MainView;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * 장소 이동 흐름을 제어하는 컨트롤러
 * 이동 → 영역 확인 → 아이템 발견 → 고양이 만남 순으로 진행
 */
public class LocationController {

    private Scanner scanner = new Scanner(System.in);

    private MainView    mainView    = new MainView();
    private FriendView  friendView  = new FriendView();
    private ItemView    itemView    = new ItemView();

    private WalkService      walkService      = new WalkService();
    private CatService       catService       = new CatService();
    private TerritoryService territoryService = new TerritoryService();
    private FriendService    friendService    = new FriendService();
    private ItemService      itemService      = new ItemService();

    /**
     * 장소 이동 전체 흐름을 실행한다
     * 1) 장소 목록 출력 및 선택
     * 2) 이동 (피로도 증가)
     * 3) 영역 상태 확인 (마킹 여부)
     * 4) 아이템 발견 판정
     * 5) 고양이 출현 판정
     */
    public void run(int catId, Date gameDate) throws SQLException {
        // 1) 장소 목록 출력
        List<LocationDTO> locations = walkService.getAllLocations();
        mainView.printMessage("\n[ 이동할 장소를 선택하세요 ]");
        for (int i = 0; i < locations.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, locations.get(i));
        }
        System.out.print("선택 > ");
        int idx;
        try {
            idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= locations.size()) {
                mainView.printMessage("잘못된 선택이다냥.");
                return;
            }
        } catch (NumberFormatException e) {
            mainView.printMessage("숫자를 입력해다냥.");
            return;
        }

        LocationDTO dest = locations.get(idx);

        // 2) 이동 처리
        catService.moveToLocation(catId, dest.getLocationId());
        catService.applyMoveFatigue(catId);
        walkService.writeLog(catId, gameDate, "MOVE", dest.getName() + "으(로) 이동했다");
        mainView.printMessage("\n🐾 " + dest.getName() + "에 도착했다냥!");

        // 3) 영역 확인
        TerritoryDTO territory = territoryService.getTerritoryInfo(dest.getLocationId());
        if (territory == null) {
            // 미개척지 → 마킹 여부 묻기
            mainView.printMessage("  📌 아무도 마킹하지 않은 곳이다. 마킹할까냥? (1.마킹 / 0.패스)");
            if ("1".equals(scanner.nextLine().trim())) {
                territoryService.markTerritory(catId, dest.getLocationId());
                walkService.writeLog(catId, gameDate, "MARK", dest.getName() + "에 마킹했다");
                mainView.printMessage("  마킹 완료! 내 구역이 됐다냥!");
            }
        } else if (territoryService.isMyTerritory(catId, dest.getLocationId())) {
            mainView.printMessage("  😼 내 구역이다냥!");
        } else {
            // 다른 고양이 구역 → 침범 or 피하기 (TerritoryController로 위임)
            mainView.printMessage("  😾 다른 고양이 구역이다! 어떻게 할까냥? (1.침범 / 0.피하기)");
            if ("1".equals(scanner.nextLine().trim())) {
                new TerritoryController().invade(catId, dest.getLocationId(), territory, gameDate);
            } else {
                mainView.printMessage("  살금살금 피해갔다냥...");
            }
        }

        // 4) 아이템 발견 판정
        if (walkService.rollItemFind(dest)) {
            ItemDTO item = itemService.findRandomItem();
            if (item != null) {
                itemView.printFoundItem(item);
                itemService.pickUpItem(catId, item.getItemId());
                walkService.writeLog(catId, gameDate, "EAT",
                        item.getName() + "을(를) 발견했다");
            }
        }

        // 5) 고양이 출현 판정
        if (walkService.rollCatAppear(dest)) {
            List<FriendCatDTO> npcs = friendService.getCatsAtLocation(dest.getLocationId());
            if (!npcs.isEmpty()) {
                FriendCatDTO npc = npcs.get(0); // 여러 마리면 첫 번째만 (확장 가능)
                friendView.printMeetCat(npc);
                // 내 고양이 성격 조회 후 관계 결정
                String myPersonality = catService.getCat(catId).getPersonality();
                String relType = friendService.meetCat(catId, npc, myPersonality);
                friendView.printRelationResult(npc.getName(), relType);
                walkService.writeLog(catId, gameDate, "MEET",
                        npc.getName() + "을(를) 만났다 → " + relType);
            }
        }
    }
}
