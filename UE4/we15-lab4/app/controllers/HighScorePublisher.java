package controllers;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;

import play.Logger;
import twitter.TwitterClient;
import twitter.TwitterStatusMessage;
import at.ac.tuwien.big.we.highscore.Failure;
import at.ac.tuwien.big.we.highscore.PublishHighScoreEndpoint;
import at.ac.tuwien.big.we.highscore.PublishHighScoreService;
import at.ac.tuwien.big.we.highscore.data.GenderType;
import at.ac.tuwien.big.we.highscore.data.HighScoreRequestType;
import at.ac.tuwien.big.we.highscore.data.UserDataType;
import at.ac.tuwien.big.we.highscore.data.UserType;
import models.JeopardyUser;
import models.JeopardyUser.Gender;
import models.Player;

public class HighScorePublisher implements Runnable{
	UserType winner;
	UserType loser;

	public HighScorePublisher(Player winner, Player loser) {
		super();

		this.winner = convertToUserType(winner);
		this.loser = convertToUserType(loser);
	}
	
	private UserType convertToUserType(Player player){
		UserType userType = new UserType();
		GregorianCalendar c = new GregorianCalendar();
		XMLGregorianCalendar date2 = null;

		try{
			c.setTime(player.getUser().getBirthDate());
		}catch(NullPointerException n){
			c.setTime(new Date(0));

		}
		
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userType.setBirthDate(date2);
		String firstName = player.getUser().getFirstName();
		if(firstName.equals("")||firstName == null){
			userType.setFirstName("N A N");
		}else{
			userType.setFirstName(firstName);
		}
		
		String lastName = player.getUser().getLastName();
		if(lastName.equals("")||lastName == null){
			userType.setLastName("4 0 4");
		}else{
			userType.setLastName(lastName);
		}
		
		//userType.setGender(GenderType.fromValue(player.getUser().getGender().name()));
		userType.setGender(GenderType.FEMALE);
		userType.setPoints(player.getProfit());
		userType.setPassword("");
		
		/*
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date(0));
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userType.setBirthDate(date2);
		userType.setFirstName("John");
		userType.setGender(GenderType.FEMALE);
		userType.setLastName("John");
		userType.setPassword("");
		userType.setPoints(0);
		*/
		return userType;
	}

	@Override
	public void run() {
		try {

			PublishHighScoreService service = new PublishHighScoreService();
			PublishHighScoreEndpoint endpoint = service.getPort(PublishHighScoreEndpoint.class);
			UserDataType j = new UserDataType();
			
			j.setWinner(winner);
			j.setLoser(loser);
			
			HighScoreRequestType request = new HighScoreRequestType();
			request.setUserKey("3ke93-gue34-dkeu9");
			request.setUserData(j);
			
			String uuid = endpoint.publishHighScore(request);
			Logger.info("Highscore publishing successfull, UUID: "+uuid);
			TwitterClient.tweet(winner.getFirstName(), uuid);

		} catch (Failure e) {
			Logger.info("Failed to publishHighScore");
		} catch (WebServiceException e) {
			Logger.info("Failed to publishHighScore");
		}
	}
}
