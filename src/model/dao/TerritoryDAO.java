package model.dao;

import common.DBUtil;
import model.dto.TerritoryDTO;

import java.sql.*;

/**
 * 영역(마킹) 관련 DB 작업을 담당하는 DAO
 * TERRITORY 테이블에 대한 CRUD 처리
 */
public class TerritoryDAO {

    /**
     * 특정 장소의 마킹 정보를 조회한다
     * 장소 이동 시 내 구역인지, 남의 구역인지, 미개척지인지 확인
     * @return 마킹 정보가 없으면 null 반환
     */
    public TerritoryDTO selectTerritoryByLocation(int locationId) throws SQLException {
        String sql = "SELECT T.*, L.NAME AS LOC_NAME "
                   + "FROM TERRITORY T JOIN LOCATION L ON T.LOCATION_ID = L.LOCATION_ID "
                   + "WHERE T.LOCATION_ID = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TerritoryDTO territory = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, locationId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                territory = new TerritoryDTO(
                    rs.getInt("TERRITORY_ID"),
                    rs.getInt("LOCATION_ID"),
                    rs.getInt("OWNER_CAT_ID"),
                    rs.getDate("MARKED_AT")
                );
                territory.setLocationName(rs.getString("LOC_NAME"));
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
        return territory;
    }

    /**
     * 새 영역 마킹을 등록한다 (미개척지에서 마킹할 때)
     */
    public void insertTerritory(TerritoryDTO territory) throws SQLException {
        String sql = "INSERT INTO TERRITORY (TERRITORY_ID, LOCATION_ID, OWNER_CAT_ID, MARKED_AT) "
                   + "VALUES (TERRITORY_SEQ.NEXTVAL, ?, ?, SYSDATE)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, territory.getLocationId());
            pstmt.setInt(2, territory.getOwnerCatId());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }

    /**
     * 영역 주인을 변경한다 (침범 성공 시)
     */
    public void updateTerritoryOwner(int locationId, int newOwnerCatId) throws SQLException {
        String sql = "UPDATE TERRITORY SET OWNER_CAT_ID=?, MARKED_AT=SYSDATE WHERE LOCATION_ID=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newOwnerCatId);
            pstmt.setInt(2, locationId);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }
}
