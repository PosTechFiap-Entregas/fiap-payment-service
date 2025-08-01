package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.product;

import br.com.fiap.postech.soat.techchallenge.domain.models.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private String description;

    private boolean active;

    @Column(name = "image_url")
    @NotEmpty
    private String imageUrl;
}