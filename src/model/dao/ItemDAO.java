package model.dao;

import common.DBUtil;
import model.dto.ItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 아이템 마스터 데이터 관련 DB 작업을 담당하는 DAO
 * ITEM 테이블에 대한 조회 처리
 */
public class ItemDAO {

    /**
     * 전체 아이템 목록을 조회한다
     * 랜덤 아이템 발견 시 목록 중 하나를 RandomUtil로 선택
     */
    public List<ItemDTO> selectAllItems() throws SQLException {
        String sql = "SELECT * FROM ITEM ORDER BY ITEM_ID";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ItemDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new ItemDTO(
                    rs.getInt("ITEM_ID"),
                    rs.getString("NAME"),
                    rs.getString("RARITY"),
                    rs.getInt("HUNGER_RECOVER")
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
     * 특정 희귀도의 아이템 목록만 조회한다
     * 장소 등급에 따라 드롭 품질을 조절할 때 활용
     */
    public List<ItemDTO> selectItemsByRarity(String rarity) throws SQLException {
        String sql = "SELECT * FROM ITEM WHERE RARITY = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ItemDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, rarity);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new ItemDTO(
                    rs.getInt("ITEM_ID"),
                    rs.getString("NAME"),
                    rs.getString("RARITY"),
                    rs.getInt("HUNGER_RECOVER")
                ));
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
        return list;
    }
}
