package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSON;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        return "catalog/Category";
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

    @GetMapping("/searchThis")
    public void searchAutoComplete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword;
        keyword=request.getParameter("keyword");
        Product product=new Product();
        Item item=new Item();
        List<Product> productList=new ArrayList<Product>();
        productList=catalogService.searchProductList(keyword);

        StringBuffer sb = new StringBuffer("[");

        for(int i=0;i<productList.size();i++){
            if(i== productList.size()-1) {
                sb.append("\"" + productList.get(i).getName() + "\"]");
            }else{
                sb.append("\"" + productList.get(i).getName() + "\",");
            }
        }
        response.getWriter().write(sb.toString());
    }

    @GetMapping("/fwItem")
    public void floatingWindow(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemId;
        itemId=request.getParameter("itemId");
        CatalogService service=new CatalogService();
        Item item=service.getItem(itemId);
        String jsonstr = JSON.toJSONString(item);
        response.getWriter().write(jsonstr);

    }

}
