package br.com.gps.tracker.test.exception;

import org.junit.Test;

import br.com.gps.tracker.exception.PositionNotFoundException;

public class PositionNotFoundExceptionTest {
	
	@Test(expected = PositionNotFoundException.class)
	public void throwExceptionWithNoMessageAndNoThrowable() {
		throw new PositionNotFoundException();
	}
	
	@Test(expected = PositionNotFoundException.class)
	public void throwExceptionWithNoThrowable() {
		throw new PositionNotFoundException("Mensagem de erro");
	}
	
	@Test(expected = PositionNotFoundException.class)
	public void throwExceptionWithNoMessage() {
		throw new PositionNotFoundException(new Throwable());
	}
	
	@Test(expected = PositionNotFoundException.class)
	public void throwException() {
		throw new PositionNotFoundException("Mensagem de erro", new Throwable());
	}

}
