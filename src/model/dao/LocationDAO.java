package model.dao;

import common.DBUtil;
import model.dto.LocationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 장소 관련 DB 작업을 담당하는 DAO
 * LOCATION 테이블에 대한 조회 처리
 */
public class LocationDAO {

    /**
     * 전체 장소 목록을 조회한다
     * 이동할 장소를 선택할 때 사용
     */
    public List<LocationDTO> selectAllLocations() throws SQLException {
        String sql = "SELECT * FROM LOCATION ORDER BY LOCATION_ID";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<LocationDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new LocationDTO(
                    rs.getInt("LOCATION_ID"),
                    rs.getString("NAME"),
                    rs.getString("ZONE_TYPE"),
                    rs.getInt("CAT_APPEAR_RATE"),
                    rs.getInt("ITEM_FIND_RATE")
                ));
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
        return list;
    }

    /**
     * locationId로 특정 장소를 조회한다
     */
    public LocationDTO selectLocationById(int locationId) throws SQLException {
        String sql = "SELECT * FROM LOCATION WHERE LOCATION_ID = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        LocationDTO location = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, locationId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                location = new LocationDTO(
                    rs.getInt("LOCATION_ID"),
                    rs.getString("NAME"),
                    rs.getString("ZONE_TYPE"),
                    rs.getInt("CAT_APPEAR_RATE"),
                    rs.getInt("ITEM_FIND_RATE")
                );
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
        return location;
    }
}
