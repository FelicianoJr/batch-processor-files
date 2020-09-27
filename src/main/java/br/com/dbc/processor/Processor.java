package br.com.dbc.processor;

import static br.com.dbc.util.StringUtils.replaceByPoint;
import static br.com.dbc.util.StringUtils.replaceByEmpty;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dbc.model.Account;
import br.com.dbc.service.ReceitaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Processor implements ItemProcessor<Account, Account> {

	@Autowired
	private ReceitaService receitaService;

	@Override
	public Account process(Account account) {
		
		try {
			final boolean response = receitaService.atualizarConta(account.getAgencia(),
					replaceByEmpty(account.getConta()),
					Double.valueOf(replaceByPoint(account.getSaldo())),
					account.getStatus());

			account.update(response);
			
		} catch (Exception e) {
			log.error("fail processing: " + "Agência:" + account.getAgencia() + ", Conta: " + account.getConta(), e);
		}

		return account;
	}
}
