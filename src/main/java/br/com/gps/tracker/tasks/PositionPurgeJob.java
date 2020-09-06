package br.com.gps.tracker.tasks;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gps.tracker.repository.PositionRepository;

@Service
@Transactional
public class PositionPurgeJob {

	@Autowired
	private PositionRepository repository;

	@Value("${purge.expiration.days}")
	private int numOfDaysAgo;

	@Scheduled(cron = "${purge.cron.expression}")
	public void purgeExpired() {
		Date expiration = Date.from(getDateMonthsAgo());
		repository.deletePositionsExpiredByDate(expiration);
	}

	private Instant getDateMonthsAgo() {
		return ZonedDateTime.now(ZoneOffset.UTC)
				.minusDays(numOfDaysAgo).toInstant();
	}
}
