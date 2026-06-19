package model.dao;

import common.DBUtil;
import model.dto.RelationshipDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 고양이 관계 관련 DB 작업을 담당하는 DAO
 * RELATIONSHIP 테이블에 대한 CRUD 처리
 */
public class RelationshipDAO {

    /**
     * 새 관계를 등록한다 (처음 만났을 때)
     * 성격에 따라 relType이 결정되어 들어옴 (Service에서 판정)
     */
    public void insertRelationship(RelationshipDTO rel) throws SQLException {
        String sql = "INSERT INTO RELATIONSHIP (REL_ID, CAT_ID, NPC_ID, REL_TYPE, MET_AT) "
                   + "VALUES (REL_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, rel.getCatId());
            pstmt.setInt(2, rel.getNpcId());
            pstmt.setString(3, rel.getRelType());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }

    /**
     * 관계 타입을 변경한다 (영역 침범 등으로 관계가 바뀔 때)
     * 예: NEUTRAL → RIVAL, RIVAL → FRIEND
     */
    public void updateRelType(int catId, int npcId, String newRelType) throws SQLException {
        String sql = "UPDATE RELATIONSHIP SET REL_TYPE=? WHERE CAT_ID=? AND NPC_ID=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newRelType);
            pstmt.setInt(2, catId);
            pstmt.setInt(3, npcId);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }

    /**
     * 내 고양이의 전체 관계 목록을 조회한다 (친구 목록 등에 사용)
     */
    public List<RelationshipDTO> selectRelationshipsByCatId(int catId) throws SQLException {
        String sql = "SELECT R.*, F.NAME AS NPC_NAME "
                   + "FROM RELATIONSHIP R JOIN FRIEND_CAT F ON R.NPC_ID = F.NPC_ID "
                   + "WHERE R.CAT_ID = ? ORDER BY R.REL_TYPE, R.MET_AT";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<RelationshipDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, catId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RelationshipDTO rel = new RelationshipDTO(
                    rs.getInt("REL_ID"),
                    rs.getInt("CAT_ID"),
                    rs.getInt("NPC_ID"),
                    rs.getString("REL_TYPE"),
                    rs.getDate("MET_AT")
                );
                rel.setNpcName(rs.getString("NPC_NAME"));
                list.add(rel);
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
        return list;
    }

    /**
     * 특정 NPC와의 관계를 조회한다
     * 이미 아는 사이인지 확인할 때 사용
     */
    public RelationshipDTO selectRelationship(int catId, int npcId) throws SQLException {
        String sql = "SELECT * FROM RELATIONSHIP WHERE CAT_ID=? AND NPC_ID=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        RelationshipDTO rel = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, catId);
            pstmt.setInt(2, npcId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rel = new RelationshipDTO(
                    rs.getInt("REL_ID"),
                    rs.getInt("CAT_ID"),
                    rs.getInt("NPC_ID"),
                    rs.getString("REL_TYPE"),
                    rs.getDate("MET_AT")
                );
            }
        } finally {
            if (rs    != null) rs.close();
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
        return rel;
    }
}
