package com.example.coursework.controller;

import com.example.coursework.model.ShoppingCartItem;
import com.example.coursework.service.ShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/shopping-cart-items")
public class ShoppingCartItemController {

    @Autowired
    private ShoppingCartItemService shoppingCartItemService;

    @PostMapping
    public String addShoppingCartItem(
            @RequestParam int bookId,
            @RequestParam int countProduct,
            @RequestParam Long userId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemService.addShoppingCartItem(bookId, countProduct, userId);
        return "redirect:/catalog";
    }

    @PostMapping("/{userId}/{bookId}/delete")
    public String deleteShoppingCartItem(@RequestParam Long userId,
                                         @RequestParam int bookId,
                                         RedirectAttributes redirectAttributes) {
        shoppingCartItemService.deleteShoppingCartItem(userId, bookId);
        redirectAttributes.addFlashAttribute("message", "Элемент корзины успешно удален");
        return "redirect:/catalog";
    }
}
