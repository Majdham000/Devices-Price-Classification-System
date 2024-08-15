package com.example.crud.model;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="Devices")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Float battery_power ;

    private Float blue  ;

    private Float clock_speed  ;

    private Float dual_sim  ;

    private Float fc  ;

    private Float four_g  ;

    private Float int_memory  ;

    private Float m_dep  ;

    private Float mobile_wt  ;

    private Float n_cores  ;

    private Float pc  ;

    private Float px_height  ;

    private Float px_width  ;

    private Float ram  ;

    private Float sc_h  ;

    private Float sc_w  ;

    private Float talk_time  ;

    private Float three_g   ;

    private Float touch_screen   ;

    private Float wifi   ;

}
