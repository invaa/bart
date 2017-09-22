package com.bart.service.impl;

import com.bart.converter.ItemDtoToItemConverter;
import com.bart.dto.ItemDto;
import com.bart.entity.Item;
import com.bart.repository.ItemRepository;
import com.bart.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemDtoToItemConverter itemConverter;

    @Override
    public Item put(final ItemDto itemDto) {
        return itemRepository.save(itemConverter.convert(itemDto));
    }

    @Override
    public Item get(final UUID itemId) {
        return itemRepository.findOne(itemId);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }
}
