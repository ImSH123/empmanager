package kr.co.sist.emp.dto;

import java.sql.Date;

public class EmpDTO {
	private int deptNo, empNo, auth, sal, positionCode, vacationDays; 
	private String deptName, empName, tel, address, status, positionName;
	private Date hireDate, retireDate;
	
	public EmpDTO() {
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public int getSal() {
		return sal;
	}

	public void setSal(int sal) {
		this.sal = sal;
	}

	public int getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(int positionCode) {
		this.positionCode = positionCode;
	}

	public int getVacationDays() {
		return vacationDays;
	}

	public void setVacationDays(int vacationDays) {
		this.vacationDays = vacationDays;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}

	@Override
	public String toString() {
		return "EmpDTO [deptNo=" + deptNo + ", empNo=" + empNo + ", auth=" + auth + ", sal=" + sal + ", positionCode="
				+ positionCode + ", vacationDays=" + vacationDays + ", deptName=" + deptName + ", empName=" + empName
				+ ", tel=" + tel + ", address=" + address + ", status=" + status + ", positionName=" + positionName
				+ ", hireDate=" + hireDate + ", retireDate=" + retireDate + "]";
	}

	
	
}//class