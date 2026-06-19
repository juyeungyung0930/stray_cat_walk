package controller;

import model.dto.CatDTO;
import model.dto.LocationDTO;
import model.service.CatService;
import model.service.WalkService;
import view.CatView;
import view.LogView;
import view.MainView;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 게임 전체 흐름을 제어하는 메인 컨트롤러
 * 메뉴 선택을 받아 각 전문 컨트롤러로 분기한다
 */
public class MainController {

    private Scanner scanner = new Scanner(System.in);

    // View
    private MainView mainView = new MainView();
    private CatView  catView  = new CatView();
    private LogView  logView  = new LogView();

    // Service
    private CatService  catService  = new CatService();
    private WalkService walkService = new WalkService();

    // 하위 컨트롤러
    private LocationController locationController = new LocationController();
    private FriendController   friendController   = new FriendController();
    private ItemController     itemController     = new ItemController();
    private TerritoryController territoryController = new TerritoryController();

    // 게임 상태 (세션 동안 유지)
    private int  catId    = 1;       // 현재 플레이어 고양이 ID (DB에서 불러옴)
    private Date gameDate;           // 현재 게임 내 날짜

    /**
     * 게임을 시작한다
     * 캐릭터 생성 → 메인 루프 진행
     */
    public void start() {
        mainView.printTitle();
        gameDate = new Date(System.currentTimeMillis()); // 오늘 날짜로 시작

        try {
            // 고양이 생성
            String name        = catView.inputName();
            String color       = catView.selectColor();
            String personality = catView.selectPersonality();
         // 국희수정
            // 유진 수정
            catId = catService.createCat(name, color, personality);
            catView.printCreateSuccess(name, color, personality);

            // 메인 게임 루프
            mainLoop();

        } catch (SQLException e) {
            mainView.printError("DB 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 메인 게임 루프
     * 0(종료)를 선택할 때까지 반복
     */
    private void mainLoop() throws SQLException {
        while (true) {
            // 현재 상태 출력
            CatDTO cat = catService.getCat(catId);
            LocationDTO location = walkService.getLocation(cat.getLocationId());
            mainView.printStatus(cat, location);
            mainView.printMainMenu();

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    // 장소 이동
                    locationController.run(catId, gameDate);
                    break;
                case "2":
                    // 아이템 인벤토리
                    itemController.run(catId, gameDate);
                    break;
                case "3":
                    // 친구/라이벌 목록
                    friendController.showRelationships(catId);
                    break;
                case "4":
                    // 오늘의 일지
                    logView.printDailyLog(walkService.getLogs(catId, gameDate), gameDate);
                    break;
                case "5":
                    // 잠자기 → 다음 날로
                    catService.applySleep(catId);
                    gameDate = new Date(gameDate.getTime() + 86400000L); // +1일
                    logView.printSleepMessage(gameDate);
                    break;
                case "0":
                    mainView.printMessage("\n냥냥~ 또 만나자!");
                    return;
                default:
                    mainView.printMessage("잘못된 입력이다냥.");
            }
        }
    }
}
