package com.ed.orderservice.repository;

import com.ed.orderservice.entity.Invoice;
import com.ed.orderservice.entity.enums.StatusInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByInvoiceStatus(String invoiceStatus);

    /**
     * старый метод
     */
//    @Query(value = "SELECT i FROM Invoice i WHERE" +
//            " i.status_invoice = 'PROCESSING' ORDER BY i.create_date ASC LIMIT 1;", nativeQuery = true)
//    Optional<Invoice> findByInvoiceStatusAndAsc(@Param("title") String invoiceStatus);

    List<Invoice> findFirst10ByInvoiceStatusOrderByInvoiceDateAsc(StatusInvoice invoiceStatus);

}
