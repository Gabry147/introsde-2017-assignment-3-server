package introsde.assignment3.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import introsde.assignment3.entities.Activity;
import introsde.assignment3.entities.Person;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface PersonActivities {

	//#1
	@WebMethod(operationName="readPersonList")
    @WebResult(name="people") 
    public List<Person> readPersonList();
	
	//#2
	@WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public List<Person> readPerson(@WebParam(name="personId") int id);
	
	//#3
	@WebMethod(operationName="updatePerson")
    @WebResult(name="person") 
    public List<Person> updatePerson(@WebParam(name="person") Person p);
	
	//#4
	@WebMethod(operationName="createPerson")
    @WebResult(name="person") 
    public List<Person> createPerson(@WebParam(name="person") Person p);
	
	//#5
	@WebMethod(operationName="deletePerson")
    @WebResult(name="person") 
    public List<Person> deletePerson(@WebParam(name="personId") int id);
	
	//#6
	@WebMethod(operationName="readPersonPreferences")
    @WebResult(name="activites") 
    public List<Activity> readPersonPreferences(@WebParam(name="personId") int id, @WebParam(name="activity_type") String activity_type);

	//#7
	@WebMethod(operationName="readPreferences")
    @WebResult(name="activites") 
    public List<Activity> readPreferences();

	//#8
	@WebMethod(operationName="readPersonPreferences")
    @WebResult(name="activity") 
	public Activity readPersonPreferences(@WebParam(name="personId") int personId, @WebParam(name="activityId") int activityId);	
	
	//#9
	@WebMethod(operationName="savePersonPreferences")
    @WebResult(name="activity") 
	public Activity savePersonPreferences(@WebParam(name="personId") int id, @WebParam(name="activity") Activity activity);
	
	//#10
	@WebMethod(operationName="updatePersonPreferences")
    @WebResult(name="activity") 
	public Activity updatePersonPreferences(@WebParam(name="personId") int id, @WebParam(name="activity") Activity activity);
	
	//#11
	@WebMethod(operationName="evaluatePersonPreferences")
    @WebResult(name="activity") 
	public Activity evaluatePersonPreferences(@WebParam(name="personId") int id, 
			@WebParam(name="activity") Activity activity, 
			@WebParam(name="value") int value);	
	
	//#12
	@WebMethod(operationName="getBestPersonPreferences")
    @WebResult(name="activity") 
	public List<Activity> getBestPersonPreferences(@WebParam(name="personId") int id);

}
