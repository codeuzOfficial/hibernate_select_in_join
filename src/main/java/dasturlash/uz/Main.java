package dasturlash.uz;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        saveData();
//        simpleJoinSelect();
        selectUsingNonEntityConstructor();
    }

    public static void simpleJoinSelect() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Query<Object[]> query = session.createQuery("select s.id, s.name, s.surname, a.city, a.district from StudentEntity s inner join s.address a");
        List<Object[]> list = query.list();

        for (Object[] obj : list) {
            Integer studentId = (Integer) obj[0];
            String name = (String) obj[1];
            String surname = (String) obj[2];
            String city = (String) obj[3];
            String district = (String) obj[4];
            System.out.println(studentId + " " + name + " " + surname + " " + city + " " + district);
        }

        factory.close();
        session.close();
    }


    public static void selectUsingNonEntityConstructor() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Query<StudentAddressInfoDTO> query = session.createQuery("select new dasturlash.uz.StudentAddressInfoDTO(s.id, s.name, s.surname,a.id, a.city, a.district) " +
                "  from StudentEntity s inner join s.address a");
        List<StudentAddressInfoDTO> list = query.list();

        for (StudentAddressInfoDTO data : list) {
            System.out.println(data.getStudentId() + " " + data.getName() + " " + data.getSurname()
                    + " " + data.getAddressId() + " " + data.getCity() + " " + data.getRegion());
        }

        factory.close();
        session.close();
    }

    public static void saveData() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        // students
        // --------------------- Student 1
        StudentEntity s1 = new StudentEntity();
        s1.setName("Ali");
        s1.setSurname("Aliyev");
        s1.setPhone("915721213");
        s1.setBirthDate(LocalDate.of(2024, 1, 1));
        session.save(s1);

        AddressEntity address1 = new AddressEntity();
        address1.setCity("Toshkent");
        address1.setDistrict("Yunusobod");
        address1.setStudent(s1);
        address1.setCreatedDate(LocalDateTime.now());
        session.save(address1);
        // --------------------- Student 2
        StudentEntity s2 = new StudentEntity();
        s2.setName("Valish");
        s2.setSurname("Valiyev");
        s2.setPhone("123456789");
        s2.setBirthDate(LocalDate.of(2024, 1, 1));
        session.save(s2);

        AddressEntity address2 = new AddressEntity();
        address2.setCity("Xorazm");
        address2.setDistrict("Xiva");
        address2.setStudent(s2);
        address2.setCreatedDate(LocalDateTime.now());
        session.save(address2);
        // --------------------- Student 3
        StudentEntity s3 = new StudentEntity();
        s3.setName("Toshmat");
        s3.setSurname("Toshmatov");
        s3.setPhone("123456789");
        s3.setBirthDate(LocalDate.of(2024, 1, 1));
        session.save(s3);

        AddressEntity address3 = new AddressEntity();
        address3.setCity("Toshkent");
        address3.setDistrict("Sergili");
        address3.setStudent(s3);
        address3.setCreatedDate(LocalDateTime.now());
        session.save(address3);

        // --------------------- Student 4
        StudentEntity s4 = new StudentEntity();
        s4.setName("Eshmat");
        s4.setSurname("Eshmatov");
        s4.setPhone("123456789");
        s4.setBirthDate(LocalDate.of(2024, 1, 1));
        session.save(s4);

        t.commit();

        factory.close();
        session.close();
    }
}