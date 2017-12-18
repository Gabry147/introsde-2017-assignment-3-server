package introsde.assignment3.soap;

import java.util.ArrayList;
import java.util.List;

import introsde.assignment3.entities.Activity;
import introsde.assignment3.entities.Person;

public class PersonActivitiesImpl implements PersonActivities{

	@Override
	public List<Person> readPersonList() {
		return Person.getAllPeople();
	}

	@Override
	public Person readPerson(int id) {
		return Person.getPersonById(id);
	}

	@Override
	public Person updatePerson(Person p) {
		return Person.updatePerson(p);
	}

	@Override
	public Person createPerson(Person p) {
		return Person.savePerson(p);
	}

	@Override
	public void deletePerson(int id) {
		Person.removePerson(Person.getPersonById(id));
	}

	@Override
	public List<Activity> readPersonPreferences(int id, String activity_type) {
		List<Activity> activities = Person.getPersonById(id).getActivitypreference();	
		List<Activity> result = new ArrayList<>();
		for(Activity activity : activities) {
			//filter for type
			if(activity.getType().getType().equals(activity_type)) {
				result.add(activity);
			}
		}		
		return result;
	}

	@Override
	public List<Activity> readPreferences() {
		return Activity.getAllActivities();
	}

	@Override
	public Activity readPersonPreferences(int personId, int activityId) {
		Activity activity = null;
		List<Activity> activities = Person.getPersonById(personId).getActivitypreference();
		for(Activity a: activities) {
			if(a.getId() == activityId) {
				activity = a;
			}
		}
		if(activity == null) return null;
		return activity;
	}

	@Override
	public Activity savePersonPreferences(int id, Activity activity) {
		Activity savedActivity = Activity.saveActivity(activity);
		Person.getPersonById(id).saveActivity(savedActivity);
		return savedActivity;
	}

	@Override
	public Activity updatePersonPreferences(int id, Activity activity) {
		return Activity.updateActivity(activity);
	}

	@Override
	public Activity evaluatePersonPreferences(int personId, int activityId, int value) {
		Activity activity = null;
		List<Activity> activities = Person.getPersonById(personId).getActivitypreference();
		for(Activity a: activities) {
			if(a.getId() == activityId) {
				activity = a;
			}
		}
		if(activity == null) return null;
		activity.setPreference(value);
		return Activity.updateActivity(activity);
	}

	@Override
	public List<Activity> getBestPersonPreferences(int id) {
		List<Activity> allActivity = Person.getPersonById(id).getActivitypreference();
		List<Activity> result = new ArrayList<>();
		for(Activity a: allActivity) {
			if(a.getPreference() > 8) {
				result.add(a);
			}
		}
		return result;
	}
}