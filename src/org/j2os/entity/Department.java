package org.j2os.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "department")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long departmentId;

    @Column(columnDefinition = "VARCHAR2(20)", nullable = false)
    private String name;

    @OneToMany(mappedBy = "department"
//            , fetch = FetchType.EAGER
            ,cascade = CascadeType.ALL
            ,orphanRemoval = true
    )
    private List<Person> persons;
}
