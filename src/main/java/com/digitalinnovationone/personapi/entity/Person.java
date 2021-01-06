package com.digitalinnovationone.personapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data // Gera automaticamente Getters e Setters
@Builder // Gera automaticamente um padrão de construção de objetos
@NoArgsConstructor // Gera automaticamente o construtor vazio
@AllArgsConstructor // Gera automaticamente o construtor com todos os atributos
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,
            unique = true
            )
    private String cpf;

    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE,
                CascadeType.REMOVE
            })
    private List<Phone> phones;
}
