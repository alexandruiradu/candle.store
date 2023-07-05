package com.candle.store.service;

import com.candle.store.entity.ChosenCandle;
import com.candle.store.entity.CustomerOrder;
import com.candle.store.entity.ShoppingCart;
import com.candle.store.entity.User;
import com.candle.store.repository.ChosenCandleRepository;
import com.candle.store.repository.CustomerOrderRepository;
import com.candle.store.repository.ShoppingCartRepository;
import com.candle.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ChosenCandleRepository chosenCandleRepository;

    public void addCustomerOrder(String email, String shippingAddress) {
        Optional<User> user = userRepository.findByEmail(email);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setUser(user.get());
        customerOrder.setShippingAddress(shippingAddress);

        customerOrderRepository.save(customerOrder);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email);
        for (ChosenCandle chosenCandle : shoppingCart.getChosenCandles()) {
            chosenCandle.setShoppingCart(null);
            chosenCandle.setCustomerOrder(customerOrder);
            chosenCandleRepository.save(chosenCandle);
        }
    }
}
