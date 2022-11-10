package com.example.safariwebstore008.repositories;

import com.example.safariwebstore008.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {

}
