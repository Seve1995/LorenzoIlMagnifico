package it.polimi.ingsw.pc22.connection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable
{
	@SerializedName("username")
	private String username;
	@SerializedName("password")
	private String password;

	private boolean isLogged = false;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean logged) {
		isLogged = logged;
	}

	public User(String username, String password, boolean isLogged)
	{
		this.username = username;
		this.password = password;
		this.isLogged = isLogged;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	
}
