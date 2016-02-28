package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.jdt.internal.compiler.parser.RecoveredStatement;

public class UserDAO {

	public UserDAO () {
		// TODO Auto-generated constructor stub
	}

	public UserModel getUserProfile ( Integer userID ) {
		System.out.println("Inside getUserProfile");

		UserModel userModel = new UserModel();

		DBConnection connection = new DBConnection();

		String sql;
		sql = "SELECT * FROM users";
		ResultSet rs;
		try {
			rs = connection.getConnection().createStatement().executeQuery(sql);
			while ( rs.next() ) {
				// Retrieve by column name
				int id = rs.getInt("UserID");
				String username = rs.getString("UserName");
				String firstname = rs.getString("FirstName");
				String lastname = rs.getString("LastName");
				String date = rs.getString("Date");
				String gender = rs.getString("Gender");

				userModel.setUserID(userID);
				userModel.setUserName(username);
				userModel.setFirstName(firstname);
				userModel.setLastName(lastname);
				userModel.setDate(date);
				userModel.setGender(gender);

				// Display values
				System.out.println("ID: " + id);
				System.out.println("UserName: " + username);
				System.out.println("FirstName: " + firstname);
				System.out.println("LastName: " + lastname);
				System.out.println("Date: " + date);
				System.out.println("Gender: " + gender);
			}
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		connection.close();
		return userModel;
	}
	
	public UserModel validate (String email, String password) {
		System.out.println("Inside getUserProfile");

		UserModel userModel = new UserModel();

		DBConnection connection = new DBConnection();

		String sql;
		sql = "SELECT users.user_id, first_name, email FROM users, login where users.user_id = login.user_id and email = '"+email+"' and password = '"+password+"'";
		System.out.println(sql);
		ResultSet rs;
		try {
			rs = connection.getConnection().createStatement().executeQuery(sql);
			
			while ( rs.next() ) {
				// Retrieve by column name
				int id = rs.getInt("user_id");
				String emailId = rs.getString("email");
				String firstname = rs.getString("first_name");

				userModel.setUserID(id);
				userModel.setEmail(emailId);
				userModel.setFirstName(firstname);
				

				// Display values
				System.out.println("ID: " + id);
				System.out.println("FirstName: " + firstname);
				
			}
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		connection.close();
		return userModel;
	}
	
	public UserModel getFriendRequests(Integer userId) {
		ArrayList<FriendModal> friends = new ArrayList<FriendModal>();
		System.out.println("Inside getFriendRequests");

		UserModel userModel = new UserModel();

		DBConnection connection = new DBConnection();

		String sql;
		sql = "select first_name,senderId from friend_requests f,users u where f.senderId = u.user_id and receiverId ="+userId+" and status = 'requested';";
		System.out.println(sql);
		ResultSet rs;
		try {
			rs = connection.getConnection().createStatement().executeQuery(sql);
			
			while ( rs.next() ) {
				FriendModal	friend = new FriendModal();
				// Retrieve by column name
				int senderId = rs.getInt("senderId");
				String first_name = rs.getString("first_name");
				friend.setFirstName(first_name);
				friend.setSenderId(senderId);
				friends.add(friend);
				//System.out.println("FirstName: " + firstname);
			}
			
			userModel.setFriends(friends);
			
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		connection.close();
		return userModel;
	}
	
	public String approveRequest(String userName, int userId, int senderId) {
		System.out.println("Inside getFriendRequests");

		UserModel userModel = new UserModel();
		DBConnection connection = new DBConnection();

		String friends_list;
		String friend_requests;
		friends_list = "insert into friends_list values ("+userId+","+senderId+"), ("+senderId+","+userId+");";
		friend_requests = "update friend_requests set status = 'approved' where senderId ="+senderId+" and receiverId = "+userId;
		System.out.println(friends_list);
		try {
			Statement statement = connection.getConnection().createStatement();
			statement.addBatch(friends_list);
			statement.addBatch(friend_requests);
			int count[] = statement.executeBatch();
			
			if(count.length > 0)
				return "success";
			else
				return "failed";
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		connection.close();
		return "failed";
	}
	
	public String rejectRequest(int userId, int senderId) {
		System.out.println("Inside getFriendRequests");
		UserModel userModel = new UserModel();
		DBConnection connection = new DBConnection();

		String friend_request;
		friend_request = "delete from friend_requests where senderId ="+senderId+" and receiverId = "+userId+";";
		try {
			connection.getConnection().createStatement().executeUpdate(friend_request);
			
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		connection.close();
		return "success";
	}
	
	public UserModel getAddFriends(int userId) {
		ArrayList<FriendModal> friends = new ArrayList<FriendModal>();
		System.out.println("Inside getFriendRequests");
		UserModel userModel = new UserModel();
		DBConnection connection = new DBConnection();

		String friend_request;
		friend_request = "select user_id,first_name from users where user_id !="+userId+" and user_id not in (select friendID from friends_list where userid ="+userId+") and user_id not in (select senderid from friend_requests where receiverid ="+userId+"  ) and user_id not in ( select receiverid from friend_requests where senderid ="+userId+" );";
		System.out.println(friend_request);
		ResultSet rs;
		try {
			rs = connection.getConnection().createStatement().executeQuery(friend_request);
			while ( rs.next() ) {
				// Retrieve by column name
				int id = rs.getInt("user_id");
				String firstname = rs.getString("first_name");

				FriendModal friend = new FriendModal();
				friend.setSenderId(id);
				friend.setFirstName(firstname);
				// Display values
				System.out.println("ID: " + id);
				System.out.println("FirstName: " + firstname);
				friends.add(friend);
			}
			userModel.setFriends(friends);
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		connection.close();
		return userModel;
	}
	
	public String sendFriendRequest(int senderId, int recieverId) {
		DBConnection connection = new DBConnection();
		String friend_request;
		friend_request = "insert into friend_requests (senderId,receiverId) values ("+senderId+","+recieverId+");";
		System.out.println(friend_request);
		int result;
		try {
			result = connection.getConnection().createStatement().executeUpdate(friend_request);
			
				if(result > 0)
					return "success";
			
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		connection.close();
		return "failure";
	}
	
	public UserModel getFriends(int userId) {
		DBConnection connection = new DBConnection();
		ArrayList<FriendModal> friends = new ArrayList<FriendModal>();
		UserModel userModel = new UserModel();
		String get_friends;
		get_friends = "select first_name from users where user_id in (select friendId from friends_list where userid ="+userId+")";
		System.out.println(get_friends);
		ResultSet rs;
		try {
			rs = connection.getConnection().createStatement().executeQuery(get_friends);
			while ( rs.next() ) {
				String firstname = rs.getString("first_name");
				FriendModal friend = new FriendModal();
				friend.setFirstName(firstname);
				friends.add(friend);
			}
			
			userModel.setFriends(friends);
			
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		connection.close();
		return userModel;
	}
	
	public ArrayList<aboutModel> getWorkInfo(int id) {
		DBConnection connection = new DBConnection();
		ArrayList<aboutModel> works = new ArrayList<aboutModel>();
		UserModel userModel = new UserModel();
		String get_work;
		get_work = "select * from about_work where userId = "+id+" order by year desc";
		System.out.println(get_work);
		ResultSet rs;
		try {
			rs = connection.getConnection().createStatement().executeQuery(get_work);
			while ( rs.next() ) {
				String company = rs.getString("company");
				String position = rs.getString("position");
				String city = rs.getString("city");
				int year = rs.getInt("year");
				aboutModel about = new aboutModel();
				about.setCompany(company);
				about.setPosition(position);
				about.setCity(city);
				about.setYear(year);
				works.add(about);
			}
			
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		connection.close();
		return works;
	}
	
	public UserModel getContactInfo(int userId) {
		DBConnection connection = new DBConnection();
		ArrayList<aboutModel> works = new ArrayList<aboutModel>();
		UserModel userModel = new UserModel();
		String get_contact;
		get_contact = "select mobile,address,dob,gender,email,shows,music,sports from users u,login l where u.user_id =l.user_id and u.user_id = "+userId;
		System.out.println(get_contact);
		ResultSet rs;
		try {
			rs = connection.getConnection().createStatement().executeQuery(get_contact);
			while ( rs.next() ) {
				
				String address = rs.getString("address");
				Long mobile = rs.getLong("mobile");
				String city = rs.getString("address");
				String dob = rs.getString("dob");
				String gender = rs.getString("gender");
				String email = rs.getString("email");
				String shows = rs.getString("shows");
				String music = rs.getString("music");
				String sports = rs.getString("sports");
				userModel.setAddress(address);
				userModel.setDate(dob);
				userModel.setEmail(email);
				userModel.setGender(gender);
				userModel.setMobile(mobile);
				userModel.setMusic(music);
				userModel.setShows(shows);
				userModel.setSports(sports);
			}
			
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		connection.close();
		return userModel;
	}
	public String signup(String firstName, String lastName, String email, String password, String gender, String dob) {
		DBConnection connection = new DBConnection();
		String signup_request;
		signup_request = "insert into users (first_name,last_name, dob, gender) values ('"+firstName+"','"+lastName+"','"+dob+"','"+gender+"');";
		System.out.println(signup_request);
		int result;
		try {
			result = connection.getConnection().createStatement().executeUpdate(signup_request);
			
				if(result > 0)
					return "success";
			
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		connection.close();
		return "failure";
	}
	
	public ArrayList<newsFeedModel> getnewsFeedData(int userId, String userName) {
		DBConnection connection = new DBConnection();
		ArrayList<newsFeedModel> newsFeed = new ArrayList<newsFeedModel>();
		String newsFeed_request;
		newsFeed_request = "select * from newsFeed where userName ='"+userName+"' or userName in ( select first_name from users,friends_list where users.user_id = friends_list.friendId and friends_list.userId ="+userId+") order by timestamp desc";
		ResultSet rs;
		try {
			rs= connection.getConnection().createStatement().executeQuery(newsFeed_request);
			while ( rs.next() ) {
				// Retrieve by column name
				String username = rs.getString("userName");
				String groupName = rs.getString("groupName");
				String activity_type = rs.getString("activity_type");
				String friendName = rs.getString("friendName");
				String status = rs.getString("status");
				String lifeEventType = rs.getString("lifeEventType");
				String lifeMessage = rs.getString("lifeMessage");

				newsFeedModel news = new newsFeedModel();
				
				news.setActivity_type(activity_type);
				news.setFriendName(friendName);
				news.setGroupName(groupName);
				news.setLifeEventType(lifeEventType);
				news.setLifeMessage(lifeMessage);
				news.setStatus(status);
				news.setUserName(username);
				
				newsFeed.add(news);
			}
				
			
		} catch ( SQLException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		connection.close();
		return newsFeed;
	}
}
