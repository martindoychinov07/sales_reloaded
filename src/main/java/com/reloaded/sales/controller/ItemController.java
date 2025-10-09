package com.reloaded.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reloaded.sales.service.ItemService;
import com.reloaded.sales.model.Item;
import com.reloaded.sales.DTO.ItemEditRequest;
import com.example.sales.repository.ItemLogRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemLogRepository itemLogRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody String name) {
        return itemService.deleteItem(name.replace("\"", "").trim());
    }
    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PatchMapping("/edit")
    public ResponseEntity<String> editItem(@RequestBody ItemEditRequest itemEditRequest) {
        return itemService.editItem(itemEditRequest);
    }

    @GetMapping("/logs")
    public List<ItemLog> getAllItemLogs(@RequestParam long itemId) {
        return itemService.getAllItemLogs(itemId);
    }

    @GetMapping("/id")
    public Optional<Item> getById(@RequestParam long id) {
        return itemService.getById(id);
    }

    @PatchMapping("/refid")
    public void setRefId() {
        itemService.setRefId();
    }
}
