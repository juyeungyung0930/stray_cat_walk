package model.dao;

import common.DBUtil;
import model.dto.FriendCatDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NPC 고양이 관련 DB 작업을 담당하는 DAO
 * FRIEND_CAT 테이블에 대한 조회 처리
 */
public class FriendDAO {

    /**
     * 특정 장소에 있는 NPC 고양이 목록을 조회한다
     * 장소 이동 후 고양이 출현 판정에 사용
     */
    public List<FriendCatDTO> selectCatsByLocation(int locationId) throws SQLException {
        String sql = "SELECT * FROM FRIEND_CAT WHERE LOCATION_ID = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FriendCatDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, locationId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new FriendCatDTO(
                    rs.getInt("NPC_ID"),
                    rs.getString("NAME"),
                    rs.getString("COLOR"),
                    rs.getString("PERSONALITY"),
                    rs.getInt("LOCATION_ID")
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
     * 친구 고양이의 현재 위치를 조회한다
     * 친구 위치 조회 기능에서 사용
     */
    public FriendCatDTO selectFriendCatById(int npcId) throws SQLException {
        String sql = "SELECT F.*, L.NAME AS LOC_NAME "
                   + "FROM FRIEND_CAT F JOIN LOCATION L ON F.LOCATION_ID = L.LOCATION_ID "
                   + "WHERE F.NPC_ID = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FriendCatDTO cat = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, npcId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cat = new FriendCatDTO(
                    rs.getInt("NPC_ID"),
                    rs.getString("NAME"),
                    rs.getString("COLOR"),
                    rs.getString("PERSONALITY"),
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
}
