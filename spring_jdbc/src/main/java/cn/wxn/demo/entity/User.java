	package cn.wxn.demo.entity;

public class User {

	
	private Integer id;
	
	private String username;
	
	private String gender;
	
	private String nickname;
	
	
	private Role role;


	public User() {
		super();
	}


	public User(String username, String gender, String nickname ) {
		super();
		this.username = username;
		this.gender = gender;
		this.nickname = nickname;
	}

	public User(String username, String gender, String nickname, Role role) {
		super();
		this.username = username;
		this.gender = gender;
		this.nickname = nickname;
		this.role = role;
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
		return "User [id=" + id + ", username=" + username + ", gender=" + gender + ", nickname=" + nickname + ", role="
				+ role + "]";
	}
	
}
