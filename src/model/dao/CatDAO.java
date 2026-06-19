package model.dao;

import common.DBUtil;
import model.dto.CatDTO;

import java.sql.*;

/**
 * 고양이 캐릭터 관련 DB 작업을 담당하는 DAO
 * CAT 테이블에 대한 CRUD 처리
 */
public class CatDAO {

    /**
     * 새 고양이 캐릭터를 DB에 저장한다 (게임 시작 시 1회 호출)
     */
    public void insertCat(CatDTO cat) throws SQLException {
        String sql = "INSERT INTO CAT (CAT_ID, NAME, COLOR, PERSONALITY, HUNGER, FATIGUE, MOOD, LOCATION_ID) "
                   + "VALUES (CAT_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cat.getName());
            pstmt.setString(2, cat.getColor());
            pstmt.setString(3, cat.getPersonality());
            pstmt.setInt(4, cat.getHunger());
            pstmt.setInt(5, cat.getFatigue());
            pstmt.setInt(6, cat.getMood());
            pstmt.setInt(7, cat.getLocationId());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }

    /**
     * catId로 고양이 정보를 조회한다
     */
    public CatDTO selectCatById(int catId) throws SQLException {
        String sql = "SELECT * FROM CAT WHERE CAT_ID = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CatDTO cat = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, catId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cat = new CatDTO(
                    rs.getInt("CAT_ID"),
                    rs.getString("NAME"),
                    rs.getString("COLOR"),
                    rs.getString("PERSONALITY"),
                    rs.getInt("HUNGER"),
                    rs.getInt("FATIGUE"),
                    rs.getInt("MOOD"),
                    rs.getInt("LOCATION_ID")
                );
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
        return cat;
    }

    /**
     * 고양이 상태 수치(허기, 피로, 기분)를 업데이트한다
     * 이동하거나 아이템을 먹을 때 호출
     */
    public void updateCatStatus(CatDTO cat) throws SQLException {
        String sql = "UPDATE CAT SET HUNGER=?, FATIGUE=?, MOOD=? WHERE CAT_ID=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cat.getHunger());
            pstmt.setInt(2, cat.getFatigue());
            pstmt.setInt(3, cat.getMood());
            pstmt.setInt(4, cat.getCatId());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }

    /**
     * 고양이의 현재 위치를 업데이트한다
     * 장소 이동 시 호출
     */
    public void updateCatLocation(int catId, int locationId) throws SQLException {
        String sql = "UPDATE CAT SET LOCATION_ID=? WHERE CAT_ID=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, locationId);
            pstmt.setInt(2, catId);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }
}
