package ftn.tseo.eEducation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.tseo.eEducation.DTO.DocumentDTO;
import ftn.tseo.eEducation.DTO.EnrollmentDTO;
import ftn.tseo.eEducation.DTO.ExamDTO;
import ftn.tseo.eEducation.DTO.FinancialCardDTO;
import ftn.tseo.eEducation.DTO.PaymentDTO;
import ftn.tseo.eEducation.DTO.PayoutDTO;
import ftn.tseo.eEducation.DTO.StudentDTO;
import ftn.tseo.eEducation.model.Enrollment;
import ftn.tseo.eEducation.model.Student;
import ftn.tseo.eEducation.model.TypeOfFinancing;
import ftn.tseo.eEducation.repository.EnrollmentRepository;
import ftn.tseo.eEducation.service.DocumentService;
import ftn.tseo.eEducation.service.EnrollmentService;
import ftn.tseo.eEducation.service.ExamService;
import ftn.tseo.eEducation.service.FinancialCardService;
import ftn.tseo.eEducation.service.PaymentService;
import ftn.tseo.eEducation.service.PayoutService;
import ftn.tseo.eEducation.service.PreExamObligationService;
import ftn.tseo.eEducation.service.StudentService;
import ftn.tseo.eEducation.service.TypeOfFinancingService;


@RestController
@RequestMapping("api/student")
public class StudentController {

	@Autowired
	 StudentService studentService; 
	
	@Autowired
	 ExamService examService; 
	
	@Autowired
	PayoutService payoutService;
	
	@Autowired
	PaymentService paymentService;

	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	EnrollmentService enrollmentService;
	
	
	@Autowired 
	 PreExamObligationService preexamObligationService; 
	
	@Autowired 
	 FinancialCardService financialCardService;
	@Autowired 
	 TypeOfFinancingService typeOfFinancingService;
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<StudentDTO>> getAllStudents() {
		List<Student> students = studentService.findAll();
		//convert students to DTOs
		List<StudentDTO> studentsDTO = new ArrayList<>();
		for (Student s : students) {
			studentsDTO.add(new StudentDTO(s));
		}
		return new ResponseEntity<>(studentsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id){
		Student student = studentService.findOne(id);
		if(student == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO studentDTO){		
		Student student = new Student();
		student.setCardNumber(studentDTO.getCardNumber());
		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());
		student.setEmail(studentDTO.getEmail());
		student.setUmnc(studentDTO.getUmnc());
		student.setPhoneNumber(studentDTO.getPhoneNumber());
		student.setAccountNumber(student.getAccountNumber());
		student.setModelNumber(studentDTO.getModelNumber());
		student.setStartedCollegeIn(studentDTO.getStartedCollegeIn());
		TypeOfFinancing typeOfFinancing =  typeOfFinancingService.findOne(studentDTO.getTypeOfFinancing().getId());
		student.setTypeOfFinancing(typeOfFinancing);
		student = studentService.save(student);
		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.CREATED);	
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO){
		//a student must exist
		Student student = studentService.findOne(studentDTO.getId()); 
		System.out.println("Student koji je pronadjen"+student);
		if (student == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		student.setId(studentDTO.getId());
		student.setCardNumber(studentDTO.getCardNumber());
		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());
		student.setEmail(studentDTO.getEmail());
		student.setUmnc(studentDTO.getUmnc());
		student.setPhoneNumber(studentDTO.getPhoneNumber());
		student.setAccountNumber(student.getAccountNumber());
		student.setModelNumber(studentDTO.getModelNumber());
		student.setStartedCollegeIn(studentDTO.getStartedCollegeIn());
		TypeOfFinancing typeOfFinancing =  typeOfFinancingService.findOne(studentDTO.getTypeOfFinancing().getId());
		
		student.setTypeOfFinancing(typeOfFinancing);
		
		student = studentService.save(student);
		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.OK);	
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
		Student student = studentService.findOne(id);
		if (student != null){
			studentService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
/*	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<ExamRegistrationDTO> registerExam(@RequestBody ExamRegistrationDTO dto) {
		
		Exam exam = new Exam(); 
		exam = examService.register(dto);
		
		if(exam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new ExamRegistrationDTO(exam), HttpStatus.CREATED);
	}*/
	
//	@GetMapping(value="/{studentId}/exams-current")
//	private List<ExamDTO> getCurrentExamsForStudent(@PathVariable("studentId") Long id) {
//		return examService.getCurrentExams(id);
//	}
	
	@GetMapping(value="/{studentId}/exams")
	private List<ExamDTO> getTakenExams(@PathVariable("studentId") Long id) {
		return examService.findStudentExams(id);
	}
	
	@GetMapping(value="/{studentId}/financial-card")
	private FinancialCardDTO getFinancialCardInfo(@PathVariable("studentId") Long id) {
		return financialCardService.findFinancialCardForStudent(id);
	}
	
	@GetMapping(value="/{id}/financial-payment")
	private List<PaymentDTO> getStudentPayment(@PathVariable("id") Long id) {
		return paymentService.getStudentFinancialCardPayment(id);
	}
	@GetMapping(value="/{studentId}/documents")
	private List<DocumentDTO> getStudentDocuments(@PathVariable("studentId") Long id) {
		return documentService.findDocumentsForStudents(id);
	}
	
	@GetMapping(value="/{studentId}/financial-payout")
	private List<PayoutDTO> getStudentPayout(@PathVariable("studentId") Long id) {
		return payoutService.getStudentFinancialCardPayout(id);
	}
	@GetMapping(value="/{studentId}/enrollments")
	private List<EnrollmentDTO> getStudentEnrollments(@PathVariable("studentId") Long id) {
		return enrollmentService.findEnrollmentForStudent(id);
	}
	
	
	//implementirati getStudentByStudyProgramAndCardNumber --> Elena 
	
}
