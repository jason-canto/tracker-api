package br.com.gps.tracker.test.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.gps.tracker.dto.PositionDto;
import br.com.gps.tracker.resource.PositionController;
import br.com.gps.tracker.service.PositionService;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = PositionController.class)
public class PositionResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PositionService service;

	private final String API_PATH = "/api/tracker/positions";

	@Test
	@WithMockUser(username="user",roles={"USER"})
	public void shouldReturnPaginated() throws Exception {
		List<PositionDto> mockedPositions = createMockPositions();
		PageRequest pageReq = PageRequest.of(1, 10);
		Page<PositionDto> page = new PageImpl<>(mockedPositions, pageReq, mockedPositions.size());
		when(service.searchByTimeFrame(anyInt(), anyInt(), anyInt())).thenReturn(page);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("timeFrame", "15");

		this.mockMvc.perform(get(API_PATH + "/search")
				.params(params)
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").exists())
			.andExpect(jsonPath("$.content[0].id", equalTo("ABC1234")))
			.andExpect(jsonPath("$.numberOfElements", equalTo(1000)))
			.andExpect(jsonPath("$.number", equalTo(1)))
			.andExpect(jsonPath("$.size", equalTo(10)))
			.andExpect(jsonPath("$.empty", equalTo(false)));
	}

	@Test
	@WithMockUser(username="user",roles={"USER"})
	public void shouldReturnOk_whenSearchingAllPositions() throws Exception {
		List<PositionDto> mockedPositions = createMockPositions();
		Page<PositionDto> page = new PageImpl<>(mockedPositions);
		when(service.findAll(anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(get(API_PATH)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username="user",roles={"USER"})
	public void shouldReturnNoContent_whenSearchingAllPositions() throws Exception {
		List<PositionDto> empty = new ArrayList<>();
		Page<PositionDto> page = new PageImpl<>(empty);
		when(service.findAll(anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(get(API_PATH)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(username="user",roles={"USER"})
	public void shouldReturnNoContent_whenSearchingByWrongTimeFrame() throws Exception {
		List<PositionDto> empty = new ArrayList<>();
		Page<PositionDto> page = new PageImpl<>(empty);
		when(service.searchByTimeFrame(anyInt(), anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(get(API_PATH)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(username="user",roles={"USER"})
	public void shouldReturnNotFound_forWrongUrl() throws Exception {
		List<PositionDto> empty = new ArrayList<>();
		Page<PositionDto> page = new PageImpl<>(empty);
		when(service.searchByTimeFrame(anyInt(), anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(get(API_PATH + "/test")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void shouldReturnUnauthorized() throws Exception {
		List<PositionDto> empty = new ArrayList<>();
		Page<PositionDto> page = new PageImpl<>(empty);
		when(service.searchByTimeFrame(anyInt(), anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(get(API_PATH)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	private List<PositionDto> createMockPositions() {
		Instant now = Instant.now();
		List<PositionDto> mockedPositions = new ArrayList<PositionDto>();
		for(int i = 0; i < 1000; i++) {
			PositionDto pos = new PositionDto();
			pos.setContractId("BRMR123");
			pos.setContainerId("MSK123");
			pos.setPlateNumber("ABC1234");
			pos.setLatitude(12.123456);
			pos.setLongitude(-12.123987);
			pos.setDate(Date.from(now.minus(15, ChronoUnit.MINUTES)));
			now = now.minusSeconds(60);
			mockedPositions.add(pos);
		}
		return mockedPositions;
	}
}
