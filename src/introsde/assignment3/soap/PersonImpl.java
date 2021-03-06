package introsde.assignment3.soap;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import introsde.assignment3.soap.PersonService;
import introsde.assignment3.entities.Activity;
import introsde.assignment3.entities.ActivityType;
import introsde.assignment3.entities.Person;
//Service Implementation
@WebService(endpointInterface = "introsde.assignment3.soap.PersonService")
public class PersonImpl implements PersonService{
    @Override
    public String getHelloWorldAsString(String name) {
        return "Hello World JAX-WS " + name;
    }
    
    private void checkPersonExists(Person databasePerson) {
		if (databasePerson == null) {
			throw new IllegalArgumentException("Requested person not found");
		}
	}

	@Override
	public List<Person> readPersonList() {
		List<Person> personList = Person.getAllPersons();		// GET all the person in DB and create the people wrapper
		return personList;
	}

	@Override
	public Person readPerson(Integer id) {
		Person databasePerson = Person.getPersonById(id);
		checkPersonExists(databasePerson);
		return databasePerson;
	}

	@Override
	public Person updatePerson(Person person) {
		Person databasePerson = Person.getPersonById(person.getId());
		checkPersonExists(databasePerson);
		
		if(person.getFirstname() != null) databasePerson.setFirstname(person.getFirstname());
		if(person.getLastname() != null) databasePerson.setLastname(person.getLastname());
		if(person.getBirthdate() != null) databasePerson.setBirthdate(person.getBirthdate());
		databasePerson = Person.updatePerson(databasePerson);
		return databasePerson;
	}
	
	@Override
	public Person createPerson(Person person) {
		if (person.getId() != null) {
			person.setId(null);
		}
		Person p = Person.savePerson(person);
		return p;
	}

	@Override
	public void deletePerson(Person person) {
		Person databasePerson = Person.getPersonById(person.getId());
		checkPersonExists(databasePerson);
		Person.removePerson(person);
	}

	@Override
	public List<Activity> readPersonPreferences(Integer id, String type) {
		Person databasePerson = Person.getPersonById(id);
		checkPersonExists(databasePerson);
		
		List<Activity> activities = databasePerson.getActivitypreference();	
		List<Activity> filtered = new ArrayList<>();
		for(Activity a : activities) {
			if(a.getType().getType().equals(type)) {
				filtered.add(a);
			}
		}
		return filtered;
	}

	@Override
	public List<Activity> readPreferences() {
		return Activity.getAllActivities();
	}

	@Override
	public Activity readPersonPreference(Integer id, Integer activityId) {
		Person databasePerson = Person.getPersonById(id);
		checkPersonExists(databasePerson);
		
		List<Activity> activities = databasePerson.getActivitypreference();
		for(Activity a : activities) {
			if(a.getId().equals(activityId)) {
				return a;
			}
		}
		return null;
	}

	@Override
	public void savePersonPreference(Integer id, Activity activity) {
		Person databasePerson = Person.getPersonById(id);
		checkPersonExists(databasePerson);
		if(activity.getId() != null) {
			activity.setId(null);
		}
		if(databasePerson.getActivitypreference()==null) {
			databasePerson.setActivitypreference(new ArrayList<Activity>());
		}
		databasePerson.getActivitypreference().add(activity);
		databasePerson = Person.updatePerson(databasePerson);
		
	}

	@Override
	public Activity updatePersonPreference(Integer id, Activity activity) {
		Person databasePerson = Person.getPersonById(id);
		ActivityType activityType = ActivityType.getById(activity.getType().getType());
		if (activityType == null) {
			throw new IllegalArgumentException("Non existant activity type");
		}
		Activity databaseActivity = Activity.getActivityById(activity.getId());
		if (databaseActivity == null) {
			throw new IllegalArgumentException("No activity with given ID");
		}
		
		int indexOf = databasePerson.getActivitypreference().indexOf(databaseActivity);
		System.out.println("indexof: " + indexOf);
		System.out.println("size: " + databasePerson.getActivitypreference().size());
		databaseActivity.setType(activityType);
		databaseActivity.setDescription(activity.getDescription());
		databaseActivity.setName(activity.getName());
		databaseActivity.setPlace(activity.getPlace());
		databaseActivity.setStartdate(activity.getStartdate());
		databaseActivity.setPreference(activity.getPreference());
		databasePerson = Person.updatePerson(databasePerson);
		
		return databaseActivity;
	}

	@Override
	public Activity evaluatePersonPreference(Integer id, Activity activity, Integer value) {
		if( value<0 || value>10 ) {
			throw new IllegalArgumentException("Preference rating should be between 0 and 10");
		}
		activity.setPreference(value);
		return updatePersonPreference(id, activity);
	}

	@Override
	public List<Activity> getBestPersonPreferences(Integer id) {
		Person databasePerson = Person.getPersonById(id);
		checkPersonExists(databasePerson);
		
		List<Activity> activities = databasePerson.getActivitypreference();	
		List<Activity> filtered = new ArrayList<>();
		for(Activity a : activities) {
			if(a.getPreference()>8) {
				filtered.add(a);
			}
		}
		return filtered;
	}
}