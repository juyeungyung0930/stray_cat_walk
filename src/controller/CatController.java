package controller;

import model.service.CatService;
import view.CatView;

/**
 * 고양이 캐릭터 생성/조회 흐름을 제어하는 컨트롤러
 * 현재는 MainController에서 직접 처리하고 있으나
 * 캐릭터 관련 기능이 늘어날 경우 이 클래스로 위임한다
 */
public class CatController {

    private CatService catService = new CatService();
    private CatView    catView    = new CatView();

    // TODO: 캐릭터 상세 조회, 수치 확인 등 기능 추가 예정
}
