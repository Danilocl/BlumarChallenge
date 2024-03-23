package com.br.blumar.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ReservationDTO {

	@Schema(example = "Fulano")
	private String nome;
	@Schema(example = "acompanhante")
	private String tipo;
	@Schema(example = "23/04/2024")    
	private String checkOutDate;



	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
