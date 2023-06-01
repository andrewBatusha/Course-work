package com.market.coursework.service;

import com.market.coursework.model.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    Item getById(String id);

    Item save(Item object);

    void delete(String id);

    List<Item> getAll(Pageable pageable);
}
