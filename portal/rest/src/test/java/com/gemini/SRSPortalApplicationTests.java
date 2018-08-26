package com.gemini;

import com.gemini.commons.beans.types.Gender;
import com.gemini.commons.database.jpa.entities.PreEnrollmentRequestEntity;
import com.gemini.commons.database.jpa.entities.StudentEntity;
import com.gemini.commons.database.jpa.respository.PreEnrollmentRepository;
import com.gemini.commons.database.jpa.respository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SRSPortalApplicationTests {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	PreEnrollmentRepository preEnrollmentRepository;

	@Test
	public void contextLoads() {
	}


	@Test
	public void testPreEnrollmentCreation() {

		PreEnrollmentRequestEntity entity = new PreEnrollmentRequestEntity();
		StudentEntity student = new StudentEntity();
		student.setDateOfBirth(new Date());
		student.setSsn("000000099");
		student.setFirstName("Pruebas");
		student.setLastName("Apellidos");
		student.setGender(Gender.F);
		entity.setStudent(student);
		entity.setGradeLevel("99");
		entity.setSchoolYear(2019L);

		student = studentRepository.save(student);
		entity = preEnrollmentRepository.save(entity);

		int n = 10000;
		do{
			entity = preEnrollmentRepository.findOne(entity.getId());
			entity = preEnrollmentRepository.save(entity);
			n--;
		}while (n > 0);
	}

	public static void main(String[] args) {
//		$2a$10$EjQuf.9E0vicXOLU8cbrm.Ih85fEMI1ApmS7SL16GOdxmUiuM05Gi
		System.out.println(new BCryptPasswordEncoder(10).encode("matricula2019"));
	}


}
