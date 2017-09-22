package com.bart.service;

import com.bart.dto.ItemDto;
import com.bart.entity.Item;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    Item put(final ItemDto itemDto);
    Item get(final UUID itemId);
    List<Item> getAll();
}
