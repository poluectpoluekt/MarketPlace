package com.ed.orderservice.repository;

import com.ed.orderservice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByInvoiceStatus(String invoiceStatus);

    @Query(value = "SELECT i FROM Invoice i WHERE" +
            " i.status_invoice = 'PROCESSING' ORDER BY i.create_date ASC LIMIT 1;", nativeQuery = true)
    Optional<Invoice> findByInvoiceStatusAndAsc(@Param("title") String invoiceStatus);

}
