package com.example.project.jwt.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mst_user")
public class Mst_User {
	
		@Id
		private Integer userid;
		private String username;
		private String email;
		private String password;
		
		public Mst_User() {}
		
		public Integer getUserid() {
			return userid;
		}
		public void setUserid(Integer userid) {
			this.userid = userid;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Mst_User(Integer userid, String username, String email, String password) {
			super();
			this.userid = userid;
			this.username = username;
			this.email = email;
			this.password = password;
		}
		@Override
		public String toString() {
			return "Mst_User [userid=" + userid + ", username=" + username + ", email=" + email + ", password="
					+ password + "]";
		}
	
}
