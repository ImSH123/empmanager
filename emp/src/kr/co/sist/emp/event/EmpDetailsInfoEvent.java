package kr.co.sist.emp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern; // [신규] 정규표현식 Pattern 임포트

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import kr.co.sist.emp.design.EmpDetailsInfoDesign;
import kr.co.sist.emp.dto.DeptDTO;
import kr.co.sist.emp.dto.EmpDTO;
import kr.co.sist.emp.dto.PositionDTO;
import kr.co.sist.emp.service.EmpDetailsInfoService;

public class EmpDetailsInfoEvent extends WindowAdapter implements ActionListener {

	private EmpDetailsInfoDesign edid;
	private EmpDetailsInfoService es;
	private int empNo;
	
	// [신규] 전화번호 정규표현식 (0XX-XXX(X)-XXXX)
	private static final String PHONE_REGEX = "^0\\d{1,2}-\\d{3,4}-\\d{4}$";

	public EmpDetailsInfoEvent(EmpDetailsInfoDesign edid) {
		this.edid = edid;
		this.es = new EmpDetailsInfoService();
		this.empNo = edid.getEmpNo(); 
		
		addListeners();
		loadComboBoxes();     // 1. 콤보박스 데이터 먼저 로드
		loadEmployeeData(); // 2. 사원 정보 로드 (콤보박스 선택)
	}

	private void addListeners() {
		edid.getJbtnSave().addActionListener(this);
		edid.getJbtnCancel().addActionListener(this);
		edid.getJbtnQuit().addActionListener(this);
		edid.addWindowListener(this);
	}

	/**
	 * 부서 및 직급 콤보박스를 DB 데이터로 채웁니다.
	 */
	private void loadComboBoxes() {
		try {
			// 부서 콤보박스 채우기
			List<DeptDTO> deptList = es.findAllDepartments();
			DefaultComboBoxModel<DeptDTO> deptModel = edid.getDcbmDept();
			for(DeptDTO dDTO : deptList) {
				deptModel.addElement(dDTO);
			}
			
			// 직급 콤보박스 채우기
			List<PositionDTO> posList = es.findAllPositions();
			DefaultComboBoxModel<PositionDTO> posModel = edid.getDcbmPosition();
			for(PositionDTO pDTO : posList) {
				posModel.addElement(pDTO);
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edid, "부서/직급 정보 로드 중 오류가 발생했습니다.");
		}
	}

	/**
	 * Service를 통해 사원 정보를 조회하여 UI에 설정
	 */
	private void loadEmployeeData() {
		try {
			EmpDTO eDTO = es.loadEmployee(this.empNo); 

			if (eDTO == null) {
				JOptionPane.showMessageDialog(edid, "사원 정보가 존재하지 않습니다.");
				edid.dispose();
				return;
			}

			// JTextField 설정
			edid.getJtfEmpNo().setText(String.valueOf(eDTO.getEmpNo()));
			edid.getJtfName().setText(eDTO.getEmpName());
			edid.getJtfTel().setText(eDTO.getTel());
			edid.getJtfAddress().setText(eDTO.getAddress());
			edid.getJtfHireDate().setText(formatDate(eDTO.getHireDate()));
			edid.getJtfQuitDate().setText(formatDate(eDTO.getRetireDate()));
			
			// [신규] 보유휴가 설정
			edid.getJtfVacationDays().setText(String.valueOf(eDTO.getVacationDays()));
			
			DecimalFormat df = new DecimalFormat("###,###,###,###원");
			edid.getJtfSal().setText(df.format(eDTO.getSal()));
			
			// 권한 JComboBox 설정
			edid.getJcbAuth().setSelectedIndex(eDTO.getAuth());
			
			// 부서 JComboBox 설정
			DefaultComboBoxModel<DeptDTO> deptModel = edid.getDcbmDept();
			for(int i = 0; i < deptModel.getSize(); i++) {
				DeptDTO dDTO = deptModel.getElementAt(i);
				if(dDTO.getDeptCode() == eDTO.getDeptNo()) {
					deptModel.setSelectedItem(dDTO);
					break;
				}
			}
			
			// 직급 JComboBox 설정
			DefaultComboBoxModel<PositionDTO> posModel = edid.getDcbmPosition();
			for(int i = 0; i < posModel.getSize(); i++) {
				PositionDTO pDTO = posModel.getElementAt(i);
				if(pDTO.getPosCode() == eDTO.getPositionCode()) {
					posModel.setSelectedItem(pDTO);
					break;
				}
			}
			
			// [수정] 퇴사자일 경우 버튼 및 휴가일수 필드 비활성화
			if(eDTO.getRetireDate() != null) {
				edid.getJbtnSave().setEnabled(false);
				edid.getJbtnQuit().setEnabled(false);
				edid.getJtfVacationDays().setEditable(false); // [신규]
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edid, "정보 로드 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}
	
	private String formatDate(Date date) {
		if(date == null) { return "null"; }
		return date.toString();
	}

	/**
	 * '저장' 버튼 클릭 시: JComboBox에서 선택된 코드를 가져와서 수정
	 */
	private void modifyEmployee() {
		int chk = JOptionPane.showConfirmDialog(edid, "정보를 수정하시겠습니까?", "수정 확인", JOptionPane.YES_NO_OPTION);
		if(chk == JOptionPane.YES_OPTION) {
			
			// 1. JComboBox에서 선택된 객체를 가져옴
			DeptDTO selectedDept = (DeptDTO) edid.getJcbDept().getSelectedItem();
			PositionDTO selectedPos = (PositionDTO) edid.getJcbPosition().getSelectedItem();
			
			// 2. JTextField에서 텍스트를 가져옴
			String tel = edid.getJtfTel().getText();
			String address = edid.getJtfAddress().getText();
			int auth = edid.getJcbAuth().getSelectedIndex();
			String vacationDaysStr = edid.getJtfVacationDays().getText();

			// [신규] 3. 유효성 검사 (Validation)
			
			// (1) 전화번호 형식 검사
			if(tel == null || !Pattern.matches(PHONE_REGEX, tel)) {
				JOptionPane.showMessageDialog(edid, "전화번호 형식(예: 010-1234-5678)으로 작성하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return; // 저장 중단
			}

			// (2) 휴가 일수 길이 검사 (3자리 초과 금지)
			if(vacationDaysStr.length() > 3) {
				JOptionPane.showMessageDialog(edid, "휴가 일수는 3자리까지만 가능합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return; // 저장 중단
			}
			
			try {
				EmpDTO eDTO = new EmpDTO();
				
				// [신규] (3) 휴가 일수 숫자 변환 검사
				int vacationDays = 0;
				try {
					vacationDays = Integer.parseInt(vacationDaysStr);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(edid, "보유 휴가일 수는 숫자로만 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
					return; // 저장 중단
				}
				
				// 4. DTO에 값 설정
				eDTO.setEmpNo(this.empNo);
				eDTO.setDeptNo(selectedDept.getDeptCode()); // 부서 코드
				eDTO.setPositionCode(selectedPos.getPosCode()); // 직급 코드
				eDTO.setTel(tel);
				eDTO.setAddress(address);
				eDTO.setAuth(auth);
				eDTO.setVacationDays(vacationDays); // [신규] DTO에 휴가일 수 설정
				
				// 5. Service의 수정 메서드 호출
				int cnt = es.modifyEmployee(eDTO);
				
				if(cnt == 1) {
					JOptionPane.showMessageDialog(edid, "사원 정보가 성공적으로 수정되었습니다.");
					edid.dispose();
				} else {
					JOptionPane.showMessageDialog(edid, "사원 정보 수정에 실패했습니다.");
				}
			} catch (NullPointerException npe) {
				// 콤보박스에 아이템이 하나도 없을 때
				JOptionPane.showMessageDialog(edid, "부서 또는 직급 정보가 선택되지 않았습니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				// (NumberFormatException은 위에서 처리함)
				e.printStackTrace();
				JOptionPane.showMessageDialog(edid, "DB 오류로 수정에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * '퇴사처리' 버튼 클릭 시
	 */
	private void retireEmployee() {
		int chk = JOptionPane.showConfirmDialog(edid, "정말로 퇴사 처리하시겠습니까?", "퇴사 확인", JOptionPane.YES_NO_OPTION);
		if(chk == JOptionPane.YES_OPTION) {
			try {
				int cnt = es.retireEmployee(this.empNo);
				
				if(cnt == 1) {
					JOptionPane.showMessageDialog(edid, "퇴사 처리가 완료되었습니다.");
					loadEmployeeData(); // UI 갱신 (퇴사일 표시, 버튼 비활성화)
				} else {
					JOptionPane.showMessageDialog(edid, "퇴사 처리에 실패했습니다.");
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(edid, "DB 오류로 퇴사 처리에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == edid.getJbtnSave()) {
			modifyEmployee();
		}
		if (ae.getSource() == edid.getJbtnQuit()) {
			retireEmployee();
		}
		if (ae.getSource() == edid.getJbtnCancel()) {
			edid.dispose();
		}
	}

	@Override
	public void windowClosing(WindowEvent we) {
		edid.dispose();
	}
	
}// class