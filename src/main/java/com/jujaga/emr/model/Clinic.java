package com.jujaga.emr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Clinic
 *
 */
@Entity
public class Clinic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	private String clinicName;
	private String clinicAddress;
	private String clinicCity;
	private String clinicPostal;
	private String clinicPhone;
	private String clinicFax;
	private String clinicLocationCode;
	private String status;
	private String clinicProvince;
	private String clinicDelimPhone;
	private String clinicDelimFax;

	public Clinic() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getClinicAddress() {
		return clinicAddress;
	}

	public void setClinicAddress(String clinicAddress) {
		this.clinicAddress = clinicAddress;
	}

	public String getClinicCity() {
		return clinicCity;
	}

	public void setClinicCity(String clinicCity) {
		this.clinicCity = clinicCity;
	}

	public String getClinicPostal() {
		return clinicPostal;
	}

	public void setClinicPostal(String clinicPostal) {
		this.clinicPostal = clinicPostal;
	}

	public String getClinicPhone() {
		return clinicPhone;
	}

	public void setClinicPhone(String clinicPhone) {
		this.clinicPhone = clinicPhone;
	}

	public String getClinicFax() {
		return clinicFax;
	}

	public void setClinicFax(String clinicFax) {
		this.clinicFax = clinicFax;
	}

	public String getClinicLocationCode() {
		return clinicLocationCode;
	}

	public void setClinicLocationCode(String clinicLocationCode) {
		this.clinicLocationCode = clinicLocationCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClinicProvince() {
		return clinicProvince;
	}

	public void setClinicProvince(String clinicProvince) {
		this.clinicProvince = clinicProvince;
	}

	public String getClinicDelimPhone() {
		return clinicDelimPhone;
	}

	public void setClinicDelimPhone(String clinicDelimPhone) {
		this.clinicDelimPhone = clinicDelimPhone;
	}

	public String getClinicDelimFax() {
		return clinicDelimFax;
	}

	public void setClinicDelimFax(String clinicDelimFax) {
		this.clinicDelimFax = clinicDelimFax;
	}
}
