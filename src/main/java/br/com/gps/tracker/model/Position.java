package br.com.gps.tracker.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "gps_tracker")
public class Position implements Serializable {

	private static final long serialVersionUID = -4191629294559993058L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "contract_id")
	private String contractId;

	@NotNull
	@Column(name = "container_id")
	private String containerId;

	@NotNull
	@Column(name = "plate_number")
	private String plateNumber;

	@NotNull
	@Column(name = "latitude")
	private Double latitude;

	@NotNull
	@Column(name = "longitude")
	private Double longitude;

	@NotNull
	@Column(name = "gps_date")
	private Date date;

	@NotNull
	@Column(name = "created_date")
	private Date createdDate;

	@NotNull
	@Column(name = "update_date")
	private Date updateDate;
}
