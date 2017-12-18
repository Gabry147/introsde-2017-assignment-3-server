package introsde.assignment3.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.assignment3.dao.PersonActivitiesDao;


@Entity
@Table(name="Activity")
@NamedQuery(name="Activity.findAll", query="SELECT a FROM Activity a")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Activity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
    @XmlAttribute(name = "id")
    protected int id;

    protected String name;
    
    protected String description;

    protected String place;

    @XmlElement(name = "type")
    @ManyToOne(fetch=FetchType.EAGER)
    protected ActivityType type;
       
    protected Date startdate;
    
    protected int preference;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public ActivityType getType() {
		return type;
	}
	public void setType(ActivityType type) {
		this.type = type;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public int getPreference() {
		return preference;
	}
	public void setPreference(int preference) {
		this.preference = preference;
	}
	
	public Activity() {
		//needed for XML
	}
	
	public static List<Activity> getAllActivities() {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();
	    List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
	    PersonActivitiesDao.instance.closeConnections(em);
	    return list;
	}

	//db access functions
	public static Activity saveActivity(Activity a) {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(a);
		tx.commit();
		PersonActivitiesDao.instance.closeConnections(em);
	    return a;
	}
	
	public static Activity updateActivity(Activity a) {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		a=em.merge(a);
		tx.commit();
		PersonActivitiesDao.instance.closeConnections(em);
	    return a;
	}
	
	public static void removeActivity(Activity a) {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    a=em.merge(a);
	    em.remove(a);
	    tx.commit();
	    PersonActivitiesDao.instance.closeConnections(em);
	}
	
	public static Activity getActivityById(int activityId) {
		EntityManager em = PersonActivitiesDao.instance.createEntityManager();
		Activity a = em.find(Activity.class, activityId);
		PersonActivitiesDao.instance.closeConnections(em);
		return a;
	}

}
