package com.br.blumar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.blumar.dto.ReservationDTO;
import com.br.blumar.model.Reservation;
import com.br.blumar.repository.ReservationRepository;
import com.br.blumar.util.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CheckInService {

	private final ReservationRepository repo;

	@Autowired
	public CheckInService(ReservationRepository reservationRepository) {
		this.repo = reservationRepository;
	}

	public Reservation checkIn(ReservationDTO dto) {
		Reservation reservation = new Reservation();
		reservation.setNome(dto.getNome());		
		reservation.setTipo(dto.getTipo());
		reservation.setCheckInDate(new Date());
		reservation.setCheckOutDate(Utils.stringToDate( dto.getCheckOutDate()));
		reservation.setNumeroQuarto(geraNumeroQuarto());
		reservation.setNumeroNoites(calculaNumeroDeNoites(reservation.getCheckInDate(), Utils.stringToDate(dto.getCheckOutDate())));
		reservation.setValor(calculaValor(dto.getTipo(), Utils.stringToDate(dto.getCheckOutDate())));

		return repo.save(reservation);
	}

	public int checaQuartosDisponiveis(String roomType) {
		return repo.countAvailableRoomsByType(roomType);
	}
	
	public List<Reservation> listaQuartosDisponiveis() {
		return repo.findAll();
	}

	private int geraNumeroQuarto() {
		return (int) (Math.random() * 100) + 1;
	}

	private double calculaValor(String roomType, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.FRIDAY || dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			return roomType.equals("individual") ? 120 : (roomType.equals("acompanhante") ? 150 : 180);
		} else {
			return roomType.equals("individual") ? 100 : (roomType.equals("acompanhante") ? 130 : 160);
		}
	}

	public int calculaNumeroDeNoites(Date checkInDate, Date checkOutDate) {
		Calendar checkIn = Calendar.getInstance();
		checkIn.setTime(checkInDate);
		Calendar checkOut = Calendar.getInstance();
		checkOut.setTime(checkOutDate);

		long differenceInMillis = checkOut.getTimeInMillis() - checkIn.getTimeInMillis();
		return (int) (differenceInMillis / (1000 * 60 * 60 * 24));
	}

	public void validaTipoQuarto(String roomType) {
		if (!"acompanhante".equalsIgnoreCase(roomType) && !"individual".equalsIgnoreCase(roomType)
				&& !"criança".equalsIgnoreCase(roomType)) {
			throw new IllegalArgumentException(
					"Tipo de quarto inválido. Os tipos válidos são 'acompanhante', 'individual' e 'criança'.");
		}
	}

}
