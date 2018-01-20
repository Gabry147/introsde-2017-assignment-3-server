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
		if (person.getActivitypreference() != null) {
			List<Activity> activityPreference = person.getActivitypreference();
			for (Activity activity : activityPreference) {
				if(activity.getId() != null) {
					activity.setId(null);
				}
			}
		}
		Person p = Person.updatePerson(person);
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
		
		databasePerson.getActivitypreference().add(activity);
		databasePerson = Person.updatePerson(databasePerson);
		
	}

	@Override
	public Activity updatePersonPreference(Integer id, Activity activity) {
		Person databasePerson = Person.getPersonById(id);
		checkPersonExists(databasePerson);

		Activity databaseActivity = Activity.getActivityById(activity.getId());
		if (databaseActivity == null) {
			throw new IllegalArgumentException("No activity with given ID");
		}
		
		int indexOf = databasePerson.getActivitypreference().indexOf(databaseActivity);
		databasePerson.getActivitypreference().get( indexOf ).setType(activity.getType());
		databasePerson.getActivitypreference().get( indexOf ).setDescription(activity.getDescription());
		databasePerson.getActivitypreference().get( indexOf ).setName(activity.getName());
		databasePerson.getActivitypreference().get( indexOf ).setPlace(activity.getPlace());
		databasePerson.getActivitypreference().get( indexOf ).setStartdate(activity.getStartdate());
		databasePerson.getActivitypreference().get( indexOf ).setPreference(activity.getPreference());
		databasePerson = Person.updatePerson(databasePerson);
		
		return databasePerson.getActivitypreference().get( indexOf );
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