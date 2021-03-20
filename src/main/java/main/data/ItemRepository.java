package main.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import main.model.Item;

interface ItemRepository extends PagingAndSortingRepository<Item, String> {

}
