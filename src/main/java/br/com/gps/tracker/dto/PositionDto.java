package br.com.gps.tracker.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PositionDto implements Serializable {

	private static final long serialVersionUID = 3326684870708417230L;

	@JsonProperty("id")
	private String plateNumber;

	@JsonProperty("contract")
	private String contractId;

	@JsonProperty("container")
	private String containerId;

	@JsonProperty("gpsLatitude")
	private Double latitude;

	@JsonProperty("gpsLongitude")
	private Double longitude;

	@JsonProperty("gpsDate")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.sssZ", timezone = "UTC")
	private Date date;
}
