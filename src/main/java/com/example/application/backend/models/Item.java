package com.example.application.backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String name;
    private String description;
    private String information;
    private String EPC;
    private boolean isInShelf;
    private ItemImage itemImage;
}
