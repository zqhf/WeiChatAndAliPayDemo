package com.example.model;

import java.io.Serializable;

public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String username;

    private String nickname;

    private String pwdMd5;

    private Integer age;

    private Integer gender;

    private String mail;

    private String phoneNumber;
    
    private Role role;

	public User() {
   	}
    
    public User(String username, String nickname, String pwdMd5, Integer age,
			Integer gender, String mail, String phoneNumber) {
		super();
		this.username = username;
		this.nickname = nickname;
		this.pwdMd5 = pwdMd5;
		this.age = age;
		this.gender = gender;
		this.mail = mail;
		this.phoneNumber = phoneNumber;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPwdMd5() {
        return pwdMd5;
    }

    public void setPwdMd5(String pwdMd5) {
        this.pwdMd5 = pwdMd5 == null ? null : pwdMd5.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

	public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }
    
    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", nickname="
				+ nickname + ", pwdMd5=" + pwdMd5 + ", age=" + age
				+ ", gender=" + gender + ", mail=" + mail + ", phoneNumber="
				+ phoneNumber + "]";
	}
}