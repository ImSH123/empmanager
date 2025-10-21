package kr.co.sist.emp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.emp.design.VacationAprDesign;
import kr.co.sist.emp.design.VacationMgmDesign;
import kr.co.sist.emp.dto.DeptDTO;
import kr.co.sist.emp.dto.VacationMgmDTO;
import kr.co.sist.emp.service.VacationMgmService;

public class VacationMgmEvt extends WindowAdapter implements ActionListener, KeyListener {

	private VacationMgmDesign vmd;
	private VacationMgmService vms;

	public VacationMgmEvt(VacationMgmDesign vmd) {
		this.vmd = vmd;
		this.vms = new VacationMgmService();
		
		addListeners();
		loadDeptComboBox();
		infoVacation();
	}
	
	private void addListeners() {
		vmd.getJbtnSearch().addActionListener(this);
		vmd.getJtfEmpName().addKeyListener(this);
		vmd.getJtVacation().addMouseListener(new JTableMouseHandler());
		vmd.addWindowListener(this);
	}
	
	private void loadDeptComboBox() {
		DefaultComboBoxModel<DeptDTO> model = vmd.getDcbmDept();
		model.addElement(new DeptDTO(-1, "전체"));
		
		List<DeptDTO> list = vms.findAllDepartments();
		for(DeptDTO dDTO : list) {
			model.addElement(dDTO);
		}
	}

	public void infoVacation() {
		List<VacationMgmDTO> list = vms.infoVacation();
		setTableData(list);
	}
	
	public void searchVacation() {
		DeptDTO selectedDept = (DeptDTO) vmd.getJcDept().getSelectedItem();
		String dName = "전체";
		if(selectedDept != null) {
			dName = selectedDept.getDName();
		}
		
		String eName = vmd.getJtfEmpName().getText().trim();
		
		String startDate = vmd.getJcStartYear().getSelectedItem().toString() + "-" +
						   vmd.getJcStartMonth().getSelectedItem().toString() + "-" +
						   vmd.getJcStartDay().getSelectedItem().toString();
		
		String endDate = vmd.getJcEndYear().getSelectedItem().toString() + "-" +
						 vmd.getJcEndMonth().getSelectedItem().toString() + "-" +
						 vmd.getJcEndDay().getSelectedItem().toString();

		List<VacationMgmDTO> list = vms.searchVacation(dName, eName, startDate, endDate);
		setTableData(list);
	}
	
	/**
	 * [수정] JTable에 10개 컬럼 데이터 추가
	 */
	private void setTableData(List<VacationMgmDTO> list) {
		DefaultTableModel dtm = vmd.getDtmVacation();
		dtm.setRowCount(0);
		
		if(list.isEmpty()) {
			JOptionPane.showMessageDialog(vmd, "데이터가 없습니다.");
			return;
		}
		
		for(VacationMgmDTO vmDTO : list) {
			Object[] rowData = {
				vmDTO.getUse_id(),
				vmDTO.getEmp_id(),
				vmDTO.geteName(),
				vmDTO.getdName(),
				vmDTO.getpName(),
				vmDTO.getVtName(),
				vmDTO.getReason(),
				vmDTO.getStartDate(),
				vmDTO.getEndDate(),
				vmDTO.getApprove() // "승인", "반려", "보류"
			};
			dtm.addRow(rowData);
		}
	}
	
	/**
	 * [수정] 9번 컬럼("승인상태")에서 현재 상태 가져옴
	 */
	public void aprVacation() {
		JTable table = vmd.getJtVacation();
		int selectedRow = table.getSelectedRow();
		
		if(selectedRow == -1) {
			return; // 클릭 시 행이 선택되므로 무시
		}
		
		int useId = (int) table.getValueAt(selectedRow, 0); // 신청ID (Col 0)
		String currentStatus = (String) table.getValueAt(selectedRow, 9); // 승인상태 (Col 9)
		
		VacationAprDesign vad = new VacationAprDesign(vmd, currentStatus);
		new VacationAprEvt(vad, useId, this);
		vad.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == vmd.getJbtnSearch()) {
			searchVacation();
		}
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		if(ke.getSource() == vmd.getJtfEmpName() && ke.getKeyCode() == KeyEvent.VK_ENTER) {
			searchVacation();
		}
	}

	@Override
	public void windowClosing(WindowEvent we) {
		vmd.dispose();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	/**
	 * [수정] 9번 컬럼("승인상태") 클릭 감지
	 */
	private class JTableMouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent me) {
			JTable table = (JTable) me.getSource();
			int row = table.rowAtPoint(me.getPoint());
			int col = table.columnAtPoint(me.getPoint());
			
			if(col == 9 && row != -1) { // 9번 컬럼("승인상태") 클릭 확인
				table.setRowSelectionInterval(row, row); // 클릭한 행 선택
				
				// [수정] "보류"가 아니더라도 (즉, "승인" 또는 "반려" 상태여도)
				// 팝업을 띄우도록 if문 제거
				aprVacation();
			}
		}
	}
}