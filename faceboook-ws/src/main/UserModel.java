package main;

import java.util.ArrayList;

public class UserModel {

	private Integer		userID;
	private String		userName;
	private String		firstName;
	private String		lastName;
	private String		date;
	private String		gender;
	private ArrayList<FriendModal>	friends;
	private String email;
	private Long mobile;
	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile2) {
		this.mobile = mobile2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShows() {
		return shows;
	}

	public void setShows(String shows) {
		this.shows = shows;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public String getSports() {
		return sports;
	}

	public void setSports(String sports) {
		this.sports = sports;
	}

	private String address;
	private String shows;
	private String music;
	private String sports;
	

	/**
	 * @return the userID
	 */
	public Integer getUserID () {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID ( Integer userID ) {
		this.userID = userID;
	}

	/**
	 * @return the userName
	 */
	public String getUserName () {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName ( String userName ) {
		this.userName = userName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName () {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName ( String firstName ) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName () {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName ( String lastName ) {
		this.lastName = lastName;
	}

	/**
	 * @return the date
	 */
	public String getDate () {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate ( String date ) {
		this.date = date;
	}

	/**
	 * @return the gender
	 */
	public String getGender () {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender ( String gender ) {
		this.gender = gender;
	}

	/**
	 * @return the friends
	 */
	public ArrayList<FriendModal> getFriends () {
		return friends;
	}

	/**
	 * @param friends
	 *            the friends to set
	 */
	public void setFriends ( ArrayList<FriendModal> friends ) {
		this.friends = friends;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
