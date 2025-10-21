package kr.co.sist.emp.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class VacationAprDAO {

	private static VacationAprDAO vDAO;

	private VacationAprDAO() {
	}

	public static VacationAprDAO getInstance() {
		if (vDAO == null) {
			vDAO = new VacationAprDAO();
		}
		return vDAO;
	}

	public int updateVacation(int use_id, String approve) throws SQLException, IOException {
		int flag = 0;
		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = gc.getConn();
			// `vacation_use` 테이블의 `APPROVE` 컬럼을 `USE_ID` 기준으로 업데이트
			String updateSql = "UPDATE VACATION_USE SET APPROVE = ? WHERE USE_ID = ?";
			pstmt = con.prepareStatement(updateSql);

			pstmt.setString(1, approve);
			pstmt.setInt(2, use_id);

			flag = pstmt.executeUpdate();

		} finally {
			gc.dbClose(con, pstmt, null);
		}
		return flag;
	}
}