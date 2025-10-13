package com.reloaded.sales.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reloaded.sales.model.Item;
import com.reloaded.sales.DTO.ItemEditRequest;
import com.reloaded.sales.exception.ItemNotFoundException;
import com.reloaded.sales.repository.ItemRepository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public ResponseEntity<String> createItem(Item item) {
        if (item == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Item is not valid");
        }

        itemRepository.save(item);
        return ResponseEntity.ok("Item created successfully");
    }

    public ResponseEntity<String> editItem(ItemEditRequest itemEditRequest)
            throws ItemNotFoundException {
        Item item = itemRepository.findById(itemEditRequest.getItem().getId())
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        BeanUtils.copyProperties(itemEditRequest.getItem(), item);
        itemRepository.save(item);

        return ResponseEntity.ok("Item edited successfully");
    }

    public ResponseEntity<String> deleteItem(String name) {
        Item item = itemRepository.findByName(name);
        if (item == null) {
            throw new ItemNotFoundException("Item not found");
        }

        item.setRating(0);

        itemRepository.save(item);

        return ResponseEntity.ok("Item deleted successfully");
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepository.findByRatingNot(0, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Item> getById(long id) {
        return itemRepository.findById(id);
    }
}
