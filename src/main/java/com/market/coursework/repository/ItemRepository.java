package com.market.coursework.repository;

import com.market.coursework.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {

    Page<Item> findAll(Pageable pageable);
}
