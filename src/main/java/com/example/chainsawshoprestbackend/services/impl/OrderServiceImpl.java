package com.example.chainsawshoprestbackend.services.impl;

import com.example.chainsawshoprestbackend.model.Chainsaw;
import com.example.chainsawshoprestbackend.model.Order;
import com.example.chainsawshoprestbackend.repositories.ChainsawRepository;
import com.example.chainsawshoprestbackend.repositories.OrderRepository;
import com.example.chainsawshoprestbackend.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ChainsawRepository chainsawRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ChainsawRepository chainsawRepository) {
        this.orderRepository = orderRepository;
        this.chainsawRepository = chainsawRepository;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order save(Order order) {
        for(Chainsaw chainsaw : order.getChainsaws()){
            Chainsaw foundChainsaw = chainsawRepository.findById(chainsaw.getId()).orElse(null);
            if(foundChainsaw == null)
                return null;
            if(foundChainsaw.getQuantity() < chainsaw.getQuantity())
                return null;
        }
        for(Chainsaw chainsaw : order.getChainsaws()){
            chainsawRepository.findById(chainsaw.getId())
                            .ifPresent(foundChainsaw -> {
                                    foundChainsaw.setQuantity(foundChainsaw.getQuantity() - chainsaw.getQuantity());
                                    chainsawRepository.save(foundChainsaw);
                            });
        }
        return orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        for(Chainsaw chainsaw : order.getChainsaws()){
            chainsawRepository.findById(chainsaw.getId())
                    .ifPresent(foundChainsaw -> {
                            foundChainsaw.setQuantity(foundChainsaw.getQuantity() + chainsaw.getQuantity());
                            chainsawRepository.save(foundChainsaw);
                    });
        }
        orderRepository.delete(order);
    }

    @Override
    public void deleteById(Long id) {
        if(orderRepository.findById(id).isPresent()) {
            for (Chainsaw chainsaw : orderRepository.findById(id).get().getChainsaws()) {
                chainsawRepository.findById(chainsaw.getId())
                        .ifPresent(foundChainsaw -> {
                            foundChainsaw.setQuantity(foundChainsaw.getQuantity() + chainsaw.getQuantity());
                            chainsawRepository.save(foundChainsaw);
                        });
            }
            orderRepository.deleteById(id);
        }
    }
}
