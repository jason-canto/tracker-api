package br.com.gps.tracker.dto;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUserDto implements Serializable {

	private static final long serialVersionUID = -8331761513664610195L;

	private String login;
	private String token;

	@JsonProperty("user")
	public void setUser(Map<String, Object> user) {
		login = (String) user.get("login");
		token = (String) user.get("token");
	}
}
