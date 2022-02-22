package com.example.application.backend.items.services;

import com.example.application.backend.items.mappers.ItemMapper;
import com.example.application.backend.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemMapper itemMapper;

    @Autowired
    public ItemService(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public void add(Item item){
        this.itemMapper.addImage(item.getItemImage());
    }
}

