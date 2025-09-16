package com.db.repository;

import com.db.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {

}
