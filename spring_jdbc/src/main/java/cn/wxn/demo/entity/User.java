package cn.wxn.demo.entity;

import java.util.Date;

public class User {

	private Long id;

	private String username;

	private String gender;

	private String nickname;

	private Date birthday;

	private Role role;

	public User() {
		super();
	}

	public User(String username, String gender, String nickname, Date birthday) {
		super();
		this.username = username;
		this.gender = gender;
		this.nickname = nickname;
		this.birthday = birthday;
	}

	public User(String username, String gender, String nickname, Date birthday, Role role) {
		super();
		this.username = username;
		this.gender = gender;
		this.nickname = nickname;
		this.birthday = birthday;
		this.role = role;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id="+id+", username=" + username + ", gender=" + gender + ", nickname=" + nickname + ", birthday=" + birthday
				+ (role==null?"":(", gid="+role.getId())) + "]";
	}
 
}
