package cn.wxn.demo.cms_core.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_user_group")
public class UserGroup {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "u_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "g_id")
	private Group group;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public UserGroup() {
		super();
	}

	public UserGroup(Integer id, User user, Group group) {
		super();
		this.id = id;
		this.user = user;
		this.group = group;
	}

	@Override
	public String toString() {
		return "UserGroup [id=" + id + ", user=" + user + ", group=" + group + "]";
	}

}
