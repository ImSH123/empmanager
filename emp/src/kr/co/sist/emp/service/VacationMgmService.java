package kr.co.sist.emp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import kr.co.sist.emp.dao.VacationMgmDAO;
import kr.co.sist.emp.dto.DeptDTO;
import kr.co.sist.emp.dto.VacationMgmDTO;

public class VacationMgmService {

	private VacationMgmDAO vDAO;

	public VacationMgmService() {
		vDAO = VacationMgmDAO.getInstance();
	}
	
	public List<DeptDTO> findAllDepartments() {
		List<DeptDTO> list = Collections.emptyList();
		try {
			list = vDAO.selectAllDepartments();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<VacationMgmDTO> infoVacation() {
		List<VacationMgmDTO> list = Collections.emptyList();
		try {
			list = vDAO.selectAllVacation();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<VacationMgmDTO> searchVacation(String dName, String eName, String startDate, String endDate) {
		List<VacationMgmDTO> list = Collections.emptyList();
		try {
			list = vDAO.selectSearchVacation(dName, eName, startDate, endDate);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}