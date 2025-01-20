package org.team6.coffeebeanery.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    
    @Column(length = 100)
    private String baseAddress;
    
    @Column(length = 100)
    private String detailAddress;
    
    @Column(length = 5)
    private String zipCode;
}