package com.megaease.easeagent.demo.dubbo.model;

import java.io.Serializable;

public class User implements Serializable {
	private String name;

	private Address address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public static class Address implements Serializable{
		private Integer provinceCode;
		private Integer cityCode;
		private String exactAddress;

		public Integer getProvinceCode() {
			return provinceCode;
		}

		public void setProvinceCode(Integer provinceCode) {
			this.provinceCode = provinceCode;
		}

		public Integer getCityCode() {
			return cityCode;
		}

		public void setCityCode(Integer cityCode) {
			this.cityCode = cityCode;
		}

		public String getExactAddress() {
			return exactAddress;
		}

		public void setExactAddress(String exactAddress) {
			this.exactAddress = exactAddress;
		}
	}
}
