package main.data;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import main.entity.Item;

interface ItemRepository extends JpaRepository<Item, String> {

	@Modifying
	@Transactional
	void deleteBylastUpdateLessThan(LocalDateTime antiquity);

}
