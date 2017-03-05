package cn.wxn.demo.cms_core.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_role")
public class Role {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;
	 
	/**
	 * 0: 管理员
	 * 1: 文章发布员
	 * 2:文章审核员
	 */
	@Column(name="role_type")
	private Integer roleType;

	@Column(name = "create_date")
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Role() {
		super();
	}

	public Role(Integer id, String name ) {
		super();
		this.id = id;
		this.name = name; 
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", roleType=" + roleType + ", createDate=" + createDate + "]";
	}

}
