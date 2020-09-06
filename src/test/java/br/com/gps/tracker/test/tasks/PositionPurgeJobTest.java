package br.com.gps.tracker.test.tasks;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gps.tracker.repository.PositionRepository;
import br.com.gps.tracker.tasks.PositionPurgeJob;

@RunWith(SpringRunner.class)
public class PositionPurgeJobTest {

	@Mock
	private PositionRepository repository;

	@InjectMocks
	private PositionPurgeJob purgeJob;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void positionPurgeJobCallTest() {
		purgeJob.purgeExpired();
		Mockito.verify(repository, Mockito.atLeast(1))
				.deletePositionsExpiredByDate(Mockito.any(Date.class));
	}
}
