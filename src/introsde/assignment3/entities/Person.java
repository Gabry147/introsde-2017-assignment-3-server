package introsde.assignment3.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.assignment3.dao.PersonActivitiesDao;

@Entity
@Table(name="Person")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @XmlAttribute(name = "id", required = true)
    private Integer id;								// ID is automatically generated
    
	@XmlElement(required = true)
    private String firstname;
    @XmlElement(required = true)
    private String lastname;
    @XmlElement(required = true)
    private Date birthdate;   
    @XmlElement(required = false)
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval = true) //orphanRemoval should eliminate Activity immediatly after person deletion
    private List<Activity> activitypreference;		// ActivityPreference is a list One:Many fetched as eagerly and cascaded as ALL (no merge)

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public List<Activity> getActivitypreference() {
		return activitypreference;
	}

	public void setActivitypreference(List<Activity> activitypreference) {
		this.activitypreference = activitypreference;
	}
	
	// DAO methods
	public static Person savePerson(Person p) {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		PersonActivitiesDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Person updatePerson(Person p) {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();		
		if(p.getId() == null) {
			throw new IllegalArgumentException("Person without an ID");
		}		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
		PersonActivitiesDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removePerson(Person p) {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    PersonActivitiesDao.instance.closeConnections(em);
	}
	
	public static Person getPersonById(int personId) {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();
		Person p = em.find(Person.class, personId);
		PersonActivitiesDao.instance.closeConnections(em);
		return p;
	}
	
	// Get all the Person from the DB using the Named Query
	public static List<Person> getAllPersons() {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();
		List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
		PersonActivitiesDao.instance.closeConnections(em);
		return list;
	}
}
