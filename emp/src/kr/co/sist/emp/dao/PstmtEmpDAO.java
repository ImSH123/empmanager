package kr.co.sist.emp.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kr.co.sist.emp.dto.DeptDTO;
import kr.co.sist.emp.dto.EmpDTO;
import kr.co.sist.emp.dto.PositionDTO;


public class PstmtEmpDAO {
	
    private static PstmtEmpDAO pmEmpDAO;
	
	private PstmtEmpDAO() {
	}
	
	public static PstmtEmpDAO getInstance() {
		if(pmEmpDAO == null) {
			pmEmpDAO= new PstmtEmpDAO();
		}
		return pmEmpDAO;
	}


	public List<DeptDTO> selectAllDepartments() throws SQLException, IOException {
		List<DeptDTO> list = new ArrayList<>();
		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = gc.getConn();
			
            // [수정] 
            // 'N' (String) 대신 0 (Number)으로 변경
            // (DB의 DELETE_YN 컬럼이 NUMBER 타입이고, 0이 'N'을 의미한다고 가정)
			String selectAllDepts = "select DEPT_CODE, DNAME from department where DELETE_YN = 0";
            
			pstmt = con.prepareStatement(selectAllDepts);
			rs = pstmt.executeQuery(); 
			
			while(rs.next()) {
				DeptDTO dDTO = new DeptDTO(rs.getInt("DEPT_CODE"), rs.getString("DNAME"));
				list.add(dDTO);
			}
		} finally {
			gc.dbClose(con, pstmt, rs);
		}
		return list;
	}

	// ... (selectAllPositions, selectEmployee, updateEmployee, updateRetireEmployee 메서드는 동일) ...
    
    public List<PositionDTO> selectAllPositions() throws SQLException, IOException {
		List<PositionDTO> list = new ArrayList<>();
		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = gc.getConn();
			String selectAllPos = "select POS_CODE, PNAME from position";
			pstmt = con.prepareStatement(selectAllPos);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PositionDTO pDTO = new PositionDTO(rs.getInt("POS_CODE"), rs.getString("PNAME"));
				list.add(pDTO);
			}
		} finally {
			gc.dbClose(con, pstmt, rs);
		}
		return list;
	}
    
	public EmpDTO selectEmployee(int empNo) throws SQLException, IOException {
		EmpDTO eDTO = null;
		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = gc.getConn();
			
			StringBuilder selectEmp = new StringBuilder();
			selectEmp
			.append("	select e.EMP_ID, e.NAME as emp_name, e.TEL, e.ADDR, s.SAL as sal, e.HIRE_DATE, e.RETIRE_DATE, ")
			.append("	       e.AUTH_TYPE, e.DEPT_CODE, d.DNAME as dept_name, e.POS_CODE, "
					+ "		   p.PNAME as position_name, e.VAC_DAYS as own_vacation			 ")
			.append("	from   employee e, department d, position p, salary s ")
			.append("	where  e.DEPT_CODE = d.DEPT_CODE and e.POS_CODE = p.POS_CODE and e.SAL_CODE = s.SAL_CODE ")
            .append("   and e.EMP_ID = ? ");
			
			pstmt = con.prepareStatement(selectEmp.toString());
			pstmt.setInt(1, empNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				eDTO = new EmpDTO();
				eDTO.setEmpNo(rs.getInt("EMP_ID"));
				eDTO.setEmpName(rs.getString("emp_name"));
				eDTO.setTel(rs.getString("TEL"));
				eDTO.setAddress(rs.getString("ADDR"));
				eDTO.setSal(rs.getInt("sal"));
				eDTO.setHireDate(rs.getDate("HIRE_DATE"));
				eDTO.setRetireDate(rs.getDate("RETIRE_DATE"));
				eDTO.setAuth(rs.getInt("AUTH_TYPE"));
				eDTO.setDeptNo(rs.getInt("DEPT_CODE"));
				eDTO.setDeptName(rs.getString("dept_name"));
				eDTO.setPositionCode(rs.getInt("POS_CODE"));
				eDTO.setPositionName(rs.getString("position_name"));
				eDTO.setVacationDays(rs.getInt("own_vacation"));
				
			}
		} finally {
			gc.dbClose(con, pstmt, rs);
		}
		return eDTO;
	}

	public int updateEmployee(EmpDTO eDTO) throws SQLException, IOException {
		int flag = 0;
		GetConnection gc=GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con=gc.getConn();
			
			StringBuilder updateEmp = new StringBuilder();
			updateEmp
			.append("	update employee		")
			.append("	set	   DEPT_CODE=?, POS_CODE=?, TEL=?, ADDR=?, AUTH_TYPE=?, VAC_DAYS=?  ") 
			.append("	where  EMP_ID=?	    ");
			pstmt = con.prepareStatement(updateEmp.toString());
			
			pstmt.setInt(1, eDTO.getDeptNo());
			pstmt.setInt(2, eDTO.getPositionCode());
			pstmt.setString(3, eDTO.getTel());
			pstmt.setString(4, eDTO.getAddress());
			pstmt.setInt(5, eDTO.getAuth());
			pstmt.setInt(6, eDTO.getVacationDays());
			pstmt.setInt(7, eDTO.getEmpNo());
			
			flag=pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		}
		return flag;
	}

	public int deleteEmployee(int empNo) throws SQLException, IOException {
		int flag = 0;
		GetConnection gc = GetConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = gc.getConn();
			String updateRetire = "update employee set RETIRE_DATE = sysdate where EMP_ID = ?";
			pstmt = con.prepareStatement(updateRetire);
			pstmt.setInt(1, empNo);
			flag = pstmt.executeUpdate();
		} finally {
			gc.dbClose(con, pstmt, null);
		}
		return flag;
	}
}//class