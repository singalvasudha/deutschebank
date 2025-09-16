package com.db.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.model.Trade;
import com.db.repository.TradeRepository;

@Service
public class TradeService {
	
	@Autowired
	private TradeRepository tradeRepository;
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public void saveTrade(Trade trade) {
		if(latestTradeVersion(trade) && maturityDateNotExceeded(trade))
			tradeRepository.save(trade);
		else
			System.out.println(""); //TODO:generate an exception
	}
	
	private boolean maturityDateNotExceeded(Trade trade) {
		try {
			Date tradeDate = getTradeDate(trade);		
			Date currentDate = getCurrentDate();			
			
			if(tradeDate.compareTo(currentDate) < 0)
			{
				System.out.println("Trade date is less than current date.");
				return false;
			}
			else
				return true;
		}
		catch(Exception e) {
			//TODO: generate date formatting issue
			return false;
		}
	}

	private Date getTradeDate(Trade trade)  throws Exception {
		return formatter.parse(trade.getMaturityDate());
	}

	private Date getCurrentDate() throws Exception {
		Date currentDate = new Date();
		return formatter.parse(formatter.format(currentDate));
	}

	private boolean latestTradeVersion(Trade trade) {
		int storedTradeVersion = getTrade(trade.getTradeId()).getVersion();
		
		if (storedTradeVersion <= trade.getVersion())
			return true;
		else
			return false;
	}

	public Trade getTrade(String tradeId) {
		Optional<Trade> optionalTrade = tradeRepository.findById(tradeId);
		if(optionalTrade.isPresent()) {
			return optionalTrade.get();
		}
		else
			return new Trade();
	}


}
