package org.j2os;

import org.j2os.common.JPA;
import org.j2os.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class OptimisticLockingTest {

    public static void testOptimisticLocking(){
        EntityManager em1 = JPA.entityManager();
        EntityManager em2 = JPA.entityManager();

        EntityTransaction ent1 = em1.getTransaction();
        EntityTransaction ent2 = em2.getTransaction();

        try {
            //Transaction 1
            ent1.begin();

            Person person1 = em1.find(Person.class, 1L);

            System.out.println(
                    "Transaction 1 - Version: "
                            + person1.getRecordVersion()
            );

            // Transaction 2
            ent2.begin();

            Person person2 = em2.find(Person.class, 1L);

            System.out.println(
                    "Transaction 2 - Version: "
                            + person2.getRecordVersion()
            );

            // Transaction 1 updates the entity
            person1.setName("Transaction 2");

            ent1.commit();

            System.out.println("Transaction 1 committed.");

            // Transaction 2 tries to update the old version
            person2.setName("Transaction 3");

            ent2.commit();

            System.out.println("Transaction 2 committed.");


        } catch (Exception e){
            System.out.println(
                    "Exception: " + e.getClass().getName()
            );

            Throwable cause = e;
            while (cause != null) {
                System.out.println(
                        "cause: " + cause.getClass().getName()
                                + " - " +cause.getMessage()
                );

                cause = cause.getCause();
            }

            if (ent1.isActive()) {
                ent1.rollback();
            }

            if (ent2.isActive()) {
                ent2.rollback();
            }
        } finally {
            em1.close();
            em2.close();
        }
    }
}
