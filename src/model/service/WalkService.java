package model.service;

import common.RandomUtil;
import model.dao.LocationDAO;
import model.dao.WalkLogDAO;
import model.dto.LocationDTO;
import model.dto.WalkLogDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * 산책(이동, 일지 기록) 관련 비즈니스 로직을 담당하는 Service
 */
public class WalkService {

    private LocationDAO locationDAO = new LocationDAO();
    private WalkLogDAO  walkLogDAO  = new WalkLogDAO();

    /**
     * 이동 가능한 전체 장소 목록을 반환한다
     */
    public List<LocationDTO> getAllLocations() throws SQLException {
        return locationDAO.selectAllLocations();
    }

    /**
     * 장소 ID로 장소 정보를 반환한다
     */
    public LocationDTO getLocation(int locationId) throws SQLException {
        return locationDAO.selectLocationById(locationId);
    }

    /**
     * 해당 장소에서 고양이가 출현하는지 확률 판정한다
     * LocationDTO의 catAppearRate를 사용
     */
    public boolean rollCatAppear(LocationDTO location) {
        return RandomUtil.isSuccess(location.getCatAppearRate());
    }

    /**
     * 해당 장소에서 아이템이 발견되는지 확률 판정한다
     * LocationDTO의 itemFindRate를 사용
     */
    public boolean rollItemFind(LocationDTO location) {
        return RandomUtil.isSuccess(location.getItemFindRate());
    }

    /**
     * 산책 일지에 행동을 기록한다
     *
     * @param catId       내 고양이 ID
     * @param gameDate    게임 내 현재 날짜
     * @param actionType  행동 종류 (MOVE / EAT / MARK / MEET / GIFT)
     * @param description 행동 설명 텍스트
     */
    public void writeLog(int catId, Date gameDate, String actionType, String description)
            throws SQLException {
        WalkLogDTO log = new WalkLogDTO();
        log.setCatId(catId);
        log.setLogDate(gameDate);
        log.setActionType(actionType);
        log.setDescription(description);
        walkLogDAO.insertLog(log);
    }

    /**
     * 특정 날짜의 산책 일지를 조회한다
     */
    public List<WalkLogDTO> getLogs(int catId, Date date) throws SQLException {
        return walkLogDAO.selectLogsByDate(catId, date);
    }
}
