package org.j2os.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "NUMBER")
    private long personId;

    @Column(columnDefinition = "VARCHAR2(20)")
    private String name;

    @Column(columnDefinition = "VARCHAR2(20)")
    private String family;

    @Version
    private int recordVersion;

    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;

}
