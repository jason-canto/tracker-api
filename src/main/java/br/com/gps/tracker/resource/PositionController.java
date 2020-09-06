package br.com.gps.tracker.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gps.tracker.dto.PositionDto;
import br.com.gps.tracker.service.PositionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/tracker")
public class PositionController {

	@Autowired
	private PositionService service;

	@ApiOperation(value = "Get all positions by a specific time frame")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Return all positions by a given time frame"),
		@ApiResponse(code = 204, message = "Empty response for the given search"),
		@ApiResponse(code = 401, message = "You do not have permission to access this resource"),
		@ApiResponse(code = 500, message = "An exception was thrown"),
	})
	@GetMapping("/positions/search")
	public ResponseEntity<Page<PositionDto>> search(@RequestParam("timeFrame") Integer timeFrame,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		Page<PositionDto> result = service.searchByTimeFrame(timeFrame, page, size);
		return result == null || result.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "Get all positions by without any filter")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Return all positions"),
		@ApiResponse(code = 204, message = "Empty response for the given search"),
		@ApiResponse(code = 401, message = "You do not have permission to access this resource"),
		@ApiResponse(code = 500, message = "An exception was thrown")
	})
	@GetMapping("/positions")
	public ResponseEntity<Page<PositionDto>> getAllPositions(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		Page<PositionDto> result = service.findAll(page, size);
		return result == null || result.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(result, HttpStatus.OK);
	}
}
