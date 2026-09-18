package org.j2os.service;

import lombok.extern.slf4j.Slf4j;
import org.j2os.common.JPA;
import org.j2os.entity.Department;
import org.j2os.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.List;

@Slf4j
public class PersonService {

    private final static PersonService PERSON_SERVICE = new PersonService();
    private PersonService(){}
    public static PersonService getInstance(){
        return PERSON_SERVICE;
    }

    public static void save(Person person) throws Exception {
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            entityManager.persist(person);

            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()){
                entityTransaction.rollback();
            }

            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void saveWithFindDepartment(Person person, Department department){
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Department departmentFounded =
                    entityManager.find(
                            Department.class,
                            department.getDepartmentId()
                    );

            Person personFounded = Person.builder()
                    .name(person.getName())
                    .family(person.getFamily())
                    .department(departmentFounded)
                    .build();

            entityManager.persist(personFounded);

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

    public static Person findOne(){
        EntityManager entityManager = JPA.entityManager();

        try {
            return entityManager.find(Person.class, 1L);
        } finally {
            entityManager.close();
        }

    }

    public static List<Person> findAll(){
        EntityManager entityManager = JPA.entityManager();

        Query query = entityManager.createQuery("select o from Person o");
        List<Person> list = query.getResultList();
        return list;
    }

    public static List<Person> findAllByName(String name){
        EntityManager entityManager = JPA.entityManager();

        try {
            return entityManager
                    .createQuery("select o from Person o where o.name = :name",
                            Person.class)
                    .setParameter("name", name)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public static List<Tuple> findAllWithSpecialColumn() {
        EntityManager entityManager = JPA.entityManager();

        try {
            return entityManager.createQuery(
                    "select o.name as x , o.family as y from Person o",
                    Tuple.class
            ).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public static List<Person> findAllWithSql() {
        EntityManager entityManager = JPA.entityManager();

        try {
            return entityManager.createNativeQuery("select * from person", Person.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public static void update(Person person) throws Exception {
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Person personFound =
                    entityManager.find(Person.class, person.getPersonId());

            log.info(
                    "Managed: " + entityManager.contains(personFound)
            );

            if (personFound != null) {
                personFound.setName(person.getName());
                personFound.setFamily(person.getFamily());
            }

            log.info(
                    "Before commit: "
                            + personFound.getName()
            );

            entityTransaction.commit();
        } catch (Exception e){
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }

            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void updateWithMerge(Person person){
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            entityManager.merge(person);

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

    public static void delete(Person person) throws Exception {
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Person personDelete = entityManager.find(Person.class, person.getPersonId());

            if (personDelete != null){
                entityManager.remove(personDelete);
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

    public static List<Person> getAllPersonByName(String name){
        EntityManager entityManager = JPA.entityManager();

        try {

             return entityManager.createQuery(
                    "select p from Person p where p.name = :name order by p.name desc",
                    Person.class)
                     .setParameter("name", name)
                     .getResultList();

        } finally {
            entityManager.close();
        }
    }

    public static List<Person> getAllPersonByDepartmentName(String departmentName){
        EntityManager entityManager = JPA.entityManager();

        try {
            return entityManager.createQuery(
                    "select p from Person p join p.department d where d.name = :departmentName",
                    Person.class)
                    .setParameter("departmentName", departmentName)
                    .getResultList();

        } finally {
            entityManager.close();
        }
    }

    public static List<Person> getAllPersonByDepartmentNameWithJoinFetch(String departmentName){
        EntityManager entityManager = JPA.entityManager();

        try {
            return entityManager.createQuery(
                            "select p from Person p join fetch p.department d where d.name = :departmentName",
                            Person.class)
                    .setParameter("departmentName", departmentName)
                    .getResultList();

        } finally {
            entityManager.close();
        }
    }

    public static List<Person> getAllPersonWithPagination(int firstResult, int maxResult){
        EntityManager entityManager = JPA.entityManager();

        try {
            return entityManager.createQuery(
                            "select p from Person p order by p.personId",
                            Person.class)
                    .setFirstResult(firstResult)
                    .setMaxResults(maxResult)
                    .getResultList();

        } finally {
            entityManager.close();
        }
    }

//    public List<Person> findAll() throws Exception {
//
//    }
}
