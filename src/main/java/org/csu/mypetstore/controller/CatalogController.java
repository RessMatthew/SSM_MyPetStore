package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping ("/main")
    public String loginForm(){
        return "/catalog/Main";
    }

    @GetMapping ("/viewCategory")
    public String viewCatalog(Category category, Model model){
        String categoryId=category.getCategoryId();
        Category categoryByCategoryId=catalogService.getCategory(categoryId);
        List<Product> productList=catalogService.getProductListByCategory(categoryId);

        model.addAttribute("productList",productList);
        model.addAttribute("category",categoryByCategoryId);
        return "/catalog/Category";
    }

    @GetMapping("/viewProduct")
    public String viewProduct(Product product, Model model){
        List<Item> itemList=catalogService.getItemListByProduct(product.getProductId());
        model.addAttribute("product",product);
        model.addAttribute("itemList",itemList);
        return "/catalog/Product";
    }

    @GetMapping("/viewItem")
    public String viewItem(Item item_post,Product product,Model model){
        Item item=catalogService.getItem(item_post.getItemId());
        int item1=catalogService.getInventoryQuantity(item.getItemId());

        model.addAttribute("item",item);
        model.addAttribute("item1",item1);
        model.addAttribute("product",product);
        return "/catalog/Item";
    }

    @PostMapping("/search")
    public String searchProduct(String keyword,Model model){
        List<Product> productList=new ArrayList<Product>();
        productList=catalogService.searchProductList(keyword);

        model.addAttribute("productList",productList);
        return "/catalog/SearchProduct";
    }


}
