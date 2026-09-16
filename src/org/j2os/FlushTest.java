package org.j2os;

import org.j2os.common.JPA;
import org.j2os.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class FlushTest {

    public static void testFlush(){
        EntityManager entityManager = JPA.entityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Person person = entityManager.find(Person.class, 1L);

            System.out.println(
                    "Before change: " + person.getName()
            );

            person.setName("Before Flush");

            System.out.println(
                    "After change: " + person.getName()
            );

            entityManager.flush();

            System.out.println("Flush completed.");

            transaction.commit();

            System.out.println("Commit completed.");
        } catch (Exception e) {
            if (transaction.isActive()){
                transaction.rollback();
            }

            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
