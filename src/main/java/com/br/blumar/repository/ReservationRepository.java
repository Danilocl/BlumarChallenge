package com.br.blumar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.blumar.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	@Query("SELECT COUNT(r) FROM Reservation r WHERE r.tipo = ?1 AND r.checkOutDate > CURRENT_DATE")
    int countAvailableRoomsByType(String roomType);
	
}
