package org.j2os.service;

import org.j2os.common.JPA;
import org.j2os.entity.Department;
import org.j2os.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class DepartmentService {

    public static void save(Department department){
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try{
            transaction.begin();

            entityManager.persist(department);

            transaction.commit();
        } catch (Exception e) {

            if (transaction.isActive()){
                transaction.rollback();
            }

            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void delete(Department department) throws Exception {
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Department deoartmentDelete = entityManager.find(Department.class, department.getDepartmentId());

            if (deoartmentDelete != null){
                entityManager.remove(deoartmentDelete);
            }

            entityTransaction.commit();
        } catch (Exception e){
            if (entityTransaction.isActive()){
                entityTransaction.rollback();
            }

            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void findAllDepartments(){
        EntityManager entityManager = JPA.entityManager();

        try {
            List<Department> departments =
                    entityManager.createQuery(
                            "select d from Department  d",
                            Department.class
                    ).getResultList();

            for (Department department : departments){
                System.out.println(
                        "Department: " + department.getName()
                );

                System.out.println(
                        "Persons: " + department.getPersons().size()
                );
            }
        } finally {
            entityManager.close();
        }
    }

    public static void findAllDepartmentsWithJoinGFetch(){
        EntityManager entityManager = JPA.entityManager();

        try {
            List<Department> departments =
                    entityManager.createQuery(
                            "select distinct d from Department  d left join fetch d.persons",
                            Department.class
                    ).getResultList();

            for (Department department : departments){
                System.out.println(
                        "Department: " + department.getName()
                );

                System.out.println(
                        "Persons: " + department.getPersons().size()
                );
            }
        } finally {
            entityManager.close();
        }
    }

    public static void testCascadePersist(){
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Department department = Department.builder()
                    .name("HR")
                    .build();

            Person person = Person.builder()
                    .name("Ali")
                    .family("Ahmadi")
                    .department(department)
                    .build();

            entityManager.persist(person);


            transaction.commit();
        } catch (Exception e){
            if (transaction.isActive()){
                transaction.rollback();
            }

            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void testOrphanRemoval(){
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Department department =
                    entityManager.find(Department.class, 2L);

            Person person =
                    department.getPersons().get(1);

            System.out.println("Before remove: "
                    + person.getDepartment().getName());

            department.getPersons().remove(person);
            person.setDepartment(null);

//            System.out.println("After remove: "
//                    + person.getDepartment());

            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {

            if (transaction.isActive()){
                transaction.rollback();
            }

            throw e;
        } finally {
            entityManager.close();
        }
    }
}
