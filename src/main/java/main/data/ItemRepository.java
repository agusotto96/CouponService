package main.data;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;

import main.entity.Item;

interface ItemRepository extends PagingAndSortingRepository<Item, String> {

	@Modifying
	@Transactional
	void deleteBylastUpdateLessThan(LocalDateTime antiquity);

}
