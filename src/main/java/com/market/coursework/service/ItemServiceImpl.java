package com.market.coursework.service;

import com.market.coursework.client.MarketBenchmarkApiClient;
import com.market.coursework.model.Item;
import com.market.coursework.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final MarketBenchmarkApiClient marketBenchmarkApiClient;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, MarketBenchmarkApiClient marketBenchmarkApiClient) {
        this.itemRepository = itemRepository;
        this.marketBenchmarkApiClient = marketBenchmarkApiClient;
    }

    @Override
    public Item getById(String id) {
        return itemRepository.findById(id).get();
    }

    @Override
    public List<Item> getAll(Pageable pageable) {
        return itemRepository.findAll(pageable).getContent();
    }

    @Override
    public Item save(Item object) {
        Double benchmark = marketBenchmarkApiClient.getBenchmark(object);
        object.setMultiScore(benchmark.intValue());
        return itemRepository.save(object);
    }

    @Override
    public void delete(String id) {
        itemRepository.deleteById(id);
    }
}
