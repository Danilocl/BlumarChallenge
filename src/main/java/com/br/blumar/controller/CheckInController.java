package com.br.blumar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.blumar.dto.ReservationDTO;
import com.br.blumar.exception.ErrorResponse;
import com.br.blumar.model.Reservation;
import com.br.blumar.service.CheckInService;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

	private final CheckInService checkInService;

	@Autowired
	public CheckInController(CheckInService checkInService) {
		this.checkInService = checkInService;
	}

	@PostMapping("/perform")
	public ResponseEntity<?> performCheckIn(@RequestBody ReservationDTO dto) {
		checkInService.validaTipoQuarto(dto.getTipo());
		
		int availableRooms = checkInService.checaQuartosDisponiveis(dto.getTipo());
		//zero caso o quarto não tenha sido reservado ainda
		if (availableRooms == 0) {
			Reservation message = checkInService.checkIn(dto);
			return ResponseEntity.ok(message);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Desculpe, não há quartos disponíveis do tipo " + dto.getTipo());
		}
	}

	@GetMapping("/availableRooms")
	public ResponseEntity<?> countAvailableRooms() {
		List<Reservation> response = checkInService.listaQuartosDisponiveis();
		if (response.isEmpty()) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Nenhum checkIn foi realizado"));
		}

		return ResponseEntity.ok(response);		
	}
}
