package cn.wxn.demo.cms_core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_user")
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String username;
	
	private String nickname;
	
	private String password;
	
	private String phone;
	
	private String email;
	
	private int status;
	
	@Column(name="create_date")
	private Date createDate;

	public User() {
		super();
	}

	public User(Integer id,String username, String nickname, String password, String phone, String email, int status,
			Date createDate) {
		super();
		this.id =id;
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.status = status;
		this.createDate = createDate;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", nickname=" + nickname + ", password=" + password
				+ ", phone=" + phone + ", email=" + email + ", status=" + status + ", createDate=" + createDate + "]";
	}
	
}
