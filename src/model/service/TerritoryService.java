package model.service;

import model.dao.TerritoryDAO;
import model.dto.TerritoryDTO;

import java.sql.SQLException;

/**
 * 영역(마킹) 관련 비즈니스 로직을 담당하는 Service
 * 마킹, 침범 판정, 영역 조회 처리
 */
public class TerritoryService {

    private TerritoryDAO territoryDAO = new TerritoryDAO();

    /**
     * 해당 장소의 마킹 정보를 조회한다
     * @return 마킹 없으면 null
     */
    public TerritoryDTO getTerritoryInfo(int locationId) throws SQLException {
        return territoryDAO.selectTerritoryByLocation(locationId);
    }

    /**
     * 미개척지에 마킹한다 (내 구역으로 등록)
     */
    public void markTerritory(int catId, int locationId) throws SQLException {
        TerritoryDTO territory = new TerritoryDTO();
        territory.setLocationId(locationId);
        territory.setOwnerCatId(catId);
        territoryDAO.insertTerritory(territory);
    }

    /**
     * 다른 고양이 구역을 침범한다
     * 침범 성공 여부는 랜덤(50%) — 성공 시 구역 소유권 변경
     *
     * @return true: 침범 성공, false: 침범 실패
     */
    public boolean invadeTerritory(int catId, int locationId) throws SQLException {
        boolean success = common.RandomUtil.isSuccess(50);
        if (success) {
            territoryDAO.updateTerritoryOwner(locationId, catId);
        }
        return success;
    }

    /**
     * 해당 장소가 내 구역인지 확인한다
     */
    public boolean isMyTerritory(int catId, int locationId) throws SQLException {
        TerritoryDTO territory = territoryDAO.selectTerritoryByLocation(locationId);
        if (territory == null) return false;
        return territory.getOwnerCatId() == catId;
    }
}
