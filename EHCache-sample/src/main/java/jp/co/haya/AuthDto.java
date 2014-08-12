package jp.co.haya;

import java.io.Serializable;

public class AuthDto implements Serializable {

	private String userId;
	private String nameJ;
	private String kana;
	private String mail;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNameJ() {
		return nameJ;
	}
	public void setNameJ(String nameJ) {
		this.nameJ = nameJ;
	}
	public String getKana() {
		return kana;
	}
	public void setKana(String kana) {
		this.kana = kana;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public String toString() {
		return "AuthDto [userId=" + userId + ", nameJ=" + nameJ + ", kana="
				+ kana + ", mail=" + mail + "]";
	}

	
}
