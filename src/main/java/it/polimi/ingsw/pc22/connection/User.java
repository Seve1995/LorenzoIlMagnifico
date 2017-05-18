package it.polimi.ingsw.pc22.connection;

import com.google.gson.annotations.SerializedName;

public class User {
	@SerializedName("username")
	private String username;
	@SerializedName("password")
	private String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUser() {
		return username;
	}
	public void setUser(String user) {
		this.username = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPasswords(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	
}
