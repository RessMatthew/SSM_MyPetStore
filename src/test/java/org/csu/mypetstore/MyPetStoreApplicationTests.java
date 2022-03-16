package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;


@SpringBootTest
class MyPetStoreApplicationTests {

    @Autowired
    private CatalogService catalogService;

    @Test
    void contextLoads() {
        List<Category> allCategoryList = catalogService.getAllCategoryList();
        Iterator<Category>  CategoryIterator = allCategoryList.iterator();

        while (CategoryIterator.hasNext()){
            System.out.println(CategoryIterator.next());
        }

    }

}
