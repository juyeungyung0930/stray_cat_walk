package controller;

import model.dto.FriendCatDTO;
import model.dto.LocationDTO;
import model.dto.RelationshipDTO;
import model.service.FriendService;
import model.service.WalkService;
import view.FriendView;
import view.MainView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * 친구/라이벌 관계 화면 흐름을 제어하는 컨트롤러
 */
public class FriendController {

    private Scanner scanner = new Scanner(System.in);

    private MainView   mainView   = new MainView();
    private FriendView friendView = new FriendView();

    private FriendService friendService = new FriendService();
    private WalkService   walkService   = new WalkService();

    /**
     * 관계 목록을 출력하고 친구 위치 조회를 제공한다
     */
    public void showRelationships(int catId) throws SQLException {
        List<RelationshipDTO> list = friendService.getMyRelationships(catId);
        friendView.printRelationshipList(list);

        if (list.isEmpty()) return;

        mainView.printMessage("\n친구 위치를 조회할까냥? (번호 입력 / 0 = 취소)");
        System.out.print("선택 > ");
        String input = scanner.nextLine().trim();
        if ("0".equals(input)) return;

        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx < 0 || idx >= list.size()) {
                mainView.printMessage("잘못된 번호다냥.");
                return;
            }
            RelationshipDTO rel = list.get(idx);
            // FRIEND 관계만 위치 조회 가능
            if (!"FRIEND".equals(rel.getRelType())) {
                mainView.printMessage("친구인 고양이만 위치를 알 수 있다냥!");
                return;
            }
            FriendCatDTO friend = friendService.getFriendLocation(rel.getNpcId());
            LocationDTO loc = walkService.getLocation(friend.getLocationId());
            friendView.printFriendLocation(friend.getName(), loc.getName());

        } catch (NumberFormatException e) {
            mainView.printMessage("숫자를 입력해다냥.");
        }
    }
}
