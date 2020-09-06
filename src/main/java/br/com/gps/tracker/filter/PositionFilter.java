package br.com.gps.tracker.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import br.com.gps.tracker.dto.RequestDto;
import br.com.gps.tracker.dto.RequestStatus;
import br.com.gps.tracker.publisher.MessageSenderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class PositionFilter implements Filter {

	@Autowired
	private MessageSenderService messageService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ContentCachingRequestWrapper wreq = new ContentCachingRequestWrapper((HttpServletRequest) request);
		ContentCachingResponseWrapper wres = new ContentCachingResponseWrapper((HttpServletResponse) response);

		try {
			Date before = new Date();
			chain.doFilter(wreq, wres);
			Date after = new Date();
			String requestUrl = getRequestUrl(wreq);
			if (!requestUrl.contains("actuator")) {
				RequestDto dto = new RequestDto();
				dto.setProjectId("lala");
				dto.setCustomerId("lalala");
				dto.setRequest(requestUrl);
				dto.setResponse(new String(wres.getContentAsByteArray()));
				dto.setRequestDate(before);
				dto.setResponseDate(after);
				dto.setStatus(RequestStatus.RECEIVED);
				wres.copyBodyToResponse();
				log.info("user request integration {}", dto.getRequest());
				messageService.sendMessage(dto);
			}
		} catch (Exception ex) {
			log.error("Error filtering the request / response {}", ex.getMessage());
		}
	}

	private static String getRequestUrl(HttpServletRequest request) {
		StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
		String queryString = request.getQueryString();
		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append('?').append(queryString).toString();
		}
	}
}
