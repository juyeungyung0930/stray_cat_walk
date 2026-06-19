package model.dao;

import common.DBUtil;
import model.dto.WalkLogDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 산책 일지 관련 DB 작업을 담당하는 DAO
 * EVENT_LOG 테이블에 대한 INSERT/SELECT 처리
 */
public class WalkLogDAO {

    /**
     * 산책 일지를 기록한다
     * 행동이 일어날 때마다 호출 (이동, 먹기, 마킹, 만남 등)
     */
    public void insertLog(WalkLogDTO log) throws SQLException {
        String sql = "INSERT INTO EVENT_LOG (LOG_ID, CAT_ID, LOG_DATE, ACTION_TYPE, DESCRIPTION) "
                   + "VALUES (LOG_SEQ.NEXTVAL, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, log.getCatId());
            pstmt.setDate(2, log.getLogDate());
            pstmt.setString(3, log.getActionType());
            pstmt.setString(4, log.getDescription());
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            DBUtil.close(conn);
        }
    }

    /**
     * 특정 날짜의 산책 일지 전체를 조회한다
     * 오늘의 일지 조회 기능에서 사용
     */
    public List<WalkLogDTO> selectLogsByDate(int catId, java.sql.Date date) throws SQLException {
        String sql = "SELECT * FROM EVENT_LOG "
                   + "WHERE CAT_ID=? AND TRUNC(LOG_DATE)=TRUNC(?) "
                   + "ORDER BY LOG_ID";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WalkLogDTO> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, catId);
            pstmt.setDate(2, date);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new WalkLogDTO(
                    rs.getInt("LOG_ID"),
                    rs.getInt("CAT_ID"),
                    rs.getDate("LOG_DATE"),
                    rs.getString("ACTION_TYPE"),
                    rs.getString("DESCRIPTION")
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
