package ftn.tseo.eEducation.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * @author Elena Krunic 
 *
 */
@Entity
public class ExamObligationStatus {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	public ExamObligationStatus() {
		super();
	}

	public ExamObligationStatus(Long id, String name, String code, Set<PreexamObligation> preexamObligation) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.preexamObligation = preexamObligation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<PreexamObligation> getPreexamObligation() {
		return preexamObligation;
	}

	public void setPreexamObligation(Set<PreexamObligation> preexamObligation) {
		this.preexamObligation = preexamObligation;
	}

	private String code;
	
	//veza sa preexam 
	@OneToMany(mappedBy="examObligationStatus",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private Set<PreexamObligation> preexamObligation = new HashSet<PreexamObligation>();
}