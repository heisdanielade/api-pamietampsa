package com.github.heisdanielade.pamietampsa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.heisdanielade.pamietampsa.enums.Sex;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@Table()
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq")
    @SequenceGenerator(name = "pet_seq", sequenceName = "pet_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String profileImageURL;

    @Column(nullable = false)
    private String species;
    private String breed;

    @Column(nullable = false)
    private Sex sex;
    private LocalDate birthDate;

    @Transient
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    @JsonIgnore
    private AppUser owner;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Pet(String name, String species, String breed, Sex sex,  LocalDate birthDate){
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.sex = sex;
        this.birthDate = birthDate;
    }
    public Pet(String name, String profileImageURL, String species, String breed, Sex sex, LocalDate birthDate){
        this.name = name;
        this.profileImageURL = profileImageURL;
        this.species = species;
        this.breed = breed;
        this.sex = sex;
        this.birthDate = birthDate;
    }
    public Pet(String name, String species, String breed, Sex sex){
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.sex = sex;
    }
    public Pet(String name, String profileImageURL, String species, String breed, Sex sex){
        this.name = name;
        this.profileImageURL = profileImageURL;
        this.species = species;
        this.breed = breed;
        this.sex = sex;
    }

    public Integer getAge(){
        if(this.birthDate != null){
            return LocalDate.now().getYear() - this.birthDate.getYear();
        } else{
            return 0;
        }
    }
}
