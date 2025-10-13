package com.reloaded.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reloaded.sales.service.ItemService;
import com.reloaded.sales.model.Item;
import com.reloaded.sales.DTO.ItemEditRequest;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody String name) {
        return itemService.deleteItem(name.replace("\"", "").trim());
    }

    @PatchMapping("/edit")
    public ResponseEntity<String> editItem(@RequestBody ItemEditRequest itemEditRequest) {
        return itemService.editItem(itemEditRequest);
    }

    @GetMapping("/id")
    public Optional<Item> getById(@RequestParam long id) {
        return itemService.getById(id);
    }

    @GetMapping("/all")
    public List<Item> getAllItems(@RequestParam int page, @RequestParam int size) {
        return itemService.getAllItems(page, size);
    }

    @GetMapping("/name")
    public List<Item> getByName(@RequestParam String name, @RequestParam int page, @RequestParam int size) {
        return itemService.getByName(name, page, size);
    }

    @GetMapping("/code")
    public List<Item> getByCode(@RequestParam String code, @RequestParam int page, @RequestParam int size) {
        return itemService.getByCode(code, page, size);
    }

    @GetMapping("/barcode")
    public List<Item> getByBarcode(@RequestParam String barcode, @RequestParam int page, @RequestParam int size) {
        return itemService.getByBarcode(barcode, page, size);
    }
}
