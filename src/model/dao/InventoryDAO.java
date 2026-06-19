package model.dao;

import common.DBUtil;
import model.dto.InventoryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 인벤토리 관련 DB 작업을 담당하는 DAO
 * INVENTORY 테이블에 대한 CRUD 처리
 */
public class InventoryDAO {

    /**
     * 내 고양이의 전체 인벤토리를 조회한다
     * 아이템 이름도 함께 보여주기 위해 ITEM 테이블과 JOIN
     */
    public List<InventoryDTO> selectInventoryByCatId(int catId) throws SQLException {
        String sql = "SELECT I.INV_ID, I.CAT_ID, I.ITEM_ID, I.QUANTITY, "
                   + "IT.NAME AS ITEM_NAME, IT.RARITY AS ITEM_RARITY "
                   + "FROM INVENTORY I JOIN ITEM IT ON I.ITEM_ID = IT.ITEM_ID "
                   + "WHERE I.CAT_ID = ? ORDER BY IT.RARITY, IT.NAME";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<InventoryDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, catId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                InventoryDTO inv = new InventoryDTO(
                    rs.getInt("INV_ID"),
                    rs.getInt("CAT_ID"),
                    rs.getInt("ITEM_ID"),
                    rs.getInt("QUANTITY")
                );
                inv.setItemName(rs.getString("ITEM_NAME"));
                inv.setItemRarity(rs.getString("ITEM_RARITY"));
                list.add(inv);
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
        return list;
    }

    /**
     * 아이템을 인벤토리에 추가한다
     * 이미 있으면 수량 증가, 없으면 새 행 INSERT
     */
    public void addItem(int catId, int itemId) throws SQLException {
        // 이미 보유 중인지 확인
        String checkSql = "SELECT INV_ID, QUANTITY FROM INVENTORY WHERE CAT_ID=? AND ITEM_ID=?";
        String updateSql = "UPDATE INVENTORY SET QUANTITY = QUANTITY + 1 WHERE INV_ID=?";
        String insertSql = "INSERT INTO INVENTORY (INV_ID, CAT_ID, ITEM_ID, QUANTITY) "
                         + "VALUES (INV_SEQ.NEXTVAL, ?, ?, 1)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, catId);
            pstmt.setInt(2, itemId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 이미 있으면 수량 +1
                int invId = rs.getInt("INV_ID");
                rs.close(); pstmt.close();
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, invId);
                pstmt.executeUpdate();
            } else {
                // 없으면 새로 INSERT
                rs.close(); pstmt.close();
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, catId);
                pstmt.setInt(2, itemId);
                pstmt.executeUpdate();
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }

    /**
     * 아이템을 인벤토리에서 1개 제거한다 (먹거나 선물할 때)
     * 수량이 0이 되면 해당 행을 삭제
     */
    public void removeItem(int catId, int itemId) throws SQLException {
        String checkSql  = "SELECT INV_ID, QUANTITY FROM INVENTORY WHERE CAT_ID=? AND ITEM_ID=?";
        String updateSql = "UPDATE INVENTORY SET QUANTITY = QUANTITY - 1 WHERE INV_ID=?";
        String deleteSql = "DELETE FROM INVENTORY WHERE INV_ID=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, catId);
            pstmt.setInt(2, itemId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int invId    = rs.getInt("INV_ID");
                int quantity = rs.getInt("QUANTITY");
                rs.close(); pstmt.close();

                if (quantity > 1) {
                    pstmt = conn.prepareStatement(updateSql);
                    pstmt.setInt(1, invId);
                } else {
                    pstmt = conn.prepareStatement(deleteSql);
                    pstmt.setInt(1, invId);
                }
                pstmt.executeUpdate();
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }
}
