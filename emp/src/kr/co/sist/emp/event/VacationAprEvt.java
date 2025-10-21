package kr.co.sist.emp.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import kr.co.sist.emp.design.VacationAprDesign;
import kr.co.sist.emp.service.VacationAprService;

public class VacationAprEvt extends WindowAdapter implements ActionListener {

	private VacationAprDesign vad;
	private VacationAprService vas;
	private VacationMgmEvt vme;
	private int useId;

	public VacationAprEvt(VacationAprDesign vad, int useId, VacationMgmEvt vme) {
		this.vad = vad;
		this.useId = useId;
		this.vme = vme;
		this.vas = new VacationAprService();
		
		
		addListeners();
	}
	
	private void addListeners() {
		vad.getJbtnConfirm().addActionListener(this);
		vad.getJbtnCancel().addActionListener(this);
		vad.addWindowListener(this);
	}

	public void approveVacation() {
		String approveStatusText = "보류"; // UI 표시용
		String approveStatusCode = "P";  // DB 저장용 ('Y', 'N', 'P')
		
		if(vad.getJrbConfirm().isSelected()) {
			approveStatusText = "승인";
			approveStatusCode = "Y";
		} else if (vad.getJrbDeny().isSelected()) {
			approveStatusText = "반려";
			approveStatusCode = "N";
		}
		
		boolean flag = vas.aprVacation(useId, approveStatusCode); // DB에는 코드 전달
		
		if(flag) {
			JOptionPane.showMessageDialog(vad, "휴가 상태가 [" + approveStatusText + "] (으)로 변경되었습니다.");
			vme.infoVacation();
			vad.dispose();
		} else {
			JOptionPane.showMessageDialog(vad, "휴가 상태 변경에 실패했습니다.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == vad.getJbtnConfirm()) {
			approveVacation();
		}
		if(ae.getSource() == vad.getJbtnCancel()) {
			vad.dispose();
		}
	}

	@Override
	public void windowClosing(WindowEvent we) {
		vad.dispose();
	}
}