package services;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import lib.json.JSONObject;
import main.FriendModal;
import main.UserDAO;
import main.UserModel;
import main.aboutModel;
import main.newsFeedModel;

@WebService
public class User {

	public static void main ( String args[] ) {
		new User().getUserProfile();
	}

	@WebMethod
	public String getUserProfile () {
		UserDAO userDAO = new UserDAO();

		UserModel userModel = userDAO.getUserProfile(1);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("UserID", userModel.getFirstName());

		return jsonObject.toString();

	}
	@WebMethod
	public String validate (String email, String password) {
		UserDAO userDAO = new UserDAO();

		UserModel userModel = userDAO.validate(email, password);

		JSONObject jsonObject = new JSONObject();

		if(userModel !=null)
		{
			jsonObject.put("status", 200);
			jsonObject.put("first_name", userModel.getFirstName());
			jsonObject.put("userId", userModel.getUserID());
			jsonObject.put("email", userModel.getEmail());
		}
		else
			jsonObject.put("status", 401);

		return jsonObject.toString();

	}
	@WebMethod
	public String getFriendRequests (Integer userId) {
		UserDAO userDAO = new UserDAO();

		UserModel userModel = userDAO.getFriendRequests(userId);

		JSONObject jsonObject = new JSONObject();

		if(userModel !=null)
		{
			jsonObject.put("status", 200);
			jsonObject.put("friends", userModel.getFriends());
			
		}
		else
			jsonObject.put("status", 401);

		return jsonObject.toString();

	}
	
	@WebMethod
	public String approveRequest(String userName, int userId, int senderId) {
		UserDAO userDAO = new UserDAO();

		String result= userDAO.approveRequest(userName, userId, senderId);

		JSONObject jsonObject = new JSONObject();

		if(result == "success")
		{
			jsonObject.put("status", 200);
			jsonObject.put("message", "Request Approved");
			
		}
		else
		{
			jsonObject.put("status", 401);
			jsonObject.put("message", "Request not Processed");
		}

		return jsonObject.toString();

	}
	
	@WebMethod
	public String rejectRequest(int userId, int senderId) {
		UserDAO userDAO = new UserDAO();

		String result= userDAO.rejectRequest(userId, senderId);

		JSONObject jsonObject = new JSONObject();

		if(result == "success")
		{
			jsonObject.put("status", 200);
			jsonObject.put("message", "Request Rejected");
			
		}
		else
		{
			jsonObject.put("status", 401);
			jsonObject.put("message", "Request not Processed");
		}

		return jsonObject.toString();

	}
	
	@WebMethod
	public String getAddFriends(int userId) {
		UserDAO userDAO = new UserDAO();

		UserModel userModel= userDAO.getAddFriends(userId);

		JSONObject jsonObject = new JSONObject();

		if(userModel != null)
		{
			jsonObject.put("status", 200);
			jsonObject.put("friends", userModel.getFriends());
			
		}
		else
		{
			jsonObject.put("status", 401);
			jsonObject.put("message", "Cannot get friends");
		}

		return jsonObject.toString();

	}
	
	@WebMethod
	public String sendFriendRequest(int senderId, int recieverId) {
		UserDAO userDAO = new UserDAO();

		String result= userDAO.sendFriendRequest(senderId, recieverId);

		JSONObject jsonObject = new JSONObject();

		if(result == "success")
		{
			jsonObject.put("status", 200);
			jsonObject.put("message", "Request Sent Successfully");
			
		}
		else
		{
			jsonObject.put("status", 401);
			jsonObject.put("message", "Request not sent");
		}

		return jsonObject.toString();

	}
	
	@WebMethod
	public String getFriends(int userId) {
		UserDAO userDAO = new UserDAO();

		UserModel friends= userDAO.getFriends(userId);

		JSONObject jsonObject = new JSONObject();

		if(friends != null)
		{
			jsonObject.put("status", 200);
			jsonObject.put("friends", friends.getFriends());
			
		}
		else
		{
			jsonObject.put("status", 404);
			jsonObject.put("message", "No Friends found");
		}

		return jsonObject.toString();

	}
	
	@WebMethod
	public String getWorkInfo(int userId) {
		UserDAO userDAO = new UserDAO();

		ArrayList<aboutModel> works= userDAO.getWorkInfo(userId);

		JSONObject jsonObject = new JSONObject();

		if(works != null)
		{
			jsonObject.put("status", 200);
			jsonObject.put("about", works);
			
		}
		else
		{
			jsonObject.put("status", 404);
			jsonObject.put("message", "No work detail found");
		}

		return jsonObject.toString();

	}
	
	@WebMethod
	public String getContactInfo(int userId) {
		UserDAO userDAO = new UserDAO();

		UserModel contact= userDAO.getContactInfo(userId);

		JSONObject jsonObject = new JSONObject();

		if(contact != null)
		{
			jsonObject.put("status", 200);
			jsonObject.put("address", contact.getAddress());
			jsonObject.put("dob", contact.getDate());
			jsonObject.put("email", contact.getEmail());
			jsonObject.put("gender", contact.getGender());
			jsonObject.put("mobile", contact.getMobile());
			jsonObject.put("shows", contact.getShows());
			jsonObject.put("sports", contact.getSports());
			jsonObject.put("shows", contact.getShows());
			
		}
		else
		{
			jsonObject.put("status", 404);
			jsonObject.put("message", "No contact found");
		}

		return jsonObject.toString();

	}
	
	@WebMethod
	public String signup(String firstName, String lastName, String email, String password, String gender, String dob ) {
		UserDAO userDAO = new UserDAO();

		String result= userDAO.signup(firstName , lastName, email, password, gender, dob);

		JSONObject jsonObject = new JSONObject();

		if(result == "success")
		{
			jsonObject.put("status", 200);
			
		}
		else
		{
			jsonObject.put("status", 404);
			jsonObject.put("message", "Failed during Sign up");
		}

		return jsonObject.toString();

	}
	//userId : request.session.userid, userName : request.session.name
	@WebMethod
	public String getnewsFeedData(int userId, String userName ) {
		UserDAO userDAO = new UserDAO();

		ArrayList<newsFeedModel> news= userDAO.getnewsFeedData(userId , userName);

		JSONObject jsonObject = new JSONObject();

		if(news != null)
		{
			jsonObject.put("status", 200);
			
		}
		else
		{
			jsonObject.put("status", 404);
			jsonObject.put("news", news);
		}

		return jsonObject.toString();

	}

}
