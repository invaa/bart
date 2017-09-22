package com.bart.controller;

import com.bart.dto.ItemDto;
import com.bart.entity.Item;
import com.bart.service.ItemService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@EnableAutoConfiguration
@RestController
@RequestMapping("items")
@Log4j
public class ItemsController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<Item> saveItem(@Valid @RequestBody final ItemDto itemDto) {
        final Item item = itemService.put(itemDto);
        log.info("Item saved: " + item.getId());
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @RequestMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Item> getItem(@PathVariable("itemId") final UUID itemId) {
        return new ResponseEntity<>(itemService.get(itemId), HttpStatus.OK);
    }

    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems() {
        final List<Item> itemList = itemService.getAll();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }
}
