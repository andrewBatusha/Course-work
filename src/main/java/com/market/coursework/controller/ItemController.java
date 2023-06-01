package com.market.coursework.controller;

import com.market.coursework.model.Item;
import com.market.coursework.service.ItemService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "Item API")
@RequiredArgsConstructor
@RequestMapping("/v1/items")
public class ItemController {

    @Autowired
    private final ItemService itemService;

    @GetMapping("/{id}")
    public String get(@PathVariable("id") String id, Model model) {
        Item item = itemService.getById(id);
        model.addAttribute("item", item);
        return "item";
    }


    @GetMapping
    public String getPage(@PageableDefault(sort = {"id"}) Pageable pageable, Model model) {
        List<Item> items = itemService.getAll(pageable);
        model.addAttribute("items", items);
        return "items";
    }


    @PostMapping
    public String save(@ModelAttribute Item addItem) {
        Item item = itemService.save(addItem);
        return "redirect:/v1/items/".concat(String.valueOf(item.getId()));

    }

    @GetMapping("/add")
    public String showAddItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "add-item";
    }

    @GetMapping("/edit/{id}")
    public String editItem(@PathVariable("id") String id, Model model) {
        Item item = itemService.getById(id);

        if (item != null) {
            model.addAttribute("item", item);
            return "edit-item";
        } else {
            // Handle item not found scenario
            return "redirect:/v1/items";
        }
    }

    @PostMapping("/edit")
    public String updateItem(@ModelAttribute Item updatedItem) {
        itemService.save(updatedItem);

        return "redirect:/v1/items";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        itemService.delete(id);
        return "redirect:/v1/items";
    }
}
