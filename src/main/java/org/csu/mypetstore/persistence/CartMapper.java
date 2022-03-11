package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CartMapper {
    boolean updateItemByUsernameAndItemId(String username, String itemId, int quantity, BigDecimal totalCost);
    CartItem selectItemByUsernameAndItemId(String username, String itemId);
    int insertItemByUsernameAndItemId(String username, String itemId, boolean instock, int quantity, BigDecimal totalCost);
    List<Cart> selectItemByUsername(String username);
    void removeItemByUsernameAndItemId(String username, String itemId);
    void updateItemByItemIdAndPay(String username, String itemId, boolean pay);
}
