package model.service;

import model.dao.CatDAO;
import model.dto.CatDTO;

import java.sql.SQLException;

/**
 * 고양이 캐릭터 관련 비즈니스 로직을 담당하는 Service
 * Controller와 DAO 사이에서 로직 처리
 */
public class CatService {

    private CatDAO catDAO = new CatDAO();

    /**
     * 게임 시작 시 고양이 캐릭터를 생성한다
     * 초기 수치: 허기 70, 피로 10, 기분 70, 시작 위치 1번 장소
     */
    public int createCat(String name, String color, String personality) throws SQLException {
        CatDTO cat = new CatDTO();
        cat.setName(name);
        cat.setColor(color);
        cat.setPersonality(personality);
        cat.setHunger(70);
        cat.setFatigue(10);
        cat.setMood(70);
        cat.setLocationId(1);
        return catDAO.insertCat(cat); // ID 반환
    }

    /**
     * 고양이 정보를 조회한다
     */
    public CatDTO getCat(int catId) throws SQLException {
        return catDAO.selectCatById(catId);
    }

    /**
     * 이동 후 피로도를 증가시킨다
     * 이동할 때마다 피로 +10, 기분 -5
     */
    public void applyMoveFatigue(int catId) throws SQLException {
        CatDTO cat = catDAO.selectCatById(catId);
        cat.setFatigue(Math.min(100, cat.getFatigue() + 10));
        cat.setMood(Math.max(0, cat.getMood() - 5));
        catDAO.updateCatStatus(cat);
    }

    /**
     * 아이템 먹기 후 허기를 회복시킨다
     *
     * @param recover 아이템의 허기 회복량
     */
    public void applyEatItem(int catId, int recover) throws SQLException {
        CatDTO cat = catDAO.selectCatById(catId);
        cat.setHunger(Math.min(100, cat.getHunger() + recover));
        cat.setMood(Math.min(100, cat.getMood() + 5));
        catDAO.updateCatStatus(cat);
    }

    /**
     * 잠자기 후 피로도를 회복시킨다 (하루 종료 시)
     * 피로 0, 기분 +20 으로 회복
     */
    public void applySleep(int catId) throws SQLException {
        CatDTO cat = catDAO.selectCatById(catId);
        cat.setFatigue(0);
        cat.setMood(Math.min(100, cat.getMood() + 20));
        catDAO.updateCatStatus(cat);
    }

    /**
     * 고양이의 현재 위치를 변경한다
     */
    public void moveToLocation(int catId, int locationId) throws SQLException {
        catDAO.updateCatLocation(catId, locationId);
    }
}
