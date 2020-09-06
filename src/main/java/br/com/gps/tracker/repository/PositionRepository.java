package br.com.gps.tracker.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gps.tracker.model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

	@Query("FROM Position p WHERE LOWER(p.workingOrder) = :workingOrder")
	Page<Position> searchByWorkOrder(@Param("workingOrder") String workingOrder, Pageable pageable);

	@Query("FROM Position p WHERE p.gpsDate >= :minutesAgo")
	Page<Position> searchByTimeFrame(@Param("minutesAgo") Date minutesAgo, Pageable pageable);

	@Modifying
	@Transactional
	@Query("delete from Position p where p.createdDate <= ?1")
	void deletePositionsExpiredByDate(Date expiration);
}
