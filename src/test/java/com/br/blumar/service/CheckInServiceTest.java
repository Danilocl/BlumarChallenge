package com.br.blumar.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.br.blumar.dto.ReservationDTO;
import com.br.blumar.model.Reservation;
import com.br.blumar.repository.ReservationRepository;

@SpringBootTest
class CheckInServiceTest {

	@Mock
	private ReservationRepository reservationRepository;

	@InjectMocks
	private CheckInService checkInService;

	@Test
	public void testPerformCheckIn() throws ParseException {
		ReservationDTO dto = criaDTO();
		Reservation reservation = criaObjeto();

		when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

		Reservation savedReservation = checkInService.checkIn(dto);

		assertEquals("Fulano", savedReservation.getNome());
		assertEquals("acompanhante", savedReservation.getTipo());
		assertEquals(truncateTime(new Date()), truncateTime(savedReservation.getCheckInDate()));
		assertEquals(truncateTime(reservation.getCheckOutDate()), truncateTime(savedReservation.getCheckOutDate()));
		assertEquals(reservation.getNumeroNoites(), savedReservation.getNumeroNoites());
		assertEquals(reservation.getNumeroQuarto(), savedReservation.getNumeroQuarto());
		assertEquals(reservation.getValor(), savedReservation.getValor());

	}

	@Test
	public void testListaQuartosDisponiveis() {

		Reservation reservation1 = new Reservation();
		reservation1.setNome("Fulano");
		reservation1.setTipo("acompanhante");

		Reservation reservation2 = new Reservation();
		reservation2.setNome("Ciclano");
		reservation2.setTipo("individual");

		List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation1);
		reservations.add(reservation2);

		when(reservationRepository.findAll()).thenReturn(reservations);

		List<Reservation> availableRooms = checkInService.listaQuartosDisponiveis();

		assertNotNull(availableRooms);
		assertEquals(2, availableRooms.size());
		assertEquals("Fulano", availableRooms.get(0).getNome());
		assertEquals("acompanhante", availableRooms.get(0).getTipo());
		assertEquals("Ciclano", availableRooms.get(1).getNome());
		assertEquals("individual", availableRooms.get(1).getTipo());
	}

	private ReservationDTO criaDTO() throws ParseException  {
		ReservationDTO dto = new ReservationDTO();

		dto.setNome("Fulano");
		dto.setTipo("acompanhante");
		dto.setCheckOutDate("23/03/2025");

		return dto;
	}

	private Reservation criaObjeto() throws ParseException  {
		ReservationDTO dto = criaDTO();
		Reservation reservation = new Reservation();
		

		reservation.setNome(dto.getNome());
		reservation.setTipo(dto.getTipo());
		reservation.setCheckInDate(new Date());
		reservation.setCheckOutDate(retornaData());
		reservation.setNumeroNoites(365);
		reservation.setNumeroQuarto(204);
		reservation.setValor(150);

		return reservation;
	}
	
	private Date retornaData() throws ParseException {
		String dateStr = "23/03/2025";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse(dateStr);
		return date;
	}

	private Date truncateTime(Date date) {
		long time = date.getTime();
		long truncatedTime = time - (time % (24 * 60 * 60 * 1000)); // Remover informações de hora, minuto, segundo e
																	// milissegundo
		return new Date(truncatedTime);
	}

}
