package br.com.gps.tracker.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.gps.tracker.dto.PositionDto;
import br.com.gps.tracker.model.Position;
import br.com.gps.tracker.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<PositionDto> searchByWorkOrder(String workingOrder, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "gpsDate");
		return repository.searchByWorkOrder(workingOrder.toLowerCase(), pageRequest)
				.map(this::convertToDto);
	}

	public Page<PositionDto> searchByTimeFrame(Integer timeFrame, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "gpsDate");
		Date dateToFilter = Date.from(getDateMinutesAgo(timeFrame));
		return repository.searchByTimeFrame(dateToFilter, pageRequest)
				.map(this::convertToDto);
	}

	public Page<PositionDto> findAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
		return new PageImpl<>(repository.findAll(), pageRequest, size)
				.map(this::convertToDto);
	}

	public Optional<PositionDto> findById(Long id) {
		return repository.findById(id)
				.map(this::convertToDto);
	}

	public Position save(PositionDto positionDto) {
		Position position = convertToEntity(positionDto);
		return repository.save(position);
	}

	private PositionDto convertToDto(Position position) {
		return modelMapper.map(position, PositionDto.class);
	}

	private Position convertToEntity(PositionDto positionDto) {
		Position position = modelMapper.map(positionDto, Position.class);
		position.setUpdateDate(new Date());
		position.setCreatedDate(new Date());
		return position;
	}

	private Instant getDateMinutesAgo(Integer minutes) {
		return ZonedDateTime.now(ZoneOffset.UTC)
				.minusMinutes(minutes)
				.toInstant();
	}
}
