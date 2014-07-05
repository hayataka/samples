package github.com.hayataka.beanscopytest.Dto;

import java.util.Date;

/**
 * for src class
 */
public class ClassA {

	private String userId;
	private String name;
	private String mail;
	private String address;
	private Date birthday;
	private int   size;
	private int   weight;
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[userId=").append(userId).append(", name=")
				.append(name).append(", mail=").append(mail)
				.append(", address=").append(address).append(", birthday=")
				.append(birthday).append(", size=").append(size)
				.append(", weight=").append(weight).append("]");
		return builder.toString();
	}
}
