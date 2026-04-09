package com.co.grandmasfood.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table("orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @Column("id")
    private Long id;

    @Column("uuid")
    private String uuid;

    @Column("client_document")
    private String clientDocument;

    @Column("product_code")
    private String productCode;

    @Column("quantity")
    private Integer quantity;

    @Column("additional_info")
    private String additionalInfo;

    @Column("unit_price_without_tax")
    private BigDecimal unitPriceWithoutTax;

    @Column("subtotal_without_tax")
    private BigDecimal subtotalWithoutTax;

    @Column("tax_amount")
    private BigDecimal taxAmount;

    @Column("total_with_tax")
    private BigDecimal totalWithTax;

    @Column("delivered")
    private Boolean delivered;

    @Column("order_date")
    private LocalDateTime orderDate;

    @Column("delivered_date")
    private LocalDateTime deliveredDate;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
