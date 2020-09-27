package br.com.dbc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	private String agencia;
	private String conta;
	private String saldo;
	private String status;
	private boolean update;
	
	public Account update(boolean response) {
		this.update = response;
		return this;
	}
}
