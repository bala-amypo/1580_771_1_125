// package com.example.demo.entity;

// import jakarta.persistence.*;
// import java.math.BigDecimal;
// import java.sql.Timestamp;
// import java.util.HashSet;
// import java.util.Set;

// @Entity
// @Table(name = "menu_items")
// public class MenuItem {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(unique = true, nullable = false)
//     private String name;

//     private String description;

//     @Column(nullable = false)
//     private BigDecimal sellingPrice;

//     private Boolean active = true;

//     private Timestamp createdAt;
//     private Timestamp updatedAt;

//     @ManyToMany
//     @JoinTable(
//             name = "menuitem_categories",
//             joinColumns = @JoinColumn(name = "menuitem_id"),
//             inverseJoinColumns = @JoinColumn(name = "category_id")
//     )
//     private Set<Category> categories = new HashSet<>();

//     @PrePersist
//     protected void onCreate() {
//         createdAt = new Timestamp(System.currentTimeMillis());
//         updatedAt = createdAt;
//     }

//     @PreUpdate
//     protected void onUpdate() {
//         updatedAt = new Timestamp(System.currentTimeMillis());
//     }

//     public Long getId() {
//         return id;
//     }

//     public String getName() {
//         return name;
//     }

//     public String getDescription() {
//         return description;
//     }

//     public BigDecimal getSellingPrice() {
//         return sellingPrice;
//     }

//     public Boolean getActive() {
//         return active;
//     }

//     public Set<Category> getCategories() {
//         return categories;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public void setDescription(String description) {
//         this.description = description;
//     }

//     public void setSellingPrice(BigDecimal sellingPrice) {
//         this.sellingPrice = sellingPrice;
//     }

//     public void setActive(Boolean active) {
//         this.active = active;
//     }

//     public void setCategories(Set<Category> categories) {
//         this.categories = categories;
//     }
// }


package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal sellingPrice;

    private Boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
        name = "menuitem_category",
        joinColumns = @JoinColumn(name = "menuitem_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    // getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
