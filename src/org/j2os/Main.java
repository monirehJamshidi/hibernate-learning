package org.j2os;

import org.j2os.common.JPA;
import org.j2os.entity.Department;
import org.j2os.entity.Person;
import org.j2os.service.DepartmentService;
import org.j2os.service.PersonService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Tuple;
import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception{

//
//        PersonService.getInstance().save(person);

//        Person person1 = PersonService.findOne();
//        System.out.println(person1.getPersonId() + " - " + person1.getName() + " - " + person1.getFamily());

//        Person personUpdate = Person.builder().personId(1).name("Monireh1").family("Jamshidi1").build();
//        PersonService.update(personUpdate);

//        List<Person> personList = PPerson person = Person.builder().name("Monireh2").family("Jamshidi2").build();ersonService.findAll();
//        personList.stream().forEach(person -> System.out.println(person.getPersonId()+person.getName()+person.getFamily()));

//        List<Person> personList = PersonService.findAllByName("Monireh1");
//        personList.stream().forEach(person -> System.out.println(person.getName() + " - " + person.getFamily()));

//        List<Tuple> tupleList = PersonService.findAllWithSpecialColumn();
//        tupleList.stream().forEach(tuple -> System.out.println(tuple.get("x") + " - " + tuple.get("y")));

//        List<Person> personList = PersonService.findAllWithSql();
//        personList.stream().forEach(person -> System.out.println(person.getName() + " - " + person.getFamily()));

//        Person person = Person.builder().name("Monireh22").family("jamshidi22").personId(2).recordVersion(2).build();
//        PersonService.updateWithMerge(person);

        //----------------------- delete Person -----------------------
//        Person person = Person.builder().personId(12).build();
//        PersonService.delete(person);


//        Person person = Person.builder()
//                .name("Monireh")
//                .family("Jamshidi")
//                .build();
//
//        PersonService.save(person);
//
//        System.out.println(
//                person.getPersonId()
//                        + " - "
//                        + person.getName()
//                        + " - "
//                        + person.getFamily()
//        );


//        Person person = Person.builder()
//                .personId(1)
//                .name("Monireh Updated 2")
//                .family("Jamshidi Updated 2")
//                .build();
//
//        PersonService.update(person);


//        OptimisticLockingTest.testOptimisticLocking();

        //-----------------------------------------------

//        FlushTest.testFlush();

        //-----------------------------------------------
//        Department department = Department.builder()
//                .name("IT")
//                .departmentId(2)
//                .build();
//
////        DepartmentService.save(department);
//
//        Person person = Person.builder()
//                .name("Test")
//                .family("Person")
//                .department(department)
//                .build();
//
//        PersonService.save(person);

        //-----------------------------------------------

//        findDepartment();

        //-----------------------------------------------

//        DepartmentService.findAllDepartments();

//        DepartmentService.findAllDepartmentsWithJoinGFetch();

//        DepartmentService.testCascadePersist();

//        DepartmentService.testOrphanRemoval();

        //----------------------- delete Department -----------------------
//        Department department = Department.builder()
//                .departmentId(7).build();
//        DepartmentService.delete(department);


        //----------------------- saveWithFindDepartment -----------------------
//        Department department = Department.builder()
//                .departmentId(6)
//                .build();
//
//        Person person = Person.builder()
//                .name("Mahdieh")
//                .family("Jamshidi")
//                .department(department)
//                .build();
//
//        PersonService.saveWithFindDepartment(person, department);
        //----------------------- getPersonByName -----------------------
//        List<Person> personList = PersonService.getAllPersonByName("Monireh");
//        personList.stream().forEach(person -> System.out.println(person.getName() + " " + person.getFamily()));


        //----------------------- getAllPersonByDepartmentName -----------------------
//        List<Person> personList = PersonService.getAllPersonByDepartmentName("IT");
//        personList.stream().forEach(person -> System.out.println(person.getName() + " " + person.getFamily()));


        //----------------------- getAllPersonByDepartmentNameWithJoinFetch -----------------------
//        List<Person> personList = PersonService.getAllPersonByDepartmentNameWithJoinFetch("IT");
//        personList.stream().forEach(person -> System.out.println(person.getName() + " " + person.getFamily()));

        //----------------------- getAllPersonByDepartmentNameWithJoinFetch -----------------------
//        List<Person> personList = PersonService.getAllPersonWithPagination(0, 5);
//        personList.stream().forEach(person -> System.out.println(person.getName() + " " + person.getFamily()));

        //getAllDepartment
        List<Department> departments = DepartmentService.getAllDepartment();
        departments.stream().forEach(department -> {
            System.out.println(department.getName() + " " + department.getDepartmentId());
        });



    }

    public static void findDepartment(){
        EntityManager entityManager = JPA.entityManager();

        try {
            Department department =
                    entityManager.find(Department.class,2L);

            System.out.println(
                    "Department: " + department.getName()
            );

//            for (Person person : department.getPersons()){
//                System.out.println(
//                        person.getName()
//                                + " "
//                                + person.getFamily()
//                );
//            }
        } finally {
            entityManager.close();
        }
    }




}
