package br.com.gps.tracker.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDto implements Serializable {

	private static final long serialVersionUID = 757484909863605158L;

	private String projectId;
	private String customerId;
	private String request;
	private String response;
	private Date requestDate;
	private Date responseDate;
	private RequestStatus status;
}
