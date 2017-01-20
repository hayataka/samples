package com.github.hayataka.logexample.dto;

import java.time.LocalDate;

public class SampleDto {

	private int id;
	
	private String name;
	
	private LocalDate birthday;
	
	private String password; 	
	//TODO DTOのパスワードや、個人氏名をログに出すか否か？どうしますか？

	
	// TODO 定型的なソースコードの省略化について
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(java.time.LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SampleDto [id=" + id + ", name=" + name + ", birthday=" + birthday + ", password=" + password + "]";
	}
	
	
}
