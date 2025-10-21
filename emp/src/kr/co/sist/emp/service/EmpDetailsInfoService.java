package kr.co.sist.emp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import kr.co.sist.emp.dao.PstmtEmpDAO;
import kr.co.sist.emp.dto.DeptDTO;
import kr.co.sist.emp.dto.EmpDTO;
import kr.co.sist.emp.dto.PositionDTO;

public class EmpDetailsInfoService {

	private PstmtEmpDAO pemDAO;

	public EmpDetailsInfoService() {
		pemDAO = PstmtEmpDAO.getInstance();
	}

	/**
	 * DAO를 호출하여 모든 부서 목록을 조회
	 */
	public List<DeptDTO> findAllDepartments() throws SQLException, IOException {
		List<DeptDTO> list = pemDAO.selectAllDepartments();
		return list;
	}
	
	/**
	 * DAO를 호출하여 모든 직급 목록을 조회
	 */
	public List<PositionDTO> findAllPositions() throws SQLException, IOException {
		List<PositionDTO> list = pemDAO.selectAllPositions();
		return list;
	}

	public EmpDTO loadEmployee(int empNo) throws SQLException, IOException {
		EmpDTO eDTO = pemDAO.selectEmployee(empNo);
		return eDTO;
	}

	public int modifyEmployee(EmpDTO eDTO) throws SQLException, IOException {
		int cnt = pemDAO.updateEmployee(eDTO);
		return cnt;
	}

	public int retireEmployee(int empNo) throws SQLException, IOException {
		int cnt = pemDAO.deleteEmployee(empNo);
		return cnt;
	}
}// class