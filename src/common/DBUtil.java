package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 데이터베이스 연결을 관리하는 유틸리티 클래스
 * 모든 DAO에서 이 클래스를 통해 Connection을 얻어 사용한다
 */
public class DBUtil {

    // Oracle DB 접속 정보 - 본인 환경에 맞게 수정
    private static final String URL      = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER     = "your_username";
    private static final String PASSWORD = "your_password";

    static {
        try {
            // JDBC 드라이버 로드 (ojdbc11.jar 필요)
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle 드라이버를 찾을 수 없습니다: " + e.getMessage());
        }
    }

    /**
     * DB 연결 객체를 반환한다
     * 사용 후 반드시 close() 호출 필요
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Connection을 안전하게 닫는다 (null 체크 포함)
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
