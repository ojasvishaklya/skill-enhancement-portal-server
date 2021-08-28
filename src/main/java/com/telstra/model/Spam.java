package com.telstra.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spam {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Long u_id;   //reporter
    @NotNull
    private Long s_id;  //reported
}
