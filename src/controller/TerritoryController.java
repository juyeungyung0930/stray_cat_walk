package controller;

import model.dto.TerritoryDTO;
import model.service.FriendService;
import model.service.TerritoryService;
import model.service.WalkService;
import view.FriendView;
import view.MainView;

import java.sql.Date;
import java.sql.SQLException;

/**
 * 영역 침범/마킹 흐름을 제어하는 컨트롤러
 */
public class TerritoryController {

    private MainView   mainView   = new MainView();
    private FriendView friendView = new FriendView();

    private TerritoryService territoryService = new TerritoryService();
    private FriendService    friendService    = new FriendService();
    private WalkService      walkService      = new WalkService();

    /**
     * 다른 고양이 구역 침범 흐름을 실행한다
     * 성공 시: 구역 소유권 변경 + 관계가 RIVAL로 악화
     * 실패 시: 그냥 물러남
     *
     * @param territory 침범 대상 영역 정보 (주인 고양이 ID 포함)
     */
    public void invade(int catId, int locationId, TerritoryDTO territory, Date gameDate)
            throws SQLException {
        boolean success = territoryService.invadeTerritory(catId, locationId);

        // 구역 주인이 NPC 고양이인 경우에만 관계 변화
        int ownerNpcId = territory.getOwnerCatId();
        String ownerName = territory.getOwnerName() != null ? territory.getOwnerName() : "낯선 고양이";

        friendView.printInvadeResult(success, territory.getLocationName(), ownerName);

        if (success) {
            // 침범 성공 → 관계가 RIVAL로 변경 (이미 아는 사이인 경우)
            try {
                friendService.changeRelType(catId, ownerNpcId, "RIVAL");
            } catch (Exception e) {
                // 처음 보는 고양이라 관계가 없을 수도 있음 → 무시
            }
            walkService.writeLog(catId, gameDate, "MARK",
                    territory.getLocationName() + " 구역 침범 성공! (" + ownerName + "의 구역 탈취)");
        } else {
            walkService.writeLog(catId, gameDate, "MARK",
                    territory.getLocationName() + " 구역 침범 실패 (" + ownerName + "한테 쫓겨남)");
        }
    }
}
